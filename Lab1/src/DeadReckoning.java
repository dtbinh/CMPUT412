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

	public void go() {
		EncoderMotor motorA  = new NXTMotor (MotorPort.A);
		EncoderMotor motorB  = new NXTMotor (MotorPort.B);
		
		for (int i = 0; i < 3; i++) {
			motorA.resetTachoCount();
			motorB.resetTachoCount();
			
			motorA.setPower(command[i][0]);
			motorB.setPower(command[i][1]);
			
			motorA.forward();
			motorB.forward();
			
			Delay.msDelay(command[i][2]*1000);
			
			motorA.stop();
			motorB.stop();
		}

		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		DeadReckoning traveler = new DeadReckoning();
		traveler.go();
	}
}
