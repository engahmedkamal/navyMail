package navymail.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import navymail.util.log.Loggable;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public final class ConnectionHandler extends Loggable{

	public static final String NAVYMAIL 	= "navyMail";

	private Map<String, BoneCP> connectionPools = new ConcurrentHashMap<String, BoneCP>();
	
	public int getConnectionCount() {
		int count = 0;
		for (BoneCP pool : connectionPools.values())
			count = pool.getTotalCreatedConnections();
		return count;
	}

	public int getFreeConnectionCount() {
		int count = 0;
		for (BoneCP pool : connectionPools.values())
			count = pool.getTotalFree();
		return count;
	}

	public int getLeasedConnectionCount() {
		int count = 0;
		for (BoneCP pool : connectionPools.values())
			count = pool.getTotalLeased();
		return count;

	}
	
	/**
	 * Initialize a DB connection, and create the DB if not exists. It doesn't
	 * touch the DB if it is already there.
	 * 
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	private void initializeConnection(String poolName)
			throws ClassNotFoundException, FileNotFoundException, IOException,SQLException {
		// Load Configurations
		BoneCPConfig config = new BoneCPConfig();
		PropertiesLoader conload = PropertiesLoader.getInstance();
		config.setJdbcUrl(conload.getValue(poolName,"JDBC_URL"));
		config.setUsername(conload.getValue(poolName,"USER"));
		config.setPassword(conload.getValue(poolName,"PASS"));
		config.setMinConnectionsPerPartition(Integer.parseInt(conload.getValue(poolName,"MIN_CONN")));
		config.setMaxConnectionsPerPartition(Integer.parseInt(conload.getValue(poolName,"MAX_CONN")));
		config.setPartitionCount(Integer.parseInt(conload.getValue(poolName,"PARTITIONS")));
		Class.forName(conload.getValue(poolName,"DRIVER"));
		config.setLazyInit(false);
		config.setConnectionTimeout(60, TimeUnit.SECONDS);
		config.setAcquireIncrement(1);
		String dbName = conload.getValue(poolName,"DATABASE_NAME");
		dbPoolMapping.put(poolName, dbName);
		String dbURL = conload.getValue(poolName,"JDBC_URL")
				+ dbName
				+ "?useUnicode=true&characterEncoding=UTF-8";
		System.out.println(dbURL);
		System.out.println(config.getUsername());
		System.out.println(config.getPassword());
		config.setJdbcUrl(dbURL);
		try {
			// Establish connection to DB
			connectionPools.put(poolName, new BoneCP(config));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error Creating DB", e);
		}
	}
	
	public void initDB(String poolName, String queryFile) throws SQLException {
		// Execute queries
		if (queryFile != null) {
			Connection exconn = null;
			try {
				exconn = getDBConnection(poolName);
				Statement st = exconn.createStatement();
				String[] queries = null;
				@SuppressWarnings("resource")
				Scanner reader = new Scanner(getClass().getResourceAsStream(queryFile));
				StringBuilder allqueries = new StringBuilder();
				while (reader.hasNext()) {
					allqueries.append(reader.nextLine());
				}
				queries = allqueries.toString().split(";");
				if (queries != null && queries.length > 1) {
					st.executeUpdate(queries[1] + ";"); // here we start by
														// index 1 to avoid
														// dropping the Database
				}
				Statement st2 = exconn.createStatement();
				for (int i = 2; i < queries.length; i++)
					st2.addBatch(queries[i] + ";"); // executes the remaining
													// statements (create
													// tables, indexes, ....)
				st2.executeBatch();
			} catch (Exception e) {
				logger.error("Error Creating DB", e);
				return;
			} finally {
				if (exconn != null)
					exconn.close();
			}
		}
	}

	private Map<String, String> dbPoolMapping = new HashMap<String, String>();
	private static ConnectionHandler instance;
	private ConnectionHandler() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		initializeConnection(NAVYMAIL);
	}
	public String getDBNameFromPoolName(String poolName){
		return dbPoolMapping.get(poolName);
	}
	public static ConnectionHandler getInstance() {
		if(instance==null)
			synchronized (ConnectionHandler.class) {
				if(instance==null){
					try {
						instance = new ConnectionHandler();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		return instance;
	}

	public void closeDBConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
			// logger.info("Database closed.");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public Connection getDBConnection(String poolName) {
		Connection conn = null;
		try {
			BoneCP connectionPool = connectionPools.get(poolName);
			conn = connectionPool.getConnection();
		} catch (SQLException e) {
			logger.error("Connection Error:", e);
		} catch (Exception e) {
			logger.error("Connection Error:", e);
		}
		return conn;
	}
}
