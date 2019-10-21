package sprintaiva;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import jxl.write.Label;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class CommonMethods  {

	By searchSprint = By.xpath(".//*[@id='sprint-header-search']");
	By signIn1 = By.xpath(".//*[@class='sprint-header-transfer__permalink sprint-header-caret']");
	By signIn = By.xpath(".//*[@id='tl-menu-signin']");
	By uName1 = By.xpath(".//*[@id='loginHeaderUsername']");
	By uName = By.xpath(".//*[@id='txtLoginUsernameDL']");
	By pwd = By.xpath(".//*[@id='txtLoginPasswordDL']");
	By submit = By.xpath(".//*[@id='btnLoginSubmitDL']");
	By pwd1 = By.xpath(".//*[@id='loginHeaderPassword']");
	By submit1 = By.xpath(".//*[@id='loginHeaderButton']");
	By liveChat2 = By.xpath(".//*[@id='inqC2CImgContainerDynBR']/div/img");
	By firstName = By.xpath(".//*[@id='view-container']/article/section/form/fieldset[1]/input");
	By search = By.xpath(".//*[@id='sprint-header-search']");
	By sprintChatLink = By.xpath(".//*[@id='top']/div[2]/div[2]/div[2]/div[1]/h5/a");
	By liveChat = By.xpath("//*[@id='inqC2C4ImgContainer']/div/img");
	By email = By.xpath(".//*[@id='view-container']/article/section/form/fieldset[2]/input");
	By pnumber = By.xpath(".//*[@id='view-container']/article/section/form/fieldset[3]/input");
	By howCan = By.xpath(".//*[@id='view-container']/article/section/form/fieldset[4]/select");
	By help = By.xpath(".//*[@id='view-container']/article/section/form/fieldset[5]/textarea");
	By submitChat = By.xpath(".//*[@id='view-container']/article/section/form/button");
	By chatWindow = By.xpath(".//*[@id='txtInput_input']");
	// By noButton = By.xpath(".//*[@id='tc-2']/button[1]");
	By noButton = By.xpath(".//*[contains(@class,'button-inactive')]");
	// By yesButton = By.xpath(".//*[@id='tc-2']/button[2]");
	By yesButton = By.xpath(".//*[contains(@class,'button-active')]");
	By continueButton = By.xpath(".//*[@id='tc-2']/button[2]");
	By chatWindowFrame = By.xpath(".//iframe[@title='Chat Window']");
	By mySprint = By.xpath(".//*[contains(@class,'sprint-menu') and span[contains(text(),'My Sprint')]]");
	By manageOrders = By.xpath(".//a[contains(text(),'Manage my orders')]");
	By reorderReturn = By.xpath(".//span[contains(text(),'Reorder a return kit')]");
	By chatLive = By.xpath(".//*[@id='inqC2CImgContainerDynBR']/img");
	
	
	By account_id = By.xpath(".//*[@id='accountId']");
	By ban = By.xpath(".//*[@id='ban']");
	By application_ID = By.xpath(".//*[@id='applicationId']");
	
	By application_id = By.xpath(".//*[@id='applicationId']");
	By applicationuser_id = By.xpath(".//*[@id='applicationUserId']");
	
	
	
	
	By get = By.xpath("(.//span[@class='raml-console-tab-label raml-console-tab-get'])[2]");
	By get_futurepayment = By.xpath("(.//span[@class='raml-console-tab-label raml-console-tab-get'])[5]");
	
			By get_paymenteligibility = By.xpath("(.//span[@class='raml-console-tab-label raml-console-tab-post'])[2]");
	
	By btn_get = By.xpath(".//button[@class='raml-console-sidebar-action raml-console-sidebar-action-get']");
	By autopay = By.xpath("(.//span[@class='cm-atom'])[1]");
	By plancode = By.xpath("(.//span[@class='cm-string'])[1]");
	By account_plans = By.xpath(".//*[@class='raml-console-sidebar-pre']");
	
	By pa_ptp = By.xpath("(.//span[@class='cm-string'])[1]");
	By asl = By.xpath("(.//span[@class='cm-atom'])[1]");
	By pastdue = By.xpath("(.//span[@class='cm-atom'])[2]");
	By date = By.xpath("(.//span[@class='cm-string'])[2]");
	
	By Asl_percentage = By.xpath("(.//span[@class='cm-number'])[10]");
	By PAeligiblity_body = By.xpath("(.//div[@class='raml-console-sidebar-row'])[5]");
	
	
	By status = By.xpath("//*[@class='raml-console-sidebar-response-item ng-binding']");
	/*By body = By.xpath("//*[contains(text(),'paymentMethods')][@class='cm-string cm-property']");*/
	By body = By.xpath("//*[@id='v1_accounts_accountId_payment_methods']/div/div[2]/form/div/div/div/div/section[2]/div/div[2]/div/div/div/div[6]/div[1]/div/div/div/div[3]/div[2]/pre");
	
	By endpoint = By.xpath("//span[contains(@class, 'raml-console-resource-path-active ng-binding ng-scope') and text() = '/services']");
	By get1 = By.xpath("//*[contains(@id, 'v1_services')]//*[contains(text(), 'GET')]");
	
	//@FindBy(xpath=".//*[@id='accountId']") public WebElement account_id;
    //@FindBy(xpath="(.//span[@class='raml-console-tab-label raml-console-tab-get'])[3]") public WebElement get;
    //@FindBy(xpath=".//button[@class='raml-console-sidebar-action raml-console-sidebar-action-get']") public WebElement btn_get;
	// By chatResponse1 =
	// By.xpath(".//*[@id='chatWindow_span']/table/tbody/tr[3]/td");

	@SuppressWarnings("deprecation")
	public WebDriver getDriver() {

		WebDriver driver;
		
		/*DesiredCapabilities caps = DesiredCapabilities.chrome();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType., Level.INFO);
		caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);*/
		
		//String path = System.getProperty("user.dir")+"\\chromedriver.exe";
		
		String path="C:\\ProgramData\\chromedriver.exe";
		//String path="C:\\Selenium\\chromedriver.exe";
	//	File DataManager = new File(path);
		System.setProperty("webdriver.chrome.driver", path);
		 //DesiredCapabilities dc = DesiredCapabilities.chrome();

		//driver = new ChromeDriver();
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments("--start-maximized");
		driver = new ChromeDriver();
		/*driver.manage().window().maximize();*/
	
		
		//IE Driver
		//String iepath=System.getProperty("user.dir")+ "\\IEDriverServer.exe";
		
	     /*System.setProperty("webdriver.ie.driver","C:\\Selenium\\IEDriverServer.exe");
	     DesiredCapabilities ie = DesiredCapabilities.internetExplorer();
	     ie.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, true);
	     ie.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS , true);
	     driver = new InternetExplorerDriver(ie);
	     driver.manage().window().maximize();*/
		
		return driver;

	}

	public void fillChatForm(WebDriver driver, String intent) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(search));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(search));
		driver.findElement(search).sendKeys("live chat");
		driver.findElement(search).sendKeys(Keys.ENTER);
		wait.until(ExpectedConditions.presenceOfElementLocated(sprintChatLink));
		js.executeScript("arguments[0].click();", driver.findElement(sprintChatLink));
		wait.until(ExpectedConditions.presenceOfElementLocated(liveChat));
		js.executeScript("arguments[0].click();", driver.findElement(liveChat));

		driver.switchTo().frame(driver.findElement(By.xpath(".//iframe[@title='Chat Window']")));
		wait.until(ExpectedConditions.presenceOfElementLocated(firstName));
		js.executeScript("arguments[0].click();", driver.findElement(firstName));
		driver.findElement(firstName).sendKeys("Sriveena");
		js.executeScript("arguments[0].click();", driver.findElement(email));
		driver.findElement(email).sendKeys("Sriveena.sridhar9@gmail.com");
		js.executeScript("arguments[0].click();", driver.findElement(pnumber));
		driver.findElement(pnumber).sendKeys("9000661957");
		Select sele = new Select(driver.findElement(howCan));
		sele.selectByValue(intent);
		js.executeScript("arguments[0].click();", driver.findElement(help));
		driver.findElement(help).sendKeys("test");
		js.executeScript("arguments[0].click();", driver.findElement(submitChat));
		wait.until(ExpectedConditions.presenceOfElementLocated(chatWindow));
	}

	
	

	
	
	
	public void apitestdata(WebDriver driver) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(get));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(get));
		
		
		
		
	}
	
	public void apitestdata1(WebDriver driver) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(get_futurepayment));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(get_futurepayment));
		
		
		
		
	}
	
	public void apitestdata2(WebDriver driver) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(get_futurepayment));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(get_futurepayment));
		
		
		
		
	}
	
	public void apitestdata3(WebDriver driver) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(get_paymenteligibility));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(get_paymenteligibility));
		
		
		
		
	}
	
	public String getautopay(WebDriver driver,String testcaseNum){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum);
		driver.findElement(application_id).clear();
		driver.findElement(application_id).sendKeys("2IE");
		
		driver.findElement(applicationuser_id).clear();
		driver.findElement(applicationuser_id).sendKeys("2IE");
		
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
			if(driver.findElement(autopay).isDisplayed())
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(autopay));
			value = driver.findElement(autopay).getText();
			
			/*value = driver.findElement(autopay).getText();*/
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					/*System.out.println("Wrong BAN");*/
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
	
	
	/*public String getplancode(WebDriver driver,String testcaseNum,String testcaseNum1){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum);
		driver.findElement(application_id).clear();
		driver.findElement(application_id).sendKeys("2IE");
		
		driver.findElement(applicationuser_id).clear();
		driver.findElement(applicationuser_id).sendKeys("2IE");
		
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
			if(driver.findElement(account_plans).isDisplayed())
				
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(account_plans));
			value = driver.findElement(account_plans).getText();
			
			value = driver.findElement(autopay).getText();
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					System.out.println("Wrong BAN");
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
	
	*/
	public String getplancode(WebDriver driver,String testcaseNum,String testcaseNum1){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum);
		driver.findElement(application_id).clear();
		driver.findElement(application_id).sendKeys("2IE");
		
		driver.findElement(applicationuser_id).clear();
		driver.findElement(applicationuser_id).sendKeys("2IE");
		
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
				//driver.findElement(By.xpath(".//span[contains(text(),'3604407723') and @class='cm-string']"));
				 driver.findElement(By.xpath(".//span[contains(text(),'"+testcaseNum1+"') and @class='cm-string']"));
			/*if(driver.findElement(account_plans).isDisplayed())*/
				
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(account_plans));
			value = driver.findElement(account_plans).getText();
			
			/*value = driver.findElement(autopay).getText();*/
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					/*System.out.println("Wrong BAN");*/
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
	
	
	
	
	
	
	
	
	
	
	/*public String getpa_ptp(WebDriver driver,String testcaseNum1){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum1);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(pa_ptp));
		String value = driver.findElement(pa_ptp).getText();
		return value;
	}*/
	
	public String getpa_ptp(WebDriver driver,String testcaseNum1){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum1);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
			if(driver.findElement(pa_ptp).isDisplayed())
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(pa_ptp));
			value = driver.findElement(pa_ptp).getText();
			
			/*value = driver.findElement(autopay).getText();*/
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					/*System.out.println("Wrong BAN");*/
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
		
		/*wait.until(ExpectedConditions.visibilityOfElementLocated(pa_ptp));
		String value = driver.findElement(pa_ptp).getText();
		return value;*/
	
	
	/*public String get_aslpastdue(WebDriver driver,String testcaseNum2){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum2);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(asl));
	    String value = driver.findElement(asl).getText();
		
		return value;*/
		
	public void getExcelData3(WebDriver driver) throws Exception {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			JavascriptExecutor js = (JavascriptExecutor) driver;

			//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
			
			String path = System.getProperty("user.dir")+"\\Input.xls";
			File DataManager = new File(path);
			
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet("SignIn").getRows();
			System.out.println(rows);
			int cols = wb.getSheet("SignIn").getColumns();
			System.out.println(cols);
			List<String> rowvalues = new ArrayList<String>();

			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					rowvalues.add(wb.getSheet("SignIn").getCell(j, i).getContents());
				}
			}

			String URL = rowvalues.get(0);
			driver.get(URL);
			
		} catch (Exception e) {
			System.out.println("error" + e);
		}

	}
		
	public String get_aslpastdue(WebDriver driver,String testcaseNum2){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum2);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
			if(driver.findElement(asl).isDisplayed())
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(asl));
			value = driver.findElement(asl).getText();
			
			/*value = driver.findElement(autopay).getText();*/
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					/*System.out.println("Wrong BAN");*/
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
		
	public String get_PAeligibility(WebDriver driver,String testcaseNum3){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum3);
		
		driver.findElement(PAeligiblity_body).clear();
		driver.findElement(PAeligiblity_body).sendKeys("");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
			if(driver.findElement(asl).isDisplayed())
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(asl));
			value = driver.findElement(asl).getText();
			
			/*value = driver.findElement(autopay).getText();*/
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					/*System.out.println("Wrong BAN");*/
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
		
	
		/*wait.until(ExpectedConditions.visibilityOfElementLocated(asl));
	    String value = driver.findElement(asl).getText();
		
		return value;*/
	
		
		
	/*public String get_pastdue(WebDriver driver,String testcaseNum2){
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum2);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(pastdue));
	    String value = driver.findElement(pastdue).getText();
		
		return value;
		
		
		
	}*/
	
	public String get_pastdue(WebDriver driver,String testcaseNum2){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum2);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
			if(driver.findElement(pastdue).isDisplayed())
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(pastdue));
			value = driver.findElement(pastdue).getText();
			
			/*value = driver.findElement(autopay).getText();*/
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					/*System.out.println("Wrong BAN");*/
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
		
		/*wait.until(ExpectedConditions.visibilityOfElementLocated(pastdue));
	    String value = driver.findElement(pastdue).getText();
		
		return value;
		
		
		
	}*/
	
	public String get_PaPtp_date(WebDriver driver,String testcaseNum1){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum1);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
			if(driver.findElement(date).isDisplayed())
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(date));
			value = driver.findElement(date).getText();
			
			/*value = driver.findElement(autopay).getText();*/
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					/*System.out.println("Wrong BAN");*/
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
	
	public String get_ASLPercent(WebDriver driver,String testcaseNum1){
		String value = null;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(account_id));
		driver.findElement(account_id).clear();
		driver.findElement(account_id).sendKeys(testcaseNum1);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(btn_get));
		wait.until(ExpectedConditions.visibilityOfElementLocated(status));
		String Status = driver.findElement(status).getText();
		if(Status.equals("200"))
		{
			
			try{
			
			if(driver.findElement(Asl_percentage).isDisplayed())
				
			{
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(Asl_percentage));
			value = driver.findElement(Asl_percentage).getText();
			
			/*value = driver.findElement(autopay).getText();*/
			}
			
		
			}
			catch ( TimeoutException a)
			{
				 if(driver.findElement(body).isEnabled())
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(body));
					System.out.println("Wrong BAN");
					/*System.out.println("Wrong BAN");*/
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		else
		{
			System.out.println("status not equal to : " +Status);
		}
		return value ;
		
	}
	

	
	public void addressChange(WebDriver driver, String SheetName, String testcaseNum) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 60);
		File DataManager = new File("C:\\Users\\ko706953\\Selenium\\Book2.xls");
		// File DataManagerOutput = new
		// File("C:\\Users\\ko706953\\Selenium\\Book1.xls");
		Workbook wb = Workbook.getWorkbook(DataManager);
		WritableWorkbook wwbCop = Workbook.createWorkbook(new File("C:\\Users\\ko706953\\Selenium\\Book1.xls"), wb);
		WritableSheet Wsheet = wwbCop.getSheet(SheetName);
		WritableCell cell;

		int rows = wb.getSheet(SheetName).getRows();
		int cols = wb.getSheet(SheetName).getColumns();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();

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

				Label labelC = new Label(3, k + 1, resp);
				cell = (WritableCell) labelC;
				String s = labelC.getContents();
				System.out.println("data is" + s);
				Wsheet.addCell(cell);

				m = m + 2;

			}

		}
		wwbCop.write();
		wwbCop.close();

	}

	public void getExcelData(WebDriver driver) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		Workbook wb = Workbook.getWorkbook(DataManager);
		int rows = wb.getSheet("SignIn").getRows();
		System.out.println(rows);
		int cols = wb.getSheet("SignIn").getColumns();
		System.out.println(cols);
		List<String> rowvalues = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rowvalues.add(wb.getSheet("SignIn").getCell(j, i).getContents());
			}
		}

		String URL = rowvalues.get(1);
		driver.get(URL);
		/*
		 * wait.until(ExpectedConditions.presenceOfElementLocated(signIn1));
		 * driver.findElement(signIn1).click(); //
		 * wait.until(ExpectedConditions.presenceOfElementLocated(signIn1)); //
		 * js.executeScript("arguments[0].click();",
		 * driver.findElement(signIn1)); String usName = rowvalues.get(1);
		 * System.out.println("user name is"+usName);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(uName1)); //
		 * if(driver.findElement(chatWindowFrame).isDisplayed()){
		 * js.executeScript("arguments[0].click();",
		 * driver.findElement(uName1));
		 * driver.findElement(uName1).sendKeys(usName); String password =
		 * rowvalues.get(2); System.out.println("password is"+password);
		 * js.executeScript("arguments[0].click();", driver.findElement(pwd1));
		 * driver.findElement(pwd1).sendKeys(password);
		 * wait.until(ExpectedConditions.elementToBeClickable(submit1));
		 * js.executeScript("arguments[0].click();",
		 * driver.findElement(submit1));
		 */
		wait.until(ExpectedConditions.presenceOfElementLocated(chatWindowFrame));
		driver.switchTo().frame(driver.findElement(chatWindowFrame));
		wait.until(ExpectedConditions.presenceOfElementLocated(chatWindow));

	}

	public void chatWindowTest(WebDriver driver) {
		// driver.get("https://st2-www.test.sprint.com/en/support.html?Cogtrial");
		// DEV2: https://www.sprint.com/landings/chat/?VA-CV
		// ST2: https://st2-www.test.sprint.com/en/support.html?Cogtrial
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(chatWindowFrame));
		driver.switchTo().frame(driver.findElement(chatWindowFrame));
		wait.until(ExpectedConditions.elementToBeClickable(chatWindow));
	}

	public void getExcelData1(WebDriver driver) throws Exception {

		File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		Workbook wb = Workbook.getWorkbook(DataManager);
		int rows = wb.getSheet("SignIn").getRows();
		System.out.println(rows);
		int cols = wb.getSheet("SignIn").getColumns();
		System.out.println(cols);
		List<String> rowvalues = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rowvalues.add(wb.getSheet("SignIn").getCell(j, i).getContents());
			}
		}

		String URL = rowvalues.get(0);
		driver.get(URL);
		driver.findElement(signIn).click();

	}

	public void getExcelData2(WebDriver driver) throws Exception {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			JavascriptExecutor js = (JavascriptExecutor) driver;

			//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
			
			String path = System.getProperty("user.dir")+"\\input.xls";
			File DataManager = new File(path);
			
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet("API").getRows();
			System.out.println(rows);
			int cols = wb.getSheet("API").getColumns();
			System.out.println(cols);
			List<String> rowvalues = new ArrayList<String>();

			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					rowvalues.add(wb.getSheet("API").getCell(j, i).getContents());
				}
			}

			String URL = rowvalues.get(0);
			driver.get(URL);
			
			// Thread.sleep(500);
			/*
			 * wait.until(ExpectedConditions.presenceOfElementLocated(signIn1));
			 * driver.findElement(signIn1).click(); Thread.sleep(500); String
			 * usName = rowvalues.get(1); System.out.println("user name is"
			 * +usName);
			 * wait.until(ExpectedConditions.presenceOfElementLocated(uName1));
			 * // if(driver.findElement(chatWindowFrame).isDisplayed()){
			 * js.executeScript("arguments[0].click();",
			 * driver.findElement(uName1));
			 * driver.findElement(uName1).sendKeys(usName); String password =
			 * rowvalues.get(2); System.out.println("password is"+password);
			 * js.executeScript("arguments[0].click();",
			 * driver.findElement(pwd1));
			 * driver.findElement(pwd1).sendKeys(password);
			 * 
			 * js.executeScript("arguments[0].click();",
			 * driver.findElement(submit1));
			 * 
			 * /* wait.until(ExpectedConditions.elementToBeClickable(mySprint));
			 * js.executeScript("arguments[0].click();",
			 * driver.findElement(mySprint));
			 * wait.until(ExpectedConditions.presenceOfElementLocated(
			 * manageOrders)); js.executeScript("arguments[0].click();",
			 * driver.findElement(manageOrders));
			 * wait.until(ExpectedConditions.presenceOfElementLocated(
			 * reorderReturn)); js.executeScript("arguments[0].click();",
			 * driver.findElement(reorderReturn));
			 * wait.until(ExpectedConditions.presenceOfElementLocated(chatLive))
			 * ; js.executeScript("arguments[0].click();",
			 * driver.findElement(chatLive));
			 * wait.until(ExpectedConditions.presenceOfElementLocated(
			 * chatWindowFrame));
			 * driver.switchTo().frame(driver.findElement(chatWindowFrame));
			 * wait.until(ExpectedConditions.presenceOfElementLocated(chatWindow
			 * ));
			 */
		} catch (Exception e) {
			System.out.println("error" + e);
		}

	}

	public String getExcelData3(WebDriver driver, String testcaseNum, int k) throws Exception {

		String username = null;

		//WebDriverWait wait = new WebDriverWait(driver, 20);

		//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		String path = System.getProperty("user.dir")+"\\input.xlsx";
		File DataManager = new File(path);
		Workbook wb = Workbook.getWorkbook(DataManager);

		int rows = wb.getSheet("API").getRows();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		List<String> rowvalues3 = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
			//System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet("API").getCell(0, i).getContents());
			//username = wb.getSheet("TestData").getCell(3, k).getContents();
			rowvalues1.add(wb.getSheet("API").getCell(3, i).getContents());
			rowvalues2.add(wb.getSheet("API").getCell(2, i).getContents());
			rowvalues3.add(wb.getSheet("API").getCell(1, i).getContents());

		}

		// int x=0;

		for (int x = 0; x < rowvalues.size(); x++) {

			//if (rowvalues.get(x).equalsIgnoreCase(testcaseNum)) {
			System.out.println("testdata test is" + testcaseNum);
				//for (int y = 0; y < rowvalues3.size(); y++) {
					//if (rowvalues3.get(y).equalsIgnoreCase("#Login")) {

						//for (int z = 0; z < rowvalues2.size(); z++) {
							if (rowvalues.get(x).equalsIgnoreCase(testcaseNum) && rowvalues2.get(x).equalsIgnoreCase("Username") && rowvalues3.get(x).equalsIgnoreCase("#Login") ) {
								
								username = rowvalues1.get(x);
								System.out.println("username is"+ username );
							}
						}
					//}

				//}

			//}
		//}
		return username;

	}

	public String getExcelData4(WebDriver driver, String testcaseNum, int k) throws Exception {

		String password = null;

		//WebDriverWait wait = new WebDriverWait(driver, 20);

		//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		String path = System.getProperty("user.dir")+"\\TestScriptAutomationSample.xls";
		File DataManager = new File(path);
		Workbook wb = Workbook.getWorkbook(DataManager);

		int rows = wb.getSheet("TestData").getRows();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		List<String> rowvalues3 = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
			//System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet("TestData").getCell(0, i).getContents());
			rowvalues1.add(wb.getSheet("TestData").getCell(3, i).getContents());
			rowvalues2.add(wb.getSheet("TestData").getCell(2, i).getContents());
			rowvalues3.add(wb.getSheet("TestData").getCell(1, i).getContents());

		}

		// int x=0;

		for (int x = 0; x < rowvalues.size(); x++) {

			//if (rowvalues.get(x).equalsIgnoreCase(testcaseNum)) {
			System.out.println("testdata pwd is" + testcaseNum);
				//for (int y = 0; y < rowvalues3.size(); y++) {
					//if (rowvalues3.get(y).equalsIgnoreCase("#Login")) {

						//for (int z = 0; z < rowvalues2.size(); z++) {
	if (rowvalues.get(x).equalsIgnoreCase(testcaseNum) && rowvalues2.get(x).equalsIgnoreCase("Password") && rowvalues3.get(x).equalsIgnoreCase("#Login") ) {
								
								password = rowvalues1.get(x);
								System.out.println("pwd is"+ password );
							}
						}

		return password;

	}

	public String getAdressLine1(WebDriver driver, String testcaseNum) throws Exception {

		String address = null;
		// try{
		WebDriverWait wait = new WebDriverWait(driver, 20);

		//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		String path = System.getProperty("user.dir")+"\\TestScriptAutomationSample.xls";
		File DataManager = new File(path);
		Workbook wb = Workbook.getWorkbook(DataManager);

		int rows = wb.getSheet("TestData").getRows();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		List<String> rowvalues3 = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
			//System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet("TestData").getCell(0, i).getContents());
			rowvalues1.add(wb.getSheet("TestData").getCell(3, i).getContents());
			rowvalues2.add(wb.getSheet("TestData").getCell(2, i).getContents());
			rowvalues3.add(wb.getSheet("TestData").getCell(1, i).getContents());

		}

		// int x=0;

		for (int x = 0; x < rowvalues.size(); x++) {

			if (rowvalues.get(x).equalsIgnoreCase(testcaseNum)) {
				for (int y = 0; y < rowvalues3.size(); y++) {
					if (rowvalues3.get(y).equalsIgnoreCase("#AddressFormInput")) {

						for (int z = 0; z < rowvalues2.size(); z++) {
							if (rowvalues2.get(z).equalsIgnoreCase("Address Line 1")) {
								address = rowvalues1.get(z);
							}
						}
					}
				}
			}
		}

		return address;

	}

	public String getCity(WebDriver driver, String testcaseNum) throws Exception {

		String city = null;
		// try{
		WebDriverWait wait = new WebDriverWait(driver, 20);

	//	File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		String path = System.getProperty("user.dir")+"\\TestScriptAutomationSample.xls";
		File DataManager = new File(path);
		Workbook wb = Workbook.getWorkbook(DataManager);

		int rows = wb.getSheet("TestData").getRows();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		List<String> rowvalues3 = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
			//System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet("TestData").getCell(0, i).getContents());
			rowvalues1.add(wb.getSheet("TestData").getCell(3, i).getContents());
			rowvalues2.add(wb.getSheet("TestData").getCell(2, i).getContents());
			rowvalues3.add(wb.getSheet("TestData").getCell(1, i).getContents());

		}

		// int x=0;

		for (int x = 0; x < rowvalues.size(); x++) {

			if (rowvalues.get(x).equalsIgnoreCase(testcaseNum)) {
				for (int y = 0; y < rowvalues3.size(); y++) {
					if (rowvalues3.get(y).equalsIgnoreCase("#AddressFormInput")) {

						for (int z = 0; z < rowvalues2.size(); z++) {
							if (rowvalues2.get(z).equalsIgnoreCase("City")) {
								city = rowvalues1.get(z);
							}
						}
					}
				}
			}
		}

		return city;

	}

	public String getState(WebDriver driver, String testcaseNum) throws Exception {

		String state = null;
		// try{
		WebDriverWait wait = new WebDriverWait(driver, 20);

		//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		String path = System.getProperty("user.dir")+"\\TestScriptAutomationSample.xls";
		File DataManager = new File(path);
		Workbook wb = Workbook.getWorkbook(DataManager);

		int rows = wb.getSheet("TestData").getRows();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		List<String> rowvalues3 = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
			//System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet("TestData").getCell(0, i).getContents());
			rowvalues1.add(wb.getSheet("TestData").getCell(3, i).getContents());
			rowvalues2.add(wb.getSheet("TestData").getCell(2, i).getContents());
			rowvalues3.add(wb.getSheet("TestData").getCell(1, i).getContents());

		}

		// int x=0;

		for (int x = 0; x < rowvalues.size(); x++) {

			if (rowvalues.get(x).equalsIgnoreCase(testcaseNum)) {
				for (int y = 0; y < rowvalues3.size(); y++) {
					if (rowvalues3.get(y).equalsIgnoreCase("#AddressFormInput")) {

						for (int z = 0; z < rowvalues2.size(); z++) {
							if (rowvalues2.get(z).equalsIgnoreCase("State")) {
								state = rowvalues1.get(z);
							}
						}
					}
				}
			}
		}

		return state;

	}

	public String getZip(WebDriver driver, String testcaseNum) throws Exception {

		String zip = null;
		// try{
		WebDriverWait wait = new WebDriverWait(driver, 20);

		//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		String path = System.getProperty("user.dir")+"\\TestScriptAutomationSample.xls";
		File DataManager = new File(path);
		Workbook wb = Workbook.getWorkbook(DataManager);

		int rows = wb.getSheet("TestData").getRows();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		List<String> rowvalues3 = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
			//System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet("TestData").getCell(0, i).getContents());
			rowvalues1.add(wb.getSheet("TestData").getCell(3, i).getContents());
			rowvalues2.add(wb.getSheet("TestData").getCell(2, i).getContents());
			rowvalues3.add(wb.getSheet("TestData").getCell(1, i).getContents());

		}

		// int x=0;

		for (int x = 0; x < rowvalues.size(); x++) {

			if (rowvalues.get(x).equalsIgnoreCase(testcaseNum)) {
				for (int y = 0; y < rowvalues3.size(); y++) {
					if (rowvalues3.get(y).equalsIgnoreCase("#AddressFormInput")) {

						for (int z = 0; z < rowvalues2.size(); z++) {
							if (rowvalues2.get(z).equalsIgnoreCase("Zip")) {
								zip = rowvalues1.get(z);
							}
						}
					}
				}
			}
		}

		return zip;

	}

	public String getApt(WebDriver driver, String testcaseNum) throws Exception {

		String apt = null;
		// try{
		WebDriverWait wait = new WebDriverWait(driver, 20);

		//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
		String path = System.getProperty("user.dir")+"\\TestScriptAutomationSample.xls";
		File DataManager = new File(path);
		Workbook wb = Workbook.getWorkbook(DataManager);

		int rows = wb.getSheet("TestData").getRows();

		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		List<String> rowvalues3 = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {
		//	System.out.println("rows are" + rows);
			rowvalues.add(wb.getSheet("TestData").getCell(0, i).getContents());
			rowvalues1.add(wb.getSheet("TestData").getCell(3, i).getContents());
			rowvalues2.add(wb.getSheet("TestData").getCell(2, i).getContents());
			rowvalues3.add(wb.getSheet("TestData").getCell(1, i).getContents());

		}

		// int x=0;

		for (int x = 0; x < rowvalues.size(); x++) {

			if (rowvalues.get(x).equalsIgnoreCase(testcaseNum)) {
				for (int y = 0; y < rowvalues3.size(); y++) {
					if (rowvalues3.get(y).equalsIgnoreCase("#AddressFormInput")) {

						for (int z = 0; z < rowvalues2.size(); z++) {
							if (rowvalues2.get(z).equalsIgnoreCase("Address Line 2")) {
								apt = rowvalues1.get(z);
							}
						}
					}
				}
			}
		}

		return apt;

	}

}
