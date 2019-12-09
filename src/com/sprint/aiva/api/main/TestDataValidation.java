package com.sprint.aiva.api.main;

import com.sprint.aiva.utility.UtilityService;

public class TestDataValidation {

	public static void main(String[] args) {
		UtilityService.readExcelFile();
		UtilityService.invokeService(); 
		UtilityService.writeToExcelFile();
	}

}
