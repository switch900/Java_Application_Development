/**
 * Project: a01029917Comp2613Assignment2
 * 
 * File: Books2.java
 * Date:Jun 14, 2018
 * Time: 10:45:00 AM
 */

package a01029917.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Andrew Hewitson, A01029917
 *
 */
public class Database {

    public static final String DB_DRIVER_KEY = "db.driver";
    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USER_KEY = "db.user";
    public static final String DB_PASSWORD_KEY = "db.password";
    private static Database theInstance = new Database();

    private static Logger LOG = LogManager.getLogger();

    private static Connection connection;
    private static Properties properties;
    private static boolean dbTableDropRequested;

    public Database() {

    }

    public Database(Properties properties) throws FileNotFoundException, IOException {
        LOG.debug("Loading database properties from db.properties");
        Database.properties = properties;
    }

    /**
     * @return the theInstance
     */
    public static Database getTheInstance() {
        return theInstance;
    }

    public static Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        }

        try {
            connect();
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        return connection;
    }

    private static void connect() throws ClassNotFoundException, SQLException {
        String dbDriver = properties.getProperty(DB_DRIVER_KEY);
        LOG.debug(dbDriver);
        Class.forName(dbDriver);
        LOG.debug("Driver loaded");
        String dbUrl = properties.getProperty(DB_URL_KEY);
        LOG.debug("DB URL=" + dbUrl);
        String dbUser = properties.getProperty(DB_USER_KEY);
        LOG.debug("DB USER=" + dbUser);
        String dbPassword = properties.getProperty(DB_PASSWORD_KEY);
        LOG.debug("DB URL=" + dbPassword);
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        LOG.debug("Database connected");
    }

    public void shutdown() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    /**
     * Determine if the database table exists.
     * 
     * @param targetTableName
     *            the target table name
     * @return true is the table exists, false otherwise
     * @throws SQLException
     *             if SQL data invalid
     */
    public static boolean tableExists(String targetTableName) throws SQLException {
        DatabaseMetaData databaseMetaData = getConnection().getMetaData();
        ResultSet resultSet = null;
        String tableName = null;

        try {
            resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
            while (resultSet.next()) {
                tableName = resultSet.getString("TABLE_NAME");
                if (tableName.equalsIgnoreCase(targetTableName)) {
                    LOG.debug("Found the target table named: " + targetTableName);
                    return true;
                }
            }
        } finally {
            resultSet.close();
        }
        return false;
    }

    public static void requestDbTableDrop() {
        dbTableDropRequested = true;
    }

    public static boolean dbTableDropRequested() {
        return dbTableDropRequested;
    }

    public static boolean isMsSQLServer() {
        return Database.properties.get(DB_DRIVER_KEY).toString().contains("sqlserver");
    }
}
