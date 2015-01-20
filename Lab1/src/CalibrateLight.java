import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;


public class CalibrateLight {
	private static float getLightIntensity(EV3ColorSensor light) {
		SampleProvider sampleProvider = light.getAmbientMode();
		int sampleSize = sampleProvider.sampleSize();
		
		float[] sample = new float[sampleSize];
		
		// Gets the sample an returns it
		sampleProvider.fetchSample(sample, 0);
		return sample[0];
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		Port port1 = LocalEV3.get().getPort("S1");
		EV3ColorSensor leftLight = new EV3ColorSensor(port1);
		
		float min = Float.POSITIVE_INFINITY;
		float max = Float.NEGATIVE_INFINITY;
		while (!Button.DOWN.isDown()) {
			float leftIntensity = getLightIntensity(leftLight);
			
			if (leftIntensity < min)
				min = leftIntensity;
			
			if (leftIntensity > max)
				max = leftIntensity;
		}
		
		PrintWriter writer = new PrintWriter("calibration.txt", "UTF-8");
		writer.println(min);
		writer.println(max);
		writer.close();
		
		leftLight.close();
	}
}
