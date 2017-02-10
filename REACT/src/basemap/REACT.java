package basemap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.esri.core.geometry.CoordinateConversion;
import com.esri.core.geometry.Envelope;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.map.MapOverlay;
import com.esri.toolkit.JLayerList;

import GUI.mapCoordPanel;
import GUI.DashBoard;
import GUI.LegendPanel;
import GUI.MapLayer;

//REACT runs the main application populating the base map as well as the GUI
//Creates the window, content pane, and initializes the map for the application

public class REACT {
	//initialize map
	private JMap jMap;
	//initialize list of layers to be used within map
	private JLayerList jLayerlist;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;
    LegendPanel lPanel = new LegendPanel(width,height);
    mapCoordPanel mPanel = new mapCoordPanel(width,height);
    MapLayer lyrPanel = new MapLayer();
    DashBoard dPanel = new DashBoard();
    JTextArea coordTxt = mPanel.getCoordTxt();
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
	    JFrame window = new JFrame("REACT - Rapid Environmental Impact for Airport Calculation Tradeoff Environment");
	    //bounds of the frame
	    window.setBounds(0, 5, width,height-80);
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
	    
	    //Sets the initial zoom status of the map. Zooms into the runway
    	Envelope initialExtent = new Envelope(-94.920484888,39.032438997,-94.543736896,39.534769654);
    	jMap.setFullExtent(initialExtent);
    	
    	JPanel layerPanel = lyrPanel.getMapLayer(jLayerlist);   
	    JPanel legendPanel = lPanel.getLegend();
	    
	    final JButton btnLegendToggle = new JButton("Toggle Legend");
	    btnLegendToggle.setLocation(width-280,10);
	    btnLegendToggle.setSize(250, 25);
	    btnLegendToggle.setVisible(true);
	    btnLegendToggle.addActionListener(new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        legendPanel.setVisible(!legendPanel.isVisible());
	      }
	    });
	    //Add control panel and map into content pane of the window
	    contentPane.add(mPanel.getCoordPanel(coordTxt),BorderLayout.CENTER);
	    contentPane.add(layerPanel,BorderLayout.CENTER);
	    contentPane.add(legendPanel,BorderLayout.CENTER); 
	    contentPane.add(btnLegendToggle,BorderLayout.CENTER);
	    contentPane.add(CreateMap(jMap),BorderLayout.CENTER);
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

	class EditLayerName implements LayerInitializeCompleteListener {
	    @Override
	    public void layerInitializeComplete(final LayerInitializeCompleteEvent event) {
	      SwingUtilities.invokeLater(new Runnable()
	      {
	        @Override
	        public void run()
	        {
	          Layer layer = event.getLayer();
	          if(layer.getName() == jMap.getLayers().get(0).getName()){
	        	  layer.setName("Population Map");
	          }else if(layer.getName() == jMap.getLayers().get(1).getName()){
	        	  layer.setName("Flight Tracks");
	          }else if(layer.getName() == jMap.getLayers().get(2).getName()){
	        	  layer.setName("Noise Contour");
	          }else{
	        	  layer.setName("Runways");
	          }
	          // Update layer list
	          jLayerlist.refresh();
	        }
	      });
	    }
    }
	 public JMap CreateMap(JMap map) {
		 	jMap.addMapOverlay(new MouseMoveOverlay());
			PopMap pmap = new PopMap();
			Runway rmap = new Runway();
			Tracks tmap = new Tracks();
			NoiseContour nmap = new NoiseContour();
			Layer pop = pmap.createPopMap();
			Layer tracks = tmap.createTracks();
			Layer noise = nmap.createNoiseContour();
			Layer runway = rmap.createRunway();
			map.getLayers().add(pop);
			map.getLayers().add(tracks);
			map.getLayers().add(noise);
			map.getLayers().add(runway);
			pop.addLayerInitializeCompleteListener(new EditLayerName());
			tracks.addLayerInitializeCompleteListener(new EditLayerName());
			noise.addLayerInitializeCompleteListener(new EditLayerName());
			runway.addLayerInitializeCompleteListener(new EditLayerName());
			return jMap;
	}
	 
	 private class MouseMoveOverlay extends MapOverlay {
		    private static final long serialVersionUID = 1L;

		    public void onMouseMoved(MouseEvent arg0) {
		      try {
		        if (!jMap.isReady()) {
		          return;
		        }
		        java.awt.Point screenPoint = arg0.getPoint();
		        com.esri.core.geometry.Point mapPoint = jMap.toMapPoint(screenPoint.x, screenPoint.y);
		        String decimalDegrees = "Decimal Degrees: " 
		            + CoordinateConversion.pointToDecimalDegrees(mapPoint, jMap.getSpatialReference(), 2);
                coordTxt.setText(decimalDegrees);

		      } finally {
		        super.onMouseMoved(arg0);
		      }
		    }
		  }
 }
	

