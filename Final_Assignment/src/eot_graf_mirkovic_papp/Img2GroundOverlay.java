package eot_graf_mirkovic_papp;
import java.io.FileWriter;
import java.io.File;
/*
 * this public class takes the result of the WMS-Request (stored locally on the
 * computer as rendered image) and inserts it as KML GroundOverlay Element into a
 * KML-conform structure
 */

public class Img2GroundOverlay {

	public static boolean WriteToKML(String FilePath, String ImageSource, String name, String description, double north, double south, double east, double west) // no ";" because it's a method! 	
	{		//"static" means that I don't need an extra object in order to call the method; "boolean" because we want to know whether the operation was successful									
			//Inside the WriteToKML()-brackets we need to put in all dynamic content 
		try
		{
			File file = new File(FilePath); // "File" creates a file object
			file.createNewFile(); 			// creates a new File at the FilePath
			FileWriter writer = new FileWriter(file); // The FileWriter is able to write into a file and at the constructor 
			
			writer.write( // writer.write shall write our typical kml-structure (for GroundOverlays)!
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"+
					"<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\r\n"+
					"<GroundOverlay> \r\n"+
					"<name>"+name+"</name> \r\n"+
					"<description>"+description+"</description> \r\n"+
					"<gx:balloonVisibility>1</gx:balloonVisibility> \r\n"+
					"<color>4dffffff</color> \r\n"+ //Opacity-value "alpha" ("alpha"RGB-syntax) --> 30% of 255 = 4d (HEX)
					"<Icon> \r\n"+
					"	<href>"+ImageSource+"</href> \r\n"+
					"</Icon> \r\n"+
					"<LatLonBox> \r\n"+
					"	<north>"+north+"</north> \r\n"+
					"	<south>"+south+"</south> \r\n"+
					"	<east>"+east+"</east> \r\n"+
					"	<west>"+west+"</west> \r\n"+
					"</LatLonBox> \r\n"+
					"</GroundOverlay> \r\n"+
					"</kml> \r\n"
					);
			writer.close();
			return true; // return value is true, fine!
		}
		
		catch (Exception e) 
		{
			return false; // return value is false = default
		}
	}
	// I'll need something like this: path variable, boundingbox variable, title/<name> variable, description variable
	
	/*
	 * Sample Code (Salzburg is used)
	 * 
	 * <?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2" xmlns:gx="http://www.google.com/kml/ext/2.2" xmlns:kml="http://www.opengis.net/kml/2.2" xmlns:atom="http://www.w3.org/2005/Atom">
<GroundOverlay>
	<name>Stadt Salzburg</name>
	<description>ich bin die Stadtteile Salzburgs.</description>
	<Icon>
		<href>https://upload.wikimedia.org/wikipedia/commons/0/00/Salzburger_Stadtteile.jpg</href>
		<viewBoundScale>0.75</viewBoundScale>
	</Icon>
	<LatLonBox>
		<north>47.85589438482411</north>
		<south>47.75037379569893</south>
		<east>13.12769948076074</east>
		<west>12.98322792797626</west>
	</LatLonBox>
</GroundOverlay>
</kml>*/
}
