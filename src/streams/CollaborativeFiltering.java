package streams;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import exercise.Traveller;
import exercise.Undefined;

public class CollaborativeFiltering {

public static void main(String args[]) throws SQLException, JsonParseException, JsonMappingException, IOException {
	//ArrayList<Traveller> collectionTravellers = Traveller.getListTr();
	/*for(int i=0; i<100; i++) {	//We add 100 historical Travelers in the Collection.
		Traveller curTraveller = new Traveller("Traveller_"+Integer.toString(i),r.nextInt(90),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2));  		
		curTraveller.setVisit("City_"+Integer.toString(i)); //Will the Traveler_i visit the City_i ?
		collectionTravellers.add(curTraveller);		
	}	
	System.out.println(collectionTravellers);
	*/
	
	
	//We have a new candidate traveler.
	//Traveller candidateTraveller = new Traveller("Traveller_Candidate",r.nextInt(90),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2));  		
	//int[] candidateTravellerCriteria=candidateTraveller.getCriteria(); //The criteria of the candidate traveler.
	
	//We see for each city what is the rank of similarity (dot product) between the candidate Traveller criteria and all the Travellers of the Collection.
	//Map <String,Integer> cityToRank=collectionTravellers.stream().collect(Collectors.toMap(i->i.getVisit(),i->innerDot(i.getCriteria(),candidateTravellerCriteria)));
	//cityToRank.forEach((k,v)->System.out.println("city:"+k+" rank: "+v));
	
	//We print the Traveller who has the highest Rank (similarity) (dot product).
	//Optional<RecommendedCity> recommendedCity=
		//	collectionTravellers.stream().map(i-> new RecommendedCity(i.getVisit(),innerDot(i.getCriteria(),candidateTravellerCriteria))).max(Comparator.comparingInt(RecommendedCity::getRank));
			
	//System.out.println("The Recommended City:"+recommendedCity.get().getCity());
	
}
	public static String findBestCity(int[] rate) {
		ArrayList<Traveller> collectionTravellers = Traveller.getListTr();
		ArrayList<Example> list = new ArrayList<>();
		collectionTravellers.stream().collect(Collectors.toSet()).forEach(i->{
			list.add(new Example(i.getVisit(),innerDot(i.getRate(),rate)));
		});
		return Collections.max(list).city;
	}

	private static int innerDot(int[] currentTraveller, int[] candidateTraveller) {
		int sum=0;
		for (int i=0; i<currentTraveller.length;i++)
			sum+=currentTraveller[i]*candidateTraveller[i];
		return sum;
			
	}
	

}