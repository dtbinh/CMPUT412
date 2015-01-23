import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Robot that stops if it hits something before it completes its travel.
 */
public class StraightLine {
	DifferentialPilot pilot;

	public void go() {
		pilot.setTravelSpeed(5);
		pilot.travel(40, true);
		
		Delay.msDelay(10000);

		
		System.out.println("Left: " + Motor.A.getTachoCount());
		System.out.println("Right: " + Motor.B.getTachoCount());
		
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		StraightLine traveler = new StraightLine();
		traveler.pilot = new DifferentialPilot(5.5f, 11.8f, Motor.A, Motor.B);
		traveler.go();
	}
}
