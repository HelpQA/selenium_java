package com.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import com.exception.ValidationException;
import com.log.LoggerFactory;
import com.log.MyLogger;

/**
 * The Class QueryHelper.
 */
public class QueryHelper {

	/**
	 * The Logger
	 */
	final static MyLogger logger = LoggerFactory.getLogger(QueryHelper.class);

	/**
	 * Interacts with the DB to execute a query and returns a count.
	 * @param message : query that we want to execute on the connected db.
	 * @return: Numeric result returned after query execution.
	 */
	public static Integer getCount(String query) throws ValidationException {
		Integer count = null;
		try {
			DataSource dataSource = ConnectionConfiguration.getDataSource();
			QueryRunner qr = new QueryRunner(dataSource);
			logger.info("Query-->" + query);
			Number result = qr.query(query, new ScalarHandler<Long>());
			count = result.intValue();
			logger.info(count);

		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
			throw new ValidationException(e.getMessage(), e, logger);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
			throw new ValidationException(e.getMessage(), e, logger);
		} 
		return count;
	}

	/**
	 * Interacts with the DB to execute a query and returns a String.
	 * @param message : query that we want to execute on the connected db.
	 * @return: String result returned after query execution.
	 */
	public static String getData(String query) throws ValidationException {
		String data = null;
		try {
			DataSource dataSource = ConnectionConfiguration.getDataSource();
			QueryRunner qr = new QueryRunner(dataSource);
			logger.info("Query-->" + query);
			String result = qr.query(query, new ScalarHandler<String>());
			data = result;
			logger.info(data);

		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
			throw new ValidationException(e.getMessage(), e, logger);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
			throw new ValidationException(e.getMessage(), e, logger);
		} 
		return data;
	}

	/*public static Collection<User> getObject(String query) {		
		DataSource datasource;
		Collection<User> testData = null;
		try {
			datasource = ConnectionConfiguration.getDataSource();
			QueryRunner qr = new QueryRunner(datasource);
			ResultSetHandler<List<User>> td = new BeanListHandler<User>(User.class);
			testData = qr.query(query, td); 
			
		} catch (IOException | ValidationException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testData;	
	}*/
}
