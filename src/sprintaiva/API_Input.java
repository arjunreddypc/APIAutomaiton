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


public class API_Input {

	private WebDriver driver;
	CommonMethods cm = new CommonMethods();
	EnquiryChat ec = new EnquiryChat();
	Test_Websevices tw = new Test_Websevices();
	
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
		
		
		
		int rows = wb.getSheet("INPUT_SHEET").getRows();
		System.out.println(rows);
		int cols = wb.getSheet("INPUT_SHEET").getColumns();
		System.out.println(cols);
		List<String> rowvalues = new ArrayList<String>();
		List<String> rowvalues1 = new ArrayList<String>();
		List<String> rowvalues2 = new ArrayList<String>();
		
		
		for (int k = 0; k < rows; k++) {
			
			for (int l = 0; l < cols; l++) {
				 rowvalues1.add(wb.getSheet("INPUT_SHEET").getCell(l, k).getContents());
				
		
				
		if(rowvalues1.get(l).matches("API NAME")){

			
		for (int i = 1; i < rows; i++) {
			for (int j = 3; j < cols; j++) {
				rowvalues.add(wb.getSheet("INPUT_SHEET").getCell(j, i).getContents());
				if(j == 3){
					
				
				//rowvalues.add(wb.getSheet("API_CONSOLE_INFO").getCell(j, i).getContents());
				String url = rowvalues.get(0);
				driver.get(url);
				}
			
		
			}
		}	
	
	}
}


		wwbCop.write();
		wwbCop.close();
}
}
}