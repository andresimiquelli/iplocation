package com.andresimiquelli.iplocation.contracts;

import com.andresimiquelli.iplocation.dtos.EventInput;

public interface LocationService {
	
	public EventDTO getLocation(EventInput event);
}
