package com.andresimiquelli.iplocation.contracts;

import java.util.Properties;

import org.apache.kafka.streams.StreamsBuilder;

public interface StreamProcessor {

	public void start();
	
	public StreamsBuilder getBuilder();
	
	public Properties getConfigs();
}
