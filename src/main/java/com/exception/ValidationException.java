package com.exception;

import com.log.MyLogger;

/**
 * Whenever a parameter in not validated, this exception is raised.
 * 
 */
public class ValidationException extends Exception {

	/**
	 * Serialized version UID for the serialized class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for the Exception class. used to create an
	 * unparameterized Exception
	 */
	public ValidationException() {
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
	public ValidationException(String message, MyLogger logger) {

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
	public ValidationException(String message, Throwable t, MyLogger logger) {
		super(message, t);
		logger.error(t.getMessage(), message);
	}
}
