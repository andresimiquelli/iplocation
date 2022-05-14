package com.andresimiquelli.iplocation.dtos;

import org.json.JSONObject;

import com.andresimiquelli.iplocation.contracts.EventDTO;

public class EventInput implements EventDTO{

	private String clientId;
	private long timestamp;
	private String ip;
	
	public EventInput() {
	}
	
	public EventInput(String serialized) {
		JSONObject obj = new JSONObject(serialized);
		fill(
			obj.getString("clientId"),
			obj.getLong("timestamp"),
			obj.getString("ip")
		);
	}
	
	public EventInput(String clientId, long timestamp, String ip) {
		fill(clientId, timestamp, ip);
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
	
	public String serialize() {
		JSONObject obj = new JSONObject(this);
		return obj.toString();
	}
	
	private void fill(String clientId, long timestamp, String ip) {
		this.clientId = clientId;
		this.timestamp = timestamp;
		this.ip = ip;
	}
}
