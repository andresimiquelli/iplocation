package com.andresimiquelli.iplocation.contracts;

import com.andresimiquelli.iplocation.dtos.EventInput;
import com.andresimiquelli.iplocation.dtos.EventOutput;

public interface LocationService {
	
	public EventOutput getLocation(EventInput event);
}
