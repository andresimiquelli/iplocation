package com.andresimiquelli.iplocation.exceptions;

import com.andresimiquelli.iplocation.dtos.EventInput;

public class EventNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public EventNullException(EventInput event) {
		super("Null response for event: CliendId="+event.getClientId()+" IP="+event.getIp());
	}

}
