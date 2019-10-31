package com.sprint.aiva.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
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
							JSONArray array = new JSONArray(response);
							for(int i=0;i<array.length();i++) {
								JSONObject  object=array.getJSONObject(i);
								String subscriberId = object.getString("id");
								System.out.println("id is "+subscriberId);
								subscriber.add(subscriberId);
							}
							System.out.println(response);
							String[] subscriberIds=response.split("\"id\": \"");
							String[] ModifiedSubscriberIds = Arrays.copyOfRange(subscriberIds, 1, subscriberIds.length);
							System.out.println(ModifiedSubscriberIds.length);
							/*for(String sub:ModifiedSubscriberIds) {
						String[] subids=sub.split("\",");
						subscriber.add(subids[0]);
						System.out.println("Subscriber ID's for ban "+data.getBan()+" is "+subids[0]);
					}*/
							/*for (int i = 1; i < subscriber.size(); i++) {
						data.setBan((ban.get(0)));
						data.setSubscriber(subscriber.get(i));
						testData.add(data);
					}*/
							System.out.println(service.getRequiredFlags().size());
							for (int i = 0; i < service.getRequiredFlags().size(); i++) {

								System.out.println(service.getRequiredFlags().get(i) + ":"
										+ getJsonValue(response, service.getRequiredFlags().get(i)));
								flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));

							}
							System.out.println("Validation completed for " + service.getName());
				}
				outputDetails.put(data.getBan(), flagValues);				
				if(subscriber.size()>0) {
					for (int i=0;i<ban.size();i++)
					{
						for(int j=0;j<subscriber.size();j++)
						{
							bans.add((ban.get(i)));

						}		
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

	public static void invokeServiceUC54() throws JSONException {
		
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
			List<String> bans = new ArrayList<String>();
			List<String> smUsers = new ArrayList<String>();
			List<String> ban = new ArrayList<String>();			
			List<String> subscriber = new ArrayList<String>();
			List<String> subscribers = new ArrayList<String>();
			List<String> nickName = new ArrayList<String>();
			List<String> ptn = new ArrayList<String>();
			List<String> modelName = new ArrayList<String>();
			List<String> status = new ArrayList<String>();
			List<String> deviceType = new ArrayList<String>();
			List<String> contracts = new ArrayList<String>();
			List<String> eligibiltiy = new ArrayList<String>();
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
							JSONArray array = new JSONArray(response);
							System.out.println("No of subscribers are in response is "+array.length());
							for(int i=0;i<array.length();i++) {
								JSONObject  object=array.getJSONObject(i);
								String subscriberId = object.getString("id");
								String status1 = object.getString("status");
								String nickName1 = object.getString("nickName");
								String ptn1 = object.getString("ptn");
								String modelName1 = object.getString("modelName");
								String deviceType1 = object.getString("deviceType");
								System.out.println("id is "+subscriberId);
								subscriber.add(subscriberId);
								subscribers.add(subscriberId);
								status.add(status1);
								nickName.add(nickName1);
								ptn.add(ptn1);
								modelName.add(modelName1);
								deviceType.add(deviceType1);
							}
							/*}
							else {
								ban.clear();
								bans.clear();
								subscriber.clear();
							}*/
							System.out.println(response);
							/*String[] subscriberIds=response.split("\"id\": \"");
							String[] ModifiedSubscriberIds = Arrays.copyOfRange(subscriberIds, 1, subscriberIds.length);
							System.out.println(ModifiedSubscriberIds.length);*/
							/*for(String sub:ModifiedSubscriberIds) {
						String[] subids=sub.split("\",");
						subscriber.add(subids[0]);
						System.out.println("Subscriber ID's for ban "+data.getBan()+" is "+subids[0]);
					}*/
							/*for (int i = 1; i < subscriber.size(); i++) {
						data.setBan((ban.get(0)));
						data.setSubscriber(subscriber.get(i));
						testData.add(data);
					}*/
							System.out.println(service.getRequiredFlags().size());
							for (int i = 0; i < service.getRequiredFlags().size(); i++) {
								
								System.out.println(service.getRequiredFlags().get(i) + ":"
										+ getJsonValue(response, service.getRequiredFlags().get(i)));
								flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));
								
							}
							System.out.println("Validation completed for " + service.getName());
				}
				outputDetails.put(data.getBan(), flagValues);				
				if(subscriber.size()>0) {
						for(int j=0;j<subscriber.size();j++)
						{
							bans.add((ban.get(0)));
							smUsers.add(data.getSmUser());				
							
						}		
				}
				ban.clear();
				subscriber.clear();
			}	
			
			
			String path = System.getProperty("user.dir")+"\\demo"+".xlsx";
			System.out.println(path);
			FileOutputStream out = new FileOutputStream(new File(path));			
			XSSFWorkbook workbook=new XSSFWorkbook();
			XSSFSheet sheet=workbook.createSheet("subscriberlist");	   
			Row header=sheet.createRow(0);
			header.createCell(0).setCellValue("Ban");
			header.createCell(1).setCellValue("Subscriber");
			header.createCell(2).setCellValue("SMuser");
			header.createCell(3).setCellValue("Status");
			header.createCell(4).setCellValue("NickName");
			header.createCell(5).setCellValue("PTN");
			header.createCell(6).setCellValue("ModelName");
			header.createCell(7).setCellValue("DeviceType");
			header.createCell(8).setCellValue("Contracts");
			header.createCell(9).setCellValue("Eligibility");
			
			for(int j=0;j<subscribers.size();j++)
			{
				Row row=sheet.createRow(temp+1);
				Cell cell1=row.createCell(0);
				cell1.setCellValue(bans.get(j));
				Cell cell=row.createCell(1);
				cell.setCellValue(subscribers.get(j));
				Cell cell2=row.createCell(2);
				cell2.setCellValue(smUsers.get(j));				
				Cell cell3=row.createCell(3);
				cell3.setCellValue(status.get(j));
				Cell cell4=row.createCell(4);
				cell4.setCellValue(nickName.get(j));
				Cell cell5=row.createCell(5);
				cell5.setCellValue(ptn.get(j));
				Cell cell6=row.createCell(6);
				cell6.setCellValue(modelName.get(j));
				Cell cell7=row.createCell(7);
				cell7.setCellValue(deviceType.get(j));
				temp=temp+1;
			}		
			Iterator<String> bansIterator=bans.iterator();
			Iterator<String> subscriberIterator=subscribers.iterator();
			Iterator<String> smUserIterator=smUsers.iterator();
			while(subscriberIterator.hasNext()) {
				String $ban=bansIterator.next();
				String $subscriber=subscriberIterator.next();
				String $smUser=smUserIterator.next();
				System.out.println("details are "+$ban+$subscriber+$smUser);
				String updatedUrl = "https://st1-apiservices-sen.test.sprint.com:8441/api/process/sub/v1/accounts/"+$ban+"/subscriptions/"+$subscriber+"/contract";
						GetMethod method = new GetMethod(updatedUrl);
						
						method.setRequestHeader("accountId", $ban);					
						method.setRequestHeader("sm_user", $smUser);
						method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
						method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
						method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
						method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
						method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
						
						// Execute the method.
						int statusCode = client.executeMethod(method);
						System.out.println("status is "+statusCode);
						if (statusCode != HttpStatus.SC_OK) {
							System.err.println("Method failed: " + method.getStatusLine());
						}
						String response = method.getResponseBodyAsString();					
						/*JSONObject object = new JSONObject(response);
						JSONArray array=object.getJSONArray("contracts");
							JSONObject  object1=array.getJSONObject(0);
							String contractType = object1.getString("contractType");
							System.out.println("id is "+contractType);*/
						contracts.add(response);
						
						
			}
			temp=0;
			for(int j=0;j<subscribers.size();j++)
			{
				Row row=sheet.getRow(temp+1);
				Cell cell8=row.createCell(8);
				cell8.setCellValue(contracts.get(j));
				temp=temp+1;
			}		
			bansIterator=bans.iterator();
			smUserIterator=smUsers.iterator();
			subscriberIterator=subscribers.iterator();
			while(subscriberIterator.hasNext()) {
				String $ban=bansIterator.next();
				String $subscriber=subscriberIterator.next();
				String $smUser=smUserIterator.next();
				System.out.println("details are "+$ban+$subscriber+$smUser);
				String updatedUrl = "https://st1-apiservices-sen.test.sprint.com:8441/api/process/sub/v1/accounts/"+$ban+"/subscriptions/"+$subscriber+"/upgrade-eligibility?checkEarlyUpgrade=true&fetchAdditionalInfoKey=JUMPUPGRADE";
						GetMethod method = new GetMethod(updatedUrl);
						
						method.setRequestHeader("accountId", $ban);					
						method.setRequestHeader("sm_user", $smUser);
						method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
						method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
						method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
						method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
						method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
						
						// Execute the method.
						int statusCode = client.executeMethod(method);
						System.out.println("status is "+statusCode);
						if (statusCode != HttpStatus.SC_OK) {
							System.err.println("Method failed: " + method.getStatusLine());
						}
						String response = method.getResponseBodyAsString();					
						eligibiltiy.add(response);
						
						
			}
			temp=0;
			for(int j=0;j<subscribers.size();j++)
			{
				Row row=sheet.getRow(temp+1);
				Cell cell9=row.createCell(9);
				cell9.setCellValue(eligibiltiy.get(j));
				temp=temp+1;
			}		
			workbook.write(out);
			out.close();
			workbook.close();
			
		} catch (Exception e ) {
			System.out.println("error in invoke service " + e);
			e.printStackTrace();
			
		}finally {
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
