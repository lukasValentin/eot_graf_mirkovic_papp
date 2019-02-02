package eot_graf_mirkovic_papp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class graphicalUserInterface extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//setup up the GUI elements -> must be public to be read from the main() method
	public JPanel contentPanel;
	public JTextField tfURL;
	public JTextField tfSRS;
	public JTextField tfBbox;
	public JButton chooseButton;
	public JTextArea chosenFile;
	public JButton openButton;
	public JTextArea chosenLoc;
	
	//specify the frame of the GUI
	//therefore, the constructor of graphicalUserInterface is used
	//remember, that the class inherits everything from JFrame
	
	graphicalUserInterface() {
		
		//title of the frame
		setTitle("GoogleEarthTweetMapper");
		
		//the way the frame could be closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//bounds of the frame
		setBounds(100,100,500,600);
		
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
			    	int res = csvFinder.showOpenDialog(graphicalUserInterface.this);
			    	
			    	//return the chosen directory to the user
			    	File file = null;
			    	if (res == JFileChooser.APPROVE_OPTION) {
			            
			    		file = csvFinder.getSelectedFile();
			    	}
			    	
			    	//return the whole path
			    	chosenFile.append("Selected: " + file.getPath() + "." + "\n");
			    	
			    } //end if

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
			    	int res = fc.showOpenDialog(graphicalUserInterface.this);
			    	
			    	//return the chosen directory to the user
			    	File file = null;
			    	if (res == JFileChooser.APPROVE_OPTION) {
			            
			    		file = fc.getSelectedFile();
			    	}
			    	
			    	//return the whole path
			    	chosenLoc.append("Selected: " + file.getPath() + "." + "\n");
			    	
			    } //end if

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
			
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog
					(graphicalUserInterface.this, "GoogleEarthTweetMapper starts now!\nPlease wait.");
				
			}
			
		});
		
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
		
				
		
	} // end of constructor
	
	//try to run the GUI
	public void runGUI() {
		
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
					graphicalUserInterface frame = new graphicalUserInterface();
					//set visible
					frame.setVisible(true);
					
				} catch (Exception e){
					
					e.printStackTrace();
					
				}
			}
		
		});
		

	}
	
}
