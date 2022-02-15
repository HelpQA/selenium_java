package com.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.exception.InitializationException;

public class PropReader {
	
	 private Properties prop;
	public PropReader(String fileName){
		 prop = new Properties();
	        try {
	            prop.load(new FileReader(fileName));
	        } catch (FileNotFoundException e) {
	            throw new InitializationException("Properties file not found " + fileName);
	        } catch (IOException e) {
	            throw new InitializationException("Unable to load properties file: " + fileName);
	        }
	       
	    }
	
	public String getValue(String key){
		return prop.getProperty(key);
	}
	
    public void setValue(String key , String value){
    	prop.setProperty(key, value);
    }
    
    
   
}
