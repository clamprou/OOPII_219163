package exercise;

public class Undefined {
	private long timestamp;
	private String name,city,visit;
	private int age;
	private int[] rate = new int[10];
	private double[] latLon = new double[2];
	private double p;
	
	public Undefined() {
		
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVisit() {
		return visit;
	}

	public void setVisit(String visit) {
		this.visit = visit;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int[] getRate() {
		return rate;
	}

	public void setRate(int[] rate) {
		this.rate = rate;
	}

	public double[] getLatLon() {
		return latLon;
	}

	public void setLatLon(double[] latLon) {
		this.latLon = latLon;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}
	
}
