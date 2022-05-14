package com.andresimiquelli.iplocation.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.andresimiquelli.iplocation.contracts.CacheService;
import com.andresimiquelli.iplocation.contracts.LocationService;
import com.andresimiquelli.iplocation.dtos.EventInput;
import com.andresimiquelli.iplocation.dtos.EventOutput;

public class IpStackLocationService implements LocationService {
	
	private String serviceUrl;
	private String accessKey;
	private final CacheService cacheService;

	public IpStackLocationService(String serviceUrl, String accessKey, CacheService cacheService) {
		this.serviceUrl = serviceUrl;
		this.accessKey = accessKey;
		this.cacheService = cacheService;
	}
	
	public EventOutput getLocation(EventInput event) {
		
		EventOutput location = cacheService.get(event);
		
		if(location == null)
			location = fetch(event);
		
		return location;
	}
	
	private EventOutput fetch(EventInput event) {
		
		String preparedUrl = this.serviceUrl+event.getIp()+"/?access_key="+accessKey; 
		
		EventOutput location = null;
        
        try {
            
            URL url = new URL(preparedUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            if(conn.getResponseCode() == 200) {
                
                BufferedReader response = new BufferedReader( new InputStreamReader(conn.getInputStream()));
                JSONObject responseJson = new JSONObject(convertToString(response));
                
                location = new EventOutput();
                location.setClientId(event.getClientId());
                location.setTimestamp(event.getTimestamp());
                location.setIp(event.getIp());
                location.setLatitude(responseJson.getBigDecimal("latitude"));
                location.setLongitude(responseJson.getBigDecimal("longitude"));
                location.setCountry(responseJson.getString("country_name"));
                location.setRegion(responseJson.getString("region_name"));
                location.setCity(responseJson.getString("city"));
                
            } else {
                Logger.getLogger(IpStackLocationService.class.getName()).log(Level.SEVERE, "Http status error: {0}", conn.getResponseCode());
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(IpStackLocationService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IpStackLocationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return location;
	}
	
	private String convertToString(BufferedReader response) throws IOException {
		String line, str = "";
		
        while ((line = response.readLine()) != null) {
            str += line;
        }
        
        return str;
	}

}
