/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web.config;

import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class DataSourceConfigurator implements ServletContextListener{

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfigurator.class);

    private static final String EMBEDDED_DATABASE_SHUTDOWN_URL = "jdbc:derby:;shutdown=true";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {}
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            DriverManager.getConnection(EMBEDDED_DATABASE_SHUTDOWN_URL);
        }catch(SQLException e){
            LOG.info("embedded datasource has been shut down");
        }catch(ClassNotFoundException e){
            LOG.error("unable to load embedded derby driver: make sure the Derby JDBC connector is present on the application server classpath", e);
        }
    }
    
    
    
}
