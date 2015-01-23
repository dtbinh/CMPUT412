import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class RotateTest2
{
    DifferentialPilot pilot ;
    
    public void  drawSquare(float length)
    {
    	pilot.setTravelSpeed(15);
    	pilot.setRotateSpeed(35);
    	
    	pilot.rotate(90);
        Button.waitForAnyPress();
    }
    
    public static void main(String[] args)
    {
        RotateTest2 sq = new RotateTest2();
        sq.pilot = new DifferentialPilot(5.5f, 12.f, Motor.A, Motor.B);
        sq.drawSquare(25);
    }
}