import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Robot that stops if it hits something before it completes its travel.
 */
public class Circle {
	DifferentialPilot pilot;

	public void go() {
		System.out.println(" " + pilot.getMovement().getAngleTurned());
		
		pilot.setTravelSpeed(15);
		pilot.steer(60, 370);
		
		System.out.println(" " + pilot.getMovement().getAngleTurned());
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		Circle traveler = new Circle();
		traveler.pilot = new DifferentialPilot(5.5f, 11.8f, Motor.A, Motor.B);
		traveler.go();
	}
}
