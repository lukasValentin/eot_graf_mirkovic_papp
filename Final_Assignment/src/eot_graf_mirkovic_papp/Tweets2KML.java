package eot_graf_mirkovic_papp;

import java.io.File;
import java.io.FileWriter;
/*
 * this public class converts the extracted tweets and inserts them
 * into a KML document
 */

public class Tweets2KML {

	public static boolean WriteToKML(String SavePath, TweetData[] TweetArray) {
		
		try {
			
			  File file = new File(SavePath);   // creates the file
		   
		      file.createNewFile();
		      		     
		      FileWriter writer = new FileWriter(file);  // creates a FileWriter Object

		      String placemarkerText= ""; // 
		     /* String introKML=""; // optional layout for the start; there could be the content of lines 24-26 written
		      String endKML=""; // optional layout for the end; there could be the content of lines 39-40 written inside the quotation marks */
		      
		      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
		    		  "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\r\n"+
		    		  "<Document> \r\n"
		    		  ); // static content; which must be at the beginning (KML-structure!)
		      
		      for (int i=0; i<=TweetArray.length-1; i++) { // arrays are starting with 0, so we use i=0
		    	  	writer.write("<Placemark>\r\n" +
		    	  				"	<name>"+TweetArray[i].created_at+"</name>\r\n" +		//we take the information of our certain tweet "i" (>=0) and take the date (information) as the header of the placemarker
		    	  				"   <description>"+TweetArray[i].tweet+"</description>\r\n" + // the description gets our tweet information
		    	  			  	"	<Style id=\"sh_ylw-pushpin\"> \r\n" +
		    	  				"		<BalloonStyle>\r\n" + // we need the BalloonStyle element in order to have a better visualization (no "from/to" at the bottom)
		    	  				"			<text><h3>$[name]</h3>$[description]</text> \r\n" +
		    	  				"		</BalloonStyle> \r\n"+
		    	  				"	</Style> \r\n"+
		    	  				"   <Point>\r\n" + 
		    	  				"   	<coordinates>"+TweetArray[i].lon+", "+TweetArray[i].lat+", 0</coordinates>\r\n" + // here we need to put in our coordinates [lon, lat, height=0]
		    	  				"   </Point>\r\n" + 
		    	  				"  </Placemark>\r\n");
		      }
		      
		    writer.write("</Document> \r\n" +
		    			"</kml>" // the backend of the kml-file
		    			);
		    writer.flush(); // if there is some waste it will flush it away
		    writer.close(); // close our writer-object
			
			
			return true; // it did it, yes!
		}
		
		catch (Exception e){
			return false; // if the function/small programme encounters an error it will signalize that something went wrong
		}
		//TweetArray[0]
	}
}
