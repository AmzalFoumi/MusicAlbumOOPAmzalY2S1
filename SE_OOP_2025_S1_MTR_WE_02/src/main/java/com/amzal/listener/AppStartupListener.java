package com.amzal.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.amzal.util.ConfigManager;

/**
 * Application Lifecycle Listener implementation class AppStartupListener
 *
 */
@WebListener
public class AppStartupListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public AppStartupListener() {
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Web app stopped");
    }
	
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	System.out.println("Web app started");
    	
    	// Load config.properties from WEB-INF
        ConfigManager.loadProperties(sce.getServletContext());
        System.out.println("Database Config loaded on app startup\n");      
    }

    
}
