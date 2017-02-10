package basemap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.esri.core.geometry.CoordinateConversion;
import com.esri.core.geometry.Envelope;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.map.MapOverlay;
import com.esri.toolkit.JLayerList;

import GUI.SwitchBox;
import GUI.mapCoordPanel;
import GUI.LegendPanel;

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
    	
    	//Creates a text field for the title of the control panel
    	JTextField txtTitle = new JTextField();
	    txtTitle.setText("Map Layers");
	    txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    txtTitle.setFont(new Font(txtTitle.getFont().getName(),Font.PLAIN,16));
	    txtTitle.setMaximumSize(new Dimension(260,30));
	    txtTitle.setBackground(new Color(0,0,0,100));
	    txtTitle.setForeground(Color.WHITE);
	    txtTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
	    
	    //creates the control panel of the proper size and background to visualize the layers in
	    final JPanel controlPanel = new JPanel();
	    BoxLayout boxLayout1 = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
	    controlPanel.setLayout(boxLayout1);
	    controlPanel.setLocation(240,10);
	    controlPanel.setSize(260,230);
	    controlPanel.setBackground(new Color(0,0,0,100));
	    controlPanel.setBorder(new LineBorder(Color.BLACK,3));
	    controlPanel.add(txtTitle);
	    controlPanel.add(jLayerlist);
	    
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
	    contentPane.add(controlPanel,BorderLayout.CENTER);
	    contentPane.add(legendPanel,BorderLayout.CENTER); 
	    contentPane.add(btnLegendToggle,BorderLayout.CENTER);
	    contentPane.add(CreateMap(jMap),BorderLayout.CENTER);
	    contentPane.add(createInputPane(),BorderLayout.WEST);
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
	
	private JPanel createInputPane(){
	    JTextField inputTitle = new JTextField();
	    inputTitle.setText("Dashboard");
	    inputTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    inputTitle.setFont(new Font(inputTitle.getFont().getName(),Font.BOLD,14));
	    inputTitle.setMaximumSize(new Dimension(250,30));
	    inputTitle.setBackground(new Color(0,0,0,100));
	    inputTitle.setEditable(false);
	    inputTitle.setEnabled(false);
	    inputTitle.setForeground(Color.WHITE);
	    inputTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
	    
	    JTextField opForecastTitle = new JTextField();
	    opForecastTitle.setText("Operations Forecasting Scenarios");
	    opForecastTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    opForecastTitle.setFont(new Font(opForecastTitle.getFont().getName(),Font.PLAIN,14));
	    opForecastTitle.setMaximumSize(new Dimension(250,45));
	    opForecastTitle.setEditable(false);
	    opForecastTitle.setEnabled(false);
	    opForecastTitle.setBackground(new Color(0,0,0,100));
	    opForecastTitle.setForeground(Color.WHITE);
	    opForecastTitle.setBorder(BorderFactory.createEmptyBorder(15,0,5,0));
	    
	    JTextField popForecastTitle = new JTextField();
	    popForecastTitle.setText("Population Forecasting Scenarios");
	    popForecastTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    popForecastTitle.setFont(new Font(popForecastTitle.getFont().getName(),Font.PLAIN,14));
	    popForecastTitle.setMaximumSize(new Dimension(250,30));
	    popForecastTitle.setEditable(false);
	    popForecastTitle.setEnabled(false);
	    popForecastTitle.setBackground(new Color(0,0,0,100));
	    popForecastTitle.setForeground(Color.WHITE);
	    popForecastTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
	    
	    JComboBox<String> opForecast = new JComboBox<>();
	    opForecast.setMaximumSize(new Dimension(250,20));
	    opForecast.setModel(new DefaultComboBoxModel<>(new String[]{"Below Nominal TAF","Nominal TAF","Above Nominal TAF"}));
	    JComboBox<String> popForecast = new JComboBox<>();
	    popForecast.setMaximumSize(new Dimension(250,20));
	    popForecast.setModel(new DefaultComboBoxModel<>(new String[]{"2020 Forecast","2030 Forecast"}));
	    
	    JTextField WhatifTitle = new JTextField();
	    WhatifTitle.setText("What-If Scenarios");
	    WhatifTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    WhatifTitle.setFont(new Font(WhatifTitle.getFont().getName(),Font.BOLD,14));
	    WhatifTitle.setMaximumSize(new Dimension(250,45));
	    WhatifTitle.setBackground(new Color(0,0,0,100));
	    WhatifTitle.setEditable(false);
	    WhatifTitle.setEnabled(false);
	    WhatifTitle.setForeground(Color.WHITE);
	    WhatifTitle.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));
        
	    SwitchBox sbx = new SwitchBox("On","Off");
	    
		JPanel inputPane = new JPanel();
	    BoxLayout boxLayout = new BoxLayout(inputPane, BoxLayout.Y_AXIS);
	    inputPane.setLayout(boxLayout);
	    inputPane.setSize(350,300);
	    inputPane.setBackground(new Color(0,0,0,100));
	    inputPane.setDoubleBuffered(true);
	    inputPane.setBorder(new LineBorder(Color.BLACK, 5, false));
	    inputPane.add(inputTitle);
	    inputPane.add(popForecastTitle);
	    inputPane.add(popForecast);
	    inputPane.add(opForecastTitle);
	    inputPane.add(opForecast);
	    inputPane.add(WhatifTitle);
	    inputPane.add(sbx);
		return inputPane;
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
	

