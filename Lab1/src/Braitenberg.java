import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Braitenberg {
	public static void main(String[] args) throws Exception {
		Port port = LocalEV3.get().getPort("S1");
		EV3ColorSensor light = new EV3ColorSensor(port);
		
		SampleProvider sampleProvider = light.getAmbientMode();
		int sampleSize = sampleProvider.sampleSize();
		
		float[] sample = new float[sampleSize];
		
        for (int i = 0; i < 200; i++) {
        	 // Gets the sample an returns it
        	sampleProvider.fetchSample(sample, 0);
        	System.out.println(i + ": " + sample[0]);
        	
        	Delay.msDelay(100);
        }

		Button.waitForAnyPress();

		light.close();
	}
}
