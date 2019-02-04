package eot_graf_mirkovic_papp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.geotools.data.wms.request.GetMapRequest;
import org.geotools.data.wms.response.GetMapResponse;
import org.geotools.data.ows.Layer;
import org.geotools.data.ows.WMSCapabilities;
import org.geotools.data.wms.WMSUtils;
import org.geotools.data.wms.WebMapServer;
import org.geotools.ows.ServiceException;

import jdk.internal.org.xml.sax.SAXException;

/*
 * this public class connects to a WMS service 
 * and retrieves a basemap within a predifined bounding
 * box and stores it locally on the computer
 */

public class WMSConnector {
	
	/* ----------------------------------------------------------------
	 * 
	 * 			Define the global variables for the class
	 * 
	   ---------------------------------------------------------------*/ 
	
	// define codes that will determine whether a WMS-Connection was established successfully or not
	final int errorCode = -1;
	final int succCode = 0;
	
	// class variables required to connect to WMS-Server and store result
	String URLString;							//URL of the WMS-Server
	String bbox;								//array for storing the bounding box
	
	String SRS;									//spatial reference system
	String storageLocation;						//where should the result be stored (directory)?
	boolean transparent;						//should the result be transparent?
	String[] imageDimensions = new String[2];	//dimensions of the output image
	
	/* ----------------------------------------------------------------
	 * 
	 * 			Define the constructors for the class
	 * 
	   ---------------------------------------------------------------*/ 
	
	// the standard constructor
	WMSConnector() {
		
		URLString = "";				//URL is empty per default
		
		bbox = "";					//bbox is empty per default
		
		SRS = "EPSG:4326";			//SRS is WGS-84 per default
		
		//default storage location will depend on the operating system
		String os = System.getProperty("os.name");
		
		if (os.startsWith("Windows")) {
			storageLocation = "C:\\User\\";	//Windows
		} else {
			storageLocation = "/home/";		//Linux, Mac
		}
		
		transparent = false;		//transparency is switched off per default
		
		imageDimensions[0] = "250";	//image-size x is 250 per default
		imageDimensions[1] = "250"; //image-size y is 250 per default
		
	}
	
	//the more advanced class constructor -> allows for user-defined inputs
	WMSConnector
		(String URLString_, String bbox_, String SRS_, String storageLocation_, boolean transparent_,
				String[] imageDimensions_) {
		
		URLString = URLString_;				//URL
		
		bbox = bbox_;						//bounding box
		
		SRS = SRS_;							//SRS
		
		storageLocation = storageLocation_;	//where to store the result?
		
		transparent = transparent_;			//transparency is switched off per default
		
		// size of the output image (x,y) in pixels
		imageDimensions[0] = imageDimensions_[0];
		imageDimensions[1] = imageDimensions_[1];
		
	}
	
	/* ----------------------------------------------------------------
	 * 
	 * 			Define the getter/setter for the class
	 * 
	   ---------------------------------------------------------------*/ 
	
	//see the actual WMS parameters
	public void getWMSConnectionParams() {
		
		System.out.println("URL:        " + URLString);
		System.out.println("Bounding Box Extents:");
		System.out.println(bbox);
		System.out.println("SRS:        " + SRS);
		System.out.println("Image-Size: " + imageDimensions[0] + " , " + imageDimensions[1]);
		System.out.println("Output-Dir: " + storageLocation);
		
	}
	
	// set WMS parameters
	public void setWMSConnectionParams
	(String URLString_, String bbox_, String SRS_, String storageLocation_, boolean transparent_,
			String[] imageDimensions_) {
		
		URLString = URLString_;				//URL
		
		bbox = bbox_;						//bounding box
		
		SRS = SRS_;							//SRS
		
		storageLocation = storageLocation_;	//where to store the result?
		
		transparent = transparent_;			//transparency is switched off per default
		
		// size of the output image (x,y) in pixels
		imageDimensions[0] = imageDimensions_[0];
		imageDimensions[1] = imageDimensions_[1];
	
	}
	
	/* ----------------------------------------------------------------
	 * 
	 * 			Define the methods for the class
	 * 
	   ---------------------------------------------------------------*/ 
	
	//method to get all layers that are provided by a WMS in a list
	public Layer[] getLayerList() {
		
		//open connection to wmsServer
		WebMapServer wmsServer = null;
		try {
			wmsServer = connectWMS();
		} catch (SAXException e) {
			JOptionPane.showMessageDialog(
					null,"Unable to request from WMS-Server!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		//sent getCapabilities request to server
		WMSCapabilities capabilities = wmsServer.getCapabilities();
		
		//get a list of all layers that are available
		Layer[] availableLayers = WMSUtils.getNamedLayers(capabilities);
		
		return availableLayers;
	}
	
	//method to connect to WMS-Server (opens a connection for a given URL) -> is private
	//returns a WebMapServer object
	private WebMapServer connectWMS() throws SAXException {
		
		//setup the server connection using URL
		URL url = null;
				
		//surround by try-catch in case URL to WMS-Server is not working
		try {
			url = new URL(URLString);
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(
					null,"The URL you provided is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
				
		//now a WMS object is created using the provided URL
		WebMapServer wms = null;
		try {
			wms = new WebMapServer(url);
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(
					null,"The WMS-Service connection returned an error!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					null,"There was an IOError with the WMS-Service!", "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		//return the WMS-object
		return wms;
		
	}
	
	//method to retrieve an image of the requested layer in a user-defined region from a given WMS-Server
	public int retrieveImageFromWMS(Layer layerName_) {
		
		//open the WMS connection
		WebMapServer wmsServer = null;
		try {
			wmsServer = connectWMS();
		} catch (SAXException e2) {
			System.out.println("Unable to request from WMS-Server!");
			e2.printStackTrace();
		}
		
		//setup the WMS request parameters
		
		// a GetMap-Request object is created using the established WMS connection
		GetMapRequest mapRequest = wmsServer.createGetMapRequest();
		
		//define version
		mapRequest.setVersion("1.3.0");
		
		// define the output format -> png (is fixed for all requests)
		mapRequest.setFormat("image/png");
		
		// set the dimensions of the returned image
		mapRequest.setDimensions(imageDimensions[0], imageDimensions[1]);
		
		// should the image be transparent?
		mapRequest.setTransparent(transparent);
		
		// SRS must be specified
		mapRequest.setSRS(SRS);
		
		// setup the bounding box -> therefore the double array must be converted to a single string
		// therefore the private method getSRSAsString is called
		mapRequest.setBBox(bbox);
		
		// set the desired layer into the request
		mapRequest.addLayer(layerName_);
		
		
		System.out.println(SRS + layerName_.getTitle());
		
		// now, as all parameters of the request are specified, the response could be requested and read as image
		GetMapResponse response = null;
		
		//surround with try and catch
		try {
			response = (GetMapResponse) wmsServer.issueRequest(mapRequest);
		} catch (ServiceException | IOException e1) {
			JOptionPane.showMessageDialog(
					null,"The server response could not be retrieved for the given WMS-Request!",
					"Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		
		// use ImageIO to read the response of the server as image
		BufferedImage responseAsImg = null;
		
		//surround with try and catch
		try {
			responseAsImg = ImageIO.read(response.getInputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					null,"The server response could not be converted to an image stream!",
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		//last, store the image on the disk of the computer
		File outputfile = new File(storageLocation + "WMS_Response.png");
		
		//surround with try-catch
		try {
			ImageIO.write(responseAsImg, "png", outputfile);
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(
					null,"Failed to save WMS response as image file!",
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			
		}
		
		//return 0 if everything was fine
		return succCode;
		
	}
	

}
