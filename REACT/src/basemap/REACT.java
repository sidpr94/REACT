/*
 * REACT is the Main File for this entire software.
 * REACT is written in Java 1.8 with ArcGIS SDK for Java v. 10.2.4
 * This software is an airport noise modeling software that allows various aviation entities to rapidly tradeoff noise mitigation strategies.
 * This is not an official software and is mainly used for educational purposes.
 */
package basemap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.esri.core.geometry.Envelope;
import com.esri.map.JMap;
import com.esri.runtime.ArcGISRuntime;
import com.esri.toolkit.JLayerList;

import content.ContentPane;
import content.DataPane;
import results.ResultPane;
import results.ResultsTable;

// TODO: Auto-generated Javadoc
/**
 *  REACT is the main thread run, and is the beginning of the REACT software.
 *  It initializes the main maps, tables, and layerlists. 
 */
public class REACT {
	
	/** The main map. */
	private JMap mainMap;
	
	/** The map compare for contour comparison. */
	private JMap mapCompare;
	
	/** The results table. */
	JTable table;
	
	/** The main layerlist. */
	private JLayerList mainLayerList;
	
	/** The screen size. */
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/** The height. */
	int height = screenSize.height;
	
	/** The width. */
	int width = screenSize.width;

	/**
	 * Instantiates a new react.
	 */
	public REACT(){}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Used to help with speedy GUI runs
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					REACT app = new REACT(); 
					app.initLicense();
					JFrame appWindow = app.createWindow();
					appWindow.setVisible(true);
					appWindow.setContentPane(app.createUI(appWindow.getSize()));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initializes the license for this ArcGIS Software.
	 */
	//creates the literal window for this app
	private void initLicense(){
		ArcGISRuntime.setClientID("LhJjc2SJvg4UEhnL");
	}
	
	/**
	 * Creates the main window.
	 *
	 * @return the main window to be displayed
	 */
	private JFrame createWindow() {
		JFrame window = new JFrame("REACT - Rapid Environmental Impact on Airport Community Tradeoff Environment: Kansas City International Airport (MCI)");
		window.setBounds(0, 10, width,height-60);
		window.setExtendedState	(Frame.MAXIMIZED_BOTH);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(new BorderLayout(0, 0));
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				super.windowClosing(windowEvent);
				if (mainMap != null ){
					mainMap.dispose();
				}
				if(mapCompare != null){
					mapCompare.dispose();
				}
			}
		});
		return window;
	}

	/**
	 * Creates the various JPanels to be inserted into the main window including: Map Tab, Database Tab, and Results Tab.
	 *
	 * @param d the dimension of the screen size
	 * @return the contents for the window
	 * @throws Exception the exception
	 */
	public JComponent createUI(Dimension d) throws Exception {
		mainMap = new JMap();
		mapCompare = new JMap();
		mainLayerList = new JLayerList(mainMap);
		ResultsTable t = new ResultsTable();
		table = t.getTable();
		JTabbedPane tabContent = new JTabbedPane();
		tabContent.setBorder(BorderFactory.createEmptyBorder());
		tabContent.setFont(new Font("Dialog",Font.BOLD,16));
		ContentPane contentPane = new ContentPane(mainMap,mainLayerList,d,mapCompare,table);

		DataPane dataPane = new DataPane();
		ResultPane resultsPane = new ResultPane(mapCompare,d,table);
		tabContent.addTab("Map", contentPane.getContentPane());
		tabContent.addTab("Database", dataPane.getfinePane());
		tabContent.addTab("Results", resultsPane.createPane());
		
		//Sets the initial zoom status of the map. Zooms into the runway
		Envelope initialExtent = new Envelope(-94.920484888,39.062438997,-94.543736896,39.534769654);
		mainMap.setFullExtent(initialExtent);
		Envelope initialExtent1 = new Envelope(-94.920484888,39.062438997,-94.493736896,39.604769654);
		mapCompare.setFullExtent(initialExtent1);
		mapCompare.setShowingEsriLogo(false);
		mapCompare.setScale(100000);
		mainMap.setShowingEsriLogo(false);
		return tabContent;
	}

}


