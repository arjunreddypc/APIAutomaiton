package sprintaiva;

import static org.testng.Assert.expectThrows;

import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import org.apache.poi.ss.usermodel.DataFormatter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import sprintaiva.MySSLSocketFactory;

public class Test_Webservices extends API_Input {

	// API_Input ai = new API_Input();

	// private static String url =
	// "https://st2-apiservices-web.test.sprint.com:7441/api/process/pay/v1/accounts/148096294/future-payments?realTimeInd=false&isUsgBan=false";

	public static void createoutput(HashMap<String,String> banmap,HashMap<String, String> paymentMethodsMap, LinkedList<String> keysList) throws Exception{
		System.out.println("Inside create output::::::::::::::::::::::::::::"+keysList);
		String path = System.getProperty("user.dir") + "\\Input2.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rnumberofrows = sheet.getPhysicalNumberOfRows();
		System.out.println(rnumberofrows);
		int numberofcols= sheet.getRow(0).getPhysicalNumberOfCells();
		System.out.println(numberofcols);
		String path1 = System.getProperty("user.dir") + "\\Output2.xlsx";
		FileOutputStream fileOut = new FileOutputStream(new File(path1) );
		int i=1;
		for (String ban:banmap.values()) {

			XSSFRow cell = sheet.createRow(i);
			XSSFCell c1=cell.createCell(0);
			c1.setCellValue(ban);	
			LinkedList<XSSFCell> cellColumn = new LinkedList<>();
			createCell(keysList.size(), cellColumn, cell);					
			for(int k=0;k<keysList.size();k++){
				//System.out.println("))))))))"+c1.getStringCellValue()+keysList.get(k));
				//System.out.println(paymentMethodsMap.get(c1.getStringCellValue()+keysList.get(k)));
				cellColumn.get(k).setCellValue(null!=paymentMethodsMap.get(c1.getStringCellValue()+keysList.get(k))
						?paymentMethodsMap.get(c1.getStringCellValue()+keysList.get(k)):"NA");
				
			}
			/*System.out.println("setting cell values::::::::::::::::"+banmap.get(String.valueOf(i))+","+autopaymap.get(c2.getStringCellValue())+","
						+aslmap.get(c2.getStringCellValue())+","+c2.getStringCellValue());*/
			i++;

		}
		System.out.println("end.................");
		workbook.write(fileOut);
		fileOut.close();

		/*workbook.close();*/
	}

	public static void createCell(int count, LinkedList<XSSFCell> cellColumn, XSSFRow cell){
		for(int j=1;j<count+1;j++){
			cellColumn.add(cell.createCell(j));
		}
	}

	public static void setValuesToMap(String ban, JSONObject response, String payloadArray, String key, HashMap<String, String> responseMap, LinkedList<String> keysList) 
			throws JSONException {
		System.out.println("*******************"+payloadArray+"//"+key);
		String[] keys = null;
		if(key.contains(",")){
			keys = key.split(",");

			for(String ke:keys){	
				String value=response.getJSONObject(payloadArray).getString(ke);
				responseMap.put(ban+payloadArray+ke, value);

			}
		}else{
			String value=response.getJSONObject(payloadArray).getString(key);
			responseMap.put(ban+payloadArray+key, value);
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+responseMap);
	}

	public static void setValuesToMapFromArray(String ban, JSONObject response, String payloadArray, String key, HashMap<String, String> responseMap, LinkedList<String> keysList) 
			throws JSONException {
		System.out.println("setValuesToMapFromArray *******************"+payloadArray+"//"+key);
		String[] keys = null;
		if(key.contains(",")){
			keys = key.split(",");
			for(String ke:keys){	
				String value=response.getJSONArray(payloadArray).getJSONObject(0).getString(ke);
				responseMap.put(ban+payloadArray+ke, value);
			}
		}else{
			String value=response.getJSONArray(payloadArray).getJSONObject(0).getString(key);
			//String value=response.getJSONArray("key").getJSONObject(0).getString(key);
			responseMap.put(ban+payloadArray+key, value);
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+responseMap);
	}


