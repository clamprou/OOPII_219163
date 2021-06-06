package streams;

public class Example implements Comparable<Example>{
	String city;
	int rank;
	public Example(String city,int rank) {
		this.city = city;
		this.rank = rank;
	}
	@Override
	public int compareTo(Example e) {
		long compare = ((Example)e).rank;
		return (int) (this.rank - compare);
	}
}