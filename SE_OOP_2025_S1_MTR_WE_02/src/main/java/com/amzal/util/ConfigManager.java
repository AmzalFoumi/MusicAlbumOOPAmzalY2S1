package com.amzal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static boolean isInitialized = false;
    
    // Private constructor to prevent instantiation
    private ConfigManager() {}
     
    //Gets called once on startup using my listener class 
    public static void loadProperties(ServletContext context) {
        if (!isInitialized) {
            try (InputStream input = context.getResourceAsStream("/WEB-INF/config.properties")) {
                if (input == null) {
                    System.err.println("Unable to find config.properties in WEB-INF");
                    return;
                }
                properties.load(input);
                isInitialized = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Get property value
    public static String getProperty(String key) {
        if (!isInitialized) {
            throw new IllegalStateException("ConfigManager not initialized. Call loadProperties() first.");
        }
        return properties.getProperty(key);
    }
}
    //    // Load properties from file
//    public static void loadProperties() {
//        if (!isInitialized) {
//            try (InputStream input = ConfigManager.class.getClassLoader()
//                    .getResourceAsStream("com/amzal/util/config.properties")) {
//                if (input == null) {
//                    System.err.println("Unable to find config.properties");
//                    return;
//                }
//                properties.load(input);
//                isInitialized = true;
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }

