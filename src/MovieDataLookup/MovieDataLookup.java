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
		//System.out.println(dataString); //For testing
		
		//From com.google.gson library ...
		omdbData = new Gson().fromJson(dataString, new TypeToken<HashMap<String, Object>>(){}.getType());
		//Directions for including: https://medium.com/programmers-blockchain/importing-gson-into-eclipse-ec8cf678ad52
	}
	
	public static void displayInConsole() //For now ...
	{
		if (omdbData.get("Title") == null)
			System.out.println("Movie not found\n");
		
		else
		{	
			System.out.println("Title: " + omdbData.get("Title"));
			System.out.println("Plot: " + omdbData.get("Plot"));
			System.out.println();
			System.out.println("Type: " + omdbData.get("Type"));
			System.out.println("Year: " + omdbData.get("Year"));
			System.out.println("Rated: " + omdbData.get("Rated"));
			System.out.println("Released: " + omdbData.get("Released"));
			System.out.println("BoxOffice: " + omdbData.get("BoxOffice"));
			System.out.println("DVD: " + omdbData.get("DVD"));
			System.out.println();
			System.out.println("Runtime: " + omdbData.get("Runtime"));
			System.out.println("Genre: " + omdbData.get("Genre"));
			System.out.println("Director: " + omdbData.get("Director"));
			System.out.println("Writer: " + omdbData.get("Writer"));
			System.out.println("Actors: " + omdbData.get("Actors"));
			System.out.println("Language: " + omdbData.get("Language"));
			System.out.println("Country: " + omdbData.get("Country"));
			System.out.println("Production: " + omdbData.get("Production"));
			System.out.println();
			System.out.println("Metascore: " + omdbData.get("Metascore"));
			System.out.println("imdbRating: " + omdbData.get("imdbRating"));
			System.out.println("Awards: " + omdbData.get("Awards"));
			System.out.println();
			System.out.println("Website: " + omdbData.get("Website"));
			System.out.println("Poster: " + omdbData.get("Poster"));
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		String k, t;
		Scanner scan = new Scanner (System.in);
		System.out.print("Enter your OMDB API key: ");
		k = scan.nextLine();
		System.out.print("Enter a title: ");
		t = scan.nextLine();
		System.out.println();
		getOmdbData(k, t);
		displayInConsole();
		scan.close();
		//c87439fd
	}
}
