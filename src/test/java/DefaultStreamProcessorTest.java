import java.util.Date;
import java.util.Properties;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.json.JSONException;
import org.json.JSONObject;

import com.andresimiquelli.iplocation.AppConfigs;
import com.andresimiquelli.iplocation.DefaultStreamProcessor;
import com.andresimiquelli.iplocation.contracts.StreamProcessor;
import junit.framework.TestCase;

public class DefaultStreamProcessorTest extends TestCase {

	public void testStream() {
		
		Properties configs = AppConfigs.get("test");
		String inputTopic = configs.getProperty(AppConfigs.INPUT_TOPIC);
		String outputTopic = configs.getProperty(AppConfigs.OUTPUT_TOPIC);
		Date date = new Date();
		
		StreamProcessor streamProcessor = new DefaultStreamProcessor(configs);
		
		StreamsBuilder builder = streamProcessor.getBuilder();
		
		Properties streamProps = streamProcessor.getConfigs();
		
		Topology topology = builder.build();
		
		try (TopologyTestDriver testDriver = new TopologyTestDriver(topology, streamProps)) {
			
			JSONObject event = new JSONObject();
			event.put("clientId", "102030");
			event.put("timestamp", date.getTime());
			event.put("ip", "34.224.241.47");
			
			TestInputTopic<String, String> eventTopic = testDriver.createInputTopic(
				    inputTopic,
				    new StringSerializer(),
				    new StringSerializer());
			
			eventTopic.pipeInput("key", event.toString());
			
			TestOutputTopic<String, String> locationTopic = testDriver.createOutputTopic(
					outputTopic, 
					new StringDeserializer(),
					new StringDeserializer());
			
			JSONObject outEvent = new JSONObject(locationTopic.readValue());
			
			assertEquals("34.224.241.47", outEvent.getString("ip"));
			assertEquals("United States", outEvent.getString("country"));
			
			System.out.println(outEvent.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
