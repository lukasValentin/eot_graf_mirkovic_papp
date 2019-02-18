package eot_graf_mirkovic_papp;

/*
 * this public class reads in Twitter data in CSV file format
 * and extracts the coordinates and the columns "tweet" and "created_at"
 * this information is required to create the kml files
 * --> Save csv-tweet-data into an array of TweetData (please mind the Tweets2KML.java-structure)
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CSVParser {
	
	// the array lists for temporarily storing the extracted tweets
    // including the coordinates, the tweet content and the creation time
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static ArrayList<String> lng = new ArrayList();
    private static ArrayList<String> lat = new ArrayList();
    private static ArrayList<String> tweet = new ArrayList();
    private static ArrayList<String> createdAt = new ArrayList();
    
    // number of lines (=number of tweets) used for storing the data
    private static int nTweets;
	
	// method to read from the csv file
	public static TweetData[] getTweets(String csvFile) throws FileNotFoundException {
		
		// first of all, we have to read from the csv file with the tweets
		// therefore, a buffered file reader is employed
		BufferedReader br = null;
		
		// temporarily, the data will be stored in an array list
		// each line is read as a single string
		String line = "";
		
		// the semicolon separates the values
        String cvSplitVBy = ";";
		
        // now we can try to read from the csv file
        try {
        	
        	br = new BufferedReader(new FileReader(csvFile));
        	
        	// integer for counting the lines in the file
            int i = 0;
            
            // loop over the file line by line until end of file (eof)
            while ((line = br.readLine()) != null){
                
                String[] values = line.split(cvSplitVBy);
                
                // grap the desired columns from the line string
                lng.add(values[1]);
                lat.add(values[2]);
                tweet.add(values[5]);
                createdAt.add(values[6]);
                
                // increase line counter
                ++i;
            }
            
            // now we know the number of tweets
            // (it is i minus 1 because there is also one header line)
            nTweets = i -1;
        	
        } catch (IOException e) {
        	
        	JOptionPane.showMessageDialog(null,"Could not read from the CSV file",
        			"IO-Error", JOptionPane.ERROR_MESSAGE);
        	
        	e.printStackTrace();
        	
        } finally {
        	
        	// close the buffered reader afterwards
        	if (br != null) {
        		
        		try {
        			
					br.close();
					
				} catch (IOException e) {
					
					JOptionPane.showMessageDialog(null,"Error when trying to close the file reader",
		        			"IO-Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					
				
				} // end try-catch
        	
        	} // end if
        	
        } //end finally
        
		// for storing the extracted Tweets a TweetData-array (see TweetData class)
		// is used to read the content from the csv and write it into a kml structure
		TweetData[] tweetData = new TweetData[nTweets];
		
		String inTweet = "";
		String created_at = "";
		double latDouble = 0.;
		double lngDouble = 0.;
		
		TweetData tweetForKML = null;
		
		// loop over the array lists to convert to TweetData format
		// start at one to avoid to convert the header line
		int jj = 0; //second counter variable for the tweetData array
		for (int ii=1; ii < nTweets + 1; ii++) {
			
			// parse into the required data types
			inTweet = tweet.get(ii);
			created_at = createdAt.get(ii);
			
			// the coordinates are required as double
			latDouble = Double.parseDouble(lat.get(ii));
			lngDouble = Double.parseDouble(lng.get(ii));
			
			// now, we can call the constructor of TweetData
			tweetForKML = new TweetData(
					inTweet, created_at, lngDouble, latDouble);
			
			// add to the TweetData array and increase counter
			tweetData[jj] = tweetForKML;
			
			++jj;
			
		} // end for
		
		// now we are finished
		return (tweetData);
			
	} //end method
	
}
