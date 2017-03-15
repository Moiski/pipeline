package com.moiski.dao.enums;

public enum StatusType {
	
	PENDING,
	IN_PROGRESS,
	SKIPPED,
	FAILED,
	COMPLETED;
	
	public static StatusType getRandomStatus() {
		int from = 1;
		int to = values().length;
		int randomIndex = (int) (Math.random() * (to - from) + from);
		return values()[randomIndex];
	}

}
