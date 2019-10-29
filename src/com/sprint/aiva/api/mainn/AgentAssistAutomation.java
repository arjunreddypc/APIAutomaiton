package com.sprint.aiva.api.mainn;

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
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sprint.agent.assist.InputMessage;
import com.sprint.agent.assist.InvalidCertificateTrustManager;
import com.sprint.aiva.utility.AIVAConstants;

public class AgentAssistAutomation {

	public static void main(String[] args) {
		readInputMessages();
		invokeBotController();
		generateOutputSheet();
	}

	public static List<InputMessage> inputMessages = new ArrayList<InputMessage>();
	public static Map<String, List<String>> outputDetails = new LinkedHashMap<String, List<String>>();

	public static void invokeBotController() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			if (true) {
				ctx.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
			}

			// This will prepare the headers
			SSLContext.setDefault(ctx);

			Iterator<InputMessage> inputIterator = inputMessages.iterator();
			prepareHeaders();
			for (int i = 0; i < inputMessages.size(); i++) {
				InputMessage input = inputMessages.get(i);
				HttpClient client = new HttpClient();
				String URL = "https://dev1-apiservices-web.dev.sprint.com:7441/api/digital/aiva/v1/virtual-agent-controller/controller/agent";
				PostMethod method = new PostMethod(URL);
				JSONObject jsonInput = new JSONObject();
				JSONObject messagesInput = new JSONObject();
				JSONObject engagementInput = new JSONObject();
				jsonInput.put("agentId", input.getAgentId());

				engagementInput.put("id", input.getEngagementId());
				engagementInput.put("intentStartTimestamp", AIVAConstants.MESSAGE_TIMESTAMP);
				messagesInput.put("messageText", inputIterator.next().getMessageText());
				messagesInput.put("userId", "");
				engagementInput.put("messages", messagesInput);
				jsonInput.put("engagements", engagementInput);
				StringRequestEntity requestEntity = new StringRequestEntity(jsonInput.toString(), "application/json",
						"UTF-8");

				method.setRequestHeader("applicationId", AIVAConstants.APPLICATION_ID);
				method.setRequestHeader("applicationUserId", AIVAConstants.APPLICATION_USR_ID);
				method.setRequestHeader("channelId", "6");
				method.setRequestHeader("directoryType", "2");
				method.setRequestHeader("enterpriseMessageId", AIVAConstants.ENTERPRISE_MSG_ID);
				method.setRequestHeader("messageId", AIVAConstants.MESSAGE_ID);
				method.setRequestHeader("messageDateTimeStamp", AIVAConstants.MESSAGE_TIMESTAMP);
				method.setRequestHeader("Authorization", "Basic bHZvZGV2OnNwcmludA==");
				method.setRequestEntity(requestEntity);

				// Execute the method.
				Integer statusCode = client.executeMethod(method);
				System.out.println(statusCode);
				if (statusCode.equals(201)) {
					String res = method.getResponseBodyAsString();
					StringBuilder documentURLs = new StringBuilder();
					JSONObject jsonObject = new JSONObject(res);
					JSONArray discoveryText = jsonObject.getJSONArray("discoveryResponse");
					for (int j = 0; j < discoveryText.length(); j++) {
						System.out.println(discoveryText.get(j));
						JSONObject discoveryObject = new JSONObject(discoveryText.get(j).toString());
						if (j != 0)
							documentURLs.append(",");
						documentURLs.append(discoveryObject.get("reposURI"));

					}
					List<String> response = Arrays.asList(input.getAgentId(), jsonObject.getString("userInitialText"),
							jsonObject.getString("intent"), documentURLs.toString());
					outputDetails.put(input.getEngagementId(), response);
				} 

			}

		} catch (Exception exception) {
			System.out.println("Exception details " + exception);
		}

	}

	public static void readInputMessages() {

		try {

			FileInputStream inputStream = new FileInputStream(new File(AIVAConstants.agentAssistInputPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			DataFormatter formatter = new DataFormatter();
			XSSFSheet inputSheet = workbook.getSheet("INPUT_SHEET");

			// Fetch details related to test data
			for (int i = 1; i < inputSheet.getPhysicalNumberOfRows(); i++) {
				InputMessage inputMessage = new InputMessage();
				inputMessage.setAgentId(formatter.formatCellValue(inputSheet.getRow(i).getCell(0)));
				inputMessage.setEngagementId(formatter.formatCellValue(inputSheet.getRow(i).getCell(1)));
				String inputMsg = formatter.formatCellValue(inputSheet.getRow(i).getCell(2));
				if (!inputMsg.equals("")) {
					inputMessage.setMessageText(inputMsg);
					inputMessages.add(inputMessage);
				}
			}

		} catch (Exception e) {
			System.out.println("There is an issue in retrieving from EXcel" + e);
		}
	}

	public static void generateOutputSheet() {
		try {
			FileInputStream inputStream = new FileInputStream(new File(AIVAConstants.agentAssistInputPath));
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			FileOutputStream fileOut = new FileOutputStream(new File(AIVAConstants.agentAssistOutputPath));
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

	public static void prepareHeaders() {

		outputDetails.put("Engagement Id",
				Arrays.asList("Agent Id", "Input text", "Identified intent", "Document URL's"));
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
}
