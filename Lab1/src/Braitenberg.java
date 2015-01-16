import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class Braitenberg {
	DifferentialPilot pilot;

	public void go() {
		pilot.setTravelSpeed(10);
    	pilot.setRotateSpeed(85);
    	
		Port port = LocalEV3.get().getPort("S1");
		EV3ColorSensor light = new EV3ColorSensor(port);
		
		SampleProvider sampleProvider = light.getAmbientMode();
		int sampleSize = sampleProvider.sampleSize();
		
		float[] sample = new float[sampleSize];
		
		float currentIntensity = 0.f;
		while (true) {
			// Gets the sample an returns it
			sampleProvider.fetchSample(sample, 0);
			currentIntensity = sample[0];

			System.out.println("Search!");
			boolean left = true;
			float degree = 0.f;
			float turnRate = 5.f;
			
			while (currentIntensity < 0.15) {
				if (left) {
					degree = -degree - turnRate;
					pilot.rotate(degree);
					left = false;
				} else {
					degree = -degree + turnRate;
					pilot.rotate(degree);
					left = true;
				}

				// Gets the sample an returns it
				sampleProvider.fetchSample(sample, 0);
				currentIntensity = sample[0];
				
				if (Button.DOWN.isDown())
					break;
			}
			
			System.out.println("Foward!");
			pilot.travel(5);
			
			if (Button.DOWN.isDown())
				break;
		}
	    
	    System.out.println("Done!");
	    light.close();
	  }

	public static void main(String[] args) {
		Braitenberg traveler = new Braitenberg();
		traveler.pilot = new DifferentialPilot(5.5f, 11.8f, Motor.A, Motor.B);
		traveler.go();
	}
}
