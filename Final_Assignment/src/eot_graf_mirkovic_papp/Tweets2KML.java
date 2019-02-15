package eot_graf_mirkovic_papp;

import java.io.File;
import java.io.FileWriter;
/*
 * this public class converts the extracted tweets and inserts them
 * into a KML document
 */

public class Tweets2KML {

	public static boolean WriteToKML(TweetData[] TweetArray) { 
		try {
			  File file = new File(FilePath);
		      
		      // creates the file
		      file.createNewFile();
		      
		     
		      FileWriter writer = new FileWriter(file);  // creates a FileWriter Object

		      String placemarkerText= ""; // 
		      

		      
		    

			
			
			return true; // it did it, yes!
		}
		
		catch (Exception e){
			return false; // if the function/small programme encounters an error it will signalize that something went wrong
		}
		//TweetArray[0]
	}
}