	public static void setValue(String ban, JSONArray response, String payloadArray, String key, HashMap<String, String> responseMap, LinkedList<String> keysList) 
			throws JSONException {
		System.out.println("*******************");
		String[] keys = null;
		if(key.contains(",")){
			keys = key.split(",");
			for(String ke:keys){	


				String value=response.getJSONArray(0).getJSONObject(0).getString(ke);
				responseMap.put(ban+payloadArray+ke, value);
			}
		}else{
			//String value=response.getJSONArray(payloadArray).getJSONObject(0).getString(key);
			String value=response.getJSONArray(0).getJSONObject(0).getString(key);
			//String value=response.getJSONArray("key").getJSONObject(0).getString(key);
			responseMap.put(ban+payloadArray+key, value);
		}
	}







	public static void main(String[] args)
			throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
			BiffException, IOException, RowsExceededException, WriteException {
		// Create an instance of HttpClient.
		/*
		 * KeyStore KeyStore= MySSLSocketFactory.getKeystore(); Protocol myHTTPS
		 * = new Protocol( "https", new MySSLSocketFactory(KeyStore), 443 );
		 * 
		 * Protocol.registerProtocol( "https", myHTTPS );
		 */






		//HashMap<String,String> banmap = new HashMap<>();
		LinkedHashMap<String, String> banmap = new LinkedHashMap<String, String>(); 
		HashMap<String, String> autopaymap = new HashMap();
		HashMap<String, String> aslmap = new HashMap();
		HashMap<String, String> typemap = new HashMap(); 
		HashMap<String, String> pastduemap = new HashMap();
		HashMap<String, String> paymenttypemap = new HashMap();
		HashMap<String, String> aslpercentagemap = new HashMap();
		HashMap<String, String> aslspendlimitmap = new HashMap();

		HashMap<String, String> paymentMethodsMap = new HashMap();
		LinkedList<String> keysList = new LinkedList();
		//HashMap<String, String> financialStatusMap = new HashMap(); 


		String path = System.getProperty("user.dir") + "\\Input2.xlsx";

		String applicationId =null;
		String applicationUserId = null;
		String	enterpriseMessageId=null;
		String	sm_user=null;
		String serviceType=null;
		FileInputStream inputStream = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		DataFormatter formatter = new DataFormatter();

		XSSFSheet sheet = workbook.getSheetAt(0);
		int rnumberofrows = sheet.getPhysicalNumberOfRows();
		System.out.println(rnumberofrows);
		int numberofcols= sheet.getRow(0).getPhysicalNumberOfCells();
		System.out.println(numberofcols);	 


		String value4 = null;
		XSSFSheet sheet3 = workbook.getSheetAt(1);
		int rnumberofrows3 = sheet3.getPhysicalNumberOfRows();
		for (int n = 1; n < rnumberofrows3; n++) {

			String value3 = formatter.formatCellValue(sheet3.getRow(n).getCell(9));
			 value4 = formatter.formatCellValue(sheet3.getRow(n).getCell(0));
			String flag = formatter.formatCellValue(sheet3.getRow(n).getCell(10));
			
			if(value3.contains(",")){
				for(String ke:value3.split(",")){
					keysList.add(value4+ke);}
			}
			else{
				keysList.add(value4+value3);
			}


			/*String value4 = formatter.formatCellValue(sheet3.getRow(2).getCell(9));
		if(value4.contains(",")){
			for(String ke:value4.split(",")){
				keysList.add("paymentMethods"+ke);}
		}
		else{
			keysList.add("paymentMethods"+value4);
		}
		String value5 = formatter.formatCellValue(sheet3.getRow(3).getCell(9));
		if(value5.contains(",")){
			for(String ke:value5.split(",")){
				keysList.add("futurePayments"+ke);}
		}
		else{
			keysList.add("futurePayments"+value5);
		}

			 */


			// XSSFSheet sheet1 = workbook.getSheetAt(1);

			/*String path1 = System.getProperty("user.dir") + "\\Output.xlsx";
		FileOutputStream fileOut = new FileOutputStream(new File(path1) );*/

			for (int i = 1; i < rnumberofrows; i++) {
				XSSFRow row = sheet.getRow(i);
				//System.out.println(row);

				String url = null;
				String body=null;
				String ban=formatter.formatCellValue(sheet.getRow(i).getCell(0));
				banmap.put(String.valueOf(i),ban);
				String Api_nmaes=formatter.formatCellValue(sheet.getRow(i).getCell(0));
				String engagementId=formatter.formatCellValue(sheet.getRow(i).getCell(1));


				//System.out.println(ban);

				XSSFSheet sheet1 = workbook.getSheetAt(1);
				int rnumberofrows1 = sheet1.getPhysicalNumberOfRows();

				for (int j = 1; j < rnumberofrows1; j++)  {
					XSSFRow row1 = sheet1.getRow(i);
					//System.out.println(row1);
					/*String API_NAME = formatter.formatCellValue(sheet1.getRow(j).getCell(0));
					String value2 = formatter.formatCellValue(sheet1.getRow(j).getCell(10));
					XSSFRow cell;*/

					//if(Api_nmaes.equals(API_NAME))

					//cell = sheet.createRow(i);
					applicationId = sheet1.getRow(j).getCell(4).toString();
					applicationUserId=sheet1.getRow(j).getCell(5).toString();
					enterpriseMessageId=sheet1.getRow(j).getCell(6).toString();
					sm_user=sheet1.getRow(j).getCell(11).toString();
					serviceType=sheet1.getRow(j).getCell(2).toString();
					url=sheet1.getRow(j).getCell(1).toString();
					if(sheet1.getRow(j).getCell(3)!=null){
						body=sheet1.getRow(j).getCell(3).toString();
						body=body.replace("$ENGAGEMENTID", engagementId);
						body=body.replace("$BAN", ban);
					}

					//String API_NAME1 = sheet.getRow(i).getCell(3).getStringCellValue();
					//System.out.println(API_NAME.replace("<<ban>>", ban));

					SSLContext ctx = SSLContext.getInstance("TLS");
					if (true) {
						ctx.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
					}
					SSLContext.setDefault(ctx);
					if(serviceType.equals("GET"))
					{
						HttpClient client = new HttpClient();
						url=url.replace("$BAN", ban);
						// Create a method instance.
						GetMethod method = new GetMethod(url);

						method.setRequestHeader("accountId", ban);

						method.setRequestHeader("sm_user", sm_user);
						
						// method.setRequestHeader("accountId", linedetail);

						// Provide custom retry handler is necessary
						// method.setRequestHeader("accountId", "148096294");
						/*
						 * method.setRequestHeader("applicationId", "ECMW");
						 * method.setRequestHeader("applicationUserId", "ECMW");
						 * method.setRequestHeader("enterpriseMessageId", "ECMW1000");
						 * method.setRequestHeader("messageDateTimeStamp",
						 * "2007-10-01T14:20:33"); method.setRequestHeader("messageId",
						 * "1000");
						 */

						try {
							// Execute the method.
							int statusCode = client.executeMethod(method);

							if (statusCode != HttpStatus.SC_OK) {
								System.err.println("Method failed: " + method.getStatusLine());
							}

							// Read the response body.
							String responseBody = method.getResponseBodyAsString(100000000);

							try {
								/*if(responseBody.contains("[{")){*/
								JSONObject js=new JSONObject(responseBody);

								System.out.println("##########################"+value3+"//"+value4+"!!"+flag);
								if("array".equalsIgnoreCase(flag)){
									setValuesToMapFromArray(ban, js, value4, value3,paymentMethodsMap,keysList);
									System.out.println("$$$$$$$$$$$$$$$$$$$$"+paymentMethodsMap);
									System.out.println("$$$$$$$$$$$$$$$$$$$$"+value3);
								}else if("object".equalsIgnoreCase(flag)){
									setValuesToMap(ban, js, value4, value3,paymentMethodsMap, keysList);
									System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@"+paymentMethodsMap);
									System.out.println("$$$$$$$$$$$$$$$$$$$$"+value3);
								} 
								
								/*
										if(value2.equals("PaymentType")){
										if(responseBody.endsWith("]")){

						responseBody="{"+"\""+"key"+"\""+":"+responseBody+"}";
						JSONObject js2=new JSONObject(responseBody);
						if(js2.getJSONArray("key").length()>0)
						paymentType=js2.getJSONArray("key").getJSONObject(0).getString("paymentType");
						paymenttypemap.put(ban, paymentType);
					}
										}					
								 */
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println(new String(responseBody));
							//wwbCop.write();

						} catch (HttpException e) {
							System.err.println("Fatal protocol violation: " + e.getMessage());
							e.printStackTrace();
						} catch (IOException e) {
							System.err.println("Fatal transport error: " + e.getMessage());
							e.printStackTrace();
						} finally {
							// Release the connection.

							//fileOut.close();
							method.releaseConnection();
						}
					}

					else
					{/*
							String everything=null;


							BufferedReader br = new BufferedReader(new FileReader("inputfile"));
							try {
								StringBuilder sb = new StringBuilder();
								String line = br.readLine();

								while (line != null) {
									sb.append(line);
									sb.append(System.lineSeparator());
									line = br.readLine();
								}
								everything = sb.toString();
							} finally {
								br.close();
							}

							 int rnumberofrows= sheet.getPhysicalNumberOfRows(); 

							// XSSFSheet sheet1 = workbook.getSheetAt(1);



							HttpClient client = new HttpClient();

							StringRequestEntity st=new StringRequestEntity(body, "application/json", "utf-8");
							// Create a method instance.
							//GetMethod method = new GetMethod(API_NAME);
							PostMethod method = new PostMethod(url);
							method.setRequestEntity(st);


							method.setRequestHeader("accountId", ban);
							method.setRequestHeader("Authorization", "Basic bHZvc3Q6c3ByaW50");
							method.setRequestHeader("Password", "sprint");
							method.setRequestHeader("applicationId", applicationId);
							method.setRequestHeader("applicationUserId", applicationUserId);

							// method.setRequestHeader("accountId", linedetail);

							// Provide custom retry handler is necessary
							// method.setRequestHeader("accountId", "148096294");

					 * method.setRequestHeader("applicationId", "ECMW");
					 * method.setRequestHeader("applicationUserId", "ECMW");
					 * method.setRequestHeader("enterpriseMessageId", "ECMW1000");
					 * method.setRequestHeader("messageDateTimeStamp",
					 * "2007-10-01T14:20:33"); method.setRequestHeader("messageId",
					 * "1000");


							try {
								// Execute the method.
								int statusCode = client.executeMethod(method);

								if (statusCode != HttpStatus.SC_OK) {
									System.err.println("Method failed: " + method.getStatusLine());
								}

								// Read the response body.
								String responseBody = method.getResponseBodyAsString(100000000);

								XSSFWorkbook workbook1 = new XSSFWorkbook(inputStream);

								XSSFSheet sheet2 = workbook.getSheet("INPUT_SHEET");
								//XSSFCell sheet1 = workbook.getSheetAt(0).createRow(i).createCell(5);
								// Input.xlsx

								String path1 = System.getProperty("user.dir") + "\\Output.xlsx";
								//File DataManager = new File(path1);

								// Workbook wb = Workbook.getWorkbook(DataManager);


								//WritableWorkbook wwbCop = Workbook.createWorkbook(new File(path1));
								// WritableWorkbook wwbCop = Workbook.createWorkbook(new
								// File(path1));
								//FileOutputStream fileOut = new FileOutputStream(new File(path1) );
								//XSSFRow headerColumn = sheet1.createRow(i);
								//XSSFCell cell = headerColumn.createCell(5);
								//XSSFRow cell = sheet.createRow(i);
								XSSFCell c=cell.createCell(3);
								XSSFCell c1=cell.createCell(0);
								XSSFCell c2=cell.createCell(1);
								XSSFCell c3=cell.createCell(2);

								c1.setCellValue(API_NAME);
								c2.setCellValue(ban);
								c3.setCellValue(engagementId);

								c.setCellValue(responseBody);
								workbook.write(fileOut);

								//wwbCop.write();
								fileOut.close();

								// Closing the workbook

								// Deal with the response.
								// Use caution: ensure correct character encoding and is not
								// binary data
								System.out.println(new String(responseBody));

								String value = new String(responseBody).toString();

							} catch (HttpException e) {
								System.err.println("Fatal protocol violation: " + e.getMessage());
								e.printStackTrace();
							} catch (IOException e) {
								System.err.println("Fatal transport error: " + e.getMessage());
								e.printStackTrace();
							} finally {
								// Release the connection.

								method.releaseConnection();
							}*/
					}
				}
			}
		}
		try{
			System.out.println("banmap::::::::::"+banmap.toString());
			System.out.println("autopaymap::::::::::"+autopaymap.toString());
			System.out.println("aslmap::::::::::"+aslmap.toString());
			createoutput( banmap, paymentMethodsMap, keysList);
		}catch(Exception e){
			System.out.println("//////////////////"+e);
		} 	
	}

}

