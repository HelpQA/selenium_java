package com.log;

import org.apache.log4j.Logger;

/**
 * This class is parallel to org.apache.log4j.Logger. The LoggerFactory is a utility class producing Loggers for various
 * logging APIs.
 */
public class LoggerFactory {
	


/**
 * Private constructor to prevent instantiation
 */
private LoggerFactory() {
		
	}
	
	/**
	 *Returns an instance of MyLogger from factory.
	 * 
	 * @param classType - Class name for which logger is required.
	 * @return instance of MyLogger
	 */
	public static MyLogger getLogger(final Class<?> className) {
		
		final Logger logger = Logger.getLogger(className);
		return new LoggerImpl(logger);
	}
}
