package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JTextPane;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.jersey.api.client.UniformInterfaceException;
import exception.WikipediaNoArcticleException;
import gui.FrameUserInfo;
import opendatarest.OpenDataRest;
import oracle.sql.OracleDBServiceCRUD;
/**
 * 
 * @author chris
 *
 */
public class City{
	private static final HashMap<String,City> cities = new HashMap<String,City>();
	private String name;
	private int[] termsVector = new int[10];
	private double[] geodesicVector = new double[2];
	private static final String[] term = new String[]{"sea","mountain","museum","cafe","restaurant","park","lake","train","metro","forest"};
	
	/**
	 * 
	 * @param city
	 * @param country
	 * @param appid
	 * @throws IOException
	 * @throws SQLException
	 */
	public City(String city,String country,String appid) throws IOException, SQLException {
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			City c = City.searchCitySys(city);
			if(c != null) {
				this.geodesicVector = c.geodesicVector;
				this.termsVector = c.termsVector;
				this.name = city;
				break;
			}
			try {
				this.setApi(city, country,appid,null,null,null);
				this.setName(city);
				OracleDBServiceCRUD.saveData(this);
				cities.put(this.getName(), this);
				break;
			}catch(UniformInterfaceException u) {
				System.out.println("Something went wrong with the input(City,Country)");
				System.out.println("Give again");
				System.out.print("City name:");
				city = stdin.readLine();
				System.out.print("Country name:");
				country = stdin.readLine();
				continue;
			}catch(WikipediaNoArcticleException w) {
				System.out.println(w.getMessage());
				System.out.print("Give a correct city name:");
				city = stdin.readLine();
				continue;
			}catch(IllegalArgumentException i) {
				System.out.println("Something went wrong with the input(City,Country)");
				System.out.println("Give again");
				System.out.print("City name:");
				city = stdin.readLine();
				System.out.print("Country name:");
				country = stdin.readLine();
				continue;
			}
			
		}
	}
	/**
	 *  for GUI
	 * @param city
	 * @param country
	 * @throws IOException
	 * @throws SQLException
	 * @throws UniformInterfaceException
	 * @throws WikipediaNoArcticleException
	 * @throws IllegalArgumentException
	 */
	public City(String city,String country,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws IOException, SQLException,UniformInterfaceException,WikipediaNoArcticleException,IllegalArgumentException {
		String appid = "Put your key";
		City c = City.searchCitySys(city);
		if(c != null) {
			this.geodesicVector = c.geodesicVector;
			this.termsVector = c.termsVector;
			this.name = city;
			
		}else {
			this.setApi(city, country,appid,txtButtonCity,txtButtonCountry,f);
			if(!f.error) {
				this.setName(city);
				OracleDBServiceCRUD.saveData(this);
				cities.put(this.getName(), this);	
			}
		}
	}
	
	/**
	 * Not for User
	 * @param name
	 * @param geodesicVector
	 * @param termsVector
	 */
	public City(String name, double[] geodesicVector, int[] termsVector) {
		this.name = name;
		this.geodesicVector = geodesicVector;
		this.termsVector = termsVector;
	}
	
	private void setApi(String city,String country,String appid,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws IOException, WikipediaNoArcticleException,UniformInterfaceException{
		final City c = this;
		Thread openWeatherMap = new Thread() {
			public void run() {
				try {
					OpenDataRest.RetrieveOpenWeatherMap(city, country, appid, c,txtButtonCity,txtButtonCountry,f);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (UniformInterfaceException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		Thread wikipedia = new Thread() {
			public void run() {
				try {
					OpenDataRest.RetrieveWikipedia(c,txtButtonCity,txtButtonCity,f);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WikipediaNoArcticleException e) {
					e.printStackTrace();
				}
			}
		};
		openWeatherMap.start();
		wikipedia.start();
		try {
			wikipedia.join();
			openWeatherMap.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Recovers the cities from the Oracle SQL DataBase
	 * @throws SQLException
	 */
	public static void recoverCities() throws SQLException {
		ArrayList<City> sqlCities = OracleDBServiceCRUD.readData();
		Iterator<City> i = sqlCities.iterator();
		while(i.hasNext()) {
			City c = i.next();
			City.cities.put(c.getName(), c);
		}
	}
	
	@Override
	public int hashCode() {
	    int prime = 31;
	    return prime + (name == null ? 0 : name.hashCode());    
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if (o == null || getClass() != o.getClass()) return false;
		City c = (City) o;
		return this.getName().equals(c.getName());
	}
	/**
	 * 
	 * @param city
	 * @return
	 */
	public static City searchCitySys(String city) {return cities.get(city);}
	
	public int[] gettermsVector() {return Arrays.copyOf(termsVector, termsVector.length);}//return a copy of the array
	
	public void settermsVector(int[] termsVector) {this.termsVector = termsVector;}//TODO
	
	public double[] getgeodesicVector() {return Arrays.copyOf(geodesicVector, geodesicVector.length);}//return a copy of the array
	
	public void setgeodesicVector(double[] geodesicVector) {this.geodesicVector = geodesicVector;}//TODO
	
	public String getName() {return name;}
	
	public void setName(String name) {this.name = name;}//TODO
	
	public static String[] getTerm() {return Arrays.copyOf(term, term.length);}//return a copy of the array
	
	public static int getNumberOfCities() {return cities.size();}
	/**
	 * 
	 * @return An ArrayList of the stored cities
	 */
	public static ArrayList<City> getListOfCities(){return new ArrayList<City>(cities.values());}
	/**
	 * 
	 * @return An Array of the stored cities
	 */
	public static City[] getArrayOfCities() {
		ArrayList<City> list = new ArrayList<City>(cities.values());
		City[] c = new City[list.size()];
		return list.toArray(c);
	}
	@Override
	public String toString() {
		return this.getName();
	}

}
