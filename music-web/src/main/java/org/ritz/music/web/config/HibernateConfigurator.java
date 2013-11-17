/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web.config;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hans
 */
public class HibernateConfigurator implements Serializable {

    private static final String EMBEDDED_DATABASE_BASE_URL = "jdbc:derby:../work/derby/tracks";
    
    private static final Logger LOG = LoggerFactory.getLogger(HibernateConfigurator.class);
    private SessionFactory sessionFactory;

    public HibernateConfigurator() {
        LOG.info("Initialising Hibernate");
        try {
            initDataSource();
        } catch (HibernateException e) {
            LOG.warn("unable to initialize Hibernate SessionFactory", e);
            LOG.warn("initializing fallback embedded database");
            LOG.warn("please do not use this configuration in a production environment");
            LOG.warn("starting Apache Derby embedded database");
            initEmbeddedDataSource();
        }
    }

    private void initDataSource() {
        Configuration config = new Configuration();
        config.configure();
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(config.getProperties());
        sessionFactory = config.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        LOG.info("datasource successfully initialised");
    }

    private void initEmbeddedDataSource(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Configuration config = new Configuration();
            config.configure("hibernate-embedded.cfg.xml");
            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(config.getProperties());
            sessionFactory = config.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
            LOG.warn("fallback datasource successfully initialised");
        } catch (HibernateException e) {
            LOG.error("unable to start fallback database", e);
            LOG.error("unable to create session factory: application will not start properly");
        }catch(ClassNotFoundException e){
            LOG.error("Derby embedded database driver not found on classpath", e);
            LOG.error("unable to create session factory: application will not start properly");
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
