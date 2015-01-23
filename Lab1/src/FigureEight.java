import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;


public class FigureEight {
	DifferentialPilot pilot;

	public void go() {		
		pilot.setTravelSpeed(15);
		pilot.steer(80, 360);
		pilot.steer(-80, -360);
		
		Button.waitForAnyPress();
	}

	public static void main(String[] args) {
		FigureEight traveler = new FigureEight();
		traveler.pilot = new DifferentialPilot(5.5f, 12.f, Motor.A, Motor.B);
		traveler.go();
	}
}
