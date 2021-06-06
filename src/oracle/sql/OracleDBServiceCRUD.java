package oracle.sql;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exercise.City;

public class OracleDBServiceCRUD {
	static Connection db_con_obj = null; // A connection (session) with a specific database. SQL statements are executed
											// and results are returned within the context
	// of a connection. A Connection object's database is able to provide
	// information describing its tables, its supported SQL grammar, its stored
	// procedures,
	// the capabilities of this connection, and so on. This information is obtained
	// with the getMetaData method.
	static PreparedStatement db_prep_obj = null;// An object that represents a precompiled SQL statement.
	// A SQL statement is precompiled and stored in a PreparedStatement object. This
	// object can then be used to efficiently execute this statement multiple times.

	public static void makeJDBCConnection() {

		try {// We check that the DB Driver is available in our project.
			Class.forName("oracle.jdbc.driver.OracleDriver"); // This code line is to check that JDBC driver is
																// available. Or else it will throw an exception. Check
																// it with 2.
			//System.out.println("Congrats - Seems your oracle JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			System.out.println(
					"Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}

		try {
			// DriverManager: The basic service for managing a set of JDBC drivers. //We
			// connect to a DBMS.
			db_con_obj = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl", "IT219163",
					"Petra123");// Returns a connection to the URL.
			// Attempts to establish a connection to the given database URL. The
			// DriverManager attempts to select an appropriate driver from the set of
			// registered JDBC drivers.
			if (db_con_obj != null) {
				//System.out.println("Connection Successful! Enjoy. Now it's time to CRUD data. ");

			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("Oracle Connection Failed!");
			e.printStackTrace();
			return;
		}

	}
	
	public static City findCityFromDataBase(String name) throws SQLException {
		db_prep_obj = db_con_obj.prepareStatement("SELECT * FROM cities WHERE cityname = '"+name+"'");
		ResultSet rs = db_prep_obj.executeQuery();
		rs.next();
		if(rs.getNString(1) == name) return new City(name,new double[]{Double.parseDouble(rs.getNString(2)),Double.parseDouble(rs.getNString(2))},new int[]{Integer.parseInt(rs.getNString(4)),Integer.parseInt(rs.getNString(5)),Integer.parseInt(rs.getNString(6)),Integer.parseInt(rs.getNString(7)),Integer.parseInt(rs.getNString(8)),Integer.parseInt(rs.getNString(9)),Integer.parseInt(rs.getNString(10)),Integer.parseInt(rs.getNString(11)),Integer.parseInt(rs.getNString(12)),Integer.parseInt(rs.getNString(13))});
		return null;
	}
	
	public static ArrayList<City> readData() throws SQLException {
		db_prep_obj = db_con_obj.prepareStatement("SELECT * FROM cities");
		ResultSet rs = db_prep_obj.executeQuery();
		ArrayList<City> cities = new ArrayList<City>();
		while (rs.next()) {
			cities.add(new City(rs.getString("cityName"), new double[] { rs.getDouble("lat"), rs.getFloat("lon") },new int[] { rs.getInt("sea"), rs.getInt("mountain"), rs.getInt("museum"), rs.getInt("cafe"),rs.getInt("restaurant"), rs.getInt("park"), rs.getInt("lake"), rs.getInt("train"),rs.getInt("train"), rs.getInt("metro"), rs.getInt("forest") }));
		}
		return cities;
	}

	public static void saveData(City city) throws SQLException {
		double[] geodesic = city.getgeodesicVector();
		int[] term = city.gettermsVector();
		db_prep_obj = db_con_obj.prepareStatement(String.format("INSERT INTO cities VALUES ('%s',%f,%f,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d)",city.getName(),geodesic[0],geodesic[1],term[0],term[1],term[2],term[3],term[4],term[5],term[6],term[7],term[8],term[9]));
		db_prep_obj.executeQuery();
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		//makeJDBCConnection();
		
	}
}