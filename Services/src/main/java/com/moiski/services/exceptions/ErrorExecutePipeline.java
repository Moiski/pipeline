package com.moiski.services.exceptions;

public class ErrorExecutePipeline extends Exception {

	private static final long serialVersionUID = 1L;

	public ErrorExecutePipeline() {
		super();
	}

	public ErrorExecutePipeline(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErrorExecutePipeline(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorExecutePipeline(String message) {
		super(message);
	}

	public ErrorExecutePipeline(Throwable cause) {
		super(cause);
	}

}
