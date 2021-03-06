package eot_graf_mirkovic_papp;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.ejml.ops.EjmlUnitTests.TestException;
import org.geotools.data.ows.Layer;

import eot_graf_mirkovic_papp.WMSConnector;

/*
 * this public class extends the JDialog class in order
 * to provide a graphical user interface for enhanced
 * user interaction
 * 
 * it also holds the main method for running the whole program
 * 
 * the user is asked to enter the URL of the WMS-Server,
 * the spatial reference system, the extent of the bounding box
 * as well as the location of the CSV file containing the twitter data
 * and where to store the image resulting from the WMS getMap request
 * 
 * it also calls directly the WMSConnector class as also the selection
 * of the desired layer is based on a user selection
 */

public class GoogleEarthTweetMapper extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	//setup up the GUI elements -> are private
	private JPanel contentPanel;
	private JTextField tfURL;
	private JTextField tfSRS;
	private JTextField tfBbox;
	private JButton chooseButton;
	private JTextArea chosenFile;
	private JButton openButton;
	private JTextArea chosenLoc;
	
	//the values the user enters are public as they are accessed by other
	//other classes (WMSConnector)
	public String URLString;
	public String SRS;
	public String bbox;
	public String tweetFile;
	public String storageLocation;
	
	public int rowIndex;
	
	//specify the frame of the GUI
	//therefore, the constructor of graphicalUserInterface is used
	//remember, that the class inherits everything from JFrame
	
	GoogleEarthTweetMapper() {
		
		//window should be resizable
		this.setResizable(true);
		
		//title of the dialog
		this.setTitle("GoogleEarthTweetMapper");
		
		//the way the dialog could be closed
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//bounds of the frame
		this.setBounds(100,100,500,600);
		
		//create a content panel
		contentPanel = new JPanel();
		
		//specify how the content panel should look like
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		//now, the single elements for user-input are added
		
		// WMS-Server URL
		//label for URL of WMS-Server
		JLabel lblURL = new JLabel("URL of WMS-Server");
		lblURL.setBounds(10, 11, 157, 15);
		//add it to the content panel
		contentPanel.add(lblURL);		
		//text field to insert the URL
		tfURL = new JTextField();
		tfURL.setBounds(10,31,400,25);
		contentPanel.add(tfURL);
		
		// SRS-input (according to WMS-Server URL)
		JLabel lblSRS = new JLabel("SRS (e.g: EPSG:4326)");
		lblSRS.setBounds(10, 71, 157, 17);
		contentPanel.add(lblSRS);
		tfSRS = new JTextField();
		tfSRS.setBounds(10, 91, 157,25);
		contentPanel.add(tfSRS);
		
		// Bounding Box
		JLabel lblBbox = new JLabel("Extent of bounding box (xmin,ymin,xmax,ymax)");
		lblBbox.setBounds(10, 131, 357, 17);
		contentPanel.add(lblBbox);
		tfBbox = new JTextField();
		tfBbox.setBounds(10, 151, 400, 25);
		contentPanel.add(tfBbox);
		
		// where to find the twitter *.csv data
	    // therefore, a file chooser is necessary
	    JFileChooser csvFinder = new JFileChooser();
	    csvFinder.setCurrentDirectory(new java.io.File("."));
	    csvFinder.setDialogTitle("Please select the CSV-file with the Tweets");
	    //as only csv files are allowed set a filter
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
	    //apply the filter
	    csvFinder.setFileFilter(filter);
	    
	    //create a label as usual
	    JLabel lblTweetsFile = new JLabel("CSV file with tweets");
	    lblTweetsFile.setBounds(10, 191, 357, 17);
	    contentPanel.add(lblTweetsFile);
	    //instead of a text field a button is required here
	    chooseButton = new JButton("Select ...");
	    chooseButton.setBounds(10,211,157,25);
		
		// also add an text field to show the chosen directory to the user
		chosenFile = new JTextArea();
		chosenFile.setBounds(10, 251, 400, 25);
	    
		// add an action listener here to allow for more complex user interaction
		chooseButton.addActionListener(new ActionListener() {
			
		// implementation of the abstract method actionPerformed of abstract
		// class ActionListener
		@Override
		public void actionPerformed(ActionEvent e) {

			    if (e.getSource() == chooseButton) {
			    	
			    	//if button is pressed the file chooser is called
			    	int res = csvFinder.showOpenDialog(GoogleEarthTweetMapper.this);
			    	
			    	//return the chosen directory to the user
			    	File file = null;
			    	if (res == JFileChooser.APPROVE_OPTION) {
			            
			    		file = csvFinder.getSelectedFile();
			    	}
			    	
			    	//return the whole path
			    	chosenFile.append(file.getPath());
			    	
			    	} 

				} // end actionPerformed
			
		}); //end action Listener
		
		contentPanel.add(chooseButton);
		contentPanel.add(chosenFile);
		// Storage Location
		// this operation requires to use a file chooser
	    JFileChooser fc = new JFileChooser();
	    // setup a startup directory
	    fc.setCurrentDirectory(new java.io.File("."));
	    fc.setDialogTitle("Please select a directory");
	    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    // remove the unnecessary file filter
	    fc.setAcceptAllFileFilterUsed(false);
	    
	    //create a label as usual
		JLabel lblStorageLoc = new JLabel("Directory to store the results");
		lblStorageLoc.setBounds(10, 291, 357, 17);
		contentPanel.add(lblStorageLoc);
		
		// instead of a text field a button is required here
		openButton = new JButton("Choose ...");
		openButton.setBounds(10,311,157,25);
		
		// also add an text field to show the chosen directory to the user
		chosenLoc = new JTextArea();
		chosenLoc.setBounds(10, 341, 400, 25);
		
		// add an action listener here to allow for more complex user interaction
		openButton.addActionListener(new ActionListener() {
		
		// implementation of the abstract method actionPerformed of abstract
		// class ActionListener
		@Override
		public void actionPerformed(ActionEvent e) {

			    if (e.getSource() == openButton) {
			    	
			    	//if button is pressed the file chooser is called
			    	int res = fc.showOpenDialog(GoogleEarthTweetMapper.this);
			    	
			    	//return the chosen directory to the user
			    	File file = null;
			    	if (res == JFileChooser.APPROVE_OPTION) {
			            
			    		file = fc.getSelectedFile();
			    	}
			    	
			    	//return the whole path
			    	chosenLoc.append(file.getPath());
			    	
			    	} 

				} // end actionPerformed
			
		}); //end action Listener
	
		// add to content panel
		contentPanel.add(openButton);
		contentPanel.add(chosenLoc);
		
		// now, a "go" and "cancel" button are required
		
		JButton goButton = new JButton("Start");
		goButton.setBounds(10, 450, 80, 30);
		
		// when start is pressed then a message window opens to inform the user
		goButton.addActionListener(new ActionListener () {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource() == goButton) {
					
						//close the window after the button was pressed
						GoogleEarthTweetMapper.this.dispose();
						
						//run the next step
						callWMSConnector();
	
				} //end if
				
		  } //end actionPerformed
			
		}); // end add actionListener
		
	
		JButton endButton = new JButton("Cancel");
		endButton.setBounds(100,450,80,30);
		
		//when cancel is pressed then the program is terminated and the GUI closed
		endButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					System.exit(0);
					
				}
				
		});
			
		contentPanel.add(goButton);
		contentPanel.add(endButton);
	
	}
	// end of constructor
	
	/*
	 * Begin of the main method
	 */
	
	//method to run the GUI -> it is the main method 
	public static void main (String args[]) {
	
		//setup a specific look and feel -> surround with try-catch
		try {
	
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	
		} catch (Throwable t) {
	
			t.printStackTrace();
	
		}

		//now, a new thread should be created to allow to run the program
		EventQueue.invokeLater(new Runnable() {
	
			public void run() {
		
				//try-catch
				try {
			
					//call the class constructor to create the frame
					GoogleEarthTweetMapper gui = new GoogleEarthTweetMapper();
					//set visible
					gui.setVisible(true);
					
					
				} catch (Exception ex){
					
					JOptionPane.showMessageDialog(null, "An error occurred while requesting data", "Error",
							JOptionPane.ERROR_MESSAGE);
					
					ex.printStackTrace();
			
				} //end try catch
				
		
			} //end run()

		}); //end runnable

	} // end runGUI()
	
	//private method to call the WMS-Connector
	private void callWMSConnector() {
		
		//collect the user-inputs from the GUI
		URLString = tfURL.getText();
		
		bbox = tfBbox.getText();
		SRS = tfSRS.getText();
		storageLocation = chosenLoc.getText();
		tweetFile = chosenFile.getText();
		
		//output image of WMS-Request should be transparent
		boolean transparent = true;
				
		//dimensions of the output image -> user input??				
		String[] imageDimensions = {"250", "250"};
						
		// call the constructor of the WMS-Connector class and instantiate an object of this class
		WMSConnector con = new WMSConnector
						(URLString, bbox, SRS, storageLocation, 
								transparent, imageDimensions);
		
		// get a list of all available layers from the WMS
		Layer[] layers = con.getLayerList();
		
		//now one layer must be chosen from all layers available from the WMS
		//therefore, a table is used
		JTable layerTable = new JTable();
		layerTable.setFillsViewportHeight(true);
		
		//setup a table model
		//column names
		final String[] colNames = {"Layer-Number", "Layer-Name"};
		
		TableModel layerTableModel = new DefaultTableModel(
				new String[layers.length][colNames.length], colNames);
		
		layerTable.setModel(layerTableModel);
		
		//write content to the table
		//write down the number and the title of the layers available
		for (int ii=0; ii<layers.length; ii++) {
			
			String layerName = (String) layers[ii].getTitle();
			
			String rowNumber = new Integer(ii).toString();
			layerTable.getModel().setValueAt(rowNumber, ii, 0);
			layerTable.getModel().setValueAt(layerName, ii, 1);
			
		}
		
		//it is required to select a row but not a column
		layerTable.setRowSelectionAllowed(true);
        layerTable.setColumnSelectionAllowed(false);
        
        //a selection tool for a certain row is required to allow for user-defined layer selection
        JButton selectLayer = new JButton("Select");
        
        //row Index of the selected row (required to determine the selected layer)
        rowIndex = 0;
        
        //an actionListener is required again
        selectLayer.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	
            	if (e.getSource() == selectLayer) {
            	
            		//we need the number of the selected row
            		int selRow = layerTable.getSelectedRow();
            		int selCol = 0; //column with the layer number -> is used for selection

            		try {
            			rowIndex = Integer.parseInt((String) layerTable.getValueAt(selRow, selCol));
            		} catch (NumberFormatException numEx) {
            			JOptionPane.showMessageDialog(null, 
            					"The layer selection failed!", "Error", JOptionPane.WARNING_MESSAGE);
            			numEx.printStackTrace();
            		}
                
            		//handle out of bound exceptions
            		if (rowIndex < 0 || rowIndex >= layerTable.getRowCount()) {
            			JOptionPane.showMessageDialog(layerTable, "Selection out of range!");
            		} 
            		
            		// call the next method to continue
            		retrieveImgFromWMS(con, layers[selRow]);
            		
            	}
            
            }
            
        });
        
		
		//the table will be shown in a new window
        JPanel layerPanel = new JPanel();
        layerPanel.setBounds(10,10,250,300);
        JScrollPane scrollPane = new JScrollPane(layerTable);
        
        //add the table and the button to the panel
        layerPanel.add(scrollPane);
		layerPanel.add(selectLayer);
		
		//set the dimensions and display the window
		UIManager.put("showMessageDialog.minimumSize",new Dimension(300,400)); 
		JOptionPane.showMessageDialog(null, layerPanel, "Select a layer", JOptionPane.PLAIN_MESSAGE);
       
		
	} // end method
	
	//method to retrieve an image as the result of the WMS getMap request
	private void retrieveImgFromWMS(WMSConnector wmsConn_, Layer layer_) {
		
		//call the retrieveImageFromWMS method from the WMSConnector class
		int status = 0;
		
		try {
			status = wmsConn_.retrieveImageFromWMS(layer_);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Retrieval of image from WMS failed!",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		
		//if status is zero everything's fine -> let the user know
		if (status == 0) {
			
			JOptionPane.showMessageDialog(null, "Retrieval of image from WMS successfull!",
					"Good News", JOptionPane.PLAIN_MESSAGE);
			
			//now, it is possible to do the next steps
			doNextSteps();
			
		}
		
	}
	
	//method to call Img2GroundOverlay
	private void doNextSteps() {
		
		//after running the GUI we have all the user inputs as well as the WMS-Server
		//response necessary to perform the next steps
		//a ground-overlay is created using the WMS-Server response
		
		//the WMS-Server response is stored at storageLocation
		String imageSource = storageLocation
				+ (String) System.getProperty("file.separator")
				+ "WMS_Response.png";
		
		//we need a name for the kml structure (will be stored at user-def. storageLocation)
		String kmlFilePath = storageLocation
				+ (String) System.getProperty("file.separator") 
				+ "groundOverlayTweets.kml";
		
		//some content of the kml must be set
		String nameOfOverlay = "Basemap";
		
		//add a short description
		String description = "This is an automatically created description. You can change it manually if you like.";
		
		//also the coordinates of the bounding box are required
		//therefore, we use the method bbox2array from the auxClass
		double[] bboxArray = new double[4];
		
		//surround with try-catch in case something goes wrong
		try {
			
			bboxArray = auxClass.bbox2array(bbox);
			
		} catch (IOException | TestException exeption) {
			
			JOptionPane.showMessageDialog(null,"The bounding box is invalid", 
					"Error", JOptionPane.ERROR_MESSAGE);
			exeption.printStackTrace();
			
		}
		
		//now the kml groundOverlay can be created
		boolean success = Img2GroundOverlay.WriteToKML(
				kmlFilePath, imageSource, nameOfOverlay, description, 
				bboxArray[3], bboxArray[1], bboxArray[2], bboxArray[0]);
		
		//the methods returns True (everything OK) or False, if something went wrong
		if (!success) {
			JOptionPane.showMessageDialog(null, "Could not create kml Ground-Overlay",
					"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "KML Ground-Overlay created successfully!",
					"Good News", JOptionPane.INFORMATION_MESSAGE);
		}
		
		//at the same time we can read the tweets form the csv file using the CSVParser class
		TweetData[] tweets4KML = null;
		
		try {
			
			tweets4KML = CSVParser.getTweets(tweetFile);
			
		} catch (IOException ioEx) {
			
			ioEx.printStackTrace();
		}
		
		// after having extracted the tweets from the csv into the TweetData array
		// we can write it to the kml file structure
		
		String kmlFilePathTweets = storageLocation
				+ (String) System.getProperty("file.separator") 
				+ "singleTweets.kml";
		
		success = Tweets2KML.WriteToKML(kmlFilePathTweets, tweets4KML);
		
		//the methods returns True (everything OK) or False, if something went wrong
		if (!success) {
			JOptionPane.showMessageDialog(null, "Could not create kml of the Tweets",
					"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "KML for Tweets created successfully! - GoogleEarth starts now!",
					"Good News", JOptionPane.INFORMATION_MESSAGE);
		}
		
		//finally, Google Earth can be started
		try {
			
			StartGoogleEarth.startUpGoogleEarth(kmlFilePath, kmlFilePathTweets);
			
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, "Could not start Google Earth",
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			
		}
		
		//after that everything is done -> FINISH :)!!!
		
	}
	
} // end class
	


