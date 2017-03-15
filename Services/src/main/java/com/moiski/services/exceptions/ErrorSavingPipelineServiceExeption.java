package com.moiski.services.exceptions;

public class ErrorSavingPipelineServiceExeption extends Exception {

	private static final long serialVersionUID = 1L;

	public ErrorSavingPipelineServiceExeption() {
		super();
	}

	public ErrorSavingPipelineServiceExeption(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErrorSavingPipelineServiceExeption(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorSavingPipelineServiceExeption(String message) {
		super(message);
	}

	public ErrorSavingPipelineServiceExeption(Throwable cause) {
		super(cause);
	}
	
	
	

}
