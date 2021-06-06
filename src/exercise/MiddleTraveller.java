package exercise;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JTextPane;

import com.sun.jersey.api.client.UniformInterfaceException;

import exception.WikipediaNoArcticleException;
import gui.FrameUserInfo;

public class MiddleTraveller extends Traveller{
	
	public MiddleTraveller(String city,String country,String appid,String name,int[] rate,int age) throws IOException, SQLException, IllegalTravellerRate, IllegalTravellerAge {
		super(city,country,appid,name,rate,age);
		this.setP(0.5);
	}
	
	public MiddleTraveller(long timestamp, String name, String city, String visit, int age, int rate[], double latLon[], double p) {
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
	public MiddleTraveller(String city,String country,String name,int[] rate,int age,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws IOException, SQLException, IllegalTravellerRate, IllegalTravellerAge, UniformInterfaceException, WikipediaNoArcticleException {
		super(city,country,name,rate,age,txtButtonCity,txtButtonCountry,f);
		this.setP(0.5);
	}
	
	@Override
	public double similarityTermsVector(City city) {
		int[] termsVectorUser = this.getRate();
		int[] termsVectorCity = city.gettermsVector();
		int sum_ab=0,sum_a=0,sum_b=0;
		for(int i=0;i<termsVectorUser.length;i++) {
			sum_ab += termsVectorUser[i]*termsVectorCity[i];
			sum_a += Math.pow(termsVectorUser[i], 2);
			sum_b += Math.pow(termsVectorCity[i], 2);
		}
		if(sum_a == 0 || sum_b == 0) return 0;
		return sum_ab/(Math.sqrt(sum_a)*Math.sqrt(sum_b));
	}
}