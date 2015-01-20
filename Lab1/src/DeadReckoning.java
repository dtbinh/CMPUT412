import lejos.hardware.Button;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
import lejos.utility.Delay;

/**
 * Robot that stops if it hits something before it completes its travel.
 */
public class DeadReckoning {
	int[][] command = {
		      { 80, 60, 2},
		      { 60, 60, 1},
		      {-50, 80, 2}
		    };
	
	EncoderMotor _left;
	EncoderMotor _right;
	
	float _wheelDiameter;
	float _distancePerTick;
	float _trackWidth;
	
	public DeadReckoning(final double wheelDiameter, final double trackWidth,
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

	public void go() {
		_left.resetTachoCount();
		_right.resetTachoCount();
		
		int leftTachoCount = _left.getTachoCount();
		int rightTachoCount = _right.getTachoCount();
		
		float heading = 0.f;
		float x = 0.f;
		float y = 0.f;
		
		for (int i = 0; i < 3; i++) {
			
			_left.setPower(command[i][0]);
			_right.setPower(command[i][1]);
			
			_left.forward();
			_right.forward();
			
			for (int delay = 0; delay < command[i][2]*100; delay++) {
				int newLeftTachoCount = _left.getTachoCount() - leftTachoCount;
				int newRightTachoCount = _right.getTachoCount() - rightTachoCount;
				
				leftTachoCount = _left.getTachoCount();
				rightTachoCount = _right.getTachoCount();
				
				float distance = ((newLeftTachoCount + newRightTachoCount) / 2.f) * _distancePerTick;
				float ticksPerRotation = (float) (Math.PI * _trackWidth) / _distancePerTick;
				float radiansPerTick = (float) (2.f * Math.PI) / ticksPerRotation;
				float changeInHeading = (float) (newRightTachoCount - newLeftTachoCount) * (radiansPerTick / 2);
				
				heading += changeInHeading;
				x += distance*Math.cos(heading);
				y += distance*Math.sin(heading);
				
				Delay.msDelay(10);
			}
			
			_left.stop();
			_right.stop();
		}
		
		System.out.println("Heading: " + (180/Math.PI)*heading % 360);
		System.out.println("Position: (" + x + ", " + y + ")");
		
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		DeadReckoning traveler = new DeadReckoning(
				5.5, 
				11.8, 
				new NXTMotor (MotorPort.A), 
				new NXTMotor (MotorPort.B));
		traveler.go();
	}
}
