package sprintaiva;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class EnquiryChat extends CommonMethods {

//	int m = 1; 
//	int n = 2;
	
	CommonMethods cm = new CommonMethods();
	
	String respXpath1 = ".//*[@id='chatWindow_span']/table/tbody/tr[";
	String respXpath2 = "]/td";
	
	
	By yesNoFormDisp = By.xpath(".//*[contains(@class,'form-yesno')]");
	By deviceCodeHeader = By.xpath(".//*[@id='header']");
	By deviceCodeText = By.xpath(".//*[contains(@name,'Device_code')]");
	By deviceContinue = By.xpath(".//*[contains(@type,'submit')]"); //and button[text(),'Continue')]
	By phoneNumber = By.xpath(".//*[contains(@name,'Phone_Number')]");
	By tcForms = By.xpath(".//*[@id='ap-form']/form");
	By formAddress = By.xpath(".//*[contains(@name,'Address')]");
	By formApt = By.xpath(".//*[contains(@name,'Apt')]");
	By formCity = By.xpath(".//*[contains(@name,'City')]");
	By formState = By.xpath(".//*[contains(@name,'State')]");
	By formZip = By.xpath(".//*[contains(@name,'Zip_Code')]");
	

	public void noButtonAction(WebDriver driver, String SheetName, String testcaseNum, Workbook wb,
			WritableWorkbook wwbCop, WritableSheet Wsheet) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 60);
		/*
		 * File DataManager = new
		 * File("C:\\Users\\ko706953\\Selenium\\Book2.xls");
		 * 
		 * Workbook wb = Workbook.getWorkbook(DataManager); WritableWorkbook
		 * wwbCop = Workbook.createWorkbook(new
		 * File("C:\\Users\\ko706953\\Selenium\\Book1.xls"), wb); WritableSheet
		 * Wsheet = wwbCop.getSheet(SheetName);
		 */

		WritableCell cell;

		int rows = wb.getSheet(SheetName).getRows();
		int cols = wb.getSheet(SheetName).getColumns();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		

		for (int i = 1; i < rows; i++) {
			System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet(SheetName).getCell(0, i).getContents());
			rowvalues1.add(wb.getSheet(SheetName).getCell(1, i).getContents());
		}

		/*
		 * for(int i=0;i<rowvalues.size();i++){ System.out.println(
		 * "rowvalues are" + rowvalues.get(i)); } for(int
		 * i=0;i<rowvalues1.size();i++){ System.out.println("rowvalues1 are" +
		 * rowvalues1.get(i)); }
		 */
		int m = 3;
		int n = 2;
		for (int k = 0; k < rowvalues.size(); k++) {

			if (rowvalues.get(k).equalsIgnoreCase(testcaseNum)) {

				String chat1 = rowvalues1.get(k);
				System.out.println(chat1);
				driver.findElement(chatWindow).sendKeys(chat1);
				driver.findElement(chatWindow).sendKeys(Keys.ENTER);

				String respXpath1 = ".//*[@id='chatWindow_span']/table/tbody/tr[";
				String respXpath2 = "]/td";
				By chatResponse1 = By.xpath(respXpath1 + m + respXpath2);
				wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse1));

				// WebElement chatResponse1 =
				// driver.findElement(By.xpath(respXpath+(m)+"]/td"));
				// wait.until(ExpectedConditions.presenceOfElementLocated((By)
				// driver.findElement(By.xpath(respXpath+m+"]/td"))));
				// WebElement chatResponse2 =
				// driver.findElement(By.xpath(respXpath+(3+k)+"]/td"));

				String resp = driver.findElement(chatResponse1).getText();
				System.out.println("response is " + resp);
				if (isElementPresent(yesNoFormDisp, driver)) {
					if (n == 2) {
						driver.findElement(noButton).click();
					} else {

						String buttonXpath1 = ".//*[@id='tc-";
						String buttonXpath2 = "']/button[1]";
						By noButton2 = By.xpath(buttonXpath1 + n + buttonXpath2);
						driver.findElement(noButton2).click();
					}

					int a = m + 1;
					By chatResponse2 = By.xpath(respXpath1 + a + respXpath2);
					wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse2));
					Label labelC = new Label(3, k + 1, resp);
					cell = (WritableCell) labelC;
					System.out.println("Cell coloumn " + cell.getColumn() + " row " + cell.getRow());
					String s = labelC.getContents();
					System.out.println("data is" + s);
					Wsheet.addCell(cell);

					m = m + 3;
					n = n + 2;
					// wwbCop.write();

				} else {
					System.out.println("form is not displayed");

					Label labelC = new Label(3, k + 1, resp);
					cell = (WritableCell) labelC;
					String s = labelC.getContents();
					System.out.println("data is" + s);
					Wsheet.addCell(cell);
					m = m + 3;
					n = n + 2;

				}
			}
			// sendCloseCommand(driver);

		}

		// wwbCop.write();
		// wwbCop.close();

	}

	public void addressChangeTest(WebDriver driver, String SheetName, String testcaseNum,Workbook wb, WritableWorkbook wwbCop,WritableSheet Wsheet,int m) throws Exception {

		try{
		WebDriverWait wait = new WebDriverWait(driver, 20);
		JavascriptExecutor js = (JavascriptExecutor)driver;

		int rows = wb.getSheet(SheetName).getRows();
		
		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		List<String> rowvalues3 = new ArrayList<String>();
		
		
		for (int i = 1; i < rows; i++) {
			System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet(SheetName).getCell(0, i).getContents());
			rowvalues1.add(wb.getSheet(SheetName).getCell(1, i).getContents());
			rowvalues2.add(wb.getSheet(SheetName).getCell(2, i).getContents());
			rowvalues3.add(wb.getSheet(SheetName).getCell(3, i).getContents());
		}
		
		//int m = 3;
		//int n = 2;
		
		for (int k = 0; k < rowvalues.size(); k++) {

			if (rowvalues.get(k).equalsIgnoreCase(testcaseNum)) {
				
				
					String usName = cm.getExcelData3(driver,testcaseNum,k);
					
					
			}
				
				if(rowvalues2.get(k).matches("#NoLogin")){
					wait.until(ExpectedConditions.presenceOfElementLocated(chatWindowFrame));
					driver.switchTo().frame(driver.findElement(chatWindowFrame));
					wait.until(ExpectedConditions.presenceOfElementLocated(chatWindow));
				}
				
				if(rowvalues2.get(k).matches("#UserEnteredText")){
					m=m+2;
			String res=	userEntersText(driver,rowvalues1,rowvalues2,rowvalues3,k,m);
				System.out.println("response is " + res);
				writeToExcel(Wsheet,res,k,wwbCop);
				
				}
				
				if(rowvalues2.get(k).matches("#UserSelectsButton")){
					m=m+1;
					String res1 = clickButtonYesNo(driver,rowvalues3,k,m);
					System.out.println("response is " + res1);
					writeToExcel(Wsheet,res1,k,wwbCop);
					
				}
							
			if(rowvalues2.get(k).matches("#UserSubmitsDeviceForm")){
				m=m+1;
				String res1 = fillDeviceCodeFom(driver,rowvalues3,k,m);
				System.out.println("response is " + res1);
				writeToExcel(Wsheet,res1,k,wwbCop);
				
				
			}
			
			if(rowvalues2.get(k).matches("#UserEntersPTN")){
				//m=m+1; First message from Cura is not populated in chat box. Hence use m=2
				m=m+1;
				String res1 = fillPTNFom(driver,rowvalues3,k,m);
				System.out.println("response is " + res1);
				writeToExcel(Wsheet,res1,k,wwbCop);
				
				
			}
			
			if(rowvalues2.get(k).matches("#secondchatbubble")){
				m=m+1;
				String respXpath1 = ".//*[@id='chatWindow_span']/table/tbody/tr[";
				String respXpath2 = "]/td";
				By chatResponse1 = By.xpath(respXpath1 + m + respXpath2);
				wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse1));	
				String resp = driver.findElement(chatResponse1).getText();
				writeToExcel(Wsheet,resp,k,wwbCop);
			}
				
			if(rowvalues2.get(k).matches("#AddressFormInput")){
				m=m+1;
				String res1 = fillAddressFom(driver,rowvalues3,k,m,testcaseNum);
				System.out.println("response is " + res1);
				writeToExcel(Wsheet,res1,k,wwbCop);
			}
			
			}
			
			
		
		
		}
		catch(Exception e){
			System.out.println("error" + e);
		}
		
	}
	
	public void navigateurl(WebDriver driver, String testcaseNum, String SheetName) throws Exception {

		
			WebDriverWait wait = new WebDriverWait(driver, 80);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
			

			//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
			
			String path = System.getProperty("user.dir")+"\\Input.xls";
			File DataManager = new File(path);
			
			Workbook wb = Workbook.getWorkbook(DataManager);
			
			
			int rows = wb.getSheet("API_CONSOLE_INFO").getRows();
			System.out.println(rows);
			int cols = wb.getSheet("API_CONSOLE_INFO").getColumns();
			System.out.println(cols);
			List<String> rowvalues = new ArrayList<String>();
			List<String> rowvalues1 = new ArrayList<String>();
			List<String> rowvalues2 = new ArrayList<String>();
			
			
			for (int k = 0; k < rows; k++) {
				
				for (int l = 0; l < cols; l++) {
					 rowvalues1.add(wb.getSheet("API_CONSOLE_INFO").getCell(l, k).getContents());
					
			
					
			if(rowvalues1.get(k).matches("Console Link")){

				
			for (int i = 0; i < rows; i++) {
				for (int j = 1; j < cols; j++) {
					rowvalues.add(wb.getSheet("API_CONSOLE_INFO").getCell(j, i).getContents());
					if(i == 0){
						
					
					//rowvalues.add(wb.getSheet("API_CONSOLE_INFO").getCell(j, i).getContents());
					String url = rowvalues.get(0);
					driver.get(url);
					}
				
			
				}
			}	
		
		}
			
			/*
			if(rowvalues1.get(k).matches("Endpoint Details")){
				

				for (int i = 1; i < rows; i++) {
					for (int j = 1; j < cols; j++) {
						rowvalues.add(wb.getSheet("API_CONSOLE_INFO").getCell(j, i).getContents());
						//String endpoint = rowvalues.get(1);
						wait.until(ExpectedConditions.visibilityOfElementLocated(endpoint));
						//driver.findElement(endpoint).click();
						System.out.println("d******************debug1");
						String respXpath1 = "(.//span[@class='raml-console-resource-path-active ng-binding ng-scope'])[";
						String respXpath2 = "]";
						By chatResponse1 = By.xpath(respXpath1 + m + respXpath2);
						
						
					}
				}
					
				}*/
				if(rowvalues1.get(k).matches("Command")){
					
					System.out.println("d******************debug22221");
					for (int i = 2; i < rows; i++) {
						for (int j = 1; j < cols; j++) {
							rowvalues.add(wb.getSheet("API_CONSOLE_INFO").getCell(j, i).getContents());
							if(i == 2){
							String endpoint = rowvalues.get(4);
							wait.until(ExpectedConditions.visibilityOfElementLocated(get1));
							//driver.findElement(get1).click();
							
							String respXpath = "//*[contains(@id, 'v1_services')]//*[contains(text(), '"+endpoint+"')]";
							System.out.println(respXpath);
							By chatResponse1 = By.xpath(respXpath);
							wait.until(ExpectedConditions.visibilityOfElementLocated(chatResponse1));
							driver.findElement(chatResponse1).click();
							
							}
						}
						
					}
				}
							
			
				
				
				for (int a = 1; a < rows; a++) {
                        	   if(rowvalues1.get(k).matches("Static Headers")){
                        		   if(rowvalues1.get(a).matches("applicationId")){
					
					System.out.println("##################################sssss");
									wait.until(ExpectedConditions.visibilityOfElementLocated(application_ID));
									driver.findElement(application_ID).clear();
									//driver.findElement(ban).click();
									for (int i = 6; i < rows; i++) {
										for (int j = 2; j < cols; j++) {
											rowvalues.add(wb.getSheet("API_CONSOLE_INFO").getCell(j, i).getContents());
											if(i == 6 && j == 2){
												wait.until(ExpectedConditions.visibilityOfElementLocated(application_ID));
											String endpoint = rowvalues.get(1);
											System.out.println(endpoint);
											driver.findElement(application_ID).sendKeys(endpoint);
											
											//driver.findElement(get1).click();
																	
											}
										}
									}
                                  }
                        	   }
				}
			}
				
	}
	}
				
											
						

				
			
		
				
			
		/*catch (Exception e) {
			System.out.println("error" + e);
		}

	}
*/	
	

	public void getExcelData3(WebDriver driver) throws Exception {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
			

			//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
			
			String path = System.getProperty("user.dir")+"\\Input.xls";
			File DataManager = new File(path);
			
			Workbook wb = Workbook.getWorkbook(DataManager);
			
			
			int rows = wb.getSheet("API_CONSOLE_INFO").getRows();
			System.out.println(rows);
			int cols = wb.getSheet("API_CONSOLE_INFO").getColumns();
			System.out.println(cols);
			List<String> rowvalues = new ArrayList<String>();

			for (int i = 0; i < rows; i++) {
				for (int j = 1; j < cols; j++) {
					rowvalues.add(wb.getSheet("API_CONSOLE_INFO").getCell(j, i).getContents());
				}
			}

			String url = rowvalues.get(0);
			driver.get(url);
			
		} catch (Exception e) {
			System.out.println("error" + e);
		}

	}
	
	
	public String userEntersText(WebDriver driver,List rowvalues1, List rowValues2, List rowvalues3, int k,int m) throws Exception{
		
		
		WebDriverWait wait = new WebDriverWait(driver, 20); 
		//String m= (String) rowvalues1.get(k);
		String chat1 = (String) rowvalues3.get(k);
		driver.findElement(chatWindow).sendKeys(chat1);
		driver.findElement(chatWindow).sendKeys(Keys.ENTER);
		String respXpath1 = ".//*[@id='chatWindow_span']/table/tbody/tr[";
		String respXpath2 = "]/td";
		By chatResponse1 = By.xpath(respXpath1 + m + respXpath2);
		wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse1));	
		String resp = driver.findElement(chatResponse1).getText();
		
		if(resp.contains("Please click here to sign")){
			cm.getExcelData2(driver);
		}
		//m=m+2;
		return resp;
	}
	
	public String clickButtonYesNo(WebDriver driver,List rowvalues3,int k,int m){
		
		//m=m+1;
		String resp1 = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String buttonText = (String) rowvalues3.get(k);
		System.out.println("text from sheet" + buttonText);
		
		if ((isElementPresent(yesNoFormDisp, driver)) && (buttonText.equalsIgnoreCase("Yes")))  {
			/*if (n == 2) {
				driver.findElement(yesButton).click();
			} else {

				String buttonXpath1 = ".//*[@id='tc-";
				String buttonXpath2 = "']/button[2]";
				By yesButton2 = By.xpath(buttonXpath1 + n + buttonXpath2);
				driver.findElement(yesButton2).click();
			}*/
			//if(buttonText.equalsIgnoreCase("Yes")){
			//wait.until(ExpectedConditions.presenceOfElementLocated(yesButton));
			js.executeScript("arguments[0].click();", driver.findElement(yesButton));
			//driver.findElement(yesButton).click();
			By chatResponse2 = By.xpath(respXpath1 + m + respXpath2);
			wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse2));
			resp1 = driver.findElement(chatResponse2).getText();
			
		}
			if ((isElementPresent(yesNoFormDisp, driver)) && (buttonText.equalsIgnoreCase("No"))){
				System.out.println("test2");
				//wait.until(ExpectedConditions.presenceOfElementLocated(noButton));
				js.executeScript("arguments[0].click();", driver.findElement(noButton));
				//driver.findElement(noButton).click();
				By chatResponse2 = By.xpath(respXpath1 + m + respXpath2);
				wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse2));
				resp1 = driver.findElement(chatResponse2).getText();
				
			}
		
			else{
				System.out.println("form is not displayed");
			}
		
			
		return resp1;
		
	}
	
	public String fillDeviceCodeFom(WebDriver driver,List rowvalues3, int k,int m) {
		
		String resp1=null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String text1 = (String) rowvalues3.get(k);
		wait.until(ExpectedConditions.presenceOfElementLocated(tcForms));
		driver.findElement(deviceCodeText).sendKeys(text1);
		driver.findElement(deviceCodeText).sendKeys(Keys.TAB);
		driver.findElement(deviceContinue).click();
		By chatResponse2 = By.xpath(respXpath1 + m + respXpath2);
		wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse2));
		resp1 = driver.findElement(chatResponse2).getText();
		return resp1;
	}
	
