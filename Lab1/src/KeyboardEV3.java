import java.io.*;

import lejos.hardware.Bluetooth;
import lejos.hardware.Button;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.remote.nxt.*;
import lejos.robotics.EncoderMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class KeyboardEV3 {
	DifferentialPilot pilot;

	public static DataOutputStream dataOut;
	public static DataInputStream dataIn;
	//public static USBConnection USBLink;
	private static NXTConnection BTLink;
	public static int speed = 50, turnSpeed = 50, speedBuffer, speedControl;
	public static int commandValue, transmitReceived;
	public static boolean[] control = new boolean[6];
	public static boolean[] command = new boolean[6];

	public static void main(String[] args) {
		KeyboardEV3 traveler = new KeyboardEV3();
		traveler.connect();
		traveler.moveBrick();
		
	}// End main

	public void connect() {
		System.out.println("Listening");
		BTLink = Bluetooth.getNXTCommConnector().waitForConnection(10000, NXTConnection.RAW);
		dataIn = BTLink.openDataInputStream();
		System.out.println("Connected :')");
	}
	
	@SuppressWarnings("resource")
	public void moveBrick(){
		int powerA = 0;
		int powerB = 0;
		int addedPowerA=0;
		int addedPowerB=0;
		
		EncoderMotor motorA =new NXTMotor (MotorPort.A);
		EncoderMotor motorB =new NXTMotor (MotorPort.B);
		motorA.resetTachoCount();
		motorB.resetTachoCount();
		
		
		while (!Button.DOWN.isDown()) {

			try {
				powerA = 0;
				powerB = 0;
				addedPowerA=0;
				addedPowerB=0;
				
				int data = dataIn.readInt();
				
				if (data==10){
					powerA = 25;
					powerB = 25;
				}
				if (data==20){
					powerA = -25;
					powerB = -25;
				}
				if (data==30){ //left
					addedPowerA=-25;
					addedPowerB=25;
				}
				if (data==40){ //right
					addedPowerA=25;
					addedPowerB=-25;
				}
				
				int totalA=powerA+addedPowerA;
				int totalB=powerB+addedPowerB;
				
				if(totalA<0){
					motorA.setPower(-totalA);
					motorA.backward();
				}else{
					motorA.setPower(totalA);
					motorA.forward();
				}
				
				if(totalB<0){
					motorB.setPower(-totalB);
					motorB.backward();
				}else{
					motorB.setPower(totalB);
					motorB.forward();
				}
				
				
				Delay.msDelay(100);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			motorA.stop();
			motorB.stop();
		}
		
	}

}// NXTtr Class

