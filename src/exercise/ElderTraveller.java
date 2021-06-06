package exercise;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JTextPane;

import com.sun.jersey.api.client.UniformInterfaceException;

import exception.WikipediaNoArcticleException;
import gui.FrameUserInfo;

public class ElderTraveller extends Traveller{
	
	public ElderTraveller(String city,String country,String appid,String name,int[] rate,int age) throws IOException, SQLException, IllegalTravellerRate, IllegalTravellerAge {
		super(city,country,appid,name,rate,age);
		this.setP(0.05);
	}
	
	public ElderTraveller(long timestamp, String name, String city, String visit, int age, int rate[], double latLon[], double p) {
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
	 * @throws IllegalTravellerRate
	 * @throws IllegalTravellerAge
	 * @throws UniformInterfaceException
	 * @throws WikipediaNoArcticleException
	 */
	public ElderTraveller(String city,String country,String name,int[] rate,int age,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws IOException, SQLException, IllegalTravellerRate, IllegalTravellerAge, UniformInterfaceException, WikipediaNoArcticleException {
		super(city,country,name,rate,age,txtButtonCity,txtButtonCountry,f);
		this.setP(0.05);
	}
	
	@Override
	public double similarityTermsVector(City city) {
		int[] termsVectorUser = this.getRate();
		int[] termsVectorCity = city.gettermsVector();
		int sumIntersection=0,sumUnion=0;
		for(int i=0;i<termsVectorUser.length;i++) {
			if(termsVectorUser[i] >= 1 && termsVectorCity[i] >= 1) sumIntersection++;
			if(termsVectorUser[i] >=1 || termsVectorCity[i] >= 1) sumUnion++;
		}
		if(sumUnion == 0) return 0;
		return sumIntersection/sumUnion;
	}

}