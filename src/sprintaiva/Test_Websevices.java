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

public class Test_Websevices extends API_Input {

	// API_Input ai = new API_Input();

	// private static String url =
	// "https://st2-apiservices-web.test.sprint.com:7441/api/process/pay/v1/accounts/148096294/future-payments?realTimeInd=false&isUsgBan=false";

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

		Set<String> newtestCases = AIVAForms.getTCTestCaseNum();
		Set<String> banset = new HashSet();
		HashMap<String, String> autopaymap = new HashMap();
		HashMap<String, String> aslmap = new HashMap();
		HashMap<String, String> typemap = new HashMap();

		String path = System.getProperty("user.dir") + "\\Input2.xlsx";

		String applicationId =null;
		String applicationUserId = null;
		String	enterpriseMessageId=null;
		String serviceType=null;
		FileInputStream inputStream = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		DataFormatter formatter = new DataFormatter();

		XSSFSheet sheet = workbook.getSheetAt(0);
		int rnumberofrows = sheet.getPhysicalNumberOfRows();
		System.out.println(rnumberofrows);
		int numberofcols= sheet.getRow(0).getPhysicalNumberOfCells();
		System.out.println(numberofcols);	 

		// XSSFSheet sheet1 = workbook.getSheetAt(1);


		for (int k = 0; k < numberofcols; k++) {
			for (int i = 1; i < rnumberofrows; i++) {
				XSSFRow row = sheet.getRow(i);
				//System.out.println(row);

				String url = null;
				String body=null;
				String ban=formatter.formatCellValue(sheet.getRow(i).getCell(0));
				banset.add(ban);
				String Api_nmaes=formatter.formatCellValue(sheet.getRow(i).getCell(0));
				String engagementId=formatter.formatCellValue(sheet.getRow(i).getCell(1));
				String value1=formatter.formatCellValue(sheet.getRow(0).getCell(k));

				//System.out.println(ban);

				XSSFSheet sheet1 = workbook.getSheetAt(1);
				int rnumberofrows1 = sheet1.getPhysicalNumberOfRows();
				for (int j = 1; j < rnumberofrows1; j++)  {
					
					//System.out.println(row1);
					String API_NAME = formatter.formatCellValue(sheet1.getRow(j).getCell(0));
					String value2 = formatter.formatCellValue(sheet1.getRow(j).getCell(10));
					
					XSSFRow cell;
					//String value3 = formatter.formatCellValue(sheet1.getRow(j+1).getCell(10));
					//if(Api_nmaes.equals(API_NAME))
					if(value1.equals(value2))
					{
						cell = sheet.createRow(i);
						applicationId = sheet1.getRow(j).getCell(4).toString();
						applicationUserId=sheet1.getRow(j).getCell(5).toString();
						enterpriseMessageId=sheet1.getRow(j).getCell(6).toString();
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


								String isAutopay="NA";
								String Type="NA";
								String paymentType ="NA";
								String ASL ="NA";
								String pastDue ="NA";
								String aslPercentage ="NA";
								String aslSpendingLimit ="NA";
								//String ebill = "NA";

								try {
									/*if(responseBody.contains("[{")){*/
									if(responseBody.contains("[")||responseBody.contains("{")){
										JSONObject js=new JSONObject(responseBody);

										if(value2.equals("ASLAccount")){
											if(js.has("financialStatus"))
											{

												ASL=js.getJSONObject("financialStatus").getString("aslAccount");
												aslmap.put(ban, ASL);
												//pastDue=js.getJSONObject("financialStatus").getString("pastDue");
												//aslPercentage=js.getJSONObject("financialStatus").getString("aslPercentageUsed");
												//aslSpendingLimit=js.getJSONObject("financialStatus").getString("aslSpendingLimit");


											}

										}


										if(value2.equals("Autopay")){
											if(js.has("paymentMethods"))

											{
												/*isAutopay=js.getJSONObject("paymentMethods").getString("isAutopay");*/
												isAutopay=js.getJSONArray("paymentMethods").getJSONObject(0).getString("isAutopay");
												autopaymap.put(ban, isAutopay);

												//Type=js.getJSONArray("paymentMethods").getJSONObject(0).getString("type");
											}
										}


										if(value2.equals("Type")){
											if(js.has("paymentMethods"))

											{


												Type=js.getJSONArray("paymentMethods").getJSONObject(0).getString("type");
											}

										}
										/*if(js.has("billDeliveryType"))
					{
						ebill=js.getString("billDeliveryType").toString();


					}*/


										/*JSONObject js1=new JSONObject(responseBody);*/



										if(value2.equals("PaymentType")){
											if(js.has("futurePayments"))
											{
												paymentType=js.getJSONArray("futurePayments").getJSONObject(0).getString("paymentType");

											}

										}
										/*}*/

										/*if(responseBody.endsWith("]"))
					{

						responseBody="{"+"\""+"key"+"\""+":"+responseBody+"}";
						JSONObject js2=new JSONObject(responseBody);
						if(js2.getJSONArray("key").length()>0)
						paymentType=js2.getJSONArray("key").getJSONObject(0).getString("paymentType");
					}*/









										/*if(js.has("financialStatus"))
					{
					 ASL=js.getJSONArray("financialStatus").getJSONObject(0).getString("aslAccount");

					}*/

									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}












								/*XSSFWorkbook workbook1 = new XSSFWorkbook(inputStream);*/

								XSSFSheet sheet2 = workbook.getSheet("INPUT_SHEET");
								//XSSFCell sheet1 = workbook.getSheetAt(0).createRow(i).createCell(5);
								// Input.xlsx

								//String path1 = System.getProperty("user.dir") + "\\Output.xlsx";
								//File DataManager = new File(path1);

								// Workbook wb = Workbook.getWorkbook(DataManager);


								//WritableWorkbook wwbCop = Workbook.createWorkbook(new File(path1));
								// WritableWorkbook wwbCop = Workbook.createWorkbook(new
								// File(path1));
								/*FileOutputStream fileOut = new FileOutputStream(new File(path1) );*/

								//XSSFRow headerColumn = sheet1.createRow(i);
								//XSSFCell cell = headerColumn.createCell(5);
								/*XSSFRow cell = sheet.createRow(i);*/


								//wwbCop.write();


							} catch (HttpException e) {
								System.err.println("Fatal protocol violation: " + e.getMessage());
								e.printStackTrace();
							} catch (IOException e) {
								System.err.println("Fatal transport error: " + e.getMessage());
								e.printStackTrace();
							} finally {
								// Release the connection.

								/*fileOut.close();*/
								method.releaseConnection();
							}
						}


						else
						{
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

							/* int rnumberofrows= sheet.getPhysicalNumberOfRows(); */

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









								/*XSSFWorkbook workbook1 = new XSSFWorkbook(inputStream);*/

								XSSFSheet sheet2 = workbook.getSheet("INPUT_SHEET");
								//XSSFCell sheet1 = workbook.getSheetAt(0).createRow(i).createCell(5);
								// Input.xlsx

								/*String path1 = System.getProperty("user.dir") + "\\Output.xlsx";*/
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
								//workbook.write(fileOut);

								//wwbCop.write();
								//fileOut.close();

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
							}




						}
					}			

				}
			}
		}
		try{
		createoutput(inputStream, banset,autopaymap,aslmap);
		}
		catch(Exception e){
			System.out.println("//////////////////"+e);
		}
		
			
		/*workbook.close();*/

	}

	public static void createoutput(FileInputStream inputStream, Set<String> banset,HashMap<String, String> autopaymap,
			HashMap<String, String> aslmap) throws Exception{
		
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rnumberofrows = sheet.getPhysicalNumberOfRows();
		System.out.println(rnumberofrows);
		int numberofcols= sheet.getRow(0).getPhysicalNumberOfCells();
		System.out.println(numberofcols);
		String path1 = System.getProperty("user.dir") + "\\Output.xlsx";
		FileOutputStream fileOut = new FileOutputStream(new File(path1) );
		for(String ban:banset){
			for (int i = 1; i < rnumberofrows; i++) {
				for (int k = 0; k < numberofcols; k++) {

					XSSFRow cell = sheet.createRow(1);
					XSSFCell c1=cell.createCell(5);
					XSSFCell c2=cell.createCell(0);
					c2.setCellValue(ban);	
					//XSSFCell c3=cell.createCell(1);

					//XSSFCell c5= cell.createCell(2);
					/*XSSFCell c6= cell.createCell(3);
					XSSFCell c7= cell.createCell(4);
					XSSFCell c8= cell.createCell(6);
					XSSFCell c9= cell.createCell(7);*/
					//XSSFCell c10= cell.createCell(8);

					XSSFCell c4= cell.createCell(1);
					c4.setCellValue(null!=autopaymap.get(ban)?autopaymap.get(ban):"NA");		

					XSSFCell c5= cell.createCell(2);
					c5.setCellValue(null!=aslmap.get(ban)?aslmap.get(ban):"NA");		

					workbook.write(fileOut);
					fileOut.close();
				}
			}
		}





		/*workbook.write(fileOut);*/
		/*fileOut.close();*/

		workbook.close();
	}
	
}

