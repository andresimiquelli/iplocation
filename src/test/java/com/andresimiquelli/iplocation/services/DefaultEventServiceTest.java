package com.andresimiquelli.iplocation.services;

import java.util.Date;
import java.util.Properties;

import org.json.JSONObject;

import com.andresimiquelli.iplocation.AppConfigs;
import com.andresimiquelli.iplocation.contracts.EventService;

import junit.framework.TestCase;

public class DefaultEventServiceTest extends TestCase {

	public void testEventProccess() {
		
		Properties configs = AppConfigs.get("test");
		EventService eventService = DefaultEventService.getInstance(configs);
		
		Date date = new Date();
		JSONObject event = new JSONObject();
		event.put("clientId", "123456");
		event.put("timestamp", date.getTime());
		event.put("ip", "34.224.241.47");
		
		JSONObject output = new JSONObject(eventService.proccess(event.toString()));
		
		assertEquals("United States",output.getString("country"));
		assertEquals("34.224.241.47",output.getString("ip"));
	}
}
