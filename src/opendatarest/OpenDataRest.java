package opendatarest;

import java.io.IOException;
import javax.swing.JTextPane;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.apache.http.client.ClientProtocolException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import exception.WikipediaNoArcticleException;
import exercise.City;
import gui.FrameUserInfo;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

/**Description and weather information using OpenData with Jackson JSON processor and Jersey Client.
* @since 29-2-2020
* @version 1.0
* @author John Violos  */
public class OpenDataRest {

/**Retrieves weather information, geotag (lan, lon) and a Wikipedia article for a given city using Jersey framework.
* @param city The Wikipedia article and OpenWeatherMap city. 
* @param country The country initials (i.e. gr, it, de).
* @param appid Your API key of the OpenWeatherMap.*/ 	
public static void RetrieveOpenWeatherMap(String city, String country, String appid,City c,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws JsonParseException, JsonMappingException, IOException, UniformInterfaceException {
	try {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://api.openweathermap.org/data/2.5/weather?q="+city+","+country+"&APPID="+appid+"").build());      
		ObjectMapper mapper = new ObjectMapper(); 
		String json= service.accept(MediaType.APPLICATION_JSON).get(String.class);
		OpenWeatherMap weather_obj = mapper.readValue(json,OpenWeatherMap.class);
		c.setgeodesicVector(new double[] {weather_obj.getCoord().getLat(),weather_obj.getCoord().getLon()});
	}catch(UniformInterfaceException e) {
		f.error = true;
		txtButtonCity.setText("Unexcpected City name");
		if(txtButtonCountry==null) return;
		txtButtonCountry.setText("Unexcpected Country name");
	}
}
	
/**Retrieves Wikipedia information, geotag (lan, lon) and a Wikipedia article for a given city using Jersey framework.
* @param city The Wikipedia article and OpenWeatherMap city. 
 * @throws WikipediaNoArcticleException */ 	
public static void RetrieveWikipedia(City city,JTextPane txtButtonCity,JTextPane txtButtonCountry,FrameUserInfo f) throws  IOException, WikipediaNoArcticleException {
	String article="";
	ClientConfig config = new DefaultClientConfig();
	Client client = Client.create(config);
	WebResource service = client.resource(UriBuilder.fromUri("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+city.getName()+"&format=json&formatversion=2").build());      
	ObjectMapper mapper = new ObjectMapper(); 
	String json= service.accept(MediaType.APPLICATION_JSON).get(String.class); 
	if (json.contains("pageid")) {
		MediaWiki mediaWiki_obj =  mapper.readValue(json, MediaWiki.class);
		article= mediaWiki_obj.getQuery().getPages().get(0).getExtract();
		//System.out.println(city+" Wikipedia article: "+article);		
	} else {
		txtButtonCity.setText("Unexcpected City name");
		txtButtonCountry.setText("Unexcpected Country name");
		f.error = true;
	}
	String[] term = City.getTerm();
	int[] arrayTerms = new int[term.length];
	for(int i=0;i<arrayTerms.length;i++) {
		arrayTerms[i] = OpenDataRest.countCriterionfCity(article,term[i]);
		if(arrayTerms[i] > 10) arrayTerms[i] = 10;//giati an mia leksi emfanizete poles fores 8a einai megalh h diafora sto 1/1+riza opote megalos paranomasths mikrh similaritytermsvector eno 8a eprepe na einai megalo
	}
	city.settermsVector(arrayTerms);
}
	
	 
public static void main(String[] args) throws ClientProtocolException, IOException, WikipediaNoArcticleException{
	//String appid ="Put your key"; //Your openweathermap id.
	//RetrieveOpenWeatherMap("Rome","it",appid);	
	//RetrieveOpenWeatherMap("Athens","gr",appid);
	//RetrieveOpenWeatherMap("Corfu","gr",appid);	
	//RetrieveOpenWeatherMap("Berlin","de",appid);
//	BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
//	String city="Athens";
//	String article="";
//	while (true) {		
//		try {		
//			article=RetrieveWikipedia(city);
//			break;
//			//RetrieveWikipedia("Athens");
//			//RetrieveWikipedia("Corfu");
//			//RetrieveWikipedia("Berlin");
//		} catch (WikipediaNoArcticleException e) {
//			System.out.println(e.getMessage());
//			System.out.print("Type a correct city name: ");
//			city = stdin.readLine();
//			continue;
//		}
//		
//	}
//	String[] criterions = {"museum", "theatre", "sea", "caf", "mountain"};
//	for (int i=0; i<criterions.length;i++) { 
//	System.out.println("The term "+criterions[i]+" occurs "+countCriterionfCity(article,criterions[i]) + " number of times.");		
//	}
}

public static int countTotalWords(String str) {	
	String s[]=str.split(" ");
	return 	s.length;
}	

/** Counts the number of times a criterion occurs in the city wikipedia article.
@param cityArticle  The String of the retrieved wikipedia article.
@param criterion The String of the criterion we are looking for.
@return An integer, the number of times the criterion-string occurs in the wikipedia article.
*/	
public static int countCriterionfCity(String cityArticle, String criterion) {
	cityArticle=cityArticle.toLowerCase();
	int index = cityArticle.indexOf(criterion);
	int count = 0;
	while (index != -1) {
	    count++;
	    cityArticle = cityArticle.substring(index + 1);
	    index = cityArticle.indexOf(criterion);
	}
	return count;
}

}