package com.andresimiquelli.iplocation;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.andresimiquelli.iplocation.contracts.EventService;
import com.andresimiquelli.iplocation.contracts.StreamProcessor;
import com.andresimiquelli.iplocation.services.DefaultEventService;

public class DefaultStreamProcessor implements StreamProcessor {
	
	private final Properties configs;
	private StreamsBuilder streamsBuilder;
	
	public DefaultStreamProcessor(Properties configs) {
		this.configs = configs;
	}

	public void start() {
		Properties props = getConfigs();
		StreamsConfig streamsConfig = new StreamsConfig(props);
		StreamsBuilder streamBuilder = getBuilder();
		KafkaStreams streams = new KafkaStreams(streamBuilder.build(), streamsConfig);
		streams.start();
	}
	
	public StreamsBuilder getBuilder() {
		
		if(streamsBuilder == null) {
			String inputTopic = configs.getProperty(AppConfigs.INPUT_TOPIC);
			String outputTopic = configs.getProperty(AppConfigs.OUTPUT_TOPIC);
			
			System.out.println("TOPICS "+inputTopic+" "+outputTopic);
			
			EventService eventService = DefaultEventService.getInstance(configs);
			
			StreamsBuilder streamBuilder = new StreamsBuilder();
			KStream<String, String> topology = streamBuilder.stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()));
			topology.mapValues(value -> {
				return eventService.proccess(value);
			}).to(outputTopic, Produced.with(Serdes.String(), Serdes.String()));
			
			return streamBuilder;
		}
		
		return streamsBuilder;
	}

	public Properties getConfigs() {
		
		String bootstrap = configs.getProperty(AppConfigs.KAFKA_SERVER_HOST)+":"+configs.getProperty(AppConfigs.KAFKA_SERVER_PORT);
		
		Properties streamProps = new Properties();
		streamProps.put(StreamsConfig.APPLICATION_ID_CONFIG, configs.getProperty(AppConfigs.KAFKA_APP_ID));
		streamProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
		streamProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());		
		
		return streamProps;
	}
	
}
