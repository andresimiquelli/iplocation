package com.andresimiquelli.iplocation.services;

import java.util.Properties;

import com.andresimiquelli.iplocation.AppConfigs;
import com.andresimiquelli.iplocation.contracts.CacheService;
import com.andresimiquelli.iplocation.contracts.EventService;
import com.andresimiquelli.iplocation.contracts.LocationService;
import com.andresimiquelli.iplocation.dtos.EventInput;
import com.andresimiquelli.iplocation.dtos.EventOutput;
import com.andresimiquelli.iplocation.exceptions.EventNullException;

public class DefaultEventService implements EventService{
	
	private static EventService instance;
	
	private final LocationService locationService;
	private final CacheService cacheService;
	
	
	public DefaultEventService(Properties configs) {
		
		cacheService = new RedisCacheService(
				configs.getProperty(AppConfigs.REDIS_SERVER_HOST), 
				Integer.valueOf(configs.getProperty(AppConfigs.REDIS_SERVER_PORT)), 
				Integer.valueOf(configs.getProperty(AppConfigs.REDIS_SERVER_DB))
		);
		
		locationService = new IpStackLocationService(
				configs.getProperty(AppConfigs.IPSTACK_SERVICE_URL),
				configs.getProperty(AppConfigs.IPSTACK_ACCESS_KEY), 
				cacheService
		);
	}
	
	public static EventService getInstance(Properties configs) {
		
		if(instance == null)
			return new DefaultEventService(configs);
		
		return instance;
	}

	public String proccess(String serialized) {
		
		EventInput event = new EventInput(serialized);		
		EventOutput location = locationService.getLocation(event);
		
		if(location == null)
			throw new EventNullException(event);
		
		return location.serialize();
	}
}
