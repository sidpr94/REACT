package basemap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.esri.core.geometry.Envelope;
import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

//REACT runs the main application populating the base map as well as the GUI
//Creates the window, content pane, and initializes the map for the application

public class REACT {
	//initialize map
	private JMap jMap;
	//initialize list of layers to be used within map
	private JLayerList jLayerlist;
	public REACT(){}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Used to help with speedy GUI runs
		 SwingUtilities.invokeLater(new Runnable() {
		      @Override
		      public void run() {
		        try {
		          // instance of this application
		          REACT app = new REACT(); 	
		          //create window for app, runs createWindow method
		          JFrame appWindow = app.createWindow();
		          //runs the createUI method
		          appWindow.setContentPane(app.createUI());
		          appWindow.setVisible(true);
		          
		          //this catch exception is there to handle errors when populating 
		        } catch (Exception e) {
		          // on any error, print error stack trace
		          e.printStackTrace();
		        }
		      }
		    });
	}
	
	//creates the literal window for this app...
	private JFrame createWindow() {
		//Title
	    JFrame window = new JFrame("REACT - Rapid Environmental Impact for Airport Tradeoff Environment");
	    //bounds of the frame
	    window.setBounds(100, 100, 1000, 700);
	    //Makes sure the app opens full screen
	    window.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    //What happens when the app is closed
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //sets the border and layout for the content within the frame
	    window.getContentPane().setLayout(new BorderLayout(0, 0));
	    //checks to see what changes happen to window
	    window.addWindowListener(new WindowAdapter() {
		@Override
		//rids of the map when closing the window so there is no leak or memory issues
	    public void windowClosing(WindowEvent windowEvent) {
	      super.windowClosing(windowEvent);
	      //disposes map on window closing
	      if (jMap != null ) 
	    	  jMap.dispose();
	      }
	    });
	    return window;
	}
	
	//creates the UI for the app. This is where the map is inserted and created
	public JComponent createUI() throws Exception {
	    // application content
	    JComponent contentPane = createContentPane();
	    
	    //Creates a map and LayerList for that map
	    jMap = new JMap();
	    jLayerlist = new JLayerList(jMap);
		CreateMap cm = new CreateMap(jMap,jLayerlist);
	    //Sets the initial zoom status of the map. Zooms into the runway
    	Envelope initialExtent = new Envelope(-94.920484888,39.032438997,-94.543736896,39.534769654);
    	jMap.setFullExtent(initialExtent);
   
    	//Creates a text field for the title of the control panel
    	JTextField txtTitle = new JTextField();
	    txtTitle.setText("Map Layers");
	    txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    txtTitle.setFont(new Font(txtTitle.getFont().getName(),Font.PLAIN,16));
	    txtTitle.setMaximumSize(new Dimension(300,30));
	    txtTitle.setBackground(new Color(0,0,0,100));
	    txtTitle.setForeground(Color.WHITE);
	    txtTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
	    
	    //creates the control panel of the proper size and background to visualize the layers in
	    final JPanel controlPanel = new JPanel();
	    BoxLayout boxLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
	    controlPanel.setLayout(boxLayout);
	    controlPanel.setLocation(10,10);
	    controlPanel.setSize(260,230);
	    controlPanel.setBackground(new Color(0,0,0,100));
	    controlPanel.setBorder(new LineBorder(Color.BLACK,3));
	    controlPanel.add(txtTitle);
	    controlPanel.add(jLayerlist);
	    
	    //Add control panel and map into content pane of the window
	    contentPane.add(controlPanel);
	    contentPane.add(cm.createMap());
	    return contentPane;
	}
	
	 //creates the content pane where the content is actually stored in the UI
	 private static JLayeredPane createContentPane() {
		    JLayeredPane contentPane = new JLayeredPane();
		    contentPane.setBounds(100, 100, 1000, 700);
		    contentPane.setLayout(new BorderLayout(5, 5));
		    contentPane.setVisible(true);
		    return contentPane;
     }

}

