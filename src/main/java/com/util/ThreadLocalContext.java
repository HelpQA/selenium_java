package com.util;

public class ThreadLocalContext {

	private static final ThreadLocal<String> context = new ThreadLocal<String>();

	public static void putTestName(String testName) {
		context.set(testName);
	}

	public static String getTestName() {
		return context.get();
	}
}
