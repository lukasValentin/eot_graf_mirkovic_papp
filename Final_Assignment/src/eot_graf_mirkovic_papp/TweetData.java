package eot_graf_mirkovic_papp;

// this class contains data of one tweet
// the purpose of this class is to merge coordinates and the columns "tweet" & "created_at" = kind of container for ONE tweet
public class TweetData {
	
	public String tweet;
	public String created_at;
	public double lon;
	public double lat;
	
	public TweetData(String inTweet, String inCreated_at, double inLon, double inLat) {
		
		// constructor for TweetData which initializes all necessary data/input
		
		tweet = inTweet;
		created_at = inCreated_at;
		lon = inLon;
		lat = inLat;
		
	}
	
}