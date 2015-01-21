import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.EncoderMotor;
import lejos.robotics.SampleProvider;
import lejos.hardware.motor.NXTMotor;
import lejos.utility.TextMenu;

public class Braitenberg {

	float _minThreshold = 0.1f;
	float _maxThreshold = 0.7f;
	
	EncoderMotor _left;
	EncoderMotor _right;
	
	float _wheelDiameter;
	float _distancePerTick;
	float _trackWidth;
	
	public Braitenberg(final double wheelDiameter, final double trackWidth,
final EncoderMotor leftMotor, final EncoderMotor rightMotor)
	{
		// left
		_left = leftMotor;
		// right
		_right = rightMotor;
		// both
		_wheelDiameter = (float) wheelDiameter;
		_distancePerTick= (float) ((Math.PI * wheelDiameter) / 360);
		_trackWidth = (float) trackWidth; 
	}

	private float getLightIntensity(EV3ColorSensor light) {
		SampleProvider sampleProvider = light.getAmbientMode();
		int sampleSize = sampleProvider.sampleSize();
		
		float[] sample = new float[sampleSize];
		
		// Gets the sample an returns it
		sampleProvider.fetchSample(sample, 0);
		return sample[0];
	}
	
	public void coward() {
		Port port1 = LocalEV3.get().getPort("S1");
		EV3ColorSensor leftLight = new EV3ColorSensor(port1);
		
		Port port2 = LocalEV3.get().getPort("S2");
		EV3ColorSensor rightLight = new EV3ColorSensor(port2);
		
		while (true) {
			float leftIntensity = getLightIntensity(leftLight);
			float rightIntensity = getLightIntensity(rightLight);
			
			if (leftIntensity > _minThreshold || rightIntensity > _minThreshold) {
				int leftPower = (int) (200*leftIntensity);
				int rightPower = (int) (200*rightIntensity);
				
				_left.setPower(leftPower);
				_right.setPower(rightPower);
				
				_left.forward();
				_right.forward();
			}
			
			if (Button.DOWN.isDown())
				break;
		}
		
		_left.stop();
		_right.stop();
	    
	    leftLight.close();
	    rightLight.close();
	}
	
	public void aggresive() {
		Port port1 = LocalEV3.get().getPort("S1");
		EV3ColorSensor leftLight = new EV3ColorSensor(port1);
		
		Port port2 = LocalEV3.get().getPort("S2");
		EV3ColorSensor rightLight = new EV3ColorSensor(port2);
		
		while (true) {
			float leftIntensity = getLightIntensity(leftLight);
			float rightIntensity = getLightIntensity(rightLight);
			
			if (leftIntensity > _minThreshold || rightIntensity > _minThreshold) {
				
				int leftPower = (int) (200*rightIntensity);
				int rightPower = (int) (200*leftIntensity);
				
				_left.setPower(leftPower);
				_right.setPower(rightPower);
				
				_left.forward();
				_right.forward();
			}
			
			if (Button.DOWN.isDown())
				break;
		}
		
		_left.stop();
		_right.stop();
	    
	    leftLight.close();
	    rightLight.close();
	}
	
	public void love() {
		Port port1 = LocalEV3.get().getPort("S1");
		EV3ColorSensor leftLight = new EV3ColorSensor(port1);
		
		Port port2 = LocalEV3.get().getPort("S2");
		EV3ColorSensor rightLight = new EV3ColorSensor(port2);
		
		while (true) {
			float leftIntensity = getLightIntensity(leftLight);
			float rightIntensity = getLightIntensity(rightLight);
			
			if ((leftIntensity > _minThreshold || rightIntensity > _minThreshold)
					&& (leftIntensity < _maxThreshold && rightIntensity < _maxThreshold)) {
				
				int leftPower = (int) (100 - 100*leftIntensity);
				int rightPower = (int) (100 - 100*rightIntensity);
				
				if (leftPower < 0)
					_left.stop();
				else
				{
					_left.setPower(leftPower);
					_left.forward();
				}
				
				if (rightPower < 0)
					_right.stop();
				else
				{
					_right.setPower(rightPower);
					_right.forward();
				}
			} else {
				_left.stop();
				_right.stop();
			}
			
			if (Button.DOWN.isDown())
				break;
		}
		
		_left.stop();
		_right.stop();
	    
	    leftLight.close();
	    rightLight.close();
	}
	
	public void explore() {
		Port port1 = LocalEV3.get().getPort("S1");
		EV3ColorSensor leftLight = new EV3ColorSensor(port1);
		
		Port port2 = LocalEV3.get().getPort("S2");
		EV3ColorSensor rightLight = new EV3ColorSensor(port2);
		
		while (true) {
			float leftIntensity = getLightIntensity(leftLight);
			float rightIntensity = getLightIntensity(rightLight);
			
			if ((leftIntensity > _minThreshold || rightIntensity > _minThreshold)
					&& (leftIntensity < _maxThreshold && rightIntensity < _maxThreshold)) {
				
				int leftPower = (int) (100 - 100*leftIntensity);
				int rightPower = (int) (100 - 100*rightIntensity);
				
				if (leftPower < 0)
					_right.stop();
				else
				{
					_right.setPower(leftPower);
					_right.forward();
				}
				
				if (rightPower < 0)
					_left.stop();
				else
				{
					_left.setPower(rightPower);
					_left.forward();
				}
			} else {
				_left.stop();
				_right.stop();
			}
			
			if (Button.DOWN.isDown())
				break;
		}
		
		_left.stop();
		_right.stop();
	    
	    leftLight.close();
	    rightLight.close();
	}

	public static void main(String[] args) throws IOException {
		Braitenberg traveler = new Braitenberg(
				5.5, 
				11.8, 
				new NXTMotor (MotorPort.A), 
				new NXTMotor (MotorPort.B));
		
		String[] items = {"coward", "aggresive", "love", "explore"};
		TextMenu menu = new TextMenu(items);
		int selected = menu.select();
		
		switch (selected) {
		case 0:
			traveler.coward();
			break;
		case 1:
			traveler.aggresive();
			break;
		case 2:
			traveler.love();
			break;
		case 3:
			traveler.explore();
			break;
		}
		
	}
}
