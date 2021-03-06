import java.io.IOException;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
/*class LeapListener extends Listener{
	public void onInit(Controller controller) {
		System.out.println("Initialized");
	}
	public void onConnect(Controller controller) {
		System.out.println("Connected to Motion Sensor");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
	}
	public void onDisconnect(Controller controller) {
		System.out.println("Motion Sensor Disconnected");
	}
	public void onExit(Controller controller) {
		System.out.println("Exited");
	}
	public void onFrame(Controller controller) {
		Frame frame=controller.frame();
		System.out.println("Frame id:"+frame.id()
							+", Timestamp "+frame.timestamp()
							+", Hands: "+frame.hands().count()
							+", Fingers: "+frame.fingers().count()
							+", Tools: "+frame.tools().count()
							+", Gestures: "+frame.gestures().count());
		for(Hand hand : frame.hands()) {
			String handType=hand.isLeft() ? "Left Hand" : "Right Hand";
			System.out.println(handType+ " "+", id: "+hand.id()+", Palm Position: "+hand.palmPosition());
			Vector normal=hand.palmNormal();
			Vector direction=hand.direction();

			System.out.println("Pitch: "+Math.toDegrees(direction.pitch())
			                   +" Roll: "+Math.toDegrees(normal.roll())
			                   +" Yaw: "+Math.toDegrees(direction.yaw()));
		for(Finger finger : frame.fingers()) {
			System.out.println("Finger Type: "+finger.type()
			 					+" ID: "+finger.id()
			 					+" Finger Length (mm): "+finger.length()
			 					+" Finger Width (mm): "+finger.width());
		}
		
	}
}
public class LeapController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LeapListener listener=new LeapListener();
		Controller controller=new Controller();
		controller.addListener(listener);
		System.out.println("Press enter to quit");
		try {
			System.in.read();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

}*/