public String fillPTNFom(WebDriver driver,List rowvalues3, int k,int m) {
		
		String resp1=null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String text1 = (String) rowvalues3.get(k);
		wait.until(ExpectedConditions.presenceOfElementLocated(tcForms));
		driver.findElement(phoneNumber).sendKeys(text1);
		driver.findElement(phoneNumber).sendKeys(Keys.TAB);
		wait.until(ExpectedConditions.elementToBeClickable(deviceContinue));
		driver.findElement(deviceContinue).click();
		By chatResponse2 = By.xpath(respXpath1 + m + respXpath2);
		wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse2));
		resp1 = driver.findElement(chatResponse2).getText();
		return resp1;
	}
	
public String fillInvalidDeviceCodeFom(WebDriver driver,List rowvalues3, int k,int m) {
		
		String resp1=null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String text1 = (String) rowvalues3.get(k);
		driver.findElement(deviceCodeText).sendKeys(text1);
		driver.findElement(deviceCodeText).sendKeys(Keys.TAB);
		driver.findElement(deviceContinue).click();
		m=m+1;
		By chatResponse2 = By.xpath(respXpath1 + m + respXpath2);
		wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse2));
		resp1 = driver.findElement(chatResponse2).getText();
		return resp1;
	}
	
	public String fillAddressFom(WebDriver driver,List rowvalues3, int k,int m, String testcaseNum) throws Exception {
		
			String resp1=null;
			WebDriverWait wait = new WebDriverWait(driver, 20);
			
			
			/*File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
			Workbook wb = Workbook.getWorkbook(DataManager);
			
			int rows = wb.getSheet("TestData").getRows();
			
			List<String> rowvalues = new ArrayList<String>();
			List<String> rowvalues1 = new ArrayList<String>();
			List<String> rowvalues2 = new ArrayList<String>();
					
			
			for (int i = 1; i < rows; i++) {
				System.out.println("rows are" + rows);
				rowvalues.add(wb.getSheet("TestData").getCell(0, i).getContents());
				rowvalues1.add(wb.getSheet("TestData").getCell(3, i).getContents());
				rowvalues2.add(wb.getSheet("TestData").getCell(1, i).getContents());
				
			}
			
			//int x=0;
						
			for (int x = 0; x < rowvalues.size(); x++) {

				if (rowvalues.get(x).equalsIgnoreCase(testcaseNum)) {
					String address = rowvalues1.get(x+2);
				*/	
			
					String address = cm.getAdressLine1(driver, testcaseNum);
					String apt=cm.getApt(driver, testcaseNum);
					String city = cm.getCity(driver, testcaseNum);
					String state = cm.getState(driver, testcaseNum);
					String zip = cm.getZip(driver, testcaseNum);
					System.out.println("test1");
					wait.until(ExpectedConditions.presenceOfElementLocated(tcForms));
					System.out.println("test2");
					driver.findElement(formAddress).sendKeys(address);
					driver.findElement(formApt).sendKeys(apt);
					driver.findElement(formCity).sendKeys(city);
					driver.findElement(formState).sendKeys(state);
					driver.findElement(formZip).sendKeys(zip);
					driver.findElement(formZip).sendKeys(Keys.TAB);
					
					driver.findElement(deviceContinue).click();
					System.out.println("test3");
			
			By chatResponse2 = By.xpath(respXpath1 + m + respXpath2);
			wait.until(ExpectedConditions.presenceOfElementLocated(chatResponse2));
			resp1 = driver.findElement(chatResponse2).getText();
			System.out.println("text is" + resp1);
			
				
			
			return resp1;
	//}catch(Exception e){
			//return resp1;
		//}
		
	}

	public boolean isElementPresent(By element, WebDriver driver) {
		try {
			driver.findElement(element);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void sendCloseCommand(WebDriver driver) {
		
		driver.findElement(chatWindow).sendKeys("close");
		driver.findElement(chatWindow).sendKeys(Keys.ENTER);
		
		
	}
	
	public void writeToExcel(WritableSheet Wsheet, String resp,int k,WritableWorkbook wwbCop) throws RowsExceededException, WriteException, IOException{
	
		WritableCell cell;
	Label labelC = new Label(4, k + 1, resp);
	cell = (WritableCell) labelC;
	System.out.println("Cell coloumn " + cell.getColumn() + " row " + cell.getRow());
	String s = labelC.getContents();
	System.out.println("data is" + s);
	Wsheet.addCell(cell);
	/*try {
		wwbCop.write();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	}

	


	
}
