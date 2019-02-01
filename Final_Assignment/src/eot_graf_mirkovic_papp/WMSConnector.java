package eot_graf_mirkovic_papp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.geotools.data.wms.request.GetMapRequest;
import org.geotools.data.wms.WebMapServer;
import org.geotools.ows.ServiceException;

/*
 * this public class connects to a WMS service 
 * and retrieves a basemap within a predifined bounding
 * box and stores it locally on the computer
 */

public class WMSConnector {
	
	
	public int connect2WMS(String URLString_, double[] bbox_) {
		
		//setup the server connection using URL
		URL url = null;
		
		//surround by try-catch in case URL to WMS-Server is not working
		try {
			 url = new URL(URLString_);
		} catch (MalformedURLException e) {
			  System.out.println("The URL you provided is invalid!");
			  e.printStackTrace();
		}
		
		//now a WMS object is created using the provided URL
		WebMapServer wms = null;
		try {
			wms = new WebMapServer(url);
		} catch (ServiceException e) {
			System.out.println("The WMS-Service connection returned an error!");
			e.printStackTrace();
		} catch (IOException e1) {
			System.out.println("There was an IOError with the WMS-Service!");
			e1.printStackTrace();
		}
				
		// a GetMap-Request object is created using the established wms connection
		GetMapRequest mapRequest = wms.createGetMapRequest();
	}
	

}
