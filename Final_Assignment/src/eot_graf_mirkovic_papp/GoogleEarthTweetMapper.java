package eot_graf_mirkovic_papp;


import org.geotools.data.ows.Layer;

import eot_graf_mirkovic_papp.WMSConnector;

/*
 * this public class holds the main method
 */

public class GoogleEarthTweetMapper {

	public static void main(String[] args) {
		
		//calls the required classes to perform the task
		graphicalUserInterface gui = new graphicalUserInterface();
		gui.runGUI();
	
	}
	
}

