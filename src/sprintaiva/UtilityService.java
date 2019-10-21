package sprintaiva;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UtilityService {

	public static void readExcelFile() {

		try {

			FileInputStream inputStream = new FileInputStream(new File(AIVAConstants.excellPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			DataFormatter formatter = new DataFormatter();
			XSSFSheet inputSheet = workbook.getSheet("INPUT_SHEET");
			XSSFSheet apiInfoSheet = workbook.getSheet("API_CONSOLE_INFO");

			List<TestData> testData = new ArrayList<TestData>();

			List<ServiceDetails> serviceDetails = new ArrayList<ServiceDetails>();

			// Fetch details related to test data
			for (int i = 1; i < inputSheet.getPhysicalNumberOfRows(); i++) {
				TestData data = new TestData();
				data.setBan(formatter.formatCellValue(inputSheet.getRow(i).getCell(0)));
				data.setSmUser(formatter.formatCellValue(inputSheet.getRow(i).getCell(1)));
				testData.add(data);
			}

			// Fetch details related to API
			for (int i = 1; i < apiInfoSheet.getPhysicalNumberOfRows(); i++) {

				ServiceDetails service = new ServiceDetails();
				service.setName(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(0)));
				service.setURL(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(1)));
				service.setServiceType(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(2)));
				String[] flags = formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(3)).split(",");
				service.setRequiredFlags(Arrays.asList(flags));
				service.setResponseType(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(4)));
				serviceDetails.add(service);
			}

			System.out.println("API Details " + serviceDetails);

		} catch (Exception e) {

		}
	}

	public static void writeToExcelFile() {

	}

	public static void invokeService() {

	}
}
