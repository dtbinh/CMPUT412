import lejos.hardware.Button;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
import lejos.utility.Delay;
public class test4 {

	// Move the robot in a straight line.
	public static void main(String[] args) {

		EncoderMotor motorA =new NXTMotor (MotorPort.A);
		EncoderMotor motorB =new NXTMotor (MotorPort.B);
		
		motorA.resetTachoCount();
		motorB.resetTachoCount();
		
		System.out.println("Motor A tachometer:" + motorA.getTachoCount());
		System.out.println("Motor B tachometer:" + motorB.getTachoCount());
		
		motorA.setPower(50);
		motorB.setPower(20);
		
		motorA.forward();
		motorB.forward();
		
		Delay.msDelay(7900);
		
		motorA.stop();
		motorB.stop();
		
		System.out.println("Motor A t:" + motorA.getTachoCount());
		System.out.println("Motor B t:" + motorB.getTachoCount());

		motorA.resetTachoCount();
		motorB.resetTachoCount();
		
		//System.out.println(motorA.getTachoCount()-motorA.getTachoCount());
		Delay.msDelay(2000);
		Button.waitForAnyPress();
		
	}

}