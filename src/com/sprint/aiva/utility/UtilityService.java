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
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
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
	public static List<TestData> Futurepayments = new ArrayList<TestData>();

	public static List<ServiceDetails> serviceDetails = new ArrayList<ServiceDetails>();

	public static Map<String, List<String>> outputDetails = new LinkedHashMap<String, List<String>>();

	public static void readExcelFile() {

		try {

			FileInputStream inputStream = new FileInputStream(new File(AIVAConstants.excellPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			DataFormatter formatter = new DataFormatter();
			XSSFSheet inputSheet = workbook.getSheet("INPUT_SHEET");
			XSSFSheet apiInfoSheet = workbook.getSheet("API_CONSOLE_INFO");
			// C:\ProgramData\Eclipse\eclipse-workspace\eclipse-workspace\APIAutomaiton\demo.xlsx
			System.out.println(System.getProperty("user.dir"));

			// Fetch details related to test data
			for (int i = 1; i < inputSheet.getPhysicalNumberOfRows(); i++) {
				TestData data = new TestData();
				if (formatter.formatCellValue(inputSheet.getRow(i).getCell(0)) != "") {
					data.setBan(formatter.formatCellValue(inputSheet.getRow(i).getCell(0)));
					data.setSmUser(formatter.formatCellValue(inputSheet.getRow(i).getCell(1)));
					System.out.println(inputSheet.getRow(i).getCell(1));
					testData.add(data);
				} else {

				}
			}

			// Fetch details related to API
			for (int i = 1; i < apiInfoSheet.getPhysicalNumberOfRows(); i++) {

				ServiceDetails service = new ServiceDetails();
				service.setName(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(0)));
				service.setURL(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(1)));
				service.setServiceType(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(3)));
				service.setRequestBody(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(4)));
				String[] flags = formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(4)).split(",");
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
			// C:\ProgramData\Eclipse\eclipse-workspace\eclipse-workspace\APIAutomaiton\demo.xlsx
			System.out.println(System.getProperty("user.dir"));

			XSSFSheet inputSheet = workbook.getSheet("INPUT_SHEET");

			// C:\ProgramData\Eclipse\eclipse-workspace\eclipse-workspace\APIAutomaiton\demo.xlsx
			System.out.println(System.getProperty("user.dir"));

			// Fetch details related to test data

			// Fetch details related to API
			for (int i = 1; i < apiInfoSheet.getPhysicalNumberOfRows(); i++) {

				ServiceDetails service = new ServiceDetails();
				service.setName(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(0)));
				service.setURL(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(1)));
				service.setServiceType(formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(2)));
				service.setRequestBody((formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(4))));
				String[] flags = formatter.formatCellValue(apiInfoSheet.getRow(i).getCell(3)).split(",");
				service.setRequiredFlags(Arrays.asList(flags));
				serviceDetails.add(service);
			}

		} catch (Exception e) {
			System.out.println("There is an issue in retrieving from EXcel" + e);
		}
		DataFormatter formatter = new DataFormatter();
		ArrayList<String> bans = new ArrayList<String>();
		ArrayList<String> subscribers = new ArrayList<String>();
		System.out.println(System.getProperty("user.dir"));
		File f = new File(System.getProperty("user.dir") + "\\Results.xlsx");
		FileInputStream fi = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(fi);
		Sheet sheet = workbook.getSheet("subscribersList");
		Sheet Future_Payments = workbook.getSheet("future-payments");
		System.out.println(sheet.getLastRowNum() + " is last row number");
		System.out.println(sheet.getFirstRowNum() + " is first row number");

		int rows = sheet.getPhysicalNumberOfRows();
		int future_payments_rows = 0;
		try {
			future_payments_rows = Future_Payments.getPhysicalNumberOfRows();
		} catch (Exception e) {
			System.out.println("Please check future payments sheet in Results file");
		}
		Row row = sheet.getRow(0);
		// Fetch details related to test data

		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			TestData data = new TestData();
			if (formatter.formatCellValue(sheet.getRow(i).getCell(14)) != "") {
				data.setBan(formatter.formatCellValue(sheet.getRow(i).getCell(14)));
				data.setSubscriber(formatter.formatCellValue(sheet.getRow(i).getCell(9)));
				data.setSmUser(formatter.formatCellValue(sheet.getRow(i).getCell(15)));
				data.setPtn(formatter.formatCellValue(sheet.getRow(i).getCell(5)));
				testData.add(data);
			}
		}

		for (int i = 1; i < future_payments_rows; i++) {
			TestData future_payments = new TestData();
			if (formatter.formatCellValue(Future_Payments.getRow(i).getCell(12)) != "") {
				future_payments.setBan(formatter.formatCellValue(Future_Payments.getRow(i).getCell(12)));
				future_payments.setVoucherNumber(formatter.formatCellValue(Future_Payments.getRow(i).getCell(6)));
				Futurepayments.add(future_payments);
			}
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

			XSSFSheet sheet=workbook.createSheet("subscribersList");   
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
					
					if (service.getURL().contains("past-payments")) {
                        ArrayList<String> hs = new ArrayList<>();
                        String headersList[] = { "action", "amount", "date", "number", "creditCardType", "type",
                                      "confirmationNumber", "paymentMethodId","autopayInd","loan" };
                        for (String hl : headersList) {
                               hs.add(hl);

                        }
                        System.out.println("past payments headers size is" + hs.size());
                        client = new HttpClient();
                        al.clear();
                        if (temp == 1) {
                               sheet = workbook.createSheet("past-payments");
                        }
                        String updatedUrl = service.getURL().contains("$BAN")
                                      ? service.getURL().replace("$BAN", data.getBan())
                                      : service.getURL();
                        GetMethod method = new GetMethod(updatedUrl);
                        method.setRequestHeader("accountId", data.getBan());
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
                        System.out.println("Response is " + response);
                        boolean b = response.startsWith("[");
                        boolean b1 = response.startsWith("{");
                        System.out.println(b);
                        try {
                               if (b == true && !response.contains("errorMessage")
                                             && new JSONArray(response).length() > 0) {
                                      JSONArray array = new JSONArray(response);
                                      System.out.println("No of payments are in response is " + array.length());
                                      for (int i = 0; i < array.length(); i++) {
                                             JSONObject object = array.getJSONObject(i);
                                             System.out.println(object.keySet());
                                             hs.add("ban");
                                             for (String headerList : hs) {
                                                    if (headerList.contains("ban")) {
                                                          hm.put("ban", data.getBan());
                                                    } else {
                                                           System.out.println(headerList);
                                                          try {
                                                                 hm.put(headerList, String.valueOf(object.get(headerList)));
                                                          } catch (Exception E) {
                                                                 hm.put(headerList, "");
                                                          }
                                                    }

                                             }
                                             if (temp == 1) {

                                                    header = sheet.createRow(0);
                                                    System.out.println("headers list is size is" + hs.size());
                                                    for (int k = 0; k < hs.size(); k++) {
                                                           header.createCell(k).setCellValue(hs.get(k));
                                                    }
                                             }
                                             header = sheet.createRow(temp);
                                             for (int k = 0; k < hs.size(); k++) {

                                                    header.createCell(k).setCellValue(hm.get(hs.get(k)));
                                             }
                                             hm.clear();
                                             temp = ++temp;
                                             if (temp > 1) {
                                                    hs.remove("ban");
                                             }

                                      }

                               } else if (b == true && !response.contains("errorMessage")
                                             && new JSONArray(response).length() == 0) {
                                      JSONArray array = new JSONArray(response);
                                      
                                      hs.add("ban");
                                      for (String headerList : hs) {
                                             if (headerList.contains("ban")) {
                                                    hm.put("ban", data.getBan());

                                             } else {
                                                    System.out.println(headerList);
                                                    try {
                                                          hm.put(headerList, String.valueOf(""));
                                                    } catch (Exception E) {
                                                          hm.put(headerList, "");
                                                    }
                                             }

                                      }
                                      if (temp == 1) {

                                             header = sheet.createRow(0);
                                             System.out.println("headers list is size is" + hs.size());
                                             for (int k = 0; k < hs.size(); k++) {
                                                    header.createCell(k).setCellValue(hs.get(k));
                                             }
                                      }
                                      header = sheet.createRow(temp);
                                      for (int k = 0; k < hs.size(); k++) {

                                             header.createCell(k).setCellValue(hm.get(hs.get(k)));
                                      }
                                      hm.clear();
                                      temp = ++temp;

                               } else if (b1) {
                                      {
                                             System.out.println(response);
                                             JSONObject object = new JSONObject(response);
                                             System.out.println("No of subscribers are in response is " + object.length());
                                             al.clear();
                                             al.addAll(object.keySet());
                                             hs.add("ban");
                                             for (String headerList : hs) {
                                                    System.out.println(hs);
                                                    if (headerList.contains("ban")) {
                                                          hm.put("ban", data.getBan());
                                                    } else {
                                                           System.out.println(headerList);
                                                          try {
                                                                 hm.put(headerList, String.valueOf(object.get(headerList)));
                                                          } catch (Exception e) {
                                                                 hm.put(headerList, "");
                                                          }
                                                    }

                                             }
                                             System.out.println(path);
                                             if (temp == 1) {
                                                    header = sheet.createRow(0);
                                                    System.out.println("headers list is size is" + hs.size());
                                                    for (int k = 0; k < hs.size(); k++) {
                                                           header.createCell(k).setCellValue(hs.get(k));
                                                    }
                                             }
                                             header = sheet.createRow(temp);
                                             for (int k = 0; k < hs.size(); k++) {

                                                    header.createCell(k).setCellValue(hm.get(hs.get(k)));
                                             }
                                             hm.clear();
                                             temp = ++temp;
                                             hs.remove("ban");

                                      }
                               } else if (response.toString().contains("errorMessage")) {
                                      JSONObject object = new JSONObject(response);
                                      if (object.toString().contains("errorMessage")) {
                                             hm.put("ban", data.getBan());
                                             hm.put("status", object.toString());
                                             System.out.println(hm.get("ban"));
                                             System.out.println(hm.get("status"));
                                             header = sheet.createRow(temp);
                                             header.createCell(0).setCellValue(hm.get("ban"));
                                             header.createCell(1).setCellValue(hm.get("status"));
                                             hm.clear();
                                             temp = ++temp;
                                      }

                               } else if (response.toString().contains("Service not available")) {
                                      if (response.toString().contains("Service not available")) {
                                             hm.put("ban", data.getBan());
                                             hm.put("status", response.toString());
                                             System.out.println(hm.get("ban"));
                                             System.out.println(hm.get("status"));
                                             header = sheet.createRow(temp);
                                             header.createCell(0).setCellValue(hm.get("ban"));
                                             header.createCell(1).setCellValue(hm.get("status"));
                                             hm.clear();
                                             temp = ++temp;
                                      }
                               }

                        } catch (Exception E) {
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

					if(service.getName().contains("accountlevel-contracts")) {
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"contractType","contractId","monthlyPayment","contractTermInMonths","canPayoffLease","durationInMonths","timeRemainingInMonths","purchaseOptionPrice","extendedMonthly","startDate","upfrontPayment","leaseSequenceNumber","flexLeaseInd","flexPurchasePreferenceInd","currentLeaseTermStatus","amtFromCustToPurchase","leaseUnbilledAmount","canSetPurchaseIntent","canSignIBPPO","canCancelIBPPO","itemId","itemName","ppoContractDetails","loanSequenceNumber","loanAmount","loanPaidAmount","payOffAmount","canPayoffLoan","flexLoanInd","bundledItemIds"};
						for(String hl:headersList) {
							hs.add(hl);

						}
						System.out.println("payment methods headers size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("accountlevel-contracts");
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
													for(String headerList:hs) {
														if(headerList.contains("ban")) 
														{
															hm.put("ban", data.getBan());
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
												for(String headerList:hs) {
													if(headerList.contains("ban")) 
													{
														hm.put("ban", data.getBan());
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
					if(service.getName().contains("accountplans")) {
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"planCode","planId","isVideoChoiceEligible","Name","description","spanishPlanTypeDesc","SpanishLongDesc","type","allowanceInfo","subscriptionList"};
						for(String hl:headersList) {
							hs.add(hl);

						}
						System.out.println("payment methods headers size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("account-plans");
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
													for(String headerList:hs) {
														if(headerList.contains("ban")) 
														{
															hm.put("ban", data.getBan());
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
												for(String headerList:hs) {
													if(headerList.contains("ban")) 
													{
														hm.put("ban", data.getBan());
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

					if (service.getURL().contains("accountbasicinfo")) {

						ArrayList<String> hs = new ArrayList<>();
						String headersList[] = { "basicInfo", "billingAddressInfo", "billingNameInfo", "headerInfo",
								"lockBoxInfo", "otherNameInfo", "splitBillInfo", "subscriberCountInfo", "macInfo",
						"otherInfo" };
						for (String hl : headersList) {
							hs.add(hl);

						}
						client = new HttpClient();
						al.clear();
						if (temp == 1) {
							sheet = workbook.createSheet("Accountbasic-Info");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan())
										: service.getURL();
								/*
								 * updatedUrl = service.getURL().contains("$SUBSCRIBER") ?
								 * updatedUrl.replaceAll("$SUBSCRIBER", data.getSubscriber()) :
								 * service.getURL();
								 */
								PostMethod method = new PostMethod(updatedUrl);

								// method.setRequestHeader("accountId", data.getBan());
								// method.setRequestHeader("sm_user", data.getSmUser());
								method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
								method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
								method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
								method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
								method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
								method.setRequestHeader("consumerId", "WebApp");
								method.setRequestHeader("conversationId", "123");

								// Execute the method.
								// JSONObject RequestBody=new JSONObject(service.getRequestBody());

								String RequestBody = service.getRequestBody().replace("$BAN", data.getBan());

								StringRequestEntity request = new StringRequestEntity(RequestBody, "application/json", "UTF-8");

								// RequestEntity request1=new RequestEntity();

								method.setRequestEntity(request);
								int statusCode = client.executeMethod(method);

								if (statusCode != HttpStatus.SC_OK) {
									System.err.println("Method failed: " + method.getStatusLine());
								}
								String response = method.getResponseBodyAsString();
								System.out.println("Response is " + response);
								boolean b1 = response.startsWith("{");
								try {
									if (b1 && response.contains("errorMessage")) {
										hs.add("ban");
										/* hs.add("subscriber"); */
										for (String headerList : hs) {
											if (headerList.contains("ban")) {
												hm.put("ban", data.getBan());
											} /*
											 * else if (headerList.contains("subscriber")) {
											 * System.out.println(data.getSubscriber() + " ," + data.getBan());
											 * hm.put("subscriber", data.getSubscriber()); }
											 */
										}
										if (temp == 1) {
											header = sheet.createRow(0);
											System.out.println("headers list is size is" + hs.size());
											for (int k = 0; k < hs.size(); k++) {
												header.createCell(k).setCellValue(hs.get(k));
											}
										}
										header = sheet.createRow(temp);
										for (int k = 0; k < hs.size(); k++) {

											header.createCell(k).setCellValue(hm.get(hs.get(k)));
										}
										header.createCell(hs.size()).setCellValue(response);
										hm.clear();
										temp = ++temp;
										hs.remove("ban");
										hs.remove("subscriber");
									} else if (b1 && !response.contains("errorMessage")) {
										{

											JSONObject object = new JSONObject(response);

											JSONObject object1 = object.getJSONObject("queryAccountBasicInfoResponse");

											// JSONObject object1=object2.getJSONObject("paEligibilityInfo");

											al.clear();
											al.addAll(object1.keySet());
											hs.add("ban");

											for (String headerList : hs) {
												if (headerList.contains("ban")) {
													hm.put("ban", data.getBan());
												}

												/*
												 * else if (headerList.contains("subscriber")) {
												 * System.out.println(data.getSubscriber() + " ," + data.getSubscriber());
												 * hm.put("subscriber", data.getSubscriber()); }
												 */
												else {
													try {
														/*
														 * if(headerList.contains("basicInfo")) {
														 * 
														 * JSONObject object2= object1.getJSONObject("basicInfo"); for (String
														 * hl : HeaderList2) { hs2.add(hl);
														 * 
														 * }
														 * 
														 * for(String HeaderList3:hs2) { hm.put(headerList,
														 * String.valueOf(object2.get(HeaderList3))); } }
														 */
														System.out.println(String.valueOf(object1.get(headerList)));
														hm.put(headerList, String.valueOf(object1.get(headerList)));
													} catch (Exception e) {
														hm.put(headerList, "");
													}
												}
											}

											if (temp == 1) {
												header = sheet.createRow(0);

												/*
												 * for (String hl : HeaderList2) { hs2.add(hl);
												 * 
												 * } hs.addAll(hs2);
												 */

												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header = sheet.createRow(temp);
											for (int k = 0; k < hs.size(); k++) {

												header.createCell(k).setCellValue(hm.get(hs.get(k)));

											}
											hm.clear();
											temp = ++temp;
											hs.remove("ban");
											// hs.remove("subscriber");
										}
									}

									else if (response.toString().contains("errorMessage")) {
										JSONObject object = new JSONObject(response);
										if (object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban"));
											System.out.println(hm.get("status"));
											header = sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp = ++temp;
										}

									} else if (response.toString().contains("Service not available")) {

										hm.put("ban", data.getBan());
										hm.put("status", response.toString());
										System.out.println(hm.get("ban"));
										System.out.println(hm.get("status"));
										header = sheet.createRow(temp);
										header.createCell(0).setCellValue(hm.get("ban"));
										header.createCell(1).setCellValue(hm.get("status"));
										hm.clear();
										temp = ++temp;

									}

								} catch (Exception E) {
									E.printStackTrace();
								}

								System.out.println("Validation completed for " + service.getName());
					}

					if (service.getName().equalsIgnoreCase("payment-eligibility-create")) {

						ArrayList<String> hs = new ArrayList<>();
						String headersList[] = { "eligibilityInd" };
						for (String hl : headersList) {
							hs.add(hl);

						}
						client = new HttpClient();
						al.clear();
						if (temp == 1) {
							sheet = workbook.createSheet("Payment-Eligibility");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan())
										: service.getURL();
								/*
								 * updatedUrl = service.getURL().contains("$SUBSCRIBER") ?
								 * updatedUrl.replaceAll("$SUBSCRIBER", data.getSubscriber()) :
								 * service.getURL();
								 */
								PostMethod method = new PostMethod(updatedUrl);

								// method.setRequestHeader("accountId", data.getBan());
								// method.setRequestHeader("sm_user", data.getSmUser());
								method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
								method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
								method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
								method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
								method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
								method.setRequestHeader("consumerId", "WEBCHAT");
								method.setRequestHeader("conversationId", "123");

								// Execute the method.
								JSONObject RequestBody = new JSONObject(service.getRequestBody());

								StringRequestEntity request = new StringRequestEntity(RequestBody.toString(),
										"application/json", "UTF-8");

								// RequestEntity request1=new RequestEntity();

								method.setRequestEntity(request);
								int statusCode = client.executeMethod(method);

								if (statusCode != HttpStatus.SC_CREATED) {
									System.err.println("Method failed: " + method.getStatusLine());
								}
								String response = method.getResponseBodyAsString();
								System.out.println("Response is " + response);
								boolean b1 = response.startsWith("{");
								try {
									if (b1 && response.contains("errorMessage")) {
										hs.add("ban");
										/* hs.add("subscriber"); */
										for (String headerList : hs) {
											if (headerList.contains("ban")) {
												hm.put("ban", data.getBan());
											} /*
											 * else if (headerList.contains("subscriber")) {
											 * System.out.println(data.getSubscriber() + " ," + data.getBan());
											 * hm.put("subscriber", data.getSubscriber()); }
											 */
										}
										if (temp == 1) {
											header = sheet.createRow(0);
											System.out.println("headers list is size is" + hs.size());
											for (int k = 0; k < hs.size(); k++) {
												header.createCell(k).setCellValue(hs.get(k));
											}
										}
										header = sheet.createRow(temp);
										for (int k = 0; k < hs.size(); k++) {

											header.createCell(k).setCellValue(hm.get(hs.get(k)));
										}
										header.createCell(hs.size()).setCellValue(response);
										hm.clear();
										temp = ++temp;
										hs.remove("ban");
										hs.remove("subscriber");
									} else if (b1 && !response.contains("errorMessage")) {
										{
											// System.out.println(response);

											JSONObject object = new JSONObject(response);

											JSONObject object2 = object.getJSONObject("checkAccountPaymentEligibilityResponse");

											JSONObject object1 = object2.getJSONObject("paEligibilityInfo");

											al.clear();
											al.addAll(object1.keySet());
											hs.add("ban");

											for (String headerList : hs) {
												if (headerList.contains("ban")) {
													hm.put("ban", data.getBan());
												}

												/*
												 * else if (headerList.contains("subscriber")) {
												 * System.out.println(data.getSubscriber() + " ," + data.getSubscriber());
												 * hm.put("subscriber", data.getSubscriber()); }
												 */

												else {
													try {
														System.out.println(String.valueOf(object1.get(headerList)));
														hm.put(headerList, String.valueOf(object1.get(headerList)));
													} catch (Exception e) {
														hm.put(headerList, "");
													}
												}
											}

											if (temp == 1) {
												header = sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header = sheet.createRow(temp);
											for (int k = 0; k < hs.size(); k++) {

												header.createCell(k).setCellValue(hm.get(hs.get(k)));

											}
											hm.clear();
											temp = ++temp;
											hs.remove("ban");
											// hs.remove("subscriber");
										}
									}

									else if (response.toString().contains("errorMessage")) {
										JSONObject object = new JSONObject(response);
										if (object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban"));
											System.out.println(hm.get("status"));
											header = sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp = ++temp;
										}

									} else if (response.toString().contains("Service not available")) {

										hm.put("ban", data.getBan());
										hm.put("status", response.toString());
										System.out.println(hm.get("ban"));
										System.out.println(hm.get("status"));
										header = sheet.createRow(temp);
										header.createCell(0).setCellValue(hm.get("ban"));
										header.createCell(1).setCellValue(hm.get("status"));
										hm.clear();
										temp = ++temp;

									}

								} catch (Exception E) {
									E.printStackTrace();
								}

								System.out.println("Validation completed for " + service.getName());
					}
					if (service.getURL().contains("negative-listed")) {

						ArrayList<String> hs = new ArrayList<>();
						String headersList[] = { "isNegativeListed" };
						for (String hl : headersList) {
							hs.add(hl);

						}
						client = new HttpClient();
						al.clear();
						if (temp == 1) {
							sheet = workbook.createSheet("negative-listed");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan())
										: service.getURL();
								GetMethod method = new GetMethod(updatedUrl);

								method.setRequestHeader("accountId", data.getBan());
								// method.setRequestHeader("sm_user", data.getSmUser());
								method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
								method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
								method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
								method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
								method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
								method.setRequestHeader("consumerId", "WEBCHAT");
								method.setRequestHeader("conversationId", "123");
								int statusCode = client.executeMethod(method);

								if (statusCode != HttpStatus.SC_OK) {
									System.err.println("Method failed: " + method.getStatusLine());
								}
								String response = method.getResponseBodyAsString();
								System.out.println("Response is " + response);
								boolean b1 = response.startsWith("{");
								try {
									if (b1 && response.contains("errorMessage")) {
										hs.add("ban");

										for (String headerList : hs) {
											if (headerList.contains("ban")) {
												hm.put("ban", data.getBan());
											}
										}
										if (temp == 1) {
											header = sheet.createRow(0);
											System.out.println("headers list is size is" + hs.size());
											for (int k = 0; k < hs.size(); k++) {
												header.createCell(k).setCellValue(hs.get(k));
											}
										}
										header = sheet.createRow(temp);
										for (int k = 0; k < hs.size(); k++) {

											header.createCell(k).setCellValue(hm.get(hs.get(k)));
										}
										header.createCell(hs.size()).setCellValue(response);
										hm.clear();
										temp = ++temp;
										hs.remove("ban");

									} else if (b1 && !response.contains("errorMessage")) {
										{

											JSONObject object = new JSONObject(response);
											al.clear();
											al.addAll(object.keySet());
											hs.add("ban");

											for (String headerList : hs) {
												if (headerList.contains("ban")) {
													hm.put("ban", data.getBan());
												}

												else {
													try {
														System.out.println(String.valueOf(object.get(headerList)));
														hm.put(headerList, String.valueOf(object.get(headerList)));
													} catch (Exception e) {
														hm.put(headerList, "");
													}
												}
											}

											if (temp == 1) {
												header = sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header = sheet.createRow(temp);
											for (int k = 0; k < hs.size(); k++) {

												header.createCell(k).setCellValue(hm.get(hs.get(k)));

											}
											hm.clear();
											temp = ++temp;
											hs.remove("ban");
										}
									}

									else if (response.toString().contains("errorMessage")) {
										JSONObject object = new JSONObject(response);
										if (object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban"));
											System.out.println(hm.get("status"));
											header = sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp = ++temp;
										}

									} else if (response.toString().contains("Service not available")) {

										hm.put("ban", data.getBan());
										hm.put("status", response.toString());
										System.out.println(hm.get("ban"));
										System.out.println(hm.get("status"));
										header = sheet.createRow(temp);
										header.createCell(0).setCellValue(hm.get("ban"));
										header.createCell(1).setCellValue(hm.get("status"));
										hm.clear();
										temp = ++temp;

									}

								} catch (Exception E) {
									E.printStackTrace();
								}

								System.out.println("Validation completed for " + service.getName());
					}

					if(service.getName().contains("subscriptions")) {
						ArrayList<String> hs=new ArrayList<>();
						String headerValues[]= {"deviceType","esn","address","unlockSimCapable","nickName","ptn","itemId","modelName","osType","id","callerId","status","primary","hppttId"};
						for(String hl:headerValues) {
							hs.add(hl);
						}
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
											hs.add("ban");
											hs.add("smUser");
											for(String arrayList:hs) {
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
												else if(arrayList.contains("smUser")) 
												{
													hm.put("smUser", data.getSmUser());
												}
												else {
													System.out.println(arrayList);
													try
													{
														hm.put(arrayList, String.valueOf(object.getString(arrayList)));
													}catch(Exception e){
														hm.put(arrayList, "");
													}
												}

											}
											String subscriberId = object.getString("id");
											System.out.println("id is "+subscriberId);
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
											hs.remove("ban");
											hs.remove("smUser");
											temp=++temp;


										}      

									}

									else
									{
										JSONObject  object=new JSONObject(response);
										if(object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("smUser", data.getSmUser());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status")); 
											System.out.println(hm.get("smUser"));

											header=sheet.createRow(temp);
											header.createCell(al.size()-2).setCellValue(hm.get("ban"));
											header.createCell(al.size()).setCellValue(hm.get("status"));
											header.createCell(al.size()-1).setCellValue(hm.get("smUser"));
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
					if(service.getName().contains("mac-adjustment")) {
						client = new HttpClient();
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"mac","macType","duration","isExisting","currentDiscount","availableDiscount","batchProcessInd"};
						for(String hl:headersList) {
							hs.add(hl);
						}
						hs.add("ban");
						hs.add("smUser");
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("mac-adjustment");
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
								System.out.println(b);
								try {
									if(b==true)
									{
										JSONArray array = new JSONArray(response);
										System.out.println("No of subscribers are in response is "+array.length());
										for(int i=0;i<array.length();i++) {
											JSONObject  object=array.getJSONObject(i);
											System.out.println(object.keySet());

											for(String arrayList:hs) {
												System.out.println(arrayList);
												if(arrayList.contains("macType")) 
												{
													String macType=object.getJSONArray(arrayList).toString();
													hm.put(arrayList, macType);
												}
												else if(arrayList.contains("ban")) 
												{
													hm.put("ban", data.getBan());
												}
												else if(arrayList.contains("smUser")) 
												{
													hm.put("smUser", data.getSmUser());
												}
												else {
													System.out.println(arrayList);
													try {
														hm.put(arrayList, String.valueOf(object.get(arrayList)));
													}catch(Exception E) {
														hm.put(arrayList, "");
													}
												}

											}
											System.out.println(path);
											if (temp==1) {
												header=sheet.createRow(0);
												System.out.println("headers list is size is" + al.size());
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

									else
									{
										JSONObject  object=new JSONObject(response);
										if(object.toString().contains("errorMessage")) {
											hm.put("ban", data.getBan());
											hm.put("smUser", data.getSmUser());
											hm.put("status", object.toString());
											System.out.println(hm.get("ban")); 
											System.out.println(hm.get("status")); 
											System.out.println(hm.get("smUser"));

											header=sheet.createRow(temp);
											header.createCell(al.size()-2).setCellValue(hm.get("ban"));
											header.createCell(al.size()).setCellValue(hm.get("status"));
											header.createCell(al.size()-1).setCellValue(hm.get("smUser"));
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
		out.close();
		workbook.close();
		System.out.println("");


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
				Iterator<TestData> futurepaymentsIterator = Futurepayments.iterator();
				temp=1;
				while (datIterator.hasNext()) {

					TestData data = datIterator.next();
					List<String> flagValues = new ArrayList<>();
					flagValues.add(data.getSmUser());
					System.out.println("For BAN " + data.getBan());					
					System.out.println(service.getURL());
					if (service.getURL().contains("subscriberservices")) {
						System.out.println("For subscriber " + data.getSubscriber());
						ArrayList<String> hs = new ArrayList<>();
						String headersList[] = { "socCode", "socType", "featureCode", "serviceType", "name",
								"effectiveDate", "isUsageType", "isSwitchType", "socSeqNumber", "featureSeqNumber",
								"pttInd", "pttNaiReq", "pamInd", "networkInd", "hppttInd", "evdoInd", "abInd",
								"glmsInd", "winInd", "recurringCharge", "billDisplayInd" };
						for (String hl : headersList) {
							hs.add(hl);

						}
						System.out.println("subscriber servicese header size is " + hs.size());
						client = new HttpClient();
						al.clear();
						if (temp == 1) {
							sheet = workbook.createSheet("subscriber-services");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan())
										: service.getURL();
								updatedUrl = updatedUrl.contains("$SUBSCRIBER")
										? updatedUrl.replace("$SUBSCRIBER", data.getSubscriber())
												: service.getURL();
										PostMethod method = new PostMethod(updatedUrl);
										method.setRequestHeader("accountId", data.getBan());
										method.setRequestHeader("sm_user", data.getSmUser());
										method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
										method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
										method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
										method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
										method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);

										String RequestBody = service.getRequestBody().replace("$PTN", data.getPtn());

										System.out.println(RequestBody);

										StringRequestEntity request = new StringRequestEntity(RequestBody, "application/json", "UTF-8");

										method.setRequestEntity(request);

										int statusCode = client.executeMethod(method);

										if (statusCode != HttpStatus.SC_OK) {
											System.err.println("Method failed: " + method.getStatusLine());
										}
										String response = method.getResponseBodyAsString();
										System.out.println("Response is " + response);
										boolean b = response.startsWith("[");
										boolean b1 = response.startsWith("{");
										try {
											if (b1 && !response.contains("error message")) {
												JSONObject object1 = new JSONObject(response);
												JSONObject object2 = object1.getJSONObject("querySubscriberServicesResponse");
												JSONObject object3 = object2.getJSONObject("featureList");
												JSONArray array = object3.getJSONArray("featureInfo");

												if (array.length() > 0) {
													for (int i = 0; i < array.length(); i++) {
														JSONObject object = array.getJSONObject(i);
														System.out.println(object.keySet());
														hs.add("ban");
														hs.add("subscriber");
														hs.add("SM_User");
														for (String headerList : hs) {
															if (headerList.contains("ban")) {
																hm.put("ban", data.getBan());
															} else if (headerList.contains("subscriber")) {
																hm.put("subscriber", data.getSubscriber());
															} else if (headerList.contains("SM_User")) {
																hm.put("SM_User", data.getSmUser());
															} else {
																try {
																	hm.put(headerList, String.valueOf(object.get(headerList)));
																} catch (Exception E) {
																	hm.put(headerList, "");
																}
															}

														}
														if (temp == 1) {

															header = sheet.createRow(0);
															System.out.println("headers list is size is" + hs.size());
															for (int k = 0; k < hs.size(); k++) {
																header.createCell(k).setCellValue(hs.get(k));
															}
														}
														header = sheet.createRow(temp);
														for (int k = 0; k < hs.size(); k++) {

															header.createCell(k).setCellValue(hm.get(hs.get(k)));
														}
														hm.clear();
														temp = ++temp;
														if (temp > 1) {
															hs.remove("ban");
															hs.remove("SM_User");
															hs.remove("subscriber");
														}

													}
												} else {

													hs.add("ban");
													hs.add("subscriber");
													hs.add("SM_User");
													for (String headerList : hs) {
														if (headerList.contains("ban")) {
															hm.put("ban", data.getBan());
														} else if (headerList.contains("subscriber")) {

															hm.put("subscriber", data.getSubscriber());
														} else if (headerList.contains("SM_User")) {
															hm.put("SM_User", data.getSmUser());
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
													if (temp == 1) {
														header = sheet.createRow(0);
														System.out.println("headers list is size is" + hs.size());
														for (int k = 0; k < hs.size(); k++) {
															header.createCell(k).setCellValue(hs.get(k));
														}
													}
													header = sheet.createRow(temp);
													for (int k = 0; k < hs.size(); k++) {

														header.createCell(k).setCellValue(hm.get(hs.get(k)));
													}
													hm.clear();
													temp = ++temp;
													hs.remove("ban");
													hs.remove("subscriber");
													hs.remove("SM_User");

												}

											}

											else if (b1 && response.contains("errorMessage")) {
												hs.add("ban");
												hs.add("subscriber");
												hs.add("SM_User");
												for (String headerList : hs) {
													if (headerList.contains("ban")) {
														hm.put("ban", data.getBan());
													} else if (headerList.contains("subscriber")) {
														System.out.println(data.getSubscriber() + " ," + data.getBan());
														hm.put("subscriber", data.getSubscriber());
													} else if (headerList.contains("SM_User")) {
														hm.put("SM_User", data.getSmUser());
													}
												}
												if (temp == 1) {
													header = sheet.createRow(0);
													System.out.println("headers list is size is" + hs.size());
													for (int k = 0; k < hs.size(); k++) {
														header.createCell(k).setCellValue(hs.get(k));
													}
												}
												header = sheet.createRow(temp);
												for (int k = 0; k < hs.size(); k++) {

													header.createCell(k).setCellValue(hm.get(hs.get(k)));
												}
												header.createCell(hs.size()).setCellValue(response);
												hm.clear();
												temp = ++temp;
												hs.remove("ban");
												hs.remove("subscriber");
												hs.remove("SM_User");
											}

											else if (response.toString().contains("errorMessage")) {
												JSONObject object = new JSONObject(response);
												if (object.toString().contains("errorMessage")) {
													hm.put("ban", data.getBan());
													hm.put("status", object.toString());
													System.out.println(hm.get("ban"));
													System.out.println(hm.get("status"));
													header = sheet.createRow(temp);
													header.createCell(0).setCellValue(hm.get("ban"));
													header.createCell(1).setCellValue(hm.get("status"));
													hm.clear();
													temp = ++temp;
												}

											} else if (response.toString().contains("Service not available")) {
												if (response.toString().contains("Service not available")) {
													hm.put("ban", data.getBan());
													hm.put("subscriber", data.getSubscriber());
													hm.put("status", response.toString());
													System.out.println(hm.get("ban"));
													System.out.println(hm.get("status"));
													header = sheet.createRow(temp);
													header.createCell(0).setCellValue(hm.get("ban"));
													header.createCell(1).setCellValue(hm.get("status"));
													header.createCell(2).setCellValue(hm.get("subscriber"));
													hm.clear();
													temp = ++temp;
												}
											}

										} catch (Exception E) {
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
					if (service.getName().equalsIgnoreCase("payment-eligibility-modify")) {
						while (futurepaymentsIterator.hasNext()) {
							TestData Future_Payments = futurepaymentsIterator.next();

							ArrayList<String> hs = new ArrayList<>();
							String headersList[] = { "eligibilityInd" };
							for (String hl : headersList) {
								hs.add(hl);

							}
							client = new HttpClient();
							al.clear();
							if (temp == 1) {
								sheet = workbook.createSheet("payment-eligibility-modify");
							}
							String updatedUrl = service.getURL().contains("$BAN")
									? service.getURL().replace("$BAN", Future_Payments.getBan())
											: service.getURL();
									/*
									 * updatedUrl = service.getURL().contains("$SUBSCRIBER") ?
									 * updatedUrl.replaceAll("$SUBSCRIBER", data.getSubscriber()) :
									 * service.getURL();
									 */
									PostMethod method = new PostMethod(updatedUrl);

									// method.setRequestHeader("accountId", data.getBan());
									// method.setRequestHeader("sm_user", data.getSmUser());
									method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
									method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
									method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
									method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
									method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
									method.setRequestHeader("consumerId", "Web");
									method.setRequestHeader("conversationId", "123");

									String RequestBody = service.getRequestBody().replace("$VoucherNumber",
											Future_Payments.getVoucherNumber());

									StringRequestEntity request = new StringRequestEntity(RequestBody, "application/json",
											"UTF-8");
									method.setRequestEntity(request);
									int statusCode = client.executeMethod(method);

									if (statusCode != HttpStatus.SC_CREATED) {
										System.err.println("Method failed: " + method.getStatusLine());
									}
									String response = method.getResponseBodyAsString();
									System.out.println("Response is " + response);
									boolean b1 = response.startsWith("{");
									try {
										if (b1 && response.contains("errorMessage")) {
											hs.add("ban");
											for (String headerList : hs) {
												if (headerList.contains("ban")) {
													hm.put("ban", Future_Payments.getBan());
												}
											}
											if (temp == 1) {
												header = sheet.createRow(0);
												System.out.println("headers list is size is" + hs.size());
												for (int k = 0; k < hs.size(); k++) {
													header.createCell(k).setCellValue(hs.get(k));
												}
											}
											header = sheet.createRow(temp);
											for (int k = 0; k < hs.size(); k++) {

												header.createCell(k).setCellValue(hm.get(hs.get(k)));
											}
											header.createCell(hs.size()).setCellValue(response);
											hm.clear();
											temp = ++temp;
											hs.remove("ban");
											hs.remove("subscriber");
										} else if (b1 && !response.contains("errorMessage")) {
											{

												JSONObject object = new JSONObject(response);

												JSONObject object2 = object
														.getJSONObject("checkAccountPaymentEligibilityResponse");

												JSONObject object1 = object2.getJSONObject("paEligibilityInfo");

												al.clear();
												al.addAll(object1.keySet());
												hs.add("ban");

												for (String headerList : hs) {
													if (headerList.contains("ban")) {
														hm.put("ban", Future_Payments.getBan());
													}

													else {
														try {

															System.out.println(String.valueOf(object1.get(headerList)));
															hm.put(headerList, String.valueOf(object1.get(headerList)));
														} catch (Exception e) {
															hm.put(headerList, "");
														}
													}
												}

												if (temp == 1) {
													header = sheet.createRow(0);

													System.out.println("headers list is size is" + hs.size());
													for (int k = 0; k < hs.size(); k++) {
														header.createCell(k).setCellValue(hs.get(k));
													}
												}
												header = sheet.createRow(temp);
												for (int k = 0; k < hs.size(); k++) {

													header.createCell(k).setCellValue(hm.get(hs.get(k)));

												}
												hm.clear();
												temp = ++temp;
												hs.remove("ban");
											}
										}

										else if (response.toString().contains("errorMessage")) {
											JSONObject object = new JSONObject(response);
											if (object.toString().contains("errorMessage")) {
												hm.put("ban", Future_Payments.getBan());
												hm.put("status", object.toString());
												System.out.println(hm.get("ban"));
												System.out.println(hm.get("status"));
												header = sheet.createRow(temp);
												header.createCell(0).setCellValue(hm.get("ban"));
												header.createCell(1).setCellValue(hm.get("status"));
												hm.clear();
												temp = ++temp;
											}

										} else if (response.toString().contains("Service not available")) {

											hm.put("ban", Future_Payments.getBan());
											hm.put("status", response.toString());
											System.out.println(hm.get("ban"));
											System.out.println(hm.get("status"));
											header = sheet.createRow(temp);
											header.createCell(0).setCellValue(hm.get("ban"));
											header.createCell(1).setCellValue(hm.get("status"));
											hm.clear();
											temp = ++temp;

										}

									} catch (Exception E) {
										E.printStackTrace();
									}

									System.out.println("Validation completed for " + service.getName());
						}
					}

					if (service.getURL().contains("subscriberbasicinfo")) {
						ArrayList<String> hs = new ArrayList<>();
						String headersList[] = { "basicInfo", "detailInfo" };
						for (String hl : headersList) {
							hs.add(hl);

						}
						client = new HttpClient();
						al.clear();
						if (temp == 1) {
							sheet = workbook.createSheet("subscriber_basicinfo");
						}
						String updatedUrl = service.getURL();
						/*
						 * .contains("$BAN") ? service.getURL().replace("$BAN", data.getBan()) :
						 * service.getURL(); updatedUrl = updatedUrl.contains("$SUBSCRIBER") ?
						 * updatedUrl.replaceAll("$SUBSCRIBER", data.getSubscriber()) : updatedUrl;
						 */
						PostMethod method = new PostMethod(updatedUrl);

						// method.setRequestHeader("accountId", data.getBan());
						// method.setRequestHeader("sm_user", data.getSmUser());
						method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
						method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
						method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
						method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
						method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
						method.setRequestHeader("consumerId", "WEBCHAT");
						method.setRequestHeader("conversationId", "123");

						String RequestBody = service.getRequestBody().replace("$PTN", data.getPtn());

						System.out.println(RequestBody);

						StringRequestEntity request = new StringRequestEntity(RequestBody, "application/json", "UTF-8");
						method.setRequestEntity(request);
						int statusCode = client.executeMethod(method);

						if (statusCode != HttpStatus.SC_OK) {
							System.err.println("Method failed: " + method.getStatusLine());
						}
						String response = method.getResponseBodyAsString();
						System.out.println("Response is " + response);
						boolean b1 = response.startsWith("{");
						try {
							if (b1 && response.contains("errorMessage")) {
								hs.add("ban");
								for (String headerList : hs) {
									if (headerList.contains("ban")) {
										hm.put("ban", data.getBan());
									}
								}
								if (temp == 1) {
									header = sheet.createRow(0);
									System.out.println("headers list is size is" + hs.size());
									for (int k = 0; k < hs.size(); k++) {
										header.createCell(k).setCellValue(hs.get(k));
									}
								}
								header = sheet.createRow(temp);
								for (int k = 0; k < hs.size(); k++) {

									header.createCell(k).setCellValue(hm.get(hs.get(k)));
								}
								header.createCell(hs.size()).setCellValue(response);
								hm.clear();
								temp = ++temp;
								hs.remove("ban");
								hs.remove("subscriber");
							} else if (b1 && !response.contains("errorMessage")) {
								{

									JSONObject object = new JSONObject(response);

									JSONObject object1 = object.getJSONObject("querySubscriberBasicInfoResponse");

									// JSONObject object1= object2.getJSONObject("paEligibilityInfo");

									al.clear();
									al.addAll(object1.keySet());
									hs.add("ban");

									for (String headerList : hs) {
										if (headerList.contains("ban")) {
											hm.put("ban", data.getBan());
										}

										else {
											try {

												System.out.println(String.valueOf(object1.get(headerList)));
												hm.put(headerList, String.valueOf(object1.get(headerList)));
											} catch (Exception e) {
												hm.put(headerList, "");
											}
										}
									}

									if (temp == 1) {
										header = sheet.createRow(0);

										System.out.println("headers list is size is" + hs.size());
										for (int k = 0; k < hs.size(); k++) {
											header.createCell(k).setCellValue(hs.get(k));
										}
									}
									header = sheet.createRow(temp);
									for (int k = 0; k < hs.size(); k++) {

										header.createCell(k).setCellValue(hm.get(hs.get(k)));

									}
									hm.clear();
									temp = ++temp;
									hs.remove("ban");
								}
							}

							else if (response.toString().contains("errorMessage")) {
								JSONObject object = new JSONObject(response);
								if (object.toString().contains("errorMessage")) {
									hm.put("ban", data.getBan());
									hm.put("status", object.toString());
									System.out.println(hm.get("ban"));
									System.out.println(hm.get("status"));
									header = sheet.createRow(temp);
									header.createCell(0).setCellValue(hm.get("ban"));
									header.createCell(1).setCellValue(hm.get("status"));
									hm.clear();
									temp = ++temp;
								}

							} else if (response.toString().contains("Service not available")) {

								hm.put("ban", data.getBan());
								hm.put("status", response.toString());
								System.out.println(hm.get("ban"));
								System.out.println(hm.get("status"));
								header = sheet.createRow(temp);
								header.createCell(0).setCellValue(hm.get("ban"));
								header.createCell(1).setCellValue(hm.get("status"));
								hm.clear();
								temp = ++temp;

							}

						} catch (Exception E) {
							E.printStackTrace();
						}

						System.out.println("Validation completed for " + service.getName());
					}

					if(service.getURL().contains("/v1/flows")) {
						System.out.println("For subscriber " + data.getSubscriber());
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"browseEligibile","addToCartEligibile","checkoutEligibile"};
						for(String hl:headersList) {
							hs.add(hl);

						}
						System.out.println("V1_flows header size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("V1_Flows");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan()) : service.getURL();
								updatedUrl = service.getURL().contains("$SUBSCRIBER")
										? updatedUrl.replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
										System.out.println(updatedUrl);          
										GetMethod method = new GetMethod(updatedUrl);
										method.setRequestHeader("accountId", data.getBan());                                 
										method.setRequestHeader("sm_user", data.getSmUser());
										System.out.println(data.getSmUser());
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

											if (b1 && response.contains("errorMessage")) {
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
													JSONObject object1=new JSONObject(response);

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
															System.out.println(data.getSubscriber()+" ,"+data.getSubscriber());
															hm.put("subscriber", data.getSubscriber());
														}                                        

														else {
															try {
																System.out.println(String.valueOf(object1.get(headerList)));
																hm.put(headerList, String.valueOf(object1.get(headerList)));
															} catch (Exception e) {
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
										System.out.println("Validation completed for " + service.getName());}

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
								updatedUrl = updatedUrl.contains("$SUBSCRIBER")
										? updatedUrl.replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
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
						String headersList[]= {"contractType","contractId","monthlyPayment","contractTermInMonths","canPayoffLease","durationInMonths","timeRemainingInMonths","purchaseOptionPrice","extendedMonthly","startDate","upfrontPayment","leaseSequenceNumber","flexLeaseInd","flexPurchasePreferenceInd","currentLeaseTermStatus","amtFromCustToPurchase","leaseUnbilledAmount","canSetPurchaseIntent","canSignIBPPO","canCancelIBPPO","itemId","itemName","ppoContractDetails","loanSequenceNumber","loanAmount","loanPaidAmount","payOffAmount","canPayoffLoan","flexLoanInd","bundledItemIds"};
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
								updatedUrl = updatedUrl.contains("$SUBSCRIBER")
										? updatedUrl.replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
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
					if(service.getURL().contains("products/v1/services")) {
						System.out.println("For subscriber " + data.getSubscriber());
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"serviceCategoryID","singleSelect","mandatory","topSelling","serviceSOCSKU","serviceSKU","socBasePrice", "socFinalPrice","oneTime","preselect","requiresTnCOnSelection","requiresWarningOnRemoval","requiresWarningOnSelection","allowUserToEdit","doNotAllowUserToAdd","devicePromotable","serviceType","prohibitiveSOCs","requiredSOCs","messagingAddOnStatus","dependingSOCs","lowerRankedExistingConflictingSOCs","tiIndicator","serviceType","serviceName","serviceEffectiveDate","serviceExpirationDate","requiresWarningOnRemoval","socPrice","systemRemoved","userRemovable","oneTime","dependingSOCs","prohibitiveSOCs","requiredSOCs","higherRankedNewConflictingSOCs","tiIndicator","existingAddonMACs"};
						String serviceCategoryDTO[]= {"serviceCategoryID","singleSelect","mandatory","topSelling"};
						String newServices[]= {"serviceSOCSKU","serviceSKU","socBasePrice", "socFinalPrice","oneTime","preselect","requiresTnCOnSelection","requiresWarningOnRemoval","requiresWarningOnSelection","allowUserToEdit","doNotAllowUserToAdd","devicePromotable","serviceType","prohibitiveSOCs","requiredSOCs","messagingAddOnStatus","dependingSOCs","lowerRankedExistingConflictingSOCs","tiIndicator"};
						String existingServices[]= {"serviceSOCSKU","serviceSKU","serviceType","serviceName","serviceEffectiveDate","serviceExpirationDate","requiresWarningOnRemoval","socPrice","systemRemoved","userRemovable","oneTime","dependingSOCs","prohibitiveSOCs","requiredSOCs","higherRankedNewConflictingSOCs","tiIndicator","existingAddonMACs"}; 
						for(String hl:headersList) {
							hs.add(hl);

						}
						System.out.println("headers size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("v1-services");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan()) : service.getURL();
								updatedUrl = updatedUrl.contains("$SUBSCRIBER")
										? updatedUrl.replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
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
													JSONArray arr=object.getJSONArray("serviceLineDetails");
													JSONArray newServiceCategoriesArray=arr.getJSONObject(0).getJSONArray("newServiceCategories");
													System.out.println(newServiceCategoriesArray.length());
													
													if(newServiceCategoriesArray.length()>0) {
														for(int i=0;i<newServiceCategoriesArray.length();i++) {
															JSONObject object1=newServiceCategoriesArray.getJSONObject(i);
															JSONObject object2=object1.getJSONObject("serviceCategoryDTO");
															System.out.println(object2.length());
															JSONArray newServiceCategories=object1.getJSONArray("newServices");
															System.out.println(newServiceCategories.length());
															for(int j=0;j<newServiceCategories.length();j++) {
																
																for(int k=0;k<newServices.length;k++) {
																try {
																	System.out.println(String.valueOf(newServiceCategories.getJSONObject(j).get(newServices[k])));
																	hm.put(newServices[k], String.valueOf(newServiceCategories.getJSONObject(j).get(newServices[k])));
																} catch (Exception e) {
																	hm.put(newServices[k], "");
																}																
																
																}
																System.out.println(object2.length());
																for(int l=0;l<object2.length();l++) {
																	try {
																		System.out.println(String.valueOf(object2.get(serviceCategoryDTO[l])));
																		hm.put(serviceCategoryDTO[l], String.valueOf(object2.get(serviceCategoryDTO[l])));
																	} catch (Exception e) {
																		hm.put(serviceCategoryDTO[l], "");
																	}
																}
																hs.add("ban");
																hs.add("subscriber");
																hs.add("service");
																hs.add("smUser");
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
																	else if(headerList.contains("smUser")) 
																	{
																		System.out.println(data.getSmUser()+" ,"+data.getBan());
																		hm.put("smUser", data.getSmUser());
																	}
																	else if(headerList.contains("service")) 
																	{
																		hm.put("service", "new Services");
																	}
																	

																}
																if (temp==1) {
																	header=sheet.createRow(0);
																	System.out.println("headers list is size is" + hs.size());
																	for (int l = 0; l < hs.size(); l++) {
																		header.createCell(l).setCellValue(hs.get(l));
																	}
																}
																header=sheet.createRow(temp);
																for(int l=0;l<hs.size();l++) {

																	header.createCell(l).setCellValue(hm.get(hs.get(l)));
																}	
																hm.clear();
																temp=++temp;
																hs.remove("subscriber");
																hs.remove("service");
																hs.remove("ban");
																hs.remove("smUser");
															}
															
														}
													}
													JSONArray existingServiceCategoriesArray=arr.getJSONObject(0).getJSONArray("existingServiceCategories");
													System.out.println(existingServiceCategoriesArray.length());
													
													if(existingServiceCategoriesArray.length()>0) {
														for(int i=0;i<existingServiceCategoriesArray.length();i++) {
															JSONObject object1=existingServiceCategoriesArray.getJSONObject(i);
															JSONObject object2=object1.getJSONObject("serviceCategoryDTO");
															System.out.println(object2.length());
															JSONArray existingServicesArray=object1.getJSONArray("existingServices");
															System.out.println(existingServicesArray.length());
															for(int j=0;j<existingServicesArray.length();j++) {
																
																for(int k=0;k<existingServices.length;k++) {
																	try {
																		System.out.println(String.valueOf(existingServicesArray.getJSONObject(j).get(existingServices[k])));
																		hm.put(existingServices[k], String.valueOf(existingServicesArray.getJSONObject(j).get(existingServices[k])));
																	} catch (Exception e) {
																		hm.put(existingServices[k], "");
																	}																
																	
																}
																System.out.println(object2.length());
																for(int l=0;l<object2.length();l++) {
																	try {
																		System.out.println(String.valueOf(object2.get(serviceCategoryDTO[l])));
																		hm.put(serviceCategoryDTO[l], String.valueOf(object2.get(serviceCategoryDTO[l])));
																	} catch (Exception e) {
																		hm.put(serviceCategoryDTO[l], "");
																	}
																}
																hs.add("ban");
																hs.add("subscriber");
																hs.add("service");
																hs.add("smUser");
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
																	else if(headerList.contains("smUser")) 
																	{
																		System.out.println(data.getSmUser()+" ,"+data.getBan());
																		hm.put("smUser", data.getSmUser());
																	}
																	else if(headerList.contains("service")) 
																	{
																		hm.put("service", "existing Services");
																	}
																	
																	
																}
																if (temp==1) {
																	header=sheet.createRow(0);
																	System.out.println("headers list is size is" + hs.size());
																	for (int l = 0; l < hs.size(); l++) {
																		header.createCell(l).setCellValue(hs.get(l));
																	}
																}
																header=sheet.createRow(temp);
																for(int l=0;l<hs.size();l++) {
																	
																	header.createCell(l).setCellValue(hm.get(hs.get(l)));
																}	
																hm.clear();
																temp=++temp;
																hs.remove("subscriber");
																hs.remove("service");
																hs.remove("ban");
																hs.remove("smUser");
															}
															
														}
													}
													if(false) {
//														if(array.length()==0) {

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
					if(service.getURL().contains("products/v1/plans")) {
						System.out.println("For subscriber " + data.getSubscriber());
						ArrayList<String> hs=new ArrayList<>();
						String headersList[]= {"categoryId","productId","planType","exclusive","existingInAccount","saleStatus","linesMinLimit","linesMaxLimit","totalBasePrice","totalFinalPrice","recommended","recommendedOfferSource","featured","otherSubImpacted","existsInCart","planCorpDiscountApplied","uniqueId","planSOCSKU","planBasePrice","planFinalPrice","planMRCPrice","lineRank","tiIndicator","lineCorpDiscountApplied","planSOCCorpDiscountApplied","serviceSOCSKU","socType","price","basePrice","promotionApplied","hasPromotionButExceedLineLimit","requiredSOCCorpDiscountApplied","macId","macStatus","amount","duration","macDescription","macType","equipmentMACType"};
						String planProducts[]= {"productId","planType","exclusive","existingInAccount","saleStatus","linesMinLimit","linesMaxLimit","totalBasePrice","totalFinalPrice","recommended","recommendedOfferSource","featured","otherSubImpacted","existsInCart","planCorpDiscountApplied"};
						String linesDetail[]= {"uniqueId","planSOCSKU","planBasePrice","planFinalPrice","planMRCPrice","lineRank","tiIndicator","lineCorpDiscountApplied","planSOCCorpDiscountApplied"};
						String systemRequiredAddons[]= {"serviceSOCSKU","socType","price","basePrice","promotionApplied","hasPromotionButExceedLineLimit","requiredSOCCorpDiscountApplied"}; 
						String planMACList[]= {"macId","macStatus","amount","duration","macDescription","macType","equipmentMACType","effectiveDate","appliedCount","catalogMacTypes"}; 
						for(String hl:headersList) {
							hs.add(hl);
							
						}
						System.out.println("headers size is"+hs.size());
						client = new HttpClient();
						al.clear();
						if(temp==1) {
							sheet=workbook.createSheet("v1-plans");
						}
						String updatedUrl = service.getURL().contains("$BAN")
								? service.getURL().replace("$BAN", data.getBan()) : service.getURL();
								updatedUrl = updatedUrl.contains("$SUBSCRIBER")
										? updatedUrl.replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
										GetMethod method = new GetMethod(updatedUrl);
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
													JSONArray arr=object.getJSONArray("planCategories");
													System.out.println("plan categories length is"+arr.length());
													
													if(arr.length()>0) {
														for(int o=0;o<arr.length();o++) {				
															JSONObject object1 = arr.getJSONObject(o);
															hm.put("categoryId", String.valueOf(arr.getJSONObject(o).get("categoryId")));
														JSONArray planProductsArray=arr.getJSONObject(o).getJSONArray("planProducts");
														System.out.println("plan products array length is "+planProductsArray.length());
														for(int i=0;i<planProductsArray.length();i++) {
															
															
															JSONObject planProductsObject=planProductsArray.getJSONObject(i);
															for(int k=0;k<planProducts.length;k++) {
																try {
																	System.out.println(String.valueOf(planProductsArray.getJSONObject(i).get(planProducts[k])));
																	hm.put(planProducts[k], String.valueOf(planProductsArray.getJSONObject(i).get(planProducts[k])));
																} catch (Exception e) {
																	hm.put(planProducts[k], "");
																}																
																
															}
														
															JSONArray lineDetails=planProductsArray.getJSONObject(i).getJSONArray("linesDetail");
															for(int i1=0;i1<lineDetails.length();i1++) {
																
																for(int k=0;k<linesDetail.length;k++) {
																	try {
																		System.out.println(String.valueOf(lineDetails.getJSONObject(i1).get(linesDetail[k])));
																		hm.put(linesDetail[k], String.valueOf(lineDetails.getJSONObject(i1).get(linesDetail[k])));
																	} catch (Exception e) {
																		hm.put(linesDetail[k], "");
																	}																
																	
																}
																JSONArray systemRequiredAddonsArray=lineDetails.getJSONObject(i1).getJSONArray("systemRequiredAddons");
																for(int i2=0;i2<systemRequiredAddonsArray.length();i2++) {
																	
																	for(int k=0;k<systemRequiredAddons.length;k++) {
																		try {
																			System.out.println(String.valueOf(systemRequiredAddonsArray.getJSONObject(i2).get(systemRequiredAddons[k])));
																			hm.put(systemRequiredAddons[k], String.valueOf(systemRequiredAddonsArray.getJSONObject(i2).get(systemRequiredAddons[k])));
																		} catch (Exception e) {
																			hm.put(systemRequiredAddons[k], "");
																		}																
																		
																	}
																	JSONArray requiredMACList = null;
																	try {
																		requiredMACList = systemRequiredAddonsArray.getJSONObject(i2).getJSONArray("requiredMACList");
																		System.out.println(requiredMACList.length());
																		for(int i3=0;i3<requiredMACList.length();i3++) {
																			
																			for(int k=0;k<planMACList.length;k++) {
																				try {
																					System.out.println(String.valueOf(requiredMACList.getJSONObject(i3).get(planMACList[k])));
																					hm.put(planMACList[k], String.valueOf(requiredMACList.getJSONObject(i3).get(planMACList[k])));
																				} catch (Exception e) {
																					hm.put(planMACList[k], "");
																				}
																			}
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
																					
																				}
																				if (temp==1) {
																					header=sheet.createRow(0);
																					System.out.println("headers list is size is" + hs.size());
																					for (int l = 0; l < hs.size(); l++) {
																						header.createCell(l).setCellValue(hs.get(l));
																					}
																				}
																				header=sheet.createRow(temp);
																				for(int l=0;l<hs.size();l++) {
																					
																					header.createCell(l).setCellValue(hm.get(hs.get(l)));
																				}	
																				temp=++temp;
																				hs.remove("subscriber");
																				hs.remove("ban");
																				for(String pl:planMACList) {
																					
																					hm.remove(planMACList);
																				}
																			
																			}
																		hm.clear();
																	} catch (Exception e1) {
																		e1.printStackTrace();
																	}
																	try {
																		requiredMACList = systemRequiredAddonsArray.getJSONObject(i2).getJSONArray("planMACList");
																		System.out.println(requiredMACList.length());
																		for(int i3=0;i3<requiredMACList.length();i3++) {
																			
																			for(int k=0;k<planMACList.length;k++) {
																				try {
																					System.out.println(String.valueOf(requiredMACList.getJSONObject(i3).get(planMACList[k])));
																					hm.put(planMACList[k], String.valueOf(requiredMACList.getJSONObject(i3).get(planMACList[k])));
																				} catch (Exception e) {
																					hm.put(planMACList[k], "");
																				}
																			}
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
																				
																			}
																			if (temp==1) {
																				header=sheet.createRow(0);
																				System.out.println("headers list is size is" + hs.size());
																				for (int l = 0; l < hs.size(); l++) {
																					header.createCell(l).setCellValue(hs.get(l));
																				}
																			}
																			header=sheet.createRow(temp);
																			for(int l=0;l<hs.size();l++) {
																				
																				header.createCell(l).setCellValue(hm.get(hs.get(l)));
																			}	
																			temp=++temp;
																			hs.remove("subscriber");
																			hs.remove("ban");
																			for(String pl:planMACList) {
																				
																				hm.remove(planMACList);
																			}
																			
																		}hm.clear();
																	} catch (Exception e1) {
																		e1.printStackTrace();
																	}
																	
																	
																	
															}
															}
															
															
															
															
																
															
														}
													}
													
													
													if(false) {
//														if(array.length()==0) {
														
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
								updatedUrl = updatedUrl.contains("$SUBSCRIBER")
										? updatedUrl.replace("$SUBSCRIBER", data.getSubscriber()) : service.getURL();
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
