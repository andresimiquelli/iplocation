package com.andresimiquelli.iplocation;

import java.util.Properties;

import com.andresimiquelli.iplocation.contracts.StreamProcessor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        App app = new App();
        app.start();
    }
    
    private void start() {
    	Properties configs = AppConfigs.get();
    	StreamProcessor streamProcessor = new DefaultStreamProcessor(configs);
    	streamProcessor.start();
    }
}
