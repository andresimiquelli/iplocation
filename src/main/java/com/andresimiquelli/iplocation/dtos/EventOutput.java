package com.andresimiquelli.iplocation.dtos;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.andresimiquelli.iplocation.contracts.EventDTO;

public class EventOutput implements EventDTO{

	private String clientId;
	private long timestamp;
	private String ip;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String country;
	private String region;
	private String city;
	
	public EventOutput() {
	}

	public EventOutput(String clientId, long timestamp, String ip, BigDecimal latitude, BigDecimal longitude,
			String country, String region, String city) {

		fill(clientId, timestamp, ip, latitude, longitude, country, region, city);
	}
	
	public EventOutput(String serialized) {
		JSONObject obj = new JSONObject(serialized);
		fill(
			obj.getString("clientId"),
			obj.getLong("timestamp"),
			obj.getString("ip"),
			obj.getBigDecimal("latitude"),
			obj.getBigDecimal("longitude"),
			obj.getString("country"),
			obj.getString("region"),
			obj.getString("city")
		);
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private void fill(String clientId, long timestamp, String ip, BigDecimal latitude, BigDecimal longitude,
			String country, String region, String city) {
		
		this.clientId = clientId;
		this.timestamp = timestamp;
		this.ip = ip;
		this.latitude = latitude;
		this.longitude = longitude;
		this.country = country;
		this.region = region;
		this.city = city;
	}

	public String serialize() {
		JSONObject obj = new JSONObject(this);
		return obj.toString();
	}
}
