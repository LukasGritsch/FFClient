package ffclient.db.base;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class TDBConnection {

	public static Connection fConnection = null;
	private Properties fDBPeoperties = getConnectionProperties("/config/db.conf");

	public TDBConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbURL = String.format("jdbc:sqlite:%s",fDBPeoperties.get("path"));
			
			fConnection = DriverManager.getConnection(dbURL);
			if (fConnection != null) {
				System.out.println("Connected to the database");
				DatabaseMetaData dm = (DatabaseMetaData) fConnection.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());

				// Dies ist nur für die Programmierung, so wird die Datenbankstruktur erstellt
				initTables();
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void initTables() throws SQLException {
		Statement smt = fConnection.createStatement();
		String q = "CREATE TABLE IF NOT EXISTS FF_USER(USER_NR INTEGER PRIMARY KEY,USER_NAME VARCHAR(45) NOT NULL,USER_PASSWD INTEGER NOT NULL"
		+",USER_PERM INTEGER)";
		smt.executeUpdate(q);
		smt.close();
		
		q = "CREATE TABLE IF NOT EXISTS FF_CATEGORY(CAT_NR INTEGER PRIMARY KEY, CAT_NAME VARCHAR(45) NOT NULL,CAT_TYPE INTEGER,CAT_BILD BLOB,CAT_TXT VARCHAR(200))";
		smt = fConnection.createStatement();
		smt.executeUpdate(q);
		smt.close();
		
		q = "CREATE TABLE IF NOT EXISTS FF_ENTRY(ENT_NR INTEGER PRIMARY KEY, ENT_PARENT INT NOT NULL,ENT_NAME VARCHAR(45) NOT NULL,"
		+"ENT_DATUM DATE NOT NULL,ENT_TXT VARCHAR(200),ENT_BILD BLOB,ENT_BILD_ONE BLOB,ENT_BILD_TWO BLOB,"
		+"FOREIGN KEY (ENT_PARENT) REFERENCES FF_CATEGORY(CAT_NR))";
		smt = fConnection.createStatement();
		smt.executeUpdate(q);
		smt.close();
		
		q = "CREATE TABLE IF NOT EXISTS FF_ARCHIV(AR_NR INTEGER PRIMARY KEY, AR_NAME VARCHAR(45) NOT NULL)";
		smt = fConnection.createStatement();
		smt.executeUpdate(q);
		smt.close();
		
		q = "CREATE TABLE IF NOT EXISTS FF_ARCHIV_ENTRY(ARENT_NR INTEGER PRIMARY KEY,ARENT_PARENT INT NOT NULL,ARENT_NAME VARCHAR(45) NOT NULL,"
		+"FOREIGN KEY (ARENT_PARENT) REFERENCES FF_ARCHIV(AR_NR))";
		smt = fConnection.createStatement();
		smt.executeUpdate(q);
		smt.close();
		
		q = "CREATE TABLE IF NOT EXISTS FF_ARCHIV_BILD(ARB_NR INTEGER PRIMARY KEY,ARB_PARENT INT NOT NULL,ARB_BILD BLOB NOT NULL,"
		+ "FOREIGN KEY (ARB_PARENT) REFERENCES FF_AECHIV_ENTRY(ARENT_NR))";
		smt = fConnection.createStatement();
		smt.executeUpdate(q);
		smt.close();
	}

	public void close() {
		if (fConnection != null) {
			try {
				fConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Properties getConnectionProperties(String aFilename) {
		try {
			InputStream hIn = TDBConnection.class.getClassLoader().getResourceAsStream(aFilename);
			Properties hProp = new Properties();
			
			hProp.load(hIn);
			return hProp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
