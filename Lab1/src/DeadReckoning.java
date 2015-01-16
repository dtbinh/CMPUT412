import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

/**
 * Robot that stops if it hits something before it completes its travel.
 */
public class DeadReckoning {
	DifferentialPilot pilot;
	int[][] command = {
		      { 80, 60, 2},
		      { 60, 60, 1},
		      {-50, 80, 2}
		    };
	
	RegulatedMotor _left;
	RegulatedMotor _right;
	
	float _leftWheelDiameter, _leftTurnRatio, _leftDegPerDistance;
	float _rightWheelDiameter, _rightTurnRatio, _rightDegPerDistance;
	
	float _trackWidth;
	
	public DeadReckoning(final double leftWheelDiameter,
final double rightWheelDiameter, final double trackWidth,
final RegulatedMotor leftMotor, final RegulatedMotor rightMotor)
	{
		_left = leftMotor;
		_leftWheelDiameter = (float) leftWheelDiameter;
		_leftTurnRatio = (float) (trackWidth / leftWheelDiameter);
		_leftDegPerDistance = (float) (360 / (Math.PI * leftWheelDiameter));
		// right
		_right = rightMotor;
		_rightWheelDiameter = (float) rightWheelDiameter;
		_rightTurnRatio = (float) (trackWidth / rightWheelDiameter);
		_rightDegPerDistance = (float) (360 / (Math.PI * rightWheelDiameter));
		// both
		_trackWidth = (float) trackWidth; 
	}
	
	private void setSpeed(final int leftSpeed, final int rightSpeed) {
		_left.setSpeed(leftSpeed);
		_right.setSpeed(rightSpeed);
	} 

	public void go() {
		
		for (int i = 0; i < 3; i++) {
			_left.resetTachoCount();
			_right.resetTachoCount();
			
			setSpeed((int) Math.round(command[i][0] * _leftDegPerDistance),
					(int) Math.round(command[i][1] * _rightDegPerDistance));
			
			_left.forward();
			_right.forward();
			
			Delay.msDelay(command[i][2]*1000);
			
			_left.stop();
			_right.stop();
			
			float leftDistance = _left.getTachoCount() * _leftDegPerDistance;
			float rightDistance = _left.getTachoCount() * _rightDegPerDistance;
		}

		
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		DeadReckoning traveler = new DeadReckoning(5.5, 5.5, 11.8, Motor.A, Motor.B);
		traveler.go();
	}
}
