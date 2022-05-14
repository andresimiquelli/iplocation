package com.andresimiquelli.iplocation.services;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.andresimiquelli.iplocation.AppConfigs;
import com.andresimiquelli.iplocation.contracts.CacheService;
import com.andresimiquelli.iplocation.dtos.EventInput;
import com.andresimiquelli.iplocation.dtos.EventOutput;

import junit.framework.*;

public class RedisCacheServiceTest extends TestCase {

	public void testCache() {
		Properties configs = AppConfigs.get();
		long timeout = 3;
		CacheService cache = new RedisCacheService(
				configs.getProperty(AppConfigs.REDIS_SERVER_HOST),
				Integer.valueOf(configs.getProperty(AppConfigs.REDIS_SERVER_PORT)),
				Integer.valueOf(configs.getProperty(AppConfigs.REDIS_SERVER_DB)),
				timeout
		);
		
		EventOutput event = new EventOutput(
				"ace762846cf77a8a7d45894325ec5acdeef1f43",
				1652554064,
				"186.193.26.6",
				new BigDecimal(-20.648780822753906),
				new BigDecimal(-41.910160064697266),
				"Brazil",
				"Minas Gerais",
				"Espera Feliz"
		);
		
		cache.set(event);
		
		EventInput input = new EventInput("ace762846cf77a8a7d45894325ec5acdeef1f43",1652554064,"186.193.26.6");
		
		EventOutput recoveredEvent = cache.get(input);
		
		assertEquals("Brazil", recoveredEvent.getCountry());
		
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		EventOutput recoveredEventNull = cache.get(input);
		
		assertNull(recoveredEventNull);
	}
}
