package com.sprint.aiva.api.main;

import java.io.IOException;

import org.json.JSONException;

import com.sprint.aiva.utility.UtilityService;

public class TestDataValidationGeneric {

	public static void main(String[] args) throws JSONException, IOException {
		UtilityService.readExcelFile();
		UtilityService.invokeServiceGeneric();
		UtilityService.writeToExcelFile();
	}

}
