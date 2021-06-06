package exercise;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JTextPane;

import com.sun.jersey.api.client.UniformInterfaceException;

import exception.WikipediaNoArcticleException;
import gui.FrameUserInfo;

public class YoungTraveller extends Traveller{
	
	public YoungTraveller(String city,String country,String appid,String name,int[] rate,int age) throws IOException, SQLException, IllegalTravellerRate, IllegalTravellerAge {
		super(city,country,appid,name,rate,age);
		this.setP(0.95);
	}
	
	public YoungTraveller(long timestamp, String name, String city, String visit, int age, int rate[], double latLon[], double p) {
		super(timestamp,name,city,visit,age,rate,latLon,p);
	}
	/**
	 * GUI
	 * @param city
	 * @param country
	 * @param name
	 * @param rate
	 * @param age
	 * @throws IOException
	 * @throws SQLException
	 * @throws UniformInterfaceException
	 * @throws WikipediaNoArcticleException
	 * @throws IllegalTravellerAge
	 * @throws IllegalTravellerRate
	 */
	public YoungTraveller(String city,String country,String name,int[] rate,int age,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws IOException, SQLException, UniformInterfaceException, WikipediaNoArcticleException, IllegalTravellerAge, IllegalTravellerRate {
		super(city,country,name,rate,age,txtButtonCity,txtButtonCountry,f);
		this.setP(0.95);
	}
	
	@Override
	public double similarityTermsVector(City city) {
		int[] termsVectorUser = this.getRate();
		int[] termsVectorCity = city.gettermsVector();
		int sum = 0;
		for(int i=0;i<termsVectorUser.length;i++) {
			sum += Math.pow(termsVectorUser[i]-termsVectorCity[i],2);
		}
		return 1/(1+Math.sqrt(sum));
	}
}