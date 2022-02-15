package com.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.log.LoggerFactory;
import com.log.MyLogger;

/**
 * DBPropertyReader class to read the DBConfig.properties file
 * 
 */
@Component
public class DBPropertyReader {

	/**
	 * The Logger
	 */
	final static MyLogger logger = LoggerFactory
			.getLogger(DBPropertyReader.class);

	/**
	 * Filename to read
	 */
	static final String fileName = "DBConfig.properties";

	/**
	 * Reference of properties class
	 */
	public static Properties obj = null;

	/**
	 * Reads the property file in Key-Value pair and return the value.
	 * 
	 * @param key
	 * @return key read from the properties file
	 */
	public static String getProperty(String key) {
		String value = null;
		LoadProperties();
		value = obj.getProperty(key);
		return value;

	}

	/**
	 * Loads the property file to be read in to a system.
	 */
	public static void LoadProperties() {
		FileReader file = null;
		obj = new Properties();
		try {
			logger.info("File name is ::"+fileName);
			file = new FileReader(fileName);
			obj.load(file);
			if (file != null) {
				file.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
