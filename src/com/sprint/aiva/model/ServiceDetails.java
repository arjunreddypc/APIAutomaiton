package com.sprint.aiva.model;

import java.util.List;

public class ServiceDetails {
	public String name;
	public String URL;
	public String serviceType;
	public List<String> requiredFlags;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public List<String> getRequiredFlags() {
		return requiredFlags;
	}

	public void setRequiredFlags(List<String> requiredFlags) {
		this.requiredFlags = requiredFlags;
	}

}