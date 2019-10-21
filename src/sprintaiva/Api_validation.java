package sprintaiva;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class Api_validation extends CommonMethods {

	private WebDriver driver;
	CommonMethods cm = new CommonMethods();
	EnquiryChat ec = new EnquiryChat();
	
	@BeforeTest

	public void setUp() {
		// driver = getDriver();

	}

	
@Test
	public void openSprint1() throws Exception {

		
		// cm.getExcelData(driver);
		// cm.fillChatForm(driver, "Billing/Payment");
		Set<String> newtestCases = AIVAForms.getTCTestCaseNum();
		
		String path = System.getProperty("user.dir")+"\\Input.xls";
		File DataManager = new File(path);
		
		Workbook wb = Workbook.getWorkbook(DataManager);
		
		

		
		
		String path1 = System.getProperty("user.dir")+"\\TestOutput-Usecase.xls";
		//File DataManager1 = new File(path1);
		
		WritableWorkbook wwbCop = Workbook.createWorkbook(new File(path1), wb);
		WritableSheet Wsheet = wwbCop.getSheet(AIVAConstants.sheetName1);

		
			
		
			driver = getDriver();
			
			
			
			
			
			
			
			
			
			
			try 
    		{
			//cm.getExcelData3(driver);
			//driver.navigate().to("https://dev2-apiservices-web.dev.sprint.com:7441/api/process/pay/v1/console/");
			/*driver.manage().window().maximize();*/
			WebDriverWait wait = new WebDriverWait(driver, 20);
			
			//ec.navigateurl(driver);
			ec.navigateurl(driver,"TestCase","TestCase" );
			//cm.apitestdata(driver);
			/*try{
			
			int rows = wb.getSheet("API").getRows();
			System.out.println(rows);
			int cols = wb.getSheet("").getColumns();
			System.out.println(cols);
			List<String> rowvalues = new ArrayList<String>();
			
			
			cm.apitestdata1(driver);
			int rows1 = wb.getSheet("API").getRows();
			System.out.println(rows1);
			
			for (int j = 1; j < rows1; j++) {
			String testcaseNum1 = wb.getSheet("API").getCell(0, j).getContents();
			System.out.println(testcaseNum1);
			
			
			String result1 = cm.getpa_ptp(driver, testcaseNum1);
			
			
			Label labelC2 = new Label(1, j, testcaseNum1);
			WritableCell cell2 = (WritableCell) labelC2;
			String s3 = labelC2.getContents();
			System.out.println("data is" + s3);
			Wsheet.addCell(cell2);
			
			Label labelC3 = new Label(1, j, result1);
			WritableCell cell3 = (WritableCell) labelC3;
			String s4 = labelC3.getContents();
			System.out.println("data is" + s4);
			Wsheet.addCell(cell3);
			
            String result2 = cm.get_PaPtp_date(driver, testcaseNum1);
			
			
			Label labelC4 = new Label(2, j, testcaseNum1);
			WritableCell cell4 = (WritableCell) labelC4;
			String s9 = labelC4.getContents();
			System.out.println("data is" + s9);
			Wsheet.addCell(cell4);
			
			Label labelC5 = new Label(2, j, result2);
			WritableCell cell5 = (WritableCell) labelC5;
			String s10 = labelC5.getContents();
			System.out.println("data is" + s10);
			Wsheet.addCell(cell5);
			
			}
			
			
    		
    		
    		} catch (Exception e) {
    			System.out.println("error" + e);
    		}

*/
    	
			

			
    		}
		
    		
    		
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			wwbCop.write();
			wwbCop.close();
		
		}
}

		
	



