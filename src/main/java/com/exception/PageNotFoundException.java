package com.exception;

import com.log.MyLogger;

/**
 * Whenever a page is not found, this exception is raised.
 * 
 */
public class PageNotFoundException extends Exception {

	/**
	 * Serialized version UID for the serialized class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for the Exception class. used to create an
	 * unparameterized Exception
	 */
	public PageNotFoundException() {
		super();
	}

	/**
	 * Creates an Exception with a customized message.
	 * 
	 * @param message
	 *            : customized message that stores the information regarding the
	 *            Exception
	 * @param logger
	 *            : customized logger to store in error state.
	 */
	public PageNotFoundException(String message, MyLogger logger) {

		super(message);
		logger.error(message);
	}

	/**
	 * Creates an Exception with a customized message which is encountered as
	 * the cause for the Exception
	 * 
	 * @param throwable
	 *            : cause of the Exception.
	 * @param message
	 *            :customized message that stores the information regarding the
	 *            Exception
	 * @param logger
	 *            : customized logger to store in error state.
	 */
	public PageNotFoundException(String message, Throwable t, MyLogger logger) {

		super(message, t);
		logger.error(t.getMessage(), message);
	}
}
