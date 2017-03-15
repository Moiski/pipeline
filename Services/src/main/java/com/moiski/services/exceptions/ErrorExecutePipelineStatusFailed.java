package com.moiski.services.exceptions;

public class ErrorExecutePipelineStatusFailed extends ErrorExecutePipeline {

	private static final long serialVersionUID = 1L;

	public ErrorExecutePipelineStatusFailed() {
		super();
	}

	public ErrorExecutePipelineStatusFailed(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErrorExecutePipelineStatusFailed(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorExecutePipelineStatusFailed(String message) {
		super(message);
	}

	public ErrorExecutePipelineStatusFailed(Throwable cause) {
		super(cause);

	}
	
}
