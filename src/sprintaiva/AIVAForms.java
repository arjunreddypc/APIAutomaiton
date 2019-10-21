package sprintaiva;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import jxl.Workbook;

public class AIVAForms {

	public static Set<String> getTDTestCaseNum() {
		Set<String> testCase = new HashSet<String>();
		
		try {
			File DataManager = new File("C:\\Users\\ko706953\\Selenium\\Test Script Automation Sample.xls");
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet(AIVAConstants.sheetName2).getRows();
			for (int i = 1; i < rows; i++) {
				testCase.add(wb.getSheet(AIVAConstants.sheetName2).getCell(0, i).getContents());
			}
			wb.close();
		} catch (Exception e) {

		}
		return testCase;
	}
	
	
	public static Set<String> getTDTestData() {
		Set<String> testData = new HashSet<String>();
		try {
			File DataManager = new File("C:\\Users\\ko706953\\Selenium\\Test Script Automation Sample.xls");
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet(AIVAConstants.sheetName2).getRows();
			for (int i = 1; i < rows; i++) {
				testData.add(wb.getSheet(AIVAConstants.sheetName2).getCell(3, i).getContents());
			}
			wb.close();
		} catch (Exception e) {

		}
		return testData;
	}

	public static Set<String> getTDTestDataType() {
		Set<String> testDataType = new HashSet<String>();
		try {
			File DataManager = new File("C:\\Users\\ko706953\\Selenium\\Test Script Automation Sample.xls");
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet(AIVAConstants.sheetName2).getRows();
			for (int i = 1; i < rows; i++) {
				testDataType.add(wb.getSheet(AIVAConstants.sheetName2).getCell(2, i).getContents());
			}
			wb.close();
		} catch (Exception e) {

		}
		return testDataType;
	}
	
	public static Set<String> getTCTestCaseNum() {
		Set<String> tctestCaseNum = new HashSet<String>();
		try {
			//File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
			String path = System.getProperty("user.dir")+"\\API.xlsx";
			File DataManager = new File(path);
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet(AIVAConstants.sheetName1).getRows();
			for (int i = 1; i < rows; i++) {
				tctestCaseNum.add(wb.getSheet(AIVAConstants.sheetName1).getCell(0, i).getContents());
			}
			wb.close();
		} catch (Exception e) {

		}
		return tctestCaseNum;
	}
	
	public static Set<String> getTCInput() {
		Set<String> tctestInput = new HashSet<String>();
		try {
			File DataManager = new File("C:\\Users\\ko706953\\Selenium\\Test Script Automation Sample.xls");
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet(AIVAConstants.sheetName1).getRows();
			for (int i = 1; i < rows; i++) {
				tctestInput.add(wb.getSheet(AIVAConstants.sheetName1).getCell(3, i).getContents());
			}
			wb.close();
		} catch (Exception e) {

		}
		return tctestInput;
	}
	
	public static Set<String> getTCTestDataType() {
		Set<String> tcDataType = new HashSet<String>();
		try {
			File DataManager = new File("C:\\Users\\ko706953\\Selenium\\Test Script Automation Sample.xls");
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet(AIVAConstants.sheetName1).getRows();
			for (int i = 1; i < rows; i++) {
				tcDataType.add(wb.getSheet(AIVAConstants.sheetName1).getCell(2, i).getContents());
			}
			wb.close();
		} catch (Exception e) {

		}
		return tcDataType;
	}
	
	public static Set<String> getTCTestCaseNumUtter() {
		Set<String> tctestCaseNum = new HashSet<String>();
		try {
			File DataManager = new File("C:\\Users\\ko706953\\Selenium\\TestScriptAutomationSample.xls");
			Workbook wb = Workbook.getWorkbook(DataManager);
			int rows = wb.getSheet(AIVAConstants.sheetName3).getRows();
			for (int i = 1; i < rows; i++) {
				tctestCaseNum.add(wb.getSheet(AIVAConstants.sheetName3).getCell(0, i).getContents());
			}
			wb.close();
		} catch (Exception e) {

		}
		return tctestCaseNum;
	}
}
