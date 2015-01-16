import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class SquareTracer
{
    DifferentialPilot pilot ;
    
    public void  drawSquare(float length)
    {
    	pilot.setTravelSpeed(15);
    	pilot.setRotateSpeed(35);
    	
        for(int i = 0; i<4 ; i++)
        {
            pilot.travel(length);
            pilot.rotate(90);                 
        }
        
        System.out.println(" " + pilot.getMovement().getDistanceTraveled());
		Button.waitForAnyPress();
    }
    
    public static void main(String[] args)
    {
        SquareTracer sq = new SquareTracer();
        sq.pilot = new DifferentialPilot(5.5f, 11.8f, Motor.A, Motor.B);
        sq.drawSquare(10);
    }
}