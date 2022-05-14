package com.andresimiquelli.iplocation.contracts;

import com.andresimiquelli.iplocation.dtos.EventInput;
import com.andresimiquelli.iplocation.dtos.EventOutput;

public interface CacheService {

	public EventOutput get(EventInput event);
	public void set(EventOutput event);
}
