package eot_graf_mirkovic_papp;

import java.io.IOException;

/*
 * this public class takes the KML files containing the GroundOverlay as well as the
 * Tweets and visualizes them using Google Earth Engine
 */

public class StartGoogleEarth {
	
	public static void startUpGoogleEarth
	(String groundOverlayKML, String singleTweetsKML) throws IOException {
		
		// first, we need the Google Earth Binary
		String googleEarthExecutable = getGoogleEarthBinary();
		
		String terminalCmd = googleEarthExecutable + " "
				+ singleTweetsKML + " "
				+ groundOverlayKML;
		
		// now we can insert into the terminal / DOS-box
		// therefore, we have to start a new process
		Runtime.getRuntime().exec(terminalCmd);
		
	}
	
	//method to determine the Google-Earth binary depending on the OS
	private static String getGoogleEarthBinary() {
		
		//under Ubuntu 18.04 this is very simple
		final String GoogleEarthUnix = "google-earth-pro";
		
		//and some more ugly Windows stuff
		final String GoogleEarthWin = 
				"C:\\Program Files\\Google\\Google Earth Pro\\client\\googleearth";
		
		String osName = System.getProperty("os.name");
		
		if (osName.equalsIgnoreCase("Win")) {
			return GoogleEarthWin;
		} else if (osName.equalsIgnoreCase("Linux")){
			return GoogleEarthUnix;	
		} else {
			return "";
		}
		
	}
	

}
