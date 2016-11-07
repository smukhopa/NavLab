package mapsAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/*
 * Used for calling the OSM API
 */
public class MapsAPIHelper {
	
	private String query;
	
	public MapsAPIHelper(double maxLat, double minLat, double maxLng, double minLng) {
		createURL(maxLat, minLat, maxLng, minLng);
	}
	
	/*
	 * Connect to the OSM API return the JSON.
	 */
	public JSONObject connect() {
		try {
			URL url = new URL(query);
			System.out.println(query);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			InputStream stream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			
			// http request is returned and gets stored in the buffer. The return format is XML.
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            JSONObject OSMobject = XML.toJSONObject(buffer.toString());
            return OSMobject;
		} catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/*
	 * Method for creating query based on LatLng
	 */
	private void createURL(double maxLat, double minLat, double maxLng, double minLng) {
		this.query = "http://overpass-api.de/api/interpreter?data=(node(" + 
				minLat + "," +
				minLng + "," + 
				maxLat + "," +
				maxLng + ");way(bn);<;);out;";
	}
	
}
