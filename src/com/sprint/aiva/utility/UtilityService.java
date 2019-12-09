package com.sprint.aiva.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.poi.hpsf.Array;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import com.sun.xml.internal.ws.api.message.HeaderList;

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
			//C:\ProgramData\Eclipse\eclipse-workspace\eclipse-workspace\APIAutomaiton\demo.xlsx
			System.out.println(System.getProperty("user.dir"));
			

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
	public static void readExcelFileBANAndSubscriber() throws IOException {
		
		try {
			
			FileInputStream inputStream = new FileInputStream(new File(AIVAConstants.excellPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			DataFormatter formatter = new DataFormatter();
			XSSFSheet apiInfoSheet = workbook.getSheet("API_CONSOLE_INFO");
			//C:\ProgramData\Eclipse\eclipse-workspace\eclipse-workspace\APIAutomaiton\demo.xlsx
			System.out.println(System.getProperty("user.dir"));
			
			
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
		DataFormatter formatter = new DataFormatter();
		ArrayList<String> bans=new ArrayList<String>();
		ArrayList<String> subscribers=new ArrayList<String>();
		System.out.println(System.getProperty("user.dir"));
		File f=new File(System.getProperty("user.dir")+"\\Results.xlsx");
		FileInputStream fi=new FileInputStream(f);
		XSSFWorkbook workbook=new XSSFWorkbook(fi);
		Sheet sheet=workbook.getSheet("subscriberlist");
		System.out.println(sheet.getLastRowNum()+" is last row number");
		System.out.println(sheet.getFirstRowNum()+" is first row number");
		int rows=sheet.getPhysicalNumberOfRows();
		Row row=sheet.getRow(0);
		// Fetch details related to test data
					for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
						TestData data = new TestData();
						data.setBan(formatter.formatCellValue(sheet.getRow(i).getCell(13)));
						data.setSubscriber(formatter.formatCellValue(sheet.getRow(i).getCell(9)));
						testData.add(data);
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
			List<String> accountcontracts = new ArrayList<String>();
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
							boolean b=response.startsWith("[");
							System.out.println(b);
							try {
							if(b==true)
							{
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
							}
							else
							{
									JSONObject  object=new JSONObject(response);
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
							}
							catch(Exception E)
							{
								E.printStackTrace();
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
			header.createCell(10).setCellValue("Account Contract");
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
			/*bansIterator=bans.iterator();
			smUserIterator=smUsers.iterator();
			//subscriberIterator=subscribers.iterator();
			while(bansIterator.hasNext()) {
				String $ban=bansIterator.next();
				//String $subscriber=subscriberIterator.next();
				String $smUser=smUserIterator.next();
				System.out.println("details are "+$ban+$smUser);
				String updatedUrl = "https://st1-apiservices-sen.test.sprint.com:8441/api/process/sub/v1/accounts/"+$ban+"/contracts/";
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
						accountcontracts.add(response);
						
						
			}
			temp=0;
			for(int j=0;j<subscribers.size();j++)
			{
				Row row=sheet.getRow(temp+1);
				Cell cell10=row.createCell(9);
				cell10.setCellValue(accountcontracts.get(j));
				temp=temp+1;
			}*/
			
			workbook.write(out);
			out.close();
			workbook.close();
			
			
		} 

		catch (Exception e ) {
			System.out.println("error in invoke service " + e);
			e.printStackTrace();
			
		}finally {
			System.out.println("");
		}
		
	
		
	}
	public static void invokeServiceGeneric() throws JSONException, IOException {
		String path = System.getProperty("user.dir")+"\\Results"+".xlsx";
		FileOutputStream out=new FileOutputStream(new File(path));
		XSSFWorkbook workbook=new XSSFWorkbook();
		int temp=1;
		try {
			
			XSSFSheet sheet=workbook.createSheet("subscriberlist");	   
			Row header;
			SSLContext ctx = SSLContext.getInstance("TLS");
			if (true) {
				ctx.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
			}
			// This will prepare the headers
			prepareExcelHeaders();
			SSLContext.setDefault(ctx);
			HttpClient client = null;
			HashMap<String, String> hm=new HashMap<>();
			Iterator<TestData> datIterator = testData.iterator();
			
			ArrayList<String> al=new ArrayList<>();
			
			
				Iterator<ServiceDetails> serviceIterator = serviceDetails.iterator();
				while (serviceIterator.hasNext()) {
					ServiceDetails service = serviceIterator.next();
					System.out.println(service.getName());
					datIterator = testData.iterator();
					temp=1;
					while (datIterator.hasNext()) {
						
						TestData data = datIterator.next();
						List<String> flagValues = new ArrayList<>();
						flagValues.add(data.getSmUser());
						System.out.println("For BAN " + data.getBan());					
					    System.out.println(service.getURL());
					    if(service.getName().contains("subscriptions")) {
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
									System.out.println("Response is "+response);
									boolean b=response.startsWith("[");
									System.out.println(b);
									try {
										if(b==true)
										{
											JSONArray array = new JSONArray(response);
											System.out.println("No of subscribers are in response is "+array.length());
											for(int i=0;i<array.length();i++) {
												JSONObject  object=array.getJSONObject(i);
												System.out.println(object.keySet());
												al.clear();
												al.addAll(object.keySet());
												al.add("ban");
												for(String arrayList:al) {
													System.out.println(arrayList);
													if(arrayList.contains("address")) 
													{
														String address=object.getJSONObject(arrayList).toString();
														System.out.println(object.getJSONObject(arrayList).toString());
														hm.put(arrayList, address);
													}
													else if(arrayList.contains("unlockSimCapable") || arrayList.contains("primary")) 
													{
														boolean bln=object.getBoolean(arrayList);
														hm.put(arrayList, String.valueOf(bln));
													}
													else if(arrayList.contains("ban")) 
													{
														hm.put("ban", data.getBan());
													}
													else {
														System.out.println(arrayList);
														hm.put(arrayList, object.getString(arrayList));
													}
													
												}
												String subscriberId = object.getString("id");
												System.out.println("id is "+subscriberId);
												System.out.println(path);
												if (temp==1) {
													header=sheet.createRow(0);
													System.out.println("headers list is size is" + al.size());
													for (int k = 0; k < al.size(); k++) {
														header.createCell(k).setCellValue(al.get(k));
													}
												}
												header=sheet.createRow(temp);
												for(int k=0;k<al.size();k++) {
													
													header.createCell(k).setCellValue(hm.get(al.get(k)));
												}	
												hm.clear();
												temp=++temp;
												
												
											}	
											
										}
										
										else
										{
											JSONObject  object=new JSONObject(response);
											if(object.toString().contains("errorMessage")) {
												hm.put("ban", data.getBan());
												hm.put("status", object.toString());
												System.out.println(hm.get("ban")); 
												System.out.println(hm.get("status")); 
												
												header=sheet.createRow(temp);
												header.createCell(al.size()-1).setCellValue(hm.get("ban"));
												header.createCell(al.size()).setCellValue(hm.get("status"));
												hm.clear();
												temp=++temp;
											}
											
										}
										
									}
									catch(Exception E)
									{
										E.printStackTrace();
									}
									System.out.println(service.getRequiredFlags().size());
									for (int i = 0; i < service.getRequiredFlags().size(); i++) {
										
										System.out.println(service.getRequiredFlags().get(i) + ":"
												+ getJsonValue(response, service.getRequiredFlags().get(i)));
										flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));
										
									}
									System.out.println("Validation completed for " + service.getName());
						}
					if(service.getURL().contains("financial-status")) {
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"aslAccount","totalDue","pastDueAmount","pastDue","estimatedAmount","billAmount","arBalance","adjustmentsAndCredits","recentPaymentsTotal","dueDate","noOfDaysToDueDate","nextDueDate","lastPayment","lastPaymentDate","recentBillSequenceNumber","aslNetBalance","aslSpendingLimit","aslPercentageUsed","aslBanSuspensionLimit","monthlyRecurringCharge"};
						for(String hl:headersList) {
							hs.add(hl);
						}
						client = new HttpClient();
						al.clear();
						if(temp==1) {
						sheet=workbook.createSheet("financial-status");
						}
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
								System.out.println("Response is "+response);
								boolean b=response.startsWith("[");
								boolean b1=response.startsWith("{");
								System.out.println(b);
								try {
									if(b==true && !response.contains("errorMessage"))
									{
										JSONArray array = new JSONArray(response);
										System.out.println("No of subscribers are in response is "+array.length());
										for(int i=0;i<array.length();i++) {
											JSONObject  object=array.getJSONObject(i);
											System.out.println(object.keySet());
											al.clear();
											al.addAll(object.keySet());
											al.add("ban");
											for(String arrayList:al) {
												System.out.println(arrayList);
												if(arrayList.contains("address")) 
												{
													String address=object.getJSONObject(arrayList).toString();
													System.out.println(object.getJSONObject(arrayList).toString());
													hm.put(arrayList, address);
												}												
												else if(arrayList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												else {
													System.out.println(arrayList);
													hm.put(arrayList, String.valueOf(object.get(arrayList)));
												}
												
											}
											if (temp==1) {
																								
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + al.size());
												for (int k = 0; k < al.size(); k++) {
													header.createCell(k).setCellValue(al.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<al.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(al.get(k)));
											}	
											hm.clear();
											temp=++temp;
											
											
										}	
										
									}
									else if (b1) {
										{
											System.out.println(response);
											JSONObject object = new JSONObject(response);
											System.out.println("No of subscribers are in response is "+object.length());
												al.clear();
												al.addAll(object.keySet());
												hs.add("ban");
												for(String headerList:hs) {
													System.out.println(hs);
													if(headerList.contains("ban")) 
													{
														hm.put("ban", data.getBan());
													}
													else {
														System.out.println(headerList);
														try {
															hm.put(headerList, String.valueOf(object.get(headerList)));
														} catch (Exception e) {
															hm.put(headerList, "");
														}
													}
													
												}
												System.out.println(path);
												if (temp==1) {
													header=sheet.createRow(0);
													System.out.println("headers list is size is" + hs.size());
													for (int k = 0; k < hs.size(); k++) {
														header.createCell(k).setCellValue(hs.get(k));
													}
												}
												header=sheet.createRow(temp);
												for(int k=0;k<hs.size();k++) {
													
													header.createCell(k).setCellValue(hm.get(hs.get(k)));
												}	
												hm.clear();
												temp=++temp;
												
												
												}
									}
									else if(response.toString().contains("errorMessage"))
									{
										JSONObject  object=new JSONObject(response);
										if(object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status"));											
											header=sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp=++temp;
										}
										
									}
									else if(response.toString().contains("Service not available")){
										if(response.toString().contains("Service not available")) {
											hm.put("ban", data.getBan());
											hm.put("status", response.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status"));											
											header=sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp=++temp;
										}
									}
									
								}
								catch(Exception E)
								{
									E.printStackTrace();
								}
								System.out.println(service.getRequiredFlags().size());
								for (int i = 0; i < service.getRequiredFlags().size(); i++) {
									
									System.out.println(service.getRequiredFlags().get(i) + ":"
											+ getJsonValue(response, service.getRequiredFlags().get(i)));
									flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));
									
								}
								System.out.println("Validation completed for " + service.getName());
					}
					if(service.getURL().contains("future-payments")) {
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"paymentType","amount","date","number","creditCardType","type","confirmationNumber","paSeqNumber","nickname","paymentMethodId","expDate","autopayInd"};
						for(String hl:headersList) {
							hs.add(hl);
							
						}
						System.out.println("future payments headers size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("future-payments");
						}
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
								System.out.println("Response is "+response);
								boolean b=response.startsWith("[");
								boolean b1=response.startsWith("{");
								System.out.println(b);
								try {
									if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()>0)
									{
										JSONArray array = new JSONArray(response);
										System.out.println("No of subscribers are in response is "+array.length());
										for(int i=0;i<array.length();i++) {
											JSONObject  object=array.getJSONObject(i);
											System.out.println(object.keySet());
											hs.add("ban");
											for(String headerList:hs) {
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												else {
													System.out.println(headerList);
													try {
													hm.put(headerList, String.valueOf(object.get(headerList)));
													}catch(Exception E) {
														hm.put(headerList, "");
													}
												}
												
											}
											if (temp==1) {
												
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											hm.clear();
											temp=++temp;
											if(temp>1) {
												hs.remove("ban");
											}
											
										}	
										
									}
									else if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()==0)
									{
										JSONArray array = new JSONArray(response);
										System.out.println("No of subscribers are in response is "+array.length());
											hs.add("ban");
											for(String headerList:hs) {
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
													
												}
												else {
													System.out.println(headerList);
													try {
														hm.put(headerList, String.valueOf(""));
													}catch(Exception E) {
														hm.put(headerList, "");
													}
												}
												
											}
											if (temp==1) {
												
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											hm.clear();
											temp=++temp;
											
										
									}
									else if (b1) {
										{
											System.out.println(response);
											JSONObject object = new JSONObject(response);
											System.out.println("No of subscribers are in response is "+object.length());
											al.clear();
											al.addAll(object.keySet());
											hs.add("ban");
											for(String headerList:hs) {
												System.out.println(hs);
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												else {
													System.out.println(headerList);
													try {
														hm.put(headerList, String.valueOf(object.get(headerList)));
													} catch (Exception e) {
														hm.put(headerList, "");
													}
												}
												
											}
											System.out.println(path);
											if (temp==1) {
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											hm.clear();
											temp=++temp;
											hs.remove("ban");
											
										}
									}
									else if(response.toString().contains("errorMessage"))
									{
										JSONObject  object=new JSONObject(response);
										if(object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status"));											
											header=sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp=++temp;
										}
										
									}
									else if(response.toString().contains("Service not available")){
										if(response.toString().contains("Service not available")) {
											hm.put("ban", data.getBan());
											hm.put("status", response.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status"));											
											header=sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp=++temp;
										}
									}
									
								}
								catch(Exception E)
								{
									E.printStackTrace();
								}
								System.out.println(service.getRequiredFlags().size());
								for (int i = 0; i < service.getRequiredFlags().size(); i++) {
									
									System.out.println(service.getRequiredFlags().get(i) + ":"
											+ getJsonValue(response, service.getRequiredFlags().get(i)));
									flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));
									
								}
								System.out.println("Validation completed for " + service.getName());
					}
					if(service.getURL().contains("payment-methods")) {
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"id","paymentMethodToken","type","creditCardType","number","expDate","nickname","isAutopay","isPrimary","name","address1","address2","city","state","zip","securityCode","isPinlessDebit","isCardAOOwned"};
						for(String hl:headersList) {
							hs.add(hl);
							
						}
						System.out.println("payment methods headers size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("payment methods");
						}
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
								System.out.println("Response is "+response);
								boolean b=response.startsWith("[");
								boolean b1=response.startsWith("{");
								System.out.println(b);
								try {
									if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()>0)
									{
										JSONArray array = new JSONArray(response);
										System.out.println("No of subscribers are in response is "+array.length());
										for(int i=0;i<array.length();i++) {
											JSONObject  object=array.getJSONObject(i);
											System.out.println(object.keySet());
											hs.add("ban");
											for(String headerList:hs) {
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												else {
													System.out.println(headerList);
													try {
														hm.put(headerList, String.valueOf(object.get(headerList)));
													}catch(Exception E) {
														hm.put(headerList, "");
													}
												}
												
											}
											if (temp==1) {
												
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											hm.clear();
											temp=++temp;
											if(temp>1) {
												hs.remove("ban");
											}
											
										}	
										
									}
									else if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()==0)
									{
										JSONArray array = new JSONArray(response);
										System.out.println("No of subscribers are in response is "+array.length());
										hs.add("ban");
										for(String headerList:hs) {
											if(headerList.contains("ban")) 
											{
												hm.put("ban", data.getBan());
												
											}
											else {
												System.out.println(headerList);
												try {
													hm.put(headerList, String.valueOf(""));
												}catch(Exception E) {
													hm.put(headerList, "");
												}
											}
											
										}
										if (temp==1) {
											
											header=sheet.createRow(0);
											System.out.println("headers list is size is" + hs.size());
											for (int k = 0; k < hs.size(); k++) {
												header.createCell(k).setCellValue(hs.get(k));
											}
										}
										header=sheet.createRow(temp);
										for(int k=0;k<hs.size();k++) {
											
											header.createCell(k).setCellValue(hm.get(hs.get(k)));
										}	
										hm.clear();
										temp=++temp;
										
										
									}
									else if (b1) {
										{
											System.out.println(response);
											JSONObject object = new JSONObject(response);
											System.out.println("No of subscribers are in response is "+object.length());
											al.clear();
											al.addAll(object.keySet());
											hs.add("ban");
											for(String headerList:hs) {
												System.out.println(hs);
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												else {
													System.out.println(headerList);
													try {
														hm.put(headerList, String.valueOf(object.get(headerList)));
													} catch (Exception e) {
														hm.put(headerList, "");
													}
												}
												
											}
											System.out.println(path);
											if (temp==1) {
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											hm.clear();
											temp=++temp;
											hs.remove("ban");
											
										}
									}
									else if(response.toString().contains("errorMessage"))
									{
										JSONObject  object=new JSONObject(response);
										if(object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status"));											
											header=sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp=++temp;
										}
										
									}
									else if(response.toString().contains("Service not available")){
										if(response.toString().contains("Service not available")) {
											hm.put("ban", data.getBan());
											hm.put("status", response.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status"));											
											header=sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp=++temp;
										}
									}
									
								}
								catch(Exception E)
								{
									E.printStackTrace();
								}
								System.out.println(service.getRequiredFlags().size());
								for (int i = 0; i < service.getRequiredFlags().size(); i++) {
									
									System.out.println(service.getRequiredFlags().get(i) + ":"
											+ getJsonValue(response, service.getRequiredFlags().get(i)));
									flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));
									
								}
								System.out.println("Validation completed for " + service.getName());
					}
				outputDetails.put(data.getBan(), flagValues);				
			}	
			}
			
			
			
		} 
		
		catch (Exception e ) {
			System.out.println("error in invoke service " + e);
			e.printStackTrace();
			
		}finally {
			workbook.write(out);
			out.close();
			workbook.close();
			System.out.println("");
		}
		
		
		
	}
	public static void invokeServiceGeneric2() throws JSONException, IOException {
		FileInputStream inputStream = new FileInputStream(new File(System.getProperty("user.dir")+"\\Results"+".xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		FileOutputStream fileOut = new FileOutputStream(new File(System.getProperty("user.dir")+"\\FinalResults"+".xlsx"));
		
		String path = System.getProperty("user.dir")+"\\Results1"+".xlsx";
		/*FileOutputStream out=new FileOutputStream(new File(path));
		workbook=new XSSFWorkbook();*/
		int temp=1;
		try {
			
			sheet=null;
			Row header;
			SSLContext ctx = SSLContext.getInstance("TLS");
			if (true) {
				ctx.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
			}
			// This will prepare the headers
			prepareExcelHeaders();
			SSLContext.setDefault(ctx);
			HttpClient client = null;
			HashMap<String, String> hm=new HashMap<>();
			Iterator<TestData> datIterator = testData.iterator();
			
			ArrayList<String> al=new ArrayList<>();
			
			
				Iterator<ServiceDetails> serviceIterator = serviceDetails.iterator();
				while (serviceIterator.hasNext()) {
					ServiceDetails service = serviceIterator.next();
					datIterator = testData.iterator();
					temp=1;
					while (datIterator.hasNext()) {
						
						TestData data = datIterator.next();
						List<String> flagValues = new ArrayList<>();
						flagValues.add(data.getSmUser());
						System.out.println("For BAN " + data.getBan());					
					    System.out.println(service.getURL());
					    if(service.getURL().contains("subscriptions") && service.getURL().contains("current-services")) {
							System.out.println("For subscriber " + data.getSubscriber());
							ArrayList<String> hs=new ArrayList<>();
							String headersList[]= {"socCode","name","charge","startDate","description","taxInclusiveInd","relatedSoc"};
							for(String hl:headersList) {
								hs.add(hl);
								
							}
							System.out.println("payment methods headers size is"+hs.size());
							client = new HttpClient();
							al.clear();
							if(temp==1) {
								sheet=workbook.createSheet("current-services");
							}
							String updatedUrl = service.getURL().contains("$BAN")
									? service.getURL().replace("$BAN", data.getBan()) : service.getURL();
									updatedUrl = service.getURL().contains("$SUBSCRIBER")
											? service.getURL().replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
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
									System.out.println("Response is "+response);
									boolean b=response.startsWith("[");
									boolean b1=response.startsWith("{");
									System.out.println(b);
									try {
										if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()>0)
										{
											JSONArray array = new JSONArray(response);
											System.out.println("No of subscribers are in response is "+array.length());
											
											for(int i=0;i<array.length();i++) {
												JSONObject  object=array.getJSONObject(i);
												System.out.println(object.keySet());
												hs.add("ban");
												for(String headerList:hs) {
													if(headerList.contains("ban")) 
													{
														hm.put("ban", data.getBan());
													}
													else if(headerList.contains("subscriber")) 
													{
														hm.put("subscriber", data.getSubscriber());
													}
													else {
														System.out.println(headerList);
														try {
															hm.put(headerList, String.valueOf(object.get(headerList)));
														}catch(Exception E) {
															hm.put(headerList, "");
														}
													}
													
												}
												if (temp==1) {
													
													header=sheet.createRow(0);
													System.out.println("headers list is size is" + hs.size());
													for (int k = 0; k < hs.size(); k++) {
														header.createCell(k).setCellValue(hs.get(k));
													}
												}
												header=sheet.createRow(temp);
												for(int k=0;k<hs.size();k++) {
													
													header.createCell(k).setCellValue(hm.get(hs.get(k)));
												}	
												hm.clear();
												temp=++temp;
												if(temp>1) {
													hs.remove("ban");
												}
												
											}	
											
										}
										else if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()==0)
										{
											JSONArray array = new JSONArray(response);
											System.out.println("No of subscribers are in response is "+array.length());
											hs.add("ban");
											for(String headerList:hs) {
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
													
												}
												else {
													System.out.println(headerList);
													try {
														hm.put(headerList, String.valueOf(""));
													}catch(Exception E) {
														hm.put(headerList, "");
													}
												}
												
											}
											if (temp==1) {
												
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											hm.clear();
											temp=++temp;
											
											
										}
										else if (b1 && response.contains("errorMessage")) {
											hs.add("ban");
											hs.add("subscriber");
											for(String headerList:hs) {
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												if(headerList.contains("subscriber")) 
												{
													System.out.println(data.getSubscriber()+" ,"+data.getBan());
													hm.put("subscriber", data.getSubscriber());
												}
												}
											if (temp==1) {
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											header.createCell(hs.size()).setCellValue(response);
											hm.clear();
											temp=++temp;
											hs.remove("ban");
											hs.remove("subscriber");
										}
										else if (b1 && !response.contains("errorMessage")) {
											{
												System.out.println(response);
												JSONObject object = new JSONObject(response);
												
												try {
													JSONArray array=object.getJSONArray("accountLevelServices");
													System.out.println(array.length());
													if(array.length()>0) {
													for(int i=0;i<array.length();i++) {
														JSONObject object1=array.getJSONObject(i);
													
													System.out.println("No of subscribers are in response is "+object.length());
													al.clear();
													al.addAll(object1.keySet());
													hs.add("ban");
													hs.add("subscriber");
													hs.add("level");
													for(String headerList:hs) {
														if(headerList.contains("ban")) 
														{
															hm.put("ban", data.getBan());
														}
														else if(headerList.contains("subscriber")) 
														{
															System.out.println(data.getSubscriber()+" ,"+data.getBan());
															hm.put("subscriber", data.getSubscriber());
														}
														else if(headerList.contains("level")) 
														{
															hm.put("level", "accountLevelServices");
														}
														else {
															System.out.println(headerList);
															try {
																System.out.println(String.valueOf(object1.get(headerList)));
																hm.put(headerList, String.valueOf(object1.get(headerList)));
															} catch (Exception e) {
																hm.put(headerList, "");
															}
														}
														
													}
													System.out.println(path);
													if (temp==1) {
														header=sheet.createRow(0);
														System.out.println("headers list is size is" + hs.size());
														for (int k = 0; k < hs.size(); k++) {
															header.createCell(k).setCellValue(hs.get(k));
														}
													}
													header=sheet.createRow(temp);
													for(int k=0;k<hs.size();k++) {
														
														header.createCell(k).setCellValue(hm.get(hs.get(k)));
													}	
													hm.clear();
													temp=++temp;
													hs.remove("ban");
													hs.remove("subscriber");
													hs.remove("level");
}
}
													if(array.length()==0) {
															
															hs.add("ban");
															hs.add("subscriber");
															hs.add("level");
															for(String headerList:hs) {
																if(headerList.contains("ban")) 
																{
																	hm.put("ban", data.getBan());
																}
																else if(headerList.contains("subscriber")) 
																{
																	System.out.println(data.getSubscriber()+" ,"+data.getBan());
																	hm.put("subscriber", data.getSubscriber());
																}
																else if(headerList.contains("level")) 
																{
																	hm.put("level", "accountLevelServices");
																}
																else {
																	System.out.println(headerList);
																	try {
																		System.out.println(String.valueOf(""));
																		hm.put(headerList, String.valueOf(""));
																	} catch (Exception e) {
																		hm.put(headerList, "");
																	}
																}
																
															}
															System.out.println(path);
															if (temp==1) {
																header=sheet.createRow(0);
																System.out.println("headers list is size is" + hs.size());
																for (int k = 0; k < hs.size(); k++) {
																	header.createCell(k).setCellValue(hs.get(k));
																}
															}
															header=sheet.createRow(temp);
															for(int k=0;k<hs.size();k++) {
																
																header.createCell(k).setCellValue(hm.get(hs.get(k)));
															}	
															hm.clear();
															temp=++temp;
															hs.remove("ban");
															hs.remove("subscriber");
															hs.remove("level");
													}
												} catch (org.json.JSONException e) {
													e.printStackTrace();
												}
												try {
													JSONArray array=object.getJSONArray("subscriptionLevelServices");
													System.out.println(array.length());
													if(array.length()>0) {
													for(int i=0;i<array.length();i++) {
														JSONObject object1=array.getJSONObject(i);
													
													System.out.println("No of subscribers are in response is "+object.length());
													al.clear();
													al.addAll(object1.keySet());
													hs.add("ban");
													hs.add("subscriber");
													hs.add("level");
													for(String headerList:hs) {
														if(headerList.contains("ban")) 
														{
															hm.put("ban", data.getBan());
														}
														else if(headerList.contains("subscriber")) 
														{
															System.out.println(data.getSubscriber()+" ,"+data.getBan());
															hm.put("subscriber", data.getSubscriber());
														}
														else if(headerList.contains("level")) 
														{
															hm.put("level", "subscriptionLevelServices");
														}
														else {
															System.out.println(headerList);
															try {
																System.out.println(String.valueOf(object1.get(headerList)));
																hm.put(headerList, String.valueOf(object1.get(headerList)));
															} catch (Exception e) {
																hm.put(headerList, "");
															}
														}
														
													}
													System.out.println(path);
													if (temp==1) {
														header=sheet.createRow(0);
														System.out.println("headers list is size is" + hs.size());
														for (int k = 0; k < hs.size(); k++) {
															header.createCell(k).setCellValue(hs.get(k));
														}
													}
													header=sheet.createRow(temp);
													for(int k=0;k<hs.size();k++) {
														
														header.createCell(k).setCellValue(hm.get(hs.get(k)));
													}	
													hm.clear();
													temp=++temp;
													hs.remove("ban");
													hs.remove("subscriber");
													hs.remove("level");
}
}
													if(array.length()==0) {
															
															hs.add("ban");
															hs.add("subscriber");
															hs.add("level");
															for(String headerList:hs) {
																if(headerList.contains("ban")) 
																{
																	hm.put("ban", data.getBan());
																}
																else if(headerList.contains("subscriber")) 
																{
																	System.out.println(data.getSubscriber()+" ,"+data.getBan());
																	hm.put("subscriber", data.getSubscriber());
																}
																else if(headerList.contains("level")) 
																{
																	hm.put("level", "subscriptionLevelServices");
																}
																else {
																	System.out.println(headerList);
																	try {
																		System.out.println(String.valueOf(""));
																		hm.put(headerList, String.valueOf(""));
																	} catch (Exception e) {
																		hm.put(headerList, "");
																	}
																}
																
															}
															System.out.println(path);
															if (temp==1) {
																header=sheet.createRow(0);
																System.out.println("headers list is size is" + hs.size());
																for (int k = 0; k < hs.size(); k++) {
																	header.createCell(k).setCellValue(hs.get(k));
																}
															}
															header=sheet.createRow(temp);
															for(int k=0;k<hs.size();k++) {
																
																header.createCell(k).setCellValue(hm.get(hs.get(k)));
															}	
															hm.clear();
															temp=++temp;
															hs.remove("ban");
															hs.remove("subscriber");
															hs.remove("level");
													}
												} catch (org.json.JSONException e) {
													e.printStackTrace();
												}
												
											}
										}
										else if(response.toString().contains("errorMessage"))
										{
											JSONObject  object=new JSONObject(response);
											if(object.toString().contains("errorMessage")) {
												hm.put("ban", data.getBan());
												hm.put("status", object.toString());
												System.out.println(hm.get("ban")); 
												System.out.println(hm.get("status"));											
												header=sheet.createRow(temp);
												header.createCell(0).setCellValue(hm.get("ban"));
												header.createCell(1).setCellValue(hm.get("status"));
												hm.clear();
												temp=++temp;
											}
											
										}
										else if(response.toString().contains("Service not available")){
											if(response.toString().contains("Service not available")) {
												hm.put("ban", data.getBan());
												hm.put("subscriber", data.getSubscriber());
												hm.put("status", response.toString());
												System.out.println(hm.get("ban")); 
												System.out.println(hm.get("status"));											
												header=sheet.createRow(temp);
												header.createCell(0).setCellValue(hm.get("ban"));
												header.createCell(1).setCellValue(hm.get("status"));
												header.createCell(2).setCellValue(hm.get("subscriber"));
												hm.clear();
												temp=++temp;
											}
										}
										
									}
									catch(Exception E)
									{
										E.printStackTrace();
									}
									System.out.println(service.getRequiredFlags().size());
									for (int i = 0; i < service.getRequiredFlags().size(); i++) {
										
										System.out.println(service.getRequiredFlags().get(i) + ":"
												+ getJsonValue(response, service.getRequiredFlags().get(i)));
										flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));
										
									}
									System.out.println("Validation completed for " + service.getName());		}    
					if(service.getURL().contains("subscriptions") && service.getURL().contains("contract")) {
						System.out.println("For subscriber " + data.getSubscriber());
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"contractType","contractId","monthlyPayment","contractTermInMonths","canPayoffLease","durationInMonths","timeRemainingInMonths","purchaseOptionPrice","extendedMonthly","startDate","upfrontPayment","leaseSequenceNumber","flexLeaseInd","flexPurchasePreferenceInd","currentLeaseTermStatus","amtFromCustToPurchase","leaseUnbilledAmount","canSetPurchaseIntent","canSignIBPPO","canCancelIBPPO","itemId","itemName","ppoContractDetails"};
						for(String hl:headersList) {
							hs.add(hl);
							
						}
						System.out.println("payment methods headers size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("contract");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan()) : service.getURL();
								updatedUrl = service.getURL().contains("$SUBSCRIBER")
										? service.getURL().replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
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
								System.out.println("Response is "+response);
								boolean b=response.startsWith("[");
								boolean b1=response.startsWith("{");
								System.out.println(b);
								try {
									if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()>0)
									{
										JSONArray array = new JSONArray(response);
										System.out.println("No of subscribers are in response is "+array.length());
										
										for(int i=0;i<array.length();i++) {
											JSONObject  object=array.getJSONObject(i);
											System.out.println(object.keySet());
											hs.add("ban");
											for(String headerList:hs) {
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												else {
													System.out.println(headerList);
													try {
														hm.put(headerList, String.valueOf(object.get(headerList)));
													}catch(Exception E) {
														hm.put(headerList, "");
													}
												}
												
											}
											if (temp==1) {
												
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											hm.clear();
											temp=++temp;
											if(temp>1) {
												hs.remove("ban");
											}
											
										}	
										
									}
									else if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()==0)
									{
										JSONArray array = new JSONArray(response);
										System.out.println("No of subscribers are in response is "+array.length());
										hs.add("ban");
										for(String headerList:hs) {
											if(headerList.contains("ban")) 
											{
												hm.put("ban", data.getBan());
												
											}
											else {
												System.out.println(headerList);
												try {
													hm.put(headerList, String.valueOf(""));
												}catch(Exception E) {
													hm.put(headerList, "");
												}
											}
											
										}
										if (temp==1) {
											
											header=sheet.createRow(0);
											System.out.println("headers list is size is" + hs.size());
											for (int k = 0; k < hs.size(); k++) {
												header.createCell(k).setCellValue(hs.get(k));
											}
										}
										header=sheet.createRow(temp);
										for(int k=0;k<hs.size();k++) {
											
											header.createCell(k).setCellValue(hm.get(hs.get(k)));
										}	
										hm.clear();
										temp=++temp;
										
										
									}
									else if (b1 && response.contains("errorMessage")) {
										hs.add("ban");
										hs.add("subscriber");
										for(String headerList:hs) {
											if(headerList.contains("ban")) 
											{
												hm.put("ban", data.getBan());
											}
											if(headerList.contains("subscriber")) 
											{
												System.out.println(data.getSubscriber()+" ,"+data.getBan());
												hm.put("subscriber", data.getSubscriber());
											}
											}
										if (temp==1) {
											header=sheet.createRow(0);
											System.out.println("headers list is size is" + hs.size());
											for (int k = 0; k < hs.size(); k++) {
												header.createCell(k).setCellValue(hs.get(k));
											}
										}
										header=sheet.createRow(temp);
										for(int k=0;k<hs.size();k++) {
											
											header.createCell(k).setCellValue(hm.get(hs.get(k)));
										}	
										header.createCell(hs.size()).setCellValue(response);
										hm.clear();
										temp=++temp;
										hs.remove("ban");
										hs.remove("subscriber");
									}
									else if (b1 && !response.contains("errorMessage")) {
										{
											System.out.println(response);
											JSONObject object = new JSONObject(response);
											JSONArray array=object.getJSONArray("contracts");
											System.out.println(array.length());
											if(array.length()>0) {
											for(int i=0;i<array.length();i++) {
												JSONObject object1=array.getJSONObject(i);
											
											System.out.println("No of subscribers are in response is "+object.length());
											al.clear();
											al.addAll(object1.keySet());
											hs.add("ban");
											hs.add("subscriber");
											for(String headerList:hs) {
												if(headerList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												else if(headerList.contains("subscriber")) 
												{
													System.out.println(data.getSubscriber()+" ,"+data.getBan());
													hm.put("subscriber", data.getSubscriber());
												}
												else {
													System.out.println(headerList);
													try {
														System.out.println(String.valueOf(object1.get(headerList)));
														hm.put(headerList, String.valueOf(object1.get(headerList)));
													} catch (Exception e) {
														hm.put(headerList, "");
													}
												}
												
											}
											System.out.println(path);
											if (temp==1) {
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header=sheet.createRow(temp);
											for(int k=0;k<hs.size();k++) {
												
												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}	
											hm.clear();
											temp=++temp;
											hs.remove("ban");
											hs.remove("subscriber");
										}
										}
											if(array.length()==0) {
													
													hs.add("ban");
													hs.add("subscriber");
													for(String headerList:hs) {
														if(headerList.contains("ban")) 
														{
															hm.put("ban", data.getBan());
														}
														else if(headerList.contains("subscriber")) 
														{
															System.out.println(data.getSubscriber()+" ,"+data.getBan());
															hm.put("subscriber", data.getSubscriber());
														}
														else {
															System.out.println(headerList);
															try {
																System.out.println(String.valueOf(""));
																hm.put(headerList, String.valueOf(""));
															} catch (Exception e) {
																hm.put(headerList, "");
															}
														}
														
													}
													System.out.println(path);
													if (temp==1) {
														header=sheet.createRow(0);
														System.out.println("headers list is size is" + hs.size());
														for (int k = 0; k < hs.size(); k++) {
															header.createCell(k).setCellValue(hs.get(k));
														}
													}
													header=sheet.createRow(temp);
													for(int k=0;k<hs.size();k++) {
														
														header.createCell(k).setCellValue(hm.get(hs.get(k)));
													}	
													hm.clear();
													temp=++temp;
													hs.remove("ban");
													hs.remove("subscriber");
											}
											
										}
									}
									else if(response.toString().contains("errorMessage"))
									{
										JSONObject  object=new JSONObject(response);
										if(object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status"));											
											header=sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp=++temp;
										}
										
									}
									else if(response.toString().contains("Service not available")){
										if(response.toString().contains("Service not available")) {
											hm.put("ban", data.getBan());
											hm.put("status", response.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status"));											
											header=sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp=++temp;
										}
									}
									
								}
								catch(Exception E)
								{
									E.printStackTrace();
								}
								System.out.println(service.getRequiredFlags().size());
								for (int i = 0; i < service.getRequiredFlags().size(); i++) {
									
									System.out.println(service.getRequiredFlags().get(i) + ":"
											+ getJsonValue(response, service.getRequiredFlags().get(i)));
									flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));
									
								}
								System.out.println("Validation completed for " + service.getName());		}
					if(service.getURL().contains("subscriptions") && service.getURL().contains("upgrade-eligibility")) {
						System.out.println("For subscriber " + data.getSubscriber());
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"eligible","lease","leaseEligibleDate","installmentBilling","installmentBillingEligibleDate","subsidy","subsidyEligibleDate","earlyUpgradeEligible","contractType","turnInOrGiveBackEligible","turnInOrGiveBackAmount","buyoutAmount","hasMultipleContracts","jumpEligibleCustomer","jumpOnDemandEligibile","jump"};
						for(String hl:headersList) {
							hs.add(hl);
							
						}
						System.out.println("payment methods headers size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("upgrade-eligibility");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan()) : service.getURL();
								updatedUrl = service.getURL().contains("$SUBSCRIBER")
										? service.getURL().replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
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
										System.out.println("Response is "+response);
										boolean b=response.startsWith("[");
										boolean b1=response.startsWith("{");
										System.out.println(b);
										try {
											if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()>0)
											{
												JSONArray array = new JSONArray(response);
												System.out.println("No of subscribers are in response is "+array.length());
												
												for(int i=0;i<array.length();i++) {
													JSONObject  object=array.getJSONObject(i);
													System.out.println(object.keySet());
													hs.add("ban");
													for(String headerList:hs) {
														if(headerList.contains("ban")) 
														{
															hm.put("ban", data.getBan());
														}
														else {
															System.out.println(headerList);
															try {
																hm.put(headerList, String.valueOf(object.get(headerList)));
															}catch(Exception E) {
																hm.put(headerList, "");
															}
														}
														
													}
													if (temp==1) {
														
														header=sheet.createRow(0);
														System.out.println("headers list is size is" + hs.size());
														for (int k = 0; k < hs.size(); k++) {
															header.createCell(k).setCellValue(hs.get(k));
														}
													}
													header=sheet.createRow(temp);
													for(int k=0;k<hs.size();k++) {
														
														header.createCell(k).setCellValue(hm.get(hs.get(k)));
													}	
													hm.clear();
													temp=++temp;
													if(temp>1) {
														hs.remove("ban");
													}
													
												}	
												
											}
											else if(b==true && !response.contains("errorMessage") && new JSONArray(response).length()==0)
											{
												JSONArray array = new JSONArray(response);
												System.out.println("No of subscribers are in response is "+array.length());
												hs.add("ban");
												for(String headerList:hs) {
													if(headerList.contains("ban")) 
													{
														hm.put("ban", data.getBan());
														
													}
													else {
														System.out.println(headerList);
														try {
															hm.put(headerList, String.valueOf(""));
														}catch(Exception E) {
															hm.put(headerList, "");
														}
													}
													
												}
												if (temp==1) {
													
													header=sheet.createRow(0);
													System.out.println("headers list is size is" + hs.size());
													for (int k = 0; k < hs.size(); k++) {
														header.createCell(k).setCellValue(hs.get(k));
													}
												}
												header=sheet.createRow(temp);
												for(int k=0;k<hs.size();k++) {
													
													header.createCell(k).setCellValue(hm.get(hs.get(k)));
												}	
												hm.clear();
												temp=++temp;
												
												
											}
											else if (b1 && response.contains("errorMessage")) {
												hs.add("ban");
												hs.add("subscriber");
												for(String headerList:hs) {
													if(headerList.contains("ban")) 
													{
														hm.put("ban", data.getBan());
													}
													if(headerList.contains("subscriber")) 
													{
														System.out.println(data.getSubscriber()+" ,"+data.getBan());
														hm.put("subscriber", data.getSubscriber());
													}
												}
												if (temp==1) {
													header=sheet.createRow(0);
													System.out.println("headers list is size is" + hs.size());
													for (int k = 0; k < hs.size(); k++) {
														header.createCell(k).setCellValue(hs.get(k));
													}
												}
												header=sheet.createRow(temp);
												for(int k=0;k<hs.size();k++) {
													
													header.createCell(k).setCellValue(hm.get(hs.get(k)));
												}	
												header.createCell(hs.size()).setCellValue(response);
												hm.clear();
												temp=++temp;
												hs.remove("ban");
												hs.remove("subscriber");
											}
											else if (b1 && !response.contains("errorMessage")) {
												{
													System.out.println(response);
													JSONObject object = new JSONObject(response);
													
															
															System.out.println("No of subscribers are in response is "+object.length());
															al.clear();
															al.addAll(object.keySet());
															hs.add("ban");
															hs.add("subscriber");
															for(String headerList:hs) {
																if(headerList.contains("ban")) 
																{
																	hm.put("ban", data.getBan());
																}
																else if(headerList.contains("subscriber")) 
																{
																	System.out.println(data.getSubscriber()+" ,"+data.getBan());
																	hm.put("subscriber", data.getSubscriber());
																}
																else {
																	System.out.println(headerList);
																	try {
																		System.out.println(String.valueOf(object.get(headerList)));
																		hm.put(headerList, String.valueOf(object.get(headerList)));
																	} catch (Exception e) {
																		hm.put(headerList, "");
																	}
																}
																
															}
															System.out.println(path);
															if (temp==1) {
																header=sheet.createRow(0);
																System.out.println("headers list is size is" + hs.size());
																for (int k = 0; k < hs.size(); k++) {
																	header.createCell(k).setCellValue(hs.get(k));
																}
															}
															header=sheet.createRow(temp);
															for(int k=0;k<hs.size();k++) {
																
																header.createCell(k).setCellValue(hm.get(hs.get(k)));
															}	
															hm.clear();
															temp=++temp;
															hs.remove("ban");
															hs.remove("subscriber");
														}
													}
													
											else if(response.toString().contains("errorMessage"))
											{
												JSONObject  object=new JSONObject(response);
												if(object.toString().contains("errorMessage")) {
													hm.put("ban", data.getBan());
													hm.put("status", object.toString());
													System.out.println(hm.get("ban")); 
													System.out.println(hm.get("status"));											
													header=sheet.createRow(temp);
													header.createCell(0).setCellValue(hm.get("ban"));
													header.createCell(1).setCellValue(hm.get("status"));
													hm.clear();
													temp=++temp;
												}
												
											}
											else if(response.toString().contains("Service not available")){
												if(response.toString().contains("Service not available")) {
													hm.put("ban", data.getBan());
													hm.put("status", response.toString());
													System.out.println(hm.get("ban")); 
													System.out.println(hm.get("status"));											
													header=sheet.createRow(temp);
													header.createCell(0).setCellValue(hm.get("ban"));
													header.createCell(1).setCellValue(hm.get("status"));
													hm.clear();
													temp=++temp;
												}
											}
											
										}
										catch(Exception E)
										{
											E.printStackTrace();
										}
										System.out.println(service.getRequiredFlags().size());
										for (int i = 0; i < service.getRequiredFlags().size(); i++) {
											
											System.out.println(service.getRequiredFlags().get(i) + ":"
													+ getJsonValue(response, service.getRequiredFlags().get(i)));
											flagValues.add(getJsonValue(response, service.getRequiredFlags().get(i)));
											
										}
										System.out.println("Validation completed for " + service.getName());		}
						outputDetails.put(data.getBan(), flagValues);				
				}	
			}
			
			
			
		} 
		
		catch (Exception e ) {
			System.out.println("error in invoke service " + e);
			e.printStackTrace();
			
		}finally {
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			System.out.println("");
		}
		DateFormat df = new SimpleDateFormat("dd-MM-yy-HH-mm-ss");
		Date dateobj = new Date();
		System.out.println(String.valueOf(df.format(dateobj)));
		inputStream = new FileInputStream(new File(System.getProperty("user.dir")+"\\FinalResults"+".xlsx"));
		workbook = new XSSFWorkbook(inputStream);
		sheet = workbook.getSheetAt(0);
		fileOut = new FileOutputStream(new File("C:\\Users\\Public\\Documents"+"\\FinalResults"+String.valueOf(df.format(dateobj))+".xlsx"));
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		
		
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
