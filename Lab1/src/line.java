import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Robot that stops if it hits something before it completes its travel.
 */
public class line {
	DifferentialPilot pilot;

	public void go() {
		pilot.setTravelSpeed(5);
		pilot.travel(10, true);

		Delay.msDelay(2000);

		System.out.println(" " + pilot.getMovement().getDistanceTraveled());
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		line traveler = new line();
		traveler.pilot = new DifferentialPilot(5.5f, 11.8f, Motor.A, Motor.B);
		traveler.go();
	}
}
