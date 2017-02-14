package basemap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.esri.core.geometry.Envelope;
import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

import GUI.mapCoordPanel;
import GUI.DashBoard;
import GUI.LegendButton;
import GUI.LegendPanel;
import GUI.MapLayer;
import GUI.MapLayerButton;

//REACT runs the main application populating the base map as well as the GUI
//Creates the window, content pane, and initializes the map for the application
//REACT will eventually run the calculations as well

public class REACT {
	//initialize map
	private JMap jMap;
	//initialize list of layers to be used within map
	private JLayerList jLayerlist;
	//grab the screen size to be used for GUI sizing
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;
    //create instances of each of the GUI panels
    
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
		          //appWindow.setContentPane(app.createUI());
		          appWindow.setVisible(true);
		          appWindow.setContentPane(app.createUI(appWindow.getSize()));
		          //this catch exception is there to handle errors when populating 
		          
		        } catch (Exception e) {
		          // on any error, print error stack trace
		          e.printStackTrace();
		        }
		      }
		    });
	}
	
	//creates the literal window for this app
	private JFrame createWindow() {
		//Title
	    JFrame window = new JFrame("REACT - Rapid Environmental Impact for Airport Calculation Tradeoff Environment");
	    //bounds of the frame (when not in full screen)
	    window.setBounds(0, 0, width,height);
	    //Makes sure the app opens full screen
	    window.setExtendedState	(JFrame.MAXIMIZED_BOTH);
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
	public JComponent createUI(Dimension d) throws Exception {
	    // application content
	    JComponent contentPane = createContentPane();
	    //Creates a map and LayerList for that map
	    jMap = new JMap();
	    jLayerlist = new JLayerList(jMap);
	    //Sets the initial zoom status of the map. Zooms into the runway
    	Envelope initialExtent = new Envelope(-94.920484888,39.032438997,-94.543736896,39.534769654);
    	jMap.setFullExtent(initialExtent);
    	
    	//creates the map layer and legend panels
        LegendPanel lPanel = new LegendPanel((int) d.getWidth(),(int) d.getHeight());
	    JPanel legendPanel = lPanel.getLegend();
	    LegendButton lgndbtn = new LegendButton(legendPanel,(int) d.getWidth(),(int) d.getHeight());
	    
        mapCoordPanel mPanel = new mapCoordPanel((int) d.getWidth(),(int) d.getHeight());
        JTextArea coordTxt = mPanel.getCoordTxt();
        
        MapLayer lyrPanel = new MapLayer((int) d.getWidth(),(int) d.getHeight());
    	JPanel layerPanel = lyrPanel.getMapLayer(jLayerlist); 
    	MapLayerButton mbtn = new MapLayerButton(layerPanel,(int) d.getWidth(),(int) d.getHeight());
    	
	    CreateMap mapCreator = new CreateMap(jMap,jLayerlist,coordTxt);
	    
	    DashBoard dPanel = new DashBoard();
	    //toggle button to turn off the visibility for the legend

	    //Add map, layer list, coordinates, legend, and dashboard into content pane of the window
	    contentPane.add(mPanel.getCoordPanel(coordTxt),BorderLayout.CENTER);
	    contentPane.add(layerPanel,BorderLayout.CENTER);
	    contentPane.add(mbtn.getMapButton(),BorderLayout.CENTER);
	    contentPane.add(legendPanel,BorderLayout.CENTER); 
	    contentPane.add(lgndbtn.getLegendButton(),BorderLayout.CENTER);
	    contentPane.add(mapCreator.getMap(),BorderLayout.CENTER);
	    contentPane.add(dPanel.getDashboard(),BorderLayout.WEST);
	    return contentPane;
	}

	//creates the content pane where the content is actually stored in the UI
	 private static JLayeredPane createContentPane() {
		    JLayeredPane contentPane = new JLayeredPane();
		    contentPane.setBounds(100, 100, 1000, 700);
		    contentPane.setLayout(new BorderLayout(0, 5));
		    contentPane.setVisible(true);
		    return contentPane;
     }
 }
	

