package com.db;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.stereotype.Component;

import com.constants.DBPropertyConstant;
import com.exception.ValidationException;
import com.log.LoggerFactory;
import com.log.MyLogger;

/**
 * The Class ConnectionConfiguration.
 */
@Component
public class ConnectionConfiguration {
	
	/**
	 * The Logger logger
	 */
	private final static MyLogger logger = LoggerFactory.getLogger(ConnectionConfiguration.class);

	/**
	 * The dataSource
	 */
	private static DataSource dataSource;

	/**
	 * private constructor to restrict initialization.
	 */
	private ConnectionConfiguration() {
	}

	/**
	 * gets the dataSource.
	 * 
	 * @return DataSource
	 */
	public static DataSource getDataSource() throws IOException,
			ValidationException {
		logger.info("Get the datasource");
		if (null == dataSource) {
			dataSource = createDataSource();
		}
		logger.info("returning the datasource");
		return dataSource;
	}

	/**
	 * Creates a dataSource, sets up all the connection configuration required
	 * to create a datasource.
	 * 
	 * @return DataSource
	 */
	private static DataSource createDataSource() {

		BasicDataSource dataSource = new BasicDataSource();
		logger.info("Set the DB URL:: " +DBPropertyReader
				.getProperty(DBPropertyConstant.DB_URL));
		dataSource.setUrl(DBPropertyReader
				.getProperty(DBPropertyConstant.DB_URL));
		logger.info("Set the username:: " +DBPropertyReader
				.getProperty(DBPropertyConstant.DB_USERNAME));
		dataSource.setUsername(DBPropertyReader
				.getProperty(DBPropertyConstant.DB_USERNAME));
		logger.info("Set the password:: "+DBPropertyReader
				.getProperty(DBPropertyConstant.DB_PASSWORD));
		dataSource.setPassword(DBPropertyReader
				.getProperty(DBPropertyConstant.DB_PASSWORD));
		logger.info("Set the driver class name:: "+DBPropertyReader
				.getProperty(DBPropertyConstant.DB_DRIVER_CLASS_NAME));
		dataSource.setDriverClassName(DBPropertyReader
				.getProperty(DBPropertyConstant.DB_DRIVER_CLASS_NAME));

		return dataSource;

	}
}
