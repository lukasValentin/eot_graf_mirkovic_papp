package eot_graf_mirkovic_papp;

import java.io.IOException;

import org.ejml.ops.EjmlUnitTests.TestException;

/*
 * this public class contains some auxiliary stuff
 */
public class auxClass {
	
	//this method converts the bbox string to a double array with four elements
	public static double[] bbox2array (String bbox_) throws IOException, TestException {
		
		double[] bboxArray = new double[4];
		
		//the bbox is inserted by a user  via the GUI manually in the form
		//xmin,ymin,xmax,ymax -> this string will be split and transformed into a double array
		
		//firstly, a String array is created by splitting the bbox_ string at the commas
		String[] bboxStringArray = bbox_.split(",");
		
		//secondly, each element of the string array is converted to double
		for (int ii=0; ii<4; ii++) {
			bboxArray[ii] = Double.parseDouble(bboxStringArray[ii]);
		}
		
		return (bboxArray);
		
	}

}
