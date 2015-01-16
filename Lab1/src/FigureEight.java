import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;


public class FigureEight {
	DifferentialPilot pilot;

	public void go() {
		System.out.println(" " + pilot.getMovement().getAngleTurned());
		
		pilot.setTravelSpeed(15);
		pilot.steer(60, 370);
		
		System.out.println("First circle");
		//Delay.msDelay(5000);
		//pilot.reset();
		pilot.steer(-60, -370);
		
		System.out.println("second circle");
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		FigureEight traveler = new FigureEight();
		traveler.pilot = new DifferentialPilot(5.5f, 11.8f, Motor.A, Motor.B);
		traveler.go();
	}
}
