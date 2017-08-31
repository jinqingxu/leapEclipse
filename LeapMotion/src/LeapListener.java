import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class LeapListener extends Listener{
		
	public void onInitialize(Controller controller){
		
		System.out.println("Initialized");
		
	}
	
	public void onConnect(Controller controller){
		
		System.out.println("Connected to Motion Sensor");
		
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		
	}
	
	public void onDisconnect(Controller controller){
		
		System.out.println("Motion Sensor Disconnected");
		
	}
	
	public void  onExit(Controller controller){
		
		System.out.println("Exited");
		
	}
	
	public void onFrame(Controller controller){
		
		Frame frame = controller.frame();
		//Tool tool=frame.tools().frontmost();
		
		Hand hand = frame.hands().rightmost();   // Get the right hand
	    Finger index = frame.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0); //(fingerType() returns an array since there could be more than one finger of a particular type in the list, which is why you need the get(0).)
	
	    Vector tipPosition = null;
	    Vector direction = null, stabilizedPosition = null, speed = null;
	    
	    if(index.isValid()){ 
	    //tipPosition=tool.tipPosition();
	    	 tipPosition = index.tipPosition(); //the instantaneous position in mm from the Leap Motion origin.	    		    	 
	    	 direction = index.direction(); //the current pointing direction vector,The direction is expressed as a unit vector pointing in the same direction as the tip.
	    	 //direction=tool.direction();
	    	 //stabilizedPosition=tool.stabilizedTipPosition();
	    	 //speed=tool.tipVelocity();
	    	 stabilizedPosition = index.stabilizedTipPosition(); //Smoothing and stabilization is performed in order to make this value more suitable for interaction with 2D content. The stabilized position lags behind the tip position by a variable amount, depending primarily on the speed of movement.
	    	 // A modified tip position of this Pointable object with some additional smoothing and stabilization applied.
	    	  speed = index.tipVelocity();//the instantaneous velocity in mm/s.
	    }
		
		//todo
	    //timestamp means frame's timestamp
		//System.out.println(frame.id() + ","+ frame.timestamp() + ","+ System.currentTimeMillis() + ","+ tipPosition.getX()  +","+ tipPosition.getY() + "," + tipPosition.getZ() +"," + direction.getX() +","+ direction.getY() +","+ direction.getZ() + ","+ direction.yaw() +","+ direction.pitch() + ","+ direction.roll() +","+ stabilizedPosition.getX() +","+ stabilizedPosition.getY() +","+ stabilizedPosition.getZ() +","+ speed.getX() +","+ speed.getY() +"," + speed.getZ() +","+ "RotationAxisX"+","+ "RotationAxisY"+","+ "RotationAxisZ"+","+"wrist position X"+","+"wrist position Y"+","+"wrist position Z"+","+"rotation Probability" +"\n");

	    // Write the Leap Motion data in the Experiment Data file
	    
		try {
			
        	//File file = new File(LeapLogin.writePath + LeapLogin.fileName);
        	//FileOutputStream outPath = new FileOutputStream(file,true);
        	
        	FileOutputStream outPath = new FileOutputStream(new File(LeapLogin.writePath + LeapLogin.fileName),true);			
        	OutputStreamWriter out = new OutputStreamWriter(outPath);
		double speedCalculate=Math.sqrt(Math.pow(speed.getX(),2)+Math.pow(speed.getY(),2)+Math.pow(speed.getZ(),2));
		//System.out.println("speedCalculate");
		//System.out.println(speedCalculate);
		System.out.println(frame.id() + ","+ frame.timestamp() + ","+ System.currentTimeMillis() + ","+ tipPosition.getX()  +","+ tipPosition.getY() + "," + tipPosition.getZ() +"," + direction.getX() +","+ direction.getY() +","+ direction.getZ() + ","+ direction.yaw() +","+ direction.pitch() + ","+ direction.roll() +","+ stabilizedPosition.getX() +","+ stabilizedPosition.getY() +","+ stabilizedPosition.getZ() +","+ speed.getX()+","+ speed.getY() +"," + speed.getZ() +","+ "RotationAxisX"+","+ "RotationAxisY"+","+ "RotationAxisZ"+","+"wrist position X"+","+"wrist position Y"+","+"wrist position Z"+","+"rotation Probability" +speedCalculate+","+"\n");
		//out.write(frame.id() + ","+ frame.timestamp() + ","+ System.currentTimeMillis() + ","+ tipPosition.getX()  +","+ tipPosition.getY() + "," + tipPosition.getZ() +"," + direction.getX() +","+ direction.getY() +","+ direction.getZ() + ","+ direction.yaw() +","+ direction.pitch() + ","+ direction.roll() +","+ stabilizedPosition.getX() +","+ stabilizedPosition.getY() +","+ stabilizedPosition.getZ() +","+ speed.getX() +","+ speed.getY() +"," + speed.getZ() +","+ "RotationAxisX"+","+ "RotationAxisY"+","+ "RotationAxisZ"+","+"wrist position X"+","+"wrist position Y"+","+"wrist position Z"+","+"rotation Probability" +"\n");
		out.write(frame.id() + ","+ frame.timestamp() + ","+ System.currentTimeMillis() + ","+ tipPosition.getX()  +","+ tipPosition.getY() + "," + tipPosition.getZ() +"," + direction.getX() +","+ direction.getY() +","+ direction.getZ() + ","+ direction.yaw() +","+ direction.pitch() + ","+ direction.roll() +","+ stabilizedPosition.getX() +","+ stabilizedPosition.getY() +","+ stabilizedPosition.getZ() +","+ speed.getX() +","+ speed.getY() +"," + speed.getZ() +"," +speedCalculate+"\n");
		out.close();

		} catch (IOException e) {
				// TODO Auto-generated catch block
			System.out.println("Error writing to file: " + e);
		}   // ENd of Try-Catch

	}
	

}

