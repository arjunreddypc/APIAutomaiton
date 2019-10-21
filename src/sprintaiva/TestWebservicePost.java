package sprintaiva;

import static org.testng.Assert.expectThrows;

import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
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
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class TestWebservicePost extends API_Input {

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

		String path = System.getProperty("user.dir") + "\\Input2.xlsx";
		 
		String applicationId =null;
		String applicationUserId = null;
	String	enterpriseMessageId=null;
String serviceType=null;
		FileInputStream inputStream = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		DataFormatter formatter = new DataFormatter();

		XSSFSheet sheet = workbook.getSheetAt(1);
		int rnumberofrows = sheet.getPhysicalNumberOfRows();
		
		System.out.println(rnumberofrows);
		/* int rnumberofrows= sheet.getPhysicalNumberOfRows(); */

		// XSSFSheet sheet1 = workbook.getSheetAt(1);

		for (int i = 1; i < rnumberofrows; i++) {
			XSSFRow row = sheet.getRow(i);
			//System.out.println(row);
  
			String url = null;
			String body=null;
			String ban=formatter.formatCellValue(sheet.getRow(i).getCell(0));
			String Api_nmaes=formatter.formatCellValue(sheet.getRow(i).getCell(0));
			String engagementId=formatter.formatCellValue(sheet.getRow(i).getCell(1));
			String value1=formatter.formatCellValue(sheet.getRow(0).getCell(2));
			
			System.out.println(ban);
			
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			int rnumberofrows1 = sheet1.getPhysicalNumberOfRows();
			for (int j = 1; j < rnumberofrows1; j++)  {
				XSSFRow row1 = sheet1.getRow(i);
				//System.out.println(row1);
				String API_NAME = formatter.formatCellValue(sheet1.getRow(j).getCell(0));
				String value2 = formatter.formatCellValue(sheet1.getRow(j).getCell(10));
				if(value1.equals(value2))
				{
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
			
	        method.addRequestHeader("accept","application/json");
	        

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

				String path1 = System.getProperty("user.dir") + "\\Output.xlsx";
				//File DataManager = new File(path1);
				
				// Workbook wb = Workbook.getWorkbook(DataManager);
				
				
				//WritableWorkbook wwbCop = Workbook.createWorkbook(new File(path1));
				// WritableWorkbook wwbCop = Workbook.createWorkbook(new
				// File(path1));
				FileOutputStream fileOut = new FileOutputStream(new File(path1) );
				//XSSFRow headerColumn = sheet1.createRow(i);
				//XSSFCell cell = headerColumn.createCell(5);
				XSSFRow cell = sheet.createRow(i);
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

			String path1 = System.getProperty("user.dir") + "\\Output.xlsx";
			//File DataManager = new File(path1);
			
			// Workbook wb = Workbook.getWorkbook(DataManager);
			
			
			//WritableWorkbook wwbCop = Workbook.createWorkbook(new File(path1));
			// WritableWorkbook wwbCop = Workbook.createWorkbook(new
			// File(path1));
			FileOutputStream fileOut = new FileOutputStream(new File(path1) );
			//XSSFRow headerColumn = sheet1.createRow(i);
			//XSSFCell cell = headerColumn.createCell(5);
			XSSFRow cell = sheet.createRow(i);
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
		}

		

		
}
			}			

		}
		}
		
		
		workbook.close();

	}
}
