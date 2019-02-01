package eot_graf_mirkovic_papp;

import org.geotools.data.ows.Layer;

import eot_graf_mirkovic_papp.WMSConnector;

/*
 * this public class holds the main method
 */

public class GoogleEarthTweetMapper {

	public static void main(String[] args) {
		
		//calls the required classes to perform the task
		
		//firstly, a WMS connection is established and the available layers are listed
		
		//URL of the WMS-Server
		String URLString = "http://129.206.228.72/cached/osm/WMSServer?REQUEST=GetCapabilities&SERVICE=WMS&VERSION=1.1.1";
		
		//array for storing the bounding box
		double[] bbox = {-71.13,42.32,-71.03,42.42};
		
		//spatial reference system
		String SRS = "EPSG:4326";
		
		//where should the result be stored (directory)?
		String storageLocation = "/daten/201819WS/SWE/";
		
		//should the result be transparent?
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
