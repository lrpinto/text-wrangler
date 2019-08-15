package org.luisa.miniwrangler.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbDataSource;

/**
 * Configuration.java
 *
 * Utility to class to load a configuration file.
 *
 * @author Luisa Pinto
 */
public class Configuration {

    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());
    public static final String MINI_WRANGLER = "mini_wrangler";
    private static Map<String, DataSource> datasourceMap;
    private static Properties properties;

    /**
     * Return the datasource with the given name
     *
     * @param name The name of the datasource
     * @return a datasource or null if datasource with given name does not exist
     */
    public static DataSource getDataSource(String name) {
        return datasourceMap.get(name);
    }

    /**
     * Return the configuration property with the given name
     *
     * @param name the name of the target configuration property
     * @return the target property represent as a String
     */
    public static String getProperty(String name) {
        final String prop = properties.getProperty(name);
        return prop == null ? null : prop.trim();
    }

    /**
     * Return configuration property represented as an Integer
     *
     * @param name The name of the target configuration property
     * @param dft  The default value for the target property
     * @return The target configuration property represented as an Integer
     */
    public static int getProperty(String name, int dft) {
        try {
            final String prop = properties.getProperty(name, "" + dft);
            return Integer.valueOf(prop.trim());
        } catch (final NumberFormatException nfe) {
            return dft;
        }
    }

    /**
     * Initialise the configuration
     *
     * @throws IOException  if an issue occurs while attempting to load the
     *                      configuration file
     * @throws SQLException if an issue occurs while setting up a datasource
     */
    public static void init() throws IOException, SQLException {
        InputStream propertiesStream = null;

        /*
         * Get the name of the configuration file - if not passed as program parameter
         * looks for a resources file name 'configuration.ini'
         */
        final String configfile = System.getProperty("configfile");
        if (configfile != null) {
            LOGGER.info("Loading configfile=" + configfile);
            propertiesStream = new FileInputStream(configfile);
        } else {
            LOGGER.info("Loading default configuration; configfile="
                    + Configuration.class.getResource("/configuration.ini").getPath());
            propertiesStream = Configuration.class.getResource("/configuration.ini").openStream();
        }

        /*
         * Load properties
         */
        loadProperties(propertiesStream);

        /*
         * Initialise datasources
         */
        initializeDataSources();
        LOGGER.info("Configuration initialized successfully");
    }

    /*
     * Auxiliary method to initialise datasources
     */
    private static void initializeDataSources() throws SQLException {
        datasourceMap = new HashMap<>();
        final String[] datasources = properties.getProperty("datasources").split(",");
        for (String datasource : datasources) {
            datasource = datasource.trim();
            final MariaDbDataSource mds = new MariaDbDataSource(properties.getProperty(datasource + ".jdbc.uri"));
            mds.setUserName(properties.getProperty(datasource + ".jdbc.username"));
            mds.setPassword(properties.getProperty(datasource + ".jdbc.password"));
            datasourceMap.put(datasource, mds);
        }
    }

    /*
     * Auxiliary method to load configuration properties
     */
    private static void loadProperties(InputStream propertiesStream) throws IOException {
        properties = new Properties();
        properties.load(propertiesStream);
    }

}
