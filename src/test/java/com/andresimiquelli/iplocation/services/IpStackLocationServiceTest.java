package com.andresimiquelli.iplocation.services;

import java.util.Date;
import java.util.Properties;

import com.andresimiquelli.iplocation.AppConfigs;
import com.andresimiquelli.iplocation.contracts.CacheService;
import com.andresimiquelli.iplocation.contracts.LocationService;
import com.andresimiquelli.iplocation.dtos.EventInput;
import com.andresimiquelli.iplocation.dtos.EventOutput;

import junit.framework.TestCase;

public class IpStackLocationServiceTest extends TestCase {

	public void testFetchedAndCached() {
		
		Properties configs = AppConfigs.get();
		long timeout = 3;
		
		CacheService cache = new RedisCacheService(
				configs.getProperty(AppConfigs.REDIS_SERVER_HOST),
				Integer.valueOf(configs.getProperty(AppConfigs.REDIS_SERVER_PORT)),
				Integer.valueOf(configs.getProperty(AppConfigs.REDIS_SERVER_DB)),
				timeout
		);
		
		LocationService locationService = new IpStackLocationService(
				configs.getProperty(AppConfigs.IPSTACK_SERVICE_URL),
				configs.getProperty(AppConfigs.IPSTACK_ACCESS_KEY), 
				cache
		);
		
		Date date = new Date();
		EventInput event = new EventInput(String.valueOf(date.getTime()), 1652554064, "54.160.38.125");
		
		EventOutput location = locationService.getLocation(event);
		
		assertEquals("54.160.38.125", location.getIp());
		assertEquals("United States", location.getCountry());
	}
}
