package eot_graf_mirkovic_papp;

/*
 * this public class holds the main method
 * 
 * authors -> classes:
 * Lukas Graf		-> grahicalUserInterface and WMSConnector
 * Stefan Mirkovic	-> Img2GroundOverlay and Tweets2KML
 * Levente Papp		->
 * 
 * GoogleEarthMapper is a collection of Java classes to
 * 1.) 	connect to the WMS-Server and download a rendered map for a selected
 * 		region as a png image file
 * 2.) 	include the downloaded image file in a kml structure as well as
 * 		tweets extracted from a CSV file
 * 3.) 	automatically start GoogleEarth with the generated kml files for displaying
 * 		the results
 * 
 * uses geotools 18.4
 * 
 */

public class GoogleEarthTweetMapper {

	public static void main(String[] args) {
		
		//calls the required classes to perform the task
		
		//firstly, the graphicalUserInterface class is called in order to
		//run the GUI for user input collection and WMS GetMap retrieval
		graphicalUserInterface gui = new graphicalUserInterface();
		gui.runGUI();
		
		/*String FilePath = "C:\\Users\\Stefan\\git\\eot_graf_mirkovic_papp\\Final_Assignment\\auxiliary\\testing.kml"; // destination path
		String ImageSource = "C:\\Users\\Stefan\\git\\eot_graf_mirkovic_papp\\Final_Assignment\\auxiliary\\wfs.png"; 
		String name = "test file";
		String description = "did it work?"; 
		double north = 40.95; 
		double south = 40.56; 
		double east = -73.63;
		double west = -74.10;
		Img2GroundOverlay.WriteToKML(FilePath, ImageSource, name, description, north, south, east, west);*/
	
	}
	
}

