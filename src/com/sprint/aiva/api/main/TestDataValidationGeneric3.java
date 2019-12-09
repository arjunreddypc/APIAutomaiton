package com.sprint.aiva.api.main;

import java.io.IOException;

import org.json.JSONException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sprint.aiva.utility.UtilityService;

public class TestDataValidationGeneric3 {
	
	
	  @BeforeMethod
	  public void beforeMethod() {
		  System.out.println("this is before method");
	  }
	  @Test(priority=0)
	  public void onlyBan() throws JSONException, IOException {
			UtilityService.readExcelFile();
			UtilityService.invokeServiceGeneric();
			UtilityService.writeToExcelFile();
	  }
	  @Test(priority=1)
	  public void banAndSubscriber() throws IOException {
		  UtilityService.readExcelFileBANAndSubscriber();
			UtilityService.invokeServiceGeneric2();
			UtilityService.writeToExcelFile();
	  }
	  @AfterMethod
	  public void afterMethod() {
	  }
}
