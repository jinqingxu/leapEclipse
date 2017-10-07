import com.leapmotion.leap.*;

import com.leapmotion.leap.Gesture.State;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.net.time.TimeTCPClient;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.Charset;

import java.lang.Math;

public class LeapListener extends Listener{
	public long initialTimestamp=0;
	public long initialSystemTime=0;
	public void onInitialize(Controller controller){
		
		System.out.println("Initialized");
		
		
	}
	
	public void onConnect(Controller controller){
		
		System.out.println("Connected to Motion Sensor");
		
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		/*System.out.println("arrive");
        try {
            TimeTCPClient client = new TimeTCPClient();
            try {
                // Set timeout of 60 seconds
                client.setDefaultTimeout(1000);
                // Connecting to time server
                // Other time servers can be found at : http://tf.nist.gov/tf-cgi/servers.cgi#
                // Make sure that your program NEVER queries a server more frequently than once every 4 seconds
                client.connect("nist.netservicesgroup.com");
                initialTimestamp=client.getTime();
                initialSystemTime=System.currentTimeMillis();
                
            } finally {
                client.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       System.out.println(initialTimestamp);
		System.out.println(initialSystemTime);*/
	}
	
	public void onDisconnect(Controller controller){
		
		System.out.println("Motion Sensor Disconnected");
		
	}
	//return dateTime,offsetX,offsetY,offsetZ
	public String[] readOffsetXYZ() {
		String readCSV = LeapLogin.writeLeapPath+LeapLogin.fileName;
        
        try {
        CsvReader reader = new CsvReader(readCSV, ',', Charset.forName("UTF-8"));
        
        
        ArrayList<String[]> csvList = new ArrayList<String[]>(); //store data of offsets
        
          
         // read the data of positions
        int i=0;
        String dateTime="";
        while(reader.readRecord()){ 
        	 //date
        	 if(i==1) {
        		 String[] value=reader.getValues(); //possible value:  Date: 2017/09/23
        		 String[] value2=value[0].split(":"); // 2017/09/23
        		 String rawDate=value2[1].trim(); //remove space
        		 String[] dateList=rawDate.split("/");// 2017 09 23
        		 dateTime=dateList[0]+'-'+dateList[1]+'-'+dateList[2];
        		
        	 }
        	 
        	 if(i==2) {
        		 String[] value=reader.getValues(); //possible value:  Date: 2017/09/23
        		 String time=value[0].substring(5, value[0].length());
        		 dateTime+=time;
        		 
        	 }
        	 
        	 if(i>=4) {
             csvList.add(reader.getValues());  
        	 }
             i++;
             
         }  
         // calculate the average position
         int offsetX=3;
         int offsetY=4;
         int offsetZ=5;
         
         ArrayList<Double> listX = new ArrayList<Double>(); 
         ArrayList<Double> listY = new ArrayList<Double>(); 
         ArrayList<Double> listZ = new ArrayList<Double>(); 
         
         
         
         reader.close();  
           
         for(int row=0;row<csvList.size();row++){  
               
            
        	 if (Math.abs(Double.parseDouble(csvList.get(row)[offsetX]))<5) { // if abs(x) > 5, there may be some problem with the data, so we omit that data.
        		 listX.add(Double.parseDouble(csvList.get(row)[offsetX]));
        		 listY.add(Double.parseDouble(csvList.get(row)[offsetY]));
        		 listZ.add(Double.parseDouble(csvList.get(row)[offsetZ]));
        	 }
      
         }  
         
         Double averageX=getAverage(listX);
         Double averageY=getAverage(listY);
         Double averageZ=getAverage(listZ);
         
         String[] result=new String[4];
         result[0]=dateTime;
         result[1]=Double.toString(averageX);
         result[2]=Double.toString(averageY);
         result[3]=Double.toString(averageZ);
         
         System.out.println(result[0]);
         
         return result;
         
           
        }
        
        catch(IOException e) {
        	e.printStackTrace();
        }
        String[] result=new String[4];
        return result;
        
		
	}
	public void writeOffsetXYZ(String[] result) {
		String fileName="Records_Of_StartButton_Offset.csv";
        String targetFile = LeapLogin.writePath+fileName;
        
        try {
     
        CsvWriter writer =new CsvWriter(targetFile,',',Charset.forName("UTF-8"));
        File file=new File(targetFile);
       
        if (!(file.exists())) {//new file
        	
        	  file.createNewFile();
        	  String[] header = {"DateTime","OffsetX(mm)","OffsetY(mm)","OffsetZ(mm)"};                    
          writer.writeRecord(header);
          writer.writeRecord(result);
          
        }
        else {
        	
        // write previous data
        	 CsvReader reader = new CsvReader(targetFile, ',', Charset.forName("UTF-8"));
        	  while(reader.readRecord()){  	
          writer.writeRecord(reader.getValues());  
        }
        	  writer.writeRecord(result);// write current data
        	  
        }
      
       writer.close();
     
           
        }
        
        catch(IOException e) {
        	e.printStackTrace();
        }
        
		
	}
	public Double getAverage(ArrayList<Double> l) {
		Double sumL=0.0;
		for (int i=0;i<l.size();++i) {
			sumL+=l.get(i);
		}
		return sumL/l.size();
		
	}
	public void  onExit(Controller controller){
		
		System.out.println("Exited");
		if (LeapLogin.pid.equals("8888")) {
			String[] result=readOffsetXYZ();
			writeOffsetXYZ(result);
		}
	
		
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
        	
        	FileOutputStream outPath = new FileOutputStream(new File(LeapCrossHair.writeLeapPath + LeapCrossHair.fileName),true);			
        	OutputStreamWriter out = new OutputStreamWriter(outPath);
		double speedCalculate=Math.sqrt(Math.pow(speed.getX(),2)+Math.pow(speed.getY(),2)+Math.pow(speed.getZ(),2));
		//System.out.println("speedCalculate");
		//System.out.println(speedCalculate);
		//long currentSystemTime=System.currentTimeMillis();
		//long curTimestamp=initialTimestamp+currentSystemTime-initialSystemTime;
		//System.out.println(curTimestamp);
		System.out.println(frame.id() + ","+ frame.timestamp() + ","+ System.currentTimeMillis() + ","+ tipPosition.getX()  +","+ tipPosition.getY() + "," + tipPosition.getZ() +"," + direction.getX() +","+ direction.getY() +","+ direction.getZ() + ","+ direction.yaw() +","+ direction.pitch() + ","+ direction.roll() +","+ stabilizedPosition.getX() +","+ stabilizedPosition.getY() +","+ stabilizedPosition.getZ() +","+ speed.getX()+","+ speed.getY() +"," + speed.getZ() +","+ "RotationAxisX"+","+ "RotationAxisY"+","+ "RotationAxisZ"+","+"wrist position X"+","+"wrist position Y"+","+"wrist position Z"+","+"rotation Probability" +speedCalculate+","+"\n");
		//out.write(frame.id() + ","+ frame.timestamp() + ","+ System.currentTimeMillis() + ","+ tipPosition.getX()  +","+ tipPosition.getY() + "," + tipPosition.getZ() +"," + direction.getX() +","+ direction.getY() +","+ direction.getZ() + ","+ direction.yaw() +","+ direction.pitch() + ","+ direction.roll() +","+ stabilizedPosition.getX() +","+ stabilizedPosition.getY() +","+ stabilizedPosition.getZ() +","+ speed.getX() +","+ speed.getY() +"," + speed.getZ() +","+ "RotationAxisX"+","+ "RotationAxisY"+","+ "RotationAxisZ"+","+"wrist position X"+","+"wrist position Y"+","+"wrist position Z"+","+"rotation Probability" +"\n");
		out.write(frame.id() + ","+ frame.timestamp() + ","+ System.currentTimeMillis() + ","+ tipPosition.getX()  +","+ tipPosition.getY() + "," + tipPosition.getZ() +"," + direction.getX() +","+ direction.getY() +","+ direction.getZ() + ","+ direction.yaw() +","+ direction.pitch() + ","+ direction.roll() +","+ stabilizedPosition.getX() +","+ stabilizedPosition.getY() +","+ stabilizedPosition.getZ() +","+ speed.getX() +","+ speed.getY() +"," + speed.getZ() +"," +speedCalculate+"\n");
		
		
	
		//out.write(frame.id() + ","+ frame.timestamp() + ","+ curTimestamp + ","+ tipPosition.getX()  +","+ tipPosition.getY() + "," + tipPosition.getZ() +"," + direction.getX() +","+ direction.getY() +","+ direction.getZ() + ","+ direction.yaw() +","+ direction.pitch() + ","+ direction.roll() +","+ stabilizedPosition.getX() +","+ stabilizedPosition.getY() +","+ stabilizedPosition.getZ() +","+ speed.getX() +","+ speed.getY() +"," + speed.getZ() +"," +speedCalculate+"\n");
		out.close();

		} catch (IOException e) {
				// TODO Auto-generated catch block
			System.out.println("Error writing to file: " + e);
		}   // ENd of Try-Catch

	}
	

}

