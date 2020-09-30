package com.learnspring.api.ws.ui.model.response;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
	RECORD_ALREADY_EXISTS("Record already exists"),
	INTERNET_SERVER_ERROR("Internel server error"),
	NO_RECORD_FOUND("Record with provided id is not found"),
	AUTHENTICATION_FAILED("Authentication Failed"),
	COULD_NOT_UPDATE_RECORD("Could not update record"),
	COULD_NOT_DELETE_ReCORD("Could not delete record"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");

	private String errorMessage;

	ErrorMessages(String errorMessage){
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
