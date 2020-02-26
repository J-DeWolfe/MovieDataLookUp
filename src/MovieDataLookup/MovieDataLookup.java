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
				"\nPLOT: " + omdbData.get("Plot") +
				"\n" +
				"\nTYPE: " + omdbData.get("Type") +
				"\nYEAR: " + omdbData.get("Year") +
				"\nRATED: " + omdbData.get("Rated") +
				"\nRELEASED: " + omdbData.get("Released") +
				"\nBoxOffice: " + omdbData.get("BoxOffice") +
				"\nDVD:: " + omdbData.get("DVD") +
				"\nRUNTIME: " + omdbData.get("Runtime") +
				"\nGENRE: " + omdbData.get("Genre") +
				"\nDIRECTOR: " + omdbData.get("Director") +
				"\nWRITER: " + omdbData.get("Writer") +
				"\nACTORS: " + omdbData.get("Actors") +
				"\nLANGUAGE: " + omdbData.get("Language") +
				"\nCOUNTRY: " + omdbData.get("Country") +
				"\nPRODUCTION: " + omdbData.get("Production") +
				"\n" +
				"\nMETASCORE: " + omdbData.get("Metascore") +
				"\nIMDB RATING: " + omdbData.get("imdbRating") +
				"\nAWARDS: " + omdbData.get("Awards") +
				"\n" +
				"\nWEBSITE: " + omdbData.get("Website");
	}
	
	public static String getPosterURL() { return (String)omdbData.get("Poster"); }
	
	public static void main(String[] args) throws IOException
	{
		MovieDataLookupGUI gui = new MovieDataLookupGUI();	
		
	}
}
