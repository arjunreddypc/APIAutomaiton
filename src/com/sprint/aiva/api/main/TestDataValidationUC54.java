package com.sprint.aiva.api.main;

import com.sprint.aiva.utility.UtilityService;

public class TestDataValidationUC54 {

	public static void main(String[] args) {
		UtilityService.readExcelFile();
		UtilityService.invokeServiceUC54();
		UtilityService.writeToExcelFile();
	}

}
