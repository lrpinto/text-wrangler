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
 * Context.java
 *
 * A class that represents Context objects.
 */
public class Context {

	private static final Logger LOGGER = Logger.getLogger(Context.class.getName());
	public static final String MINI_WRANGLER = "mini_wrangler";
	private static Map<String, DataSource> datasourceMap;
	private static Properties properties;

	/**
	 * @param name
	 * @param def
	 * @return
	 */
	public static int getConfigAsInt(String name, int def) {
		try {
			if (properties.containsKey(name)) {
				return Integer.valueOf(properties.getProperty(name).trim());
			} else {
				return def;
			}
		} catch (final NumberFormatException nfe) {
			return def;
		}
	}

	/**
	 * @param name
	 * @return
	 */
	public static String getConfigAsString(String name) {
		final String tmp = properties.getProperty(name);
		return tmp == null ? null : tmp.trim();
	}

	/**
	 * @param type
	 * @return
	 */
	public static DataSource getDataSource(String type) {
		return datasourceMap.get(type);
	}

	/**
	 * Create a Context as specified.
	 *
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void init() throws IOException, SQLException {
		InputStream propertiesStream = null;
		final String configfile = System.getProperty("configfile");
		if (configfile != null) {
			LOGGER.info("Loading configfile=" + configfile);
			propertiesStream = new FileInputStream(configfile);
		} else {
			LOGGER.info("Loading default configuration; configfile="
					+ Context.class.getResource("/configuration.ini").getPath());
			propertiesStream = Context.class.getResource("/configuration.ini").openStream();
		}
		setProperties(propertiesStream);
		initializeDataSources();
		LOGGER.info("Context initialized successfully");
	}

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

	private static void setProperties(InputStream propertiesStream) throws IOException {
		properties = new Properties();
		properties.load(propertiesStream);
	}

}
