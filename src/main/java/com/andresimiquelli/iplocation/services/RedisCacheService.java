package com.andresimiquelli.iplocation.services;

import com.andresimiquelli.iplocation.contracts.CacheService;
import com.andresimiquelli.iplocation.dtos.EventInput;
import com.andresimiquelli.iplocation.dtos.EventOutput;

import redis.clients.jedis.Jedis;

public class RedisCacheService implements CacheService{

	private Jedis connection;
	private Long timeout = null;
	
	public RedisCacheService(String host, int port, int db) {
		connect(host, port, db);
	}
	
	public RedisCacheService(String host, int port, int db, long timeout) {
		this.timeout = timeout;
		connect(host, port, db);
	}

	public EventOutput get(EventInput event) {
		
		String value = connection.get(keyGen(event.getClientId(), event.getIp()));
		
		if(value != null) {
			return new EventOutput(value);
		}
		
		return null;
	}

	public void set(EventOutput event) {
		String key = keyGen(event.getClientId(), event.getIp());
        connection.set(key, event.serialize());
        
        if(timeout != null) {
        	connection.expire(key, timeout);
        }
		
	}
	
	private void connect(String host, int port, int db) {
		connection = new Jedis("redis://"+host+":"+port+"/"+db);
	}
	
	private String keyGen(String id, String ip) {
		return id+"@"+ip;
	}
	
}
