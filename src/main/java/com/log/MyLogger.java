package com.log;


/** MyLogger a wrapper of org.apache.log4j.Logger for logging info, errors and other logging level log.
 * statements.
 * @author cbhaka
 */
public interface MyLogger {

	/**
	 * Log a message at the ERROR level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	void error(final Object... msgParams);

	/**
	 * Log a message at the DEBUG level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	void debug(final Object... msgParams);

	/**
	 * Log a message at the TRACE level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	void trace(final Object... msgParams);

	/**
	 * Log a message at the INFO level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	void info(final Object... msgParams);

	/**
	 * Log a message at the FATAL level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	void fatal(final Object... msgParams);

	/**
	 * Log an exception at the ERROR level with an accompanying message.
	 * 
	 * @param throwable the exception (throwable) to log
	 * @param msgParams the message accompanying the exception.
	 */
	void error(final Throwable throwable, final Object... msgParams);

	/**
	 * Log an exception at the DEBUG level with an accompanying message.
	 * 
	 * @param throwable the exception (throwable) to log
	 * @param msgParams the message accompanying the exception.
	 */
	void debug(final Throwable throwable, final Object... msgParams);

	/**
	 * Log an exception at the TRACE level with an accompanying message.
	 * 
	 * @param throwable the exception (throwable) to log
	 * @param msgParams the message accompanying the exception.
	 */
	void trace(final Throwable throwable, final Object... msgParams);

	/**
	 * Log an exception at the INFO level with an accompanying message.
	 * 
	 * @param throwable the exception (throwable) to log
	 * @param msgParams the message accompanying the exception.
	 */
	void info(final Throwable throwable, final Object... msgParams);

	/**
	 * Log an exception at the FATAL level with an accompanying message.
	 * 
	 * @param throwable the exception (throwable) to log
	 * @param msgParams the message accompanying the exception.
	 */
	void fatal(final Throwable throwable, final Object... msgParams);

}
