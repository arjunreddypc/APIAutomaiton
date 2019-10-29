package com.sprint.aiva.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sprint.aiva.model.ServiceDetails;
import com.sprint.aiva.model.TestData;

public class UtilityService {

	public static List<TestData> testData = new ArrayList<TestData>();

	public static List<ServiceDetails> serviceDetails = new ArrayList<ServiceDetails>();

	public static Map<String, List<String>> outputDetails = new LinkedHashMap<String, List<String>>();

	public static void readExcelFile() {

		try {

			FileInputStream inputStream = new FileInputStream(new File(AIVAConstants.excellPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			DataFormatter formatter = new DataFormatter();
			XSSFSheet inputSheet = workbook.getSheet("INPUT_SHEET");
			XSSFSheet apiInfoSheet = workbook.getSheet("API_CONSOLE_INFO");

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
				serviceDetails.add(service);
			}

		} catch (Exception e) {
			System.out.println("There is an issue in retrieving from EXcel" + e);
		}
	}

	public static void writeToExcelFile() {
		try {
			FileInputStream inputStream = new FileInputStream(new File(AIVAConstants.excellPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			FileOutputStream fileOut = new FileOutputStream(new File(AIVAConstants.apiOutputSheet));
			int i = 0;

			for (Map.Entry<String, List<String>> entry : outputDetails.entrySet()) {

				XSSFRow row = sheet.createRow(i);
				XSSFCell cell = row.createCell(0);
				cell.setCellValue(entry.getKey());

				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				List<String> cellValues = entry.getValue();
				for (int cellIndex = 1; cellIndex <= cellValues.size(); cellIndex++) {
					row.createCell(cellIndex).setCellValue(cellValues.get(cellIndex - 1));
				}
				i++;

			}
			workbook.write(fileOut);
		} catch (Exception exception) {
			System.out.println("Error in writing excel " + exception);
		} finally {

		}
	}

	public static void invokeService() {
		
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			if (true) {
				ctx.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
			}
			// This will prepare the headers
			prepareExcelHeaders();
			SSLContext.setDefault(ctx);
			HttpClient client = null;
			Iterator<TestData> datIterator = testData.iterator();
			List<String> ban = new ArrayList<String>();
			List<String> bans = new ArrayList<String>();
			List<String> subscriber = new ArrayList<String>();
			int temp=0;
			
			while (datIterator.hasNext()) {
				
				TestData data = datIterator.next();
				ban.add(data.getBan());
				List<String> flagValues = new ArrayList<>();
				flagValues.add(data.getSmUser());
				System.out.println("For BAN " + data.getBan());
				Iterator<ServiceDetails> serviceIterator = serviceDetails.iterator();
				while (serviceIterator.hasNext()) {
					
					ServiceDetails service = serviceIterator.next();
					client = new HttpClient();
					String updatedUrl = service.getURL().contains("$BAN")
							? service.getURL().replace("$BAN", data.getBan()) : service.getURL();
					GetMethod method = new GetMethod(updatedUrl);

					method.setRequestHeader("accountId", data.getBan());					
					method.setRequestHeader("sm_user", data.getSmUser());
					method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
					method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
					method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
					method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
					method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
					
					// Execute the method.
					int statusCode = client.executeMethod(method);

					if (statusCode != HttpStatus.SC_OK) {
						System.err.println("Method failed: " + method.getStatusLine());
					}
					String response = method.getResponseBodyAsString();
					System.out.println(response);
					String[] subscriberIds=response.split("\"id\": \"");
					String[] ModifiedSubscriberIds = Arrays.copyOfRange(subscriberIds, 1, subscriberIds.length);
					System.out.println(ModifiedSubscriberIds.length);
					for(String sub:ModifiedSubscriberIds) {
						String[] subids=sub.split("\",");
						subscriber.add(subids[0]);
						System.out.println("Subscriber ID's for ban "+data.getBan()+" is "+subids[0]);
					}
					/*for (int i = 1; i < subscriber.size(); i++) {
						data.setBan((ban.get(0)));
						data.setSubscriber(subscriber.get(i));
						testData.add(data);
					}*/
					/*System.out.println(service.getRequiredFlags().size());
					for (int i = 0; i < service.getRequiredFlags().size(); i++) {

						System.out.println(service.getRequiredFlags().get(i) + ":"
								+ getJsonValue(response, service.getRequiredFlags().get(i)));
						flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));

					}
					System.out.println("Validation completed for " + service.getName());*/
				}
				outputDetails.put(data.getBan(), flagValues);				
				 for (int i=0;i<ban.size();i++)
					{
						for(int j=0;j<subscriber.size();j++)
					{
						bans.add((ban.get(i)));
						
						}		
					}	
				 ban.clear();
			}	
			 
			String path = System.getProperty("user.dir")+"\\demo"+".xlsx";
			System.out.println(path);
			FileOutputStream out = new FileOutputStream(new File(path));			
			XSSFWorkbook workbook=new XSSFWorkbook();
			XSSFSheet sheet=workbook.createSheet("subscriberlist");	   
			Row header=sheet.createRow(0);
			header.createCell(0).setCellValue("Ban");
			header.createCell(1).setCellValue("Subscriber");
			
					for(int j=0;j<subscriber.size();j++)
					{
					Row row=sheet.createRow(temp+1);
					Cell cell1=row.createCell(0);
					cell1.setCellValue(bans.get(j));
					Cell cell=row.createCell(1);
					cell.setCellValue(subscriber.get(j));
					temp=temp+1;
					}		
				
		        workbook.write(out);
		        out.close();
		        workbook.close();
			
	        
		} catch (Exception e) {
			System.out.println("error in invoke service " + e);

		} finally {
			System.out.println("");
		}
		
		
	}

	public static String getJsonValue(String JSONString, String Field) {
		try {
			String[] value = JSONString
					.substring(JSONString.indexOf(Field), JSONString.indexOf("\n", JSONString.indexOf(Field)))
					.replace(Field + "\": \"", "").replace("\"", "").replace(",", "").split(":");
			return value.length > 1 ? value[1] : value[0];
		} catch (Exception e) {

		}
		return "NA";
	}

	public static void prepareExcelHeaders() {
		List<String> requiredFlags = new ArrayList<String>();
		Iterator<ServiceDetails> iterator = serviceDetails.iterator();
		requiredFlags.add("SM_USER");
		while (iterator.hasNext()) {
			ServiceDetails service = iterator.next();
			for (int i = 0; i < service.getRequiredFlags().size(); i++) {
				requiredFlags.add(service.getRequiredFlags().get(i));
			}
		}
		outputDetails.put("BAN", requiredFlags);
	}
	
	
}
