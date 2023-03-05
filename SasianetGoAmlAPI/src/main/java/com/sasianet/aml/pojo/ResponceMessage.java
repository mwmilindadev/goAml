package com.sasianet.aml.pojo;

import java.io.Serializable;

public class ResponceMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String responceStatus;
	
	public String getResponceStatus() {
		return responceStatus;
	}

	public void setResponceStatus(String responceStatus) {
		this.responceStatus = responceStatus;
	}

	@Override
	public String toString() {
		return "ResponceMessage [responceStatus=" + responceStatus + "]";
	}

	

	

}
