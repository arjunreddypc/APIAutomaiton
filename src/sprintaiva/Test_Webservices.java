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

	public static void createoutput(HashMap<String, String> banmap, HashMap<String, String> smusermap,
			HashMap<String, String> paymentMethodsMap, LinkedList<String> requiredFlagsList) throws Exception {
		System.out.println("Inside create output::::::::::::::::::::::::::::" + requiredFlagsList);
		String path = System.getProperty("user.dir") + "\\Input2.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rnumberofrows = sheet.getPhysicalNumberOfRows();
		System.out.println(rnumberofrows);
		int numberofcols = sheet.getRow(0).getPhysicalNumberOfCells();
		System.out.println(numberofcols);
		String path1 = System.getProperty("user.dir") + "\\Output2.xlsx";
		FileOutputStream fileOut = new FileOutputStream(new File(path1));
		int i = 1;
		int s = 0;
		System.out.println(
				"DDDDDDDDDDDDDDDDDDDDD" + paymentMethodsMap + "AAAAAAAAAAAAA" + smusermap + "//" + smusermap.get(i));
		for (String ban : banmap.values()) {

			for (String smuser : smusermap.values()) {
				XSSFRow cell = sheet.createRow(i);
				XSSFCell c1 = cell.createCell(0);
				c1.setCellValue(ban);

				XSSFCell c2 = cell.createCell(1);
				c2.setCellValue(smuser);

				LinkedList<XSSFCell> cellColumn = new LinkedList<>();
				createCell(requiredFlagsList.size(), cellColumn, cell);
				for (int k = 0; k < requiredFlagsList.size() - 1; k++) {
					// System.out.println("))))))))"+c1.getStringCellValue()+requiredFlagsList.get(k));
					// System.out.println(paymentMethodsMap.get(c1.getStringCellValue()+requiredFlagsList.get(k)));
					cellColumn.get(k).setCellValue(
							null != paymentMethodsMap.get(c1.getStringCellValue() + requiredFlagsList.get(k))
									? paymentMethodsMap.get(c1.getStringCellValue() + requiredFlagsList.get(k)) : "NA");

				}
			}
			/*
			 * System.out.println("setting cell values::::::::::::::::"
			 * +banmap.get(String.valueOf(i))+","+autopaymap.get(c2.
			 * getStringCellValue())+","
			 * +aslmap.get(c2.getStringCellValue())+","+c2.getStringCellValue())
			 * ;
			 */
			i++;

		}
		System.out.println("end.................");
		workbook.write(fileOut);
		fileOut.close();

		/* workbook.close(); */
	}

	public static void createCell(int count, LinkedList<XSSFCell> cellColumn, XSSFRow cell) {
		for (int j = 2; j < count + 1; j++) {
			cellColumn.add(cell.createCell(j));
		}
	}

	public static void setValuesToMap(String ban, String smuser, JSONObject response, String payloadArray, String key,
			HashMap<String, String> responseMap, LinkedList<String> requiredFlagsList) throws JSONException {
		System.out.println("*******************" + payloadArray + "//" + key);
		String[] keys = null;
		if (key.contains(",")) {
			keys = key.split(",");

			for (String ke : keys) {
				String value = response.getJSONObject(payloadArray).getString(ke);
				responseMap.put(ban + payloadArray + ke, value);

			}
		} else {
			String value = response.getJSONObject(payloadArray).getString(key);
			responseMap.put(ban + payloadArray + key, value);
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + responseMap);
	}

	public static void setValuesToMapFromArray(String ban, String smuser, JSONObject response, String payloadArray,
			String key, HashMap<String, String> responseMap, LinkedList<String> requiredFlagsList)
			throws JSONException {
		System.out.println("setValuesToMapFromArray *******************" + payloadArray + "//" + key);
		String[] keys = null;
		if (key.contains(",")) {
			keys = key.split(",");
			for (String ke : keys) {
				String value = response.getJSONArray(payloadArray).getJSONObject(0).getString(ke);
				responseMap.put(ban + payloadArray + ke, value);
			}
		} else {
			String value = response.getJSONArray(payloadArray).getJSONObject(0).getString(key);
			// String
			// value=response.getJSONArray("key").getJSONObject(0).getString(key);
			responseMap.put(ban + payloadArray + key, value);
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + responseMap);
	}

	public static void setValue(String ban, String smuser, JSONArray response, String payloadArray, String key,
			HashMap<String, String> responseMap, LinkedList<String> requiredFlagsList) throws JSONException {
		System.out.println("*******************");
		String[] keys = null;
		if (key.contains(",")) {
			keys = key.split(",");
			for (String ke : keys) {

				String value = response.getJSONArray(0).getJSONObject(0).getString(ke);
				responseMap.put(ban + payloadArray + ke, value);
			}
		} else {
			// String
			// value=response.getJSONArray(payloadArray).getJSONObject(0).getString(key);
			String value = response.getJSONArray(0).getJSONObject(0).getString(key);
			// String
			// value=response.getJSONArray("key").getJSONObject(0).getString(key);
			responseMap.put(ban + payloadArray + key, value);
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

		// HashMap<String,String> banmap = new HashMap<>();
		LinkedHashMap<String, String> banmap = new LinkedHashMap<String, String>();
		LinkedHashMap<String, String> smusermap = new LinkedHashMap<String, String>();
		HashMap<String, String> autopaymap = new HashMap();
		HashMap<String, String> aslmap = new HashMap();
		HashMap<String, String> typemap = new HashMap();
		HashMap<String, String> pastduemap = new HashMap();
		HashMap<String, String> paymenttypemap = new HashMap();
		HashMap<String, String> aslpercentagemap = new HashMap();
		HashMap<String, String> aslspendlimitmap = new HashMap();

		HashMap<String, String> paymentMethodsMap = new HashMap();
		LinkedList<String> requiredFlagsList = new LinkedList();
		// HashMap<String, String> financialStatusMap = new HashMap();

		String path = System.getProperty("user.dir") + "\\Input2.xlsx";

		String applicationId = null;
		String applicationUserId = null;
		String enterpriseMessageId = null;
		String MessageId = null;
		String Messagedate = null;
		String sm_user = null;
		String serviceType = null;
		FileInputStream inputStream = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		DataFormatter formatter = new DataFormatter();

		XSSFSheet sheet = workbook.getSheetAt(0);
		int rnumberofrows = sheet.getPhysicalNumberOfRows();
		System.out.println(rnumberofrows);
		int numberofcols = sheet.getRow(0).getPhysicalNumberOfCells();
		System.out.println(numberofcols);

		String value4 = null;
		XSSFSheet sheet3 = workbook.getSheetAt(1);
		int rnumberofrows3 = sheet3.getPhysicalNumberOfRows();
		for (int n = 1; n < rnumberofrows3; n++) {

			String requiredFlags = formatter.formatCellValue(sheet3.getRow(n).getCell(9));
			value4 = formatter.formatCellValue(sheet3.getRow(n).getCell(0));
			String flag = formatter.formatCellValue(sheet3.getRow(n).getCell(10));

			if (requiredFlags.contains(",")) {
				for (String ke : requiredFlags.split(",")) {
					requiredFlagsList.add(value4 + ke);
				}
			} else {
				requiredFlagsList.add(value4 + requiredFlags);
			}

			for (int i = 1; i < rnumberofrows; i++) {
				XSSFRow row = sheet.getRow(i);
				// System.out.println(row);

				String url = null;
				String body = null;
				String ban = formatter.formatCellValue(sheet.getRow(i).getCell(0));
				String smuser = formatter.formatCellValue(sheet.getRow(i).getCell(1));
				banmap.put(String.valueOf(i), ban);
				smusermap.put(String.valueOf(i), smuser);
				String Api_nmaes = formatter.formatCellValue(sheet.getRow(i).getCell(0));
				String engagementId = formatter.formatCellValue(sheet.getRow(i).getCell(1));
				String responsetype = formatter.formatCellValue(sheet.getRow(i).getCell(10));

				// System.out.println(ban);

				XSSFSheet sheet1 = workbook.getSheetAt(1);
				int rnumberofrows1 = sheet1.getPhysicalNumberOfRows();

				for (int j = 1; j < rnumberofrows1; j++) {
					XSSFRow row1 = sheet1.getRow(i);

					applicationId = sheet1.getRow(j).getCell(4).toString();
					applicationUserId = sheet1.getRow(j).getCell(5).toString();

					enterpriseMessageId = sheet1.getRow(j).getCell(6).toString();
					Messagedate = sheet1.getRow(j).getCell(7).toString();
					MessageId = sheet1.getRow(j).getCell(8).toString();
					serviceType = sheet1.getRow(j).getCell(2).toString();
					url = sheet1.getRow(j).getCell(1).toString();
					if (sheet1.getRow(j).getCell(3) != null) {
						body = sheet1.getRow(j).getCell(3).toString();
						body = body.replace("$ENGAGEMENTID", engagementId);
						body = body.replace("$BAN", ban);
					}

					SSLContext ctx = SSLContext.getInstance("TLS");
					if (true) {
						ctx.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
					}
					SSLContext.setDefault(ctx);
					if (serviceType.equals("GET")) {
						HttpClient client = new HttpClient();
						url = url.replace("$BAN", ban);
						// Create a method instance.
						GetMethod method = new GetMethod(url);

						method.setRequestHeader("accountId", ban);

						method.setRequestHeader("sm_user", smuser);
						method.setRequestHeader("applicationId", applicationId);
						method.setRequestHeader("applicationUserId", applicationUserId);
						method.setRequestHeader("enterpriseMessageId", enterpriseMessageId);
						method.setRequestHeader("messageId", MessageId);
						method.setRequestHeader("messageDateTimeStamp", Messagedate);

						try {
							// Execute the method.
							int statusCode = client.executeMethod(method);

							if (statusCode != HttpStatus.SC_OK) {
								System.err.println("Method failed: " + method.getStatusLine());
							}

							// Read the response body.
							String responseBody = method.getResponseBodyAsString(100000000);

							JSONObject jsonObject = null;
							JSONArray jsonArray = null;
							try {
								/* if(responseBody.contains("[{")){ */
								if (responsetype.equals("object")) {
									jsonObject = new JSONObject(responseBody);

								}

								if (responsetype.equals("array")) {
									jsonArray = new JSONArray(responseBody);

								}
								System.out.println(
										"##########################" + requiredFlags + "//" + value4 + "!!" + flag);
								if ("array".equalsIgnoreCase(flag)) {

									/*setValuesToMapFromArray(ban, smuser, jsonArray, value4, requiredFlags,
											paymentMethodsMap, requiredFlagsList);*/
									System.out.println("$$$$$$$$$$$$$$$$$$$$" + paymentMethodsMap);
									System.out.println("$$$$$$$$$$$$$$$$$$$$" + requiredFlags);
								} else if ("object".equalsIgnoreCase(flag)) {
									/*setValuesToMap(ban, smuser, js, value4, requiredFlags, paymentMethodsMap,
											requiredFlagsList);*/
									System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@" + paymentMethodsMap);
									System.out.println("$$$$$$$$$$$$$$$$$$$$" + requiredFlags);
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println(new String(responseBody));
							// wwbCop.write();

						} catch (HttpException e) {
							System.err.println("Fatal protocol violation: " + e.getMessage());
							e.printStackTrace();
						} catch (IOException e) {
							System.err.println("Fatal transport error: " + e.getMessage());
							e.printStackTrace();
						} finally {
							// Release the connection.

							// fileOut.close();
							method.releaseConnection();
						}
					}

					else {
					}
				}
			}
		}
		try {
			System.out.println("banmap::::::::::" + banmap.toString());
			System.out.println("autopaymap::::::::::" + autopaymap.toString());
			System.out.println("aslmap::::::::::" + aslmap.toString());
			System.out.println("smusermap::::::::::" + smusermap.toString());
			createoutput(banmap, smusermap, paymentMethodsMap, requiredFlagsList);
		} catch (Exception e) {
			System.out.println("//////////////////" + e);
		}
	}

}
