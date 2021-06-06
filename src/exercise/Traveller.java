package exercise;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTextPane;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.UniformInterfaceException;

import exception.WikipediaNoArcticleException;
import gui.FrameUserInfo;

/**
 * 
 * @author chris
 *
 */
public abstract class Traveller implements Comparable<Traveller>{
	private static ArrayList<Traveller> listTr = new ArrayList<Traveller>();
	private long timestamp;
	private String name,city,visit;//city is where he lives
	private int age;
	private int[] rate = new int[10];
	private double[] latLon = new double[2];
	private double p;
	public static final double maxdist=15327;
	
	/**
	 * default
	 */
	public Traveller() {}
	/**
	 * 
	 * @param city
	 * @param country
	 * @param appid
	 * @param name
	 * @param rate
	 * @param age
	 * @throws IOException
	 * @throws SQLException
	 * @throws IllegalTravellerRate 
	 * @throws IllegalTravellerAge 
	 */
	public Traveller(String city,String country,String appid,String name,int[] rate,int age) throws IOException, SQLException, IllegalTravellerRate, IllegalTravellerAge{
		City c = new City(city, country, appid);
		this.setApi(c);
		this.setRate(rate);
		this.setCity(c.getName());
		this.setName(name);
		this.setAge(age);
		this.timestamp = -1;
		this.visit = null;
	}
	/**
	 * 
	 * @param timestamp
	 * @param name
	 * @param city
	 * @param visit
	 * @param age
	 * @param rate
	 * @param latLon
	 * @param p
	 */
	public Traveller(long timestamp, String name, String city, String visit, int age, int[] rate,double[] latLon, double p) {
		this.timestamp = timestamp;
		this.name = name;
		this.city = city;
		this.visit = visit;
		this.age = age;
		this.rate = rate;
		this.latLon = latLon;
		this.p = p;
	}
	/**
	 * GUI
	 * @param city2
	 * @param country
	 * @param name2
	 * @param rate2
	 * @param age2
	 * @throws WikipediaNoArcticleException 
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws UniformInterfaceException 
	 * @throws IllegalTravellerAge 
	 * @throws IllegalTravellerRate 
	 */
	public Traveller(String city, String country, String name, int[] rate, int age,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws UniformInterfaceException, IOException, SQLException, WikipediaNoArcticleException, IllegalTravellerAge, IllegalTravellerRate {
		City c = new City(city, country,txtButtonCity,txtButtonCountry,f);
		this.setApi(c);
		this.setRate(rate);
		this.setCity(c.getName());
		this.setName(name);
		this.setAge(age);
		this.timestamp = -1;
		this.visit = null;
	}
	private void setApi(City c) {this.setLatLon(c.getgeodesicVector());}
	/**
	 * 
	 * @param city
	 * @return
	 */
	public final double calculateSimilarity(City city) {
		if(city == null) return -1;
		return this.getP()*this.similarityTermsVector(city)+(1-this.getP())*this.similarityGeodesicVector(city);
	}
	/**
	 * Calculates the similarity to this Traveler to the given city
	 * @param city
	 * @return String %
	 */
	public final String calculateSimilarityToString(City city) {
		if(city == null) throw new NullPointerException("City points to null");
		return (int)Math.ceil((this.calculateSimilarity(city))*100)+"%";
	}
	
	public abstract double similarityTermsVector(City city);
	
	private final double similarityGeodesicVector(City city) {
		double[] travGeodesic = this.getLatLon();
		double[] cityGeodesic = city.getgeodesicVector();
		double distance = DistanceCalculator.distance(travGeodesic[0], travGeodesic[1], cityGeodesic[0], cityGeodesic[1], "K");
		return (Math.log(2/(2-distance/maxdist)) / Math.log(2));//den exei etimh me8odo gia to log2
	}
	/**
	 * Returns the maximum similarity city from the array
	 * @param array
	 * @return City
	 */
	public final City compareCities(City[] array) {
		double max=-1,similarity;
		City maxCity=null;
		if(array == null) return null;
		if(array.length == 0) return null;
		for(int i=0;i<array.length;i++) {
			similarity = this.calculateSimilarity(array[i]);
			if(similarity > max) {
				max = similarity;
				maxCity = array[i];
			}
		}
		this.setTimestamp();
		this.setVisit(maxCity.getName());
		if(this instanceof YoungTraveller) (new YoungTraveller(this.getTimestamp(),this.getName(),this.getCity(),this.getVisit(),this.getAge(),this.getRate(),this.getLatLon(),this.getP())).addToCollection();
		else if(this instanceof ElderTraveller) (new ElderTraveller(this.getTimestamp(),this.getName(),this.getCity(),this.getVisit(),this.getAge(),this.getRate(),this.getLatLon(),this.getP())).addToCollection();
		else (new MiddleTraveller(this.getTimestamp(),this.getName(),this.getCity(),this.getVisit(),this.getAge(),this.getRate(),this.getLatLon(),this.getP())).addToCollection();
		return maxCity;
	}
	/**
	 * Compares the cities's similarity to this Traveler and returns the (int number) first cities with the maximum similarity
	 * @param array
	 * @param number
	 * @return Array of cities City[]
	 */
	public final City[] compareCities(City[] array,int number) {
		if(number < 2 || number > 5 || array == null || array.length < number) return null;
		double[] similarity = new double[array.length];
		City[] output = array.clone();
		boolean swapped;
		for(int i=0;i<similarity.length;i++) {
			similarity[i] = this.calculateSimilarity(array[i]);
		}
		for(int i=0;i<similarity.length-1;i++) {
			swapped = false;
			for(int j=0;j<similarity.length-1-i;j++) {
				if(similarity[j]<similarity[j+1]) {
					double temp1 = similarity[j];
					similarity[j] = similarity[j+1];
					similarity[j+1] = temp1;
					City temp2 = output[j];
					output[j] = output[j+1];
					output[j+1] = temp2;
				}
			}
			if (swapped == false) break;
		}
		return Arrays.copyOf(output, number);
	}
	/**
	 * Returns witch traveler from the array has the maximum similarity for the given city 
	 * @param t
	 * @param c
	 * @return Max Traveler
	 */
	public static Traveller ticket(Traveller[] t,City c) {//polymorphism
		double max=-1,similarity;
		Traveller maxTrav=null;
		if(t == null || c == null) return null;
		for(int i=0;i<t.length;i++) {
			similarity = t[i].calculateSimilarity(c);
			if(similarity > max) {
				max = similarity;
				maxTrav = t[i];
			}
		}
		return maxTrav;
	}
	/**
	 * creates a Traveler
	 * @returns the Traveler
	 * @throws IOException
	 * @throws SQLException
	 * @throws IllegalTravellerAge 
	 * @throws IllegalTravellerRate 
	 */
	public static Traveller createTraveller() throws IOException, SQLException, IllegalTravellerRate, IllegalTravellerAge {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.print("Give your name:");
		String name = scan.next();
		System.out.print("Give your City:");
		String city = scan.next();
		System.out.print("Give your Country:");
		String country = scan.next();
		String appid = "27ceb339722ca7d7d10a68896574cc91";
		System.out.print("Give your age:");
		int age = scan.nextInt();
		while(age < 16 || age > 115) {
			System.out.print("Wrong age give again (16-115):");
			age = scan.nextInt();
		}
		System.out.println("Give rate from 1 to 10");
		String[] term = City.getTerm();
		int[] termValue = new int[10];
		for(int i=0;i<term.length;i++) {
			System.out.print(term[i]+":");
			termValue[i] = scan.nextInt();
			while(termValue[i] > 10 || termValue[i] < 0) {
				System.out.print(term[i]+":");
				termValue[i] = scan.nextInt();
			}
		}
		if(age >= 16 && age <= 25) return new YoungTraveller(city,country,appid,name,termValue,age);
		else if(age > 25 && age <= 60) return new MiddleTraveller(city,country,appid,name,termValue,age);
		else if(age > 60 && age <= 115) return new ElderTraveller(city,country,appid,name,termValue,age);
		return null;
	}
	/**
	 * GUI
	 * @param name
	 * @param city
	 * @param country
	 * @param age
	 * @param rate
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws IllegalTravellerRate
	 * @throws IllegalTravellerAge
	 * @throws UniformInterfaceException
	 * @throws WikipediaNoArcticleException
	 */
	public static Traveller createTravellerGui(String name, String city, String country, int age,int[] rate,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws IOException, SQLException, IllegalTravellerRate, IllegalTravellerAge, UniformInterfaceException, WikipediaNoArcticleException {
		if(age >= 16 && age <= 25) return new YoungTraveller(city,country,name,rate,age,txtButtonCity,txtButtonCountry,f);
		else if(age > 25 && age <= 60) return new MiddleTraveller(city,country,name,rate,age,txtButtonCity,txtButtonCountry,f);
		else if(age > 60 && age <= 115) return new ElderTraveller(city,country,name,rate,age,txtButtonCity,txtButtonCountry,f);
		return null;
	}
	/**
	 * Returns a sorted ArrayList of Travelers who have searched a city without duplicates
	 * @return ArrayList Traveller
	 */
	public static ArrayList<Traveller> getListSorted() {//returns the list sorted without duplicates and doesn't save the changes
		ArrayList<Traveller> list = Traveller.getListTr();
		ArrayList<Traveller> newList = new ArrayList<Traveller>();
		Collections.sort(list);
		Iterator<Traveller> i = list.iterator();
		while(i.hasNext()) {
			Traveller t = i.next();
			if(!newList.contains(t)) newList.add(t);//throws duplicates
		}
		return newList;
	}
	/**
	 * Stores the ArrayList of Travelers who have searched a city to a traveller.json file
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void storeTravellers() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("traveller.json");
		file.createNewFile();
		mapper.writeValue(file, listTr);//TODO
	}
	
	/**
	 * Recovers the Travelers from the traveller.json file
	 * @param appid
	 * @return An ArrayList of the Travelers from the traveller.json file, if the file is empty returns null
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void recoverTravellers(String appid) throws JsonParseException, JsonMappingException, IOException {
		File file = new File("traveller.json");
		if(!file.exists()) return;
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Undefined> undefined = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Undefined.class));
		if(undefined == null) throw new NullPointerException("Something went wrong with the JSON file");
		if(undefined.size() == 0) return;
		ArrayList<Traveller> traveller = new ArrayList<Traveller>();
		Iterator<Undefined> i = undefined.iterator();
		while(i.hasNext()) {
			Undefined undef = i.next();
			int ageOfUndefined = undef.getAge();
			if(ageOfUndefined >= 16 && ageOfUndefined <=25) traveller.add(new YoungTraveller(undef.getTimestamp(),undef.getName(),undef.getCity(),undef.getVisit(),undef.getAge(),undef.getRate(),undef.getLatLon(),undef.getP()));
			else if(ageOfUndefined > 25 && ageOfUndefined <=60) traveller.add(new MiddleTraveller(undef.getTimestamp(),undef.getName(),undef.getCity(),undef.getVisit(),undef.getAge(),undef.getRate(),undef.getLatLon(),undef.getP()));
			else if(ageOfUndefined > 60 && ageOfUndefined <=115) traveller.add(new ElderTraveller(undef.getTimestamp(),undef.getName(),undef.getCity(),undef.getVisit(),undef.getAge(),undef.getRate(),undef.getLatLon(),undef.getP()));
			else throw new IllegalArgumentException("Error you changed json file data (age)");
		}
		Traveller.addToCollection(traveller);
	}

	public int[] getRate() {return Arrays.copyOf(rate,rate.length);}//return a copy of the array
	
	public void setRate(int[] rate) throws IllegalTravellerRate {//TODO
		if(rate == null ) throw new IllegalTravellerRate();
		if(rate.length != 10) throw new IllegalTravellerRate();
		for(int i=0;i<rate.length;i++) {
			if(rate[i] > 10 || rate[i] < 0) throw new IllegalTravellerRate();
			this.rate[i] = rate[i];
		}
	}
	
	public class IllegalTravellerRate extends Exception{
		private static final long serialVersionUID = 1L;
		
		public IllegalTravellerRate() {
		}
		
		public String getMessage() {
			
			return "Illegal rate, should be <=0 and <=10";
		}
	}
	
	public double[] getLatLon() {return Arrays.copyOf(latLon, latLon.length);}//return a copy of the array
	
	private void setLatLon(double[] latLon) {this.latLon = latLon;}//TODO
	
	public double getP() {return p;}
	
	public void setP(double p) {
		if(p > 1 || p < 0) throw new IllegalArgumentException("0<p<1");
		this.p = p;
	}
	
	public int getAge() {return age;}
	
	private void setAge(int age) throws IllegalTravellerAge {
		if(this instanceof YoungTraveller) {
			if(age < 16 || age > 25) {throw new IllegalTravellerAge(age);}else this.age = age;
		}else if(this instanceof MiddleTraveller) {
			if(age <= 25 || age > 60) {throw new IllegalTravellerAge(age);}else this.age = age;
		}else if(this instanceof ElderTraveller) {
			if(age <= 60 || age > 115) {throw new IllegalTravellerAge(age);}else this.age = age;
		}
		else throw new IllegalTravellerAge(age);
	}
	
	 public class IllegalTravellerAge extends Exception{
		private static final long serialVersionUID = 1L;
		private int travAge;
		
		public IllegalTravellerAge(int travAge) {
			this.travAge=travAge;
		}
		
		public String getMessage() {
			
			return "Age cant be "+travAge+".";
		}
	}

	public void setName(String name) {this.name = name;}//TODO
	
	public String getName() {return name;}

	public String getCity() {return city;}

	private void setCity(String city) {this.city = city;}//TODO
	
	public long getTimestamp() {return timestamp;}

	public void setTimestamp() {//TODO
		this.timestamp = new Date().getTime();
	}

	public String getVisit() {return visit;}

	public void setVisit(String visit) {//TODO
		this.visit = visit;
	}

	public static String getAllDetails(Traveller t) {
		return t.getName()+" "+t.getAge()+" "+t.getCity();
	}
	
	public void addToCollection() {listTr.add(this);}
	
	/**
	 * Appends the ArrayList
	 * @param list
	 */
	public static void addToCollection(ArrayList<Traveller> list) {
		List<Traveller> newList = new ArrayList<Traveller>(list);
		listTr.addAll(newList);
	}

	public static ArrayList<Traveller> getListTr(){return new ArrayList<Traveller>(listTr);}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(this == null || o.getClass() != this.getClass()) return false;
		Traveller t = (Traveller) o;
		if(this.getAge() == t.getAge() && this.getCity().equals(t.getCity()) && this.getName().equals(t.getName())) return true;
		else return false;
	}
	
	@Override
	public int compareTo(Traveller t) {
		long compareTime = ((Traveller)t).getTimestamp();
		return (int) (this.getTimestamp() - compareTime);
	}
}