import java.io.*;

import lejos.hardware.Bluetooth;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.remote.nxt.*;

public class KeyboardEV32 {
	public static DataOutputStream dataOut;
	public static DataInputStream dataIn;
	private static NXTConnection BTLink;
	public static int speed = 50, turnSpeed = 50, speedBuffer, speedControl;
	public static int commandValue, transmitReceived;
	public static boolean[] control = new boolean[6];
	public static boolean[] command = new boolean[6];

	public static void main(String[] args) {
		connect();

		while (!Button.DOWN.isDown()) {
			control = checkCommand();
			speedControl = getSpeed(control);
			move(control, speedControl);
		}
	}// End main

	public static boolean[] checkCommand()// check input data
	{

		try {
			transmitReceived = dataIn.readInt();

			if (transmitReceived == 1) {
				command[0] = true;
			}// forward
			if (transmitReceived == 10) {
				command[0] = false;
			}
			if (transmitReceived == 2) {
				command[1] = true;
			}// backward
			if (transmitReceived == 20) {
				command[1] = false;
			}
			if (transmitReceived == 3) {
				command[2] = true;
			}// leftTurn
			if (transmitReceived == 30) {
				command[2] = false;
			}
			if (transmitReceived == 4) {
				command[3] = true;
			}// rightTurn
			if (transmitReceived == 40) {
				command[3] = false;
			}
			if (transmitReceived == 5) {
				command[0] = false;// stop
				command[1] = false;
				command[2] = false;
				command[3] = false;
			}
			if (transmitReceived == 6) {
				command[4] = true;
			}// speed up
			if (transmitReceived == 60) {
				command[4] = false;
			}
			if (transmitReceived == 7) {
				command[5] = true;
			}// slow down
			if (transmitReceived == 70) {
				command[5] = false;
			} else {
			}
			;
		}

		catch (IOException ioe) {
			System.out.println("IO Exception readInt");
		}
		return command;

	}// End checkCommand

	public static void move(boolean[] D, int S) {
		int movingSpeed;
		boolean[] direction = new boolean[4];

		direction[0] = D[0];
		direction[1] = D[1];
		direction[2] = D[2];
		direction[3] = D[3];

		movingSpeed = S;

		Motor.A.setSpeed(movingSpeed);
		Motor.B.setSpeed(movingSpeed);

		if (direction[0] == true) {
			Motor.A.forward();
			Motor.B.forward();
		}

		if (direction[1] == true) {
			Motor.A.backward();
			Motor.B.backward();
		}

		if (direction[2] == true) {
			Motor.A.setSpeed(turnSpeed);
			Motor.B.setSpeed(turnSpeed);
			Motor.A.forward();
			Motor.B.backward();
		}

		if (direction[3] == true) {
			Motor.A.setSpeed(turnSpeed);
			Motor.B.setSpeed(turnSpeed);
			Motor.A.backward();
			Motor.B.forward();
		}

		if (direction[0] == true && direction[2] == true) {
			speedBuffer = (int) (movingSpeed * 1.5);
			
			Motor.A.setSpeed(speedBuffer);
			Motor.B.setSpeed(movingSpeed);
			Motor.B.forward();
			Motor.A.forward();
		}

		if (direction[0] == true && direction[3] == true) {
			speedBuffer = (int) (movingSpeed * 1.5);
			
			Motor.A.setSpeed(movingSpeed);
			Motor.B.setSpeed(speedBuffer);
			Motor.B.forward();
			Motor.A.forward();
		}

		if (direction[1] == true && direction[2] == true) {
			speedBuffer = (int) (movingSpeed * 1.5);

			Motor.A.setSpeed(speedBuffer);
			Motor.B.setSpeed(movingSpeed);
			Motor.B.backward();
			Motor.A.backward();
		}

		if (direction[1] == true && direction[3] == true) {
			speedBuffer = (int) (movingSpeed * 1.5);

			Motor.A.setSpeed(movingSpeed);
			Motor.B.setSpeed(speedBuffer);
			Motor.B.backward();
			Motor.A.backward();
		}

		if (direction[0] == false && direction[1] == false
				&& direction[2] == false && direction[3] == false) {
			Motor.A.setSpeed(0);
			Motor.B.setSpeed(0);
			Motor.A.stop();
			Motor.B.stop();
		}

	}// End move

	public static int getSpeed(boolean[] D) {
		boolean accelerate, decelerate;

		accelerate = D[4];
		decelerate = D[5];

		if (accelerate == true) {
			speed += 50;
			command[4] = false;
		}

		if (decelerate == true) {
			speed -= 50;
			command[5] = false;
		}

		return speed;
	}// End getSpeed

	public static void connect() {
		System.out.println("Listening");
		BTLink = Bluetooth.getNXTCommConnector().waitForConnection(10000,
				NXTConnection.RAW);
		dataIn = BTLink.openDataInputStream();
		System.out.println("Connected :')");
	}

}// NXTtr Class

