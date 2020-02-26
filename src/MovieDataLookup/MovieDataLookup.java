package MovieDataLookup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class MovieDataLookup
{	
	private static Map<String, Object> omdbData;
	
	private static void getOmdbData(String k, String t) throws IOException
	{
		String title = t.replace(' ',  '+'); //Readable to query-able
		String dataString = "";	
		
		try
		{
			String omdbapi = "http://www.omdbapi.com/";
			String query = "?apikey=" + k + "&t=" + title;
			URL url = new URL(omdbapi + query);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
	        
			int responseCode = connection.getResponseCode();
			if (responseCode != 200)
	        	throw new RuntimeException("HttpResponseOce: " + responseCode);
	        
			else
	        {
	        	Scanner scan = new Scanner(url.openStream());
	        	while (scan.hasNext())
	        		dataString+=scan.nextLine();
	        	scan.close();
	        } 
	    }
		catch (Exception e) { System.out.println("Exception: " + e); }	
		
		/*	Directions for including the  com.google.gson library:
			https://medium.com/programmers-blockchain/importing-gson-into-eclipse-ec8cf678ad52 */
		omdbData = new Gson().fromJson(dataString, new TypeToken<HashMap<String, Object>>(){}.getType());
	}
		
	public static String getMovieDataString(String k, String t) throws IOException
	{
		getOmdbData(k, t);
		
		if (omdbData.get("Title") == null) return "Movie not found";
		return "" + ((String)omdbData.get("Title")).toUpperCase() + 
				"\n" +
				"\nPlot: " + omdbData.get("Plot") +
				"\n" +
				"\nType: " + omdbData.get("Type") +
				"\nYear: " + omdbData.get("Year") +
				"\nRated: " + omdbData.get("Rated") +
				"\nReleased: " + omdbData.get("Released") +
				"\nBox Office: " + omdbData.get("BoxOffice") +
				"\nDVD: " + omdbData.get("DVD") +
				"\nRuntime: " + omdbData.get("Runtime") +
				"\nGenre: " + omdbData.get("Genre") +
				"\nDirector: " + omdbData.get("Director") +
				"\nWriter: " + omdbData.get("Writer") +
				"\nActors: " + omdbData.get("Actors") +
				"\nLanguage: " + omdbData.get("Language") +
				"\nCountry: " + omdbData.get("Country") +
				"\nProduction: " + omdbData.get("Production") +
				"\n" +
				"\nMetascore: " + omdbData.get("Metascore") +
				"\nIMDB Rating: " + omdbData.get("imdbRating") +
				"\nAwards: " + omdbData.get("Awards") +
				"\n" +
				"\nWebsite: " + omdbData.get("Website");
	}
	
	public static String getPosterURL() { return (String)omdbData.get("Poster"); }
	
	public static void main(String[] args) throws IOException
	{
		MovieDataLookupGUI gui = new MovieDataLookupGUI();
		
	}
}
