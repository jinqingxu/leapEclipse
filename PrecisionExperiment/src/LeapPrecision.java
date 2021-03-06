import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import com.leapmotion.leap.*;
import java.io.File;  
public class LeapPrecision {
	
	static String fileName;
	static String readPath = "/Users/irene/Documents/McGillUni/ACT_Research_Lab/Experiments/Motion Tracking Study/Experiment Data/Data from LEAP";	
	static String writePath = "/Users/irene/Documents/McGillUni/ACT_Research_Lab/Experiments/Motion Tracking Study/Leap Accuracy/Precision Data/";
	static String writeLeapPath="";
	static String pid;
	// to create a directory for an experiment
	public static boolean createDir(String destDirName) {  
	        File dir = new File(destDirName);  
	        if (!destDirName.endsWith(File.separator)) {  
	            destDirName = destDirName + File.separator;  
	        }  
	         
	        if (dir.mkdirs()) {  
	            
	            return true;  
	        } else {  
	            System.out.println("fail to mkdir");  
	            return false;  
	        }  
	    }  
	public static void main(String [] args){
		
        String date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());  // get current Date
        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());    // get current Time


		Scanner s = new Scanner(System.in);
		System.out.println("Participant ID:");

		pid =s.nextLine();
		fileName = "PID_"+ pid +"_Data_from_LEAPtest_results_Frame.csv";
	    writeLeapPath=writePath;
		writeLeapPath=writeLeapPath+"PID_"+pid;
		createDir(writeLeapPath);
		writeLeapPath+='/';
		
		System.out.println("frame ID"+ ","+"timestamp"+","+ "systemTime" + ","+"tipPositionX "+","+"tipPositionY"+","+"tipPositionZ"+"," +"directionUnitX "+","+"directionUnitY"+","+"directionUnitZ"+ ","+ "Yaw"+","+"Pitch"+","+"Roll"+","+ "stabilizedPositionX "+","+ "stabilizedPositionY "+","+ "stabilizedPositionZ "+","+"speedX"+","+ "speedY "+","+ "speedZ "+","+"boneDistal1X"+","+ "boneDistal1Y "+","+ "boneDistal1Z "+","+"boneDistal2X"+","+ "boneDistal2Y "+","+ "boneDistal2Z "+","+"DistalDirUnitX"+","+"DistalDirUnitY"+","+"DistalDirUnitZ"+","+"Distal Width"+","+"boneIntermediate1X"+","+ "boneIntermediate1Y "+","+ "boneIntermediate1Z "+","+"boneIntermediate2X"+","+ "boneIntermediate2Y "+","+ "boneIntermeidiate2Z "+","+"IntermediateDirUnitX"+","+"IntermediateDirUnitY"+","+"IntermediateDirUnitZ"+","+"Intermediate Width"+","+"boneProximal1X"+","+ "boneProximal1Y "+","+ "boneProximal1Z "+","+"boneProximal2X"+","+ "boneProximal2Y "+","+ "boneProximal2Z "+","+"ProximalDirUnitX"+","+"ProximalDirUnitY"+","+"ProximalDirUnitZ"+","+"Proximal Width"+","+ "RotationAxisX"+","+ "RotationAxisY"+","+ "RotationAxisZ"+","+"wrist position X"+","+"wrist position Y"+","+"wrist position Z"+","+"rotation Probability" +"\n");

		// Write the Header of the Experiment Data Files
		
		try {
						
			BufferedWriter	out = new BufferedWriter(new FileWriter(writeLeapPath + fileName));
			
            out.write("Participant ID: " + pid+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" ");  // avoid ArrayIndexOutOfBoundsException Error when reading file and analyzing File  (here to AQ)
            out.write('\n');
            out.write("Date: " + date+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" ");
            out.write('\n');
            out.write("Time: " + time+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" "+","+" ");
            out.write('\n');
            out.write('\n');

			//out.write("frame ID"+ ","+"timestamp"+","+ "systemTime" + ","+"tipPositionX "+","+"tipPositionY"+","+"tipPositionZ"+"," +"directionUnitX "+","+"directionUnitY"+","+"directionUnitZ"+ ","+ "Yaw"+","+"Pitch"+","+"Roll"+","+ "stabilizedPositionX "+","+ "stabilizedPositionY "+","+ "stabilizedPositionZ "+","+"speedX"+","+ "speedY "+","+ "speedZ "+","+"boneDistal1X"+","+ "boneDistal1Y "+","+ "boneDistal1Z "+","+"boneDistal2X"+","+ "boneDistal2Y "+","+ "boneDistal2Z "+","+"DistalDirUnitX"+","+"DistalDirUnitY"+","+"DistalDirUnitZ"+","+"Distal Width"+","+"boneIntermediate1X"+","+ "boneIntermediate1Y "+","+ "boneIntermediate1Z "+","+"boneIntermediate2X"+","+ "boneIntermediate2Y "+","+ "boneIntermeidiate2Z "+","+"IntermediateDirUnitX"+","+"IntermediateDirUnitY"+","+"IntermediateDirUnitZ"+","+"Intermediate Width"+","+"boneProximal1X"+","+ "boneProximal1Y "+","+ "boneProximal1Z "+","+"boneProximal2X"+","+ "boneProximal2Y "+","+ "boneProximal2Z "+","+"ProximalDirUnitX"+","+"ProximalDirUnitY"+","+"ProximalDirUnitZ"+","+"Proximal Width"+","+ "RotationAxisX"+","+ "RotationAxisY"+","+ "RotationAxisZ"+","+"wrist position X"+","+"wrist position Y"+","+"wrist position Z"+","+"rotation Probability" +"\n");
            out.write("frame ID"+ ","+"timestamp"+","+ "systemTime" + ","+"tipPositionX "+","+"tipPositionY"+","+"tipPositionZ"+"," +"directionUnitX "+","+"directionUnitY"+","+"directionUnitZ"+ ","+ "Yaw"+","+"Pitch"+","+"Roll"+","+ "stabilizedPositionX "+","+ "stabilizedPositionY "+","+ "stabilizedPositionZ "+","+"speedX(mm/s)"+","+ "speedY(mm/s) "+","+ "speedZ(mm/s) "+","+"speedCalculated"+","+"boneDistal1X"+","+ "boneDistal1Y "+","+ "boneDistal1Z "+","+"boneDistal2X"+","+ "boneDistal2Y "+","+ "boneDistal2Z "+","+"DistalDirUnitX"+","+"DistalDirUnitY"+","+"DistalDirUnitZ"+","+"Distal Width"+","+"boneIntermediate1X"+","+ "boneIntermediate1Y "+","+ "boneIntermediate1Z "+","+"boneIntermediate2X"+","+ "boneIntermediate2Y "+","+ "boneIntermeidiate2Z "+","+"IntermediateDirUnitX"+","+"IntermediateDirUnitY"+","+"IntermediateDirUnitZ"+","+"Intermediate Width"+","+"boneProximal1X"+","+ "boneProximal1Y "+","+ "boneProximal1Z "+","+"boneProximal2X"+","+ "boneProximal2Y "+","+ "boneProximal2Z "+","+"ProximalDirUnitX"+","+"ProximalDirUnitY"+","+"ProximalDirUnitZ"+","+"Proximal Width"+","+ "RotationAxisX"+","+ "RotationAxisY"+","+ "RotationAxisZ"+","+"wrist position X"+","+"wrist position Y"+","+"wrist position Z"+","+"rotation Probability" +"\n");
		    out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error writing to file: " + e);
		} 

		LeapListener listener = new LeapListener();     // Initialize a new Leap Listener to collect data from Leap Motion
		Controller controller = new Controller();		// Initialize a new Controller to collect data from Leap Motion
		controller.addListener(listener);      			// Add listener to the controller
		
		
		System.out.println("Press Enter to Quit");
		
		try{
			System.in.read();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		controller.removeListener(listener);
		
	}


}

