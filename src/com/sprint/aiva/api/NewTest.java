package com.sprint.aiva.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class NewTest {
  @Test
  public void f() {
  }
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("this is before method");
  }

  @AfterMethod
  public void afterMethod() {
  }

}
