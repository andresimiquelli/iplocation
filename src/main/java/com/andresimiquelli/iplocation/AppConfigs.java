package com.andresimiquelli.iplocation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppConfigs {

	public static final String INPUT_TOPIC = "INPUT_TOPIC";
    public static final String OUTPUT_TOPIC = "OUTPUT_TOPIC";
    public static final String KAFKA_APP_ID = "KAFKA_APP_ID";
    public static final String KAFKA_SERVER_HOST = "KAFKA_SERVER_HOST";
    public static final String KAFKA_SERVER_PORT = "KAFKA_SERVER_PORT";
    public static final String REDIS_SERVER_HOST = "REDIS_SERVER_HOST";
    public static final String REDIS_SERVER_PORT = "REDIS_SERVER_PORT";
    public static final String REDIS_SERVER_DB = "REDIS_SERVER_DB";
    public static final String IPSTACK_ACCESS_KEY = "IPSTACK_ACCESS_KEY";
    public static final String IPSTACK_SERVICE_URL = "IPSTACK_SERVICE_URL";
    
    private static Properties properties;
    
    public static Properties get() {
    	if(properties == null)
    		loadProperties("default");
    	
    	return properties;
    }
    
    public static Properties get(String profile) {
    	if(properties == null)
    		loadProperties(profile);
    	
    	return properties;
    }
    
    private static void loadProperties(String profile) {
    	try {
    		
            InputStream file = new FileInputStream(new File(profile+".properties"));
            properties = new Properties();
            properties.load(file);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppConfigs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AppConfigs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
