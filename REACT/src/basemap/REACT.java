package basemap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.BorderFactory;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.esri.core.geometry.Envelope;
import com.esri.map.JMap;
import com.esri.runtime.ArcGISRuntime;
import com.esri.toolkit.JLayerList;

import content.ContentPane;
import content.DataPane;

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
		//URL url = REACT.class.getClassLoader().getResource("REACT_lib/json.jar");
		//System.setProperty("user.dir", url.getPath());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					// instance of this application
					REACT app = new REACT(); 
					app.initLicense();
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
	private void initLicense(){
		ArcGISRuntime.setClientID("LhJjc2SJvg4UEhnL");
	}
	private JFrame createWindow() {
		//Title
		JFrame window = new JFrame("REACT - Rapid Environmental Impact on Airport Community Tradeoff Environment");
		//bounds of the frame (when not in full screen)
		window.setBounds(0, 10, width,height-60);
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
		jMap = new JMap();
		//JMap mp = new JMap();
		jLayerlist = new JLayerList(jMap);
		JTabbedPane tabContent = new JTabbedPane();
		tabContent.setBorder(BorderFactory.createEmptyBorder());
		tabContent.setFont(new Font("Dialog",Font.BOLD,16));
		ContentPane contentPane = new ContentPane(jMap,jLayerlist,d);

		DataPane dataPane = new DataPane();

		tabContent.addTab("Map", contentPane.getContentPane());
		tabContent.addTab("Database", dataPane.getfinePane());
		//JLayeredPane contentPane = createContentPane();
		//Creates a map and LayerList for that map
		//Sets the initial zoom status of the map. Zooms into the runway
		Envelope initialExtent = new Envelope(-94.920484888,39.062438997,-94.543736896,39.534769654);
		jMap.setFullExtent(initialExtent);
		jMap.setShowingEsriLogo(false);
		return tabContent;
	}

	//creates the content pane where the content is actually stored in the UI
}


