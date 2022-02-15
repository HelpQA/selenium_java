package com.log;

import org.apache.log4j.Logger;

import com.util.StringUtil;

/**
 * The LoggerImpl is a utility class producing Loggers for various logging APIs
 * for various logging levels.
 * 
 * @see org.slf4j.LoggerFactory
 */
public class LoggerImpl implements MyLogger {

	/**
	 * Logger logger
	 */
	private Logger logger;

	/**
	 * An instance of LoggerImpl
	 * 
	 * @param logger
	 */
	public LoggerImpl(final Logger logger) {
		this.logger = logger;
	}

	/**
	 * Log a message at the ERROR level.
	 * 
	 * @param msgParams
	 *            the message string to be appended before logging.
	 */
	public void error(Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.error(getCallingFileNameAndLineNumber()+" "+msgStr);

	}

	/**
	 * Log a message at the DEBUG level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	public void debug(Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.debug(getCallingFileNameAndLineNumber()+" "+msgStr);

	}

	/**
	 * Log a message at the TRACE level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	public void trace(Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.trace(getCallingFileNameAndLineNumber()+" "+msgStr);

	}

	/**
	 * Log a message at the INFO level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	public void info(Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.info(getCallingFileNameAndLineNumber()+" " +msgStr);

	}

	/**
	 * Log a message at the FATAL level.
	 * 
	 * @param msgParams the message string to be appended before logging.
	 */
	public void fatal(Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.fatal(getCallingFileNameAndLineNumber()+" "+msgStr);

	}

	/**
	 * Log an exception (throwable) at the ERROR level with an accompanying message.
	 * 
	 * @param msgParams the message accompanying the exception.
	 * @param throwable the exception (throwable) to log
	 */
	public void error(Throwable throwable, Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.error(msgStr, throwable);

	}

	/**
	 * Log an exception (throwable) at the DEBUG level with an accompanying message.
	 * 
	 * @param msgParams the message accompanying the exception.
	 * @param throwable the exception (throwable) to log
	 */
	public void debug(Throwable throwable, Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.debug(getCallingFileNameAndLineNumber()+" "+msgStr, throwable);

	}

	/**
	 * Log an exception (throwable) at the TRACE level with an accompanying message.
	 * 
	 * @param msgParams the message accompanying the exception.
	 * @param throwable the exception (throwable) to log
	 */
	public void trace(Throwable throwable, Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.trace(getCallingFileNameAndLineNumber()+" "+msgStr, throwable);

	}

	/**
	 * Log an exception (throwable) at the INFO level with an accompanying message.
	 * 
	 * @param msgParams the message accompanying the exception.
	 * @param throwable the exception (throwable) to log
	 */
	public void info(Throwable throwable, Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.info(msgStr, throwable);

	}

	/**
	 * Log an exception (throwable) at the FATAL level with an accompanying message.
	 * 
	 * @param msgParams the message accompanying the exception.
	 * @param throwable Throwable} the exception (throwable) to log
	 */
	public void fatal(Throwable throwable, Object... msgParams) {
		final String msgStr = StringUtil.concatenate(msgParams);
		logger.fatal(msgStr, throwable);
	}

	
	 private String getCallingFileNameAndLineNumber() {
	        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
	        for (StackTraceElement ste : stes) {
	            String thisClassName = this.getClass().getName();
	            String stackClassName = ste.getClassName();
	            String threadClass="java.lang.Thread";
	            if (!stackClassName.equals(thisClassName) && !threadClass.equals(stackClassName)) {
	                return "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
	            }
	        }
	        return null;
	    }
}
