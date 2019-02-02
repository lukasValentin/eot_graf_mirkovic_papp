package eot_graf_mirkovic_papp;

import org.geotools.data.ows.Layer;

import eot_graf_mirkovic_papp.WMSConnector;
import eot_graf_mirkovic_papp.graphicalUserInterface;

/*
 * this public class holds the main method
 */

public class GoogleEarthTweetMapper {

	public static void main(String[] args) {
		
		//calls the required classes to perform the task
		
		graphicalUserInterface gui = new graphicalUserInterface();
		gui.runGUI();
		
		/*
		 * now, we can extract the user-entered values from the GUI
		 */
		
		//URL of the WMS-Server
		String URLString = gui.tfURL.getText();
		//Bounding Box (xmin,ymin,xmax,ymax)
		String bbox = gui.tfBbox.getText();
		//spatial reference system
		String SRS = gui.tfSRS.getText();
		//csv-file with tweets
		String tweetFile = gui.chosenFile.getText();
		//storage location
		String storageLocation = gui.chosenLoc.getText();
		
		//output image of WMS-Request should be transparent
		boolean transparent = true;
		
		//dimensions of the output image					
		String[] imageDimensions = {"250", "250"};
		
		// call the constructor of the WMS-Connector class and instantiate an object of this class
		WMSConnector con = new WMSConnector
				(URLString, bbox, SRS, storageLocation, transparent, imageDimensions);
		
		Layer[] layers = con.getLayerList();
		
		//TEST!!!
		
		int status = con.retrieveImageFromWMS(layers[3]);
		
		System.out.println(status);

	}

}
