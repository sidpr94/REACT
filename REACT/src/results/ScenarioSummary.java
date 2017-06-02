package results;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import angim.RunANGIM;

public class ScenarioSummary {
	
	private static DefaultStyledDocument document = new DefaultStyledDocument();
	
	private static JTextPane summary = new JTextPane();
	
	public static String scenarioTitle = "";
	
	public static String scenarioPopForecast = "";
	
	public static String scenarioOpForecast = "";
	
	public static String populationBlocksChanged = "";
	
	public static String scenarioTracksChanged = "";
	
	public static String scenarioAircraftChanged = "";
	
	public ScenarioSummary(){}
	
	public JTextPane getScenarioSummary() throws BadLocationException{
		summary.setEditable(false);
		StyleContext context = new StyleContext();
		Style style = context.addStyle("Summary", null);
		
		StyleConstants.setForeground(style, new Color(0,37,76));
		StyleConstants.setFontSize(style, 20);
		StyleConstants.setBold(style, false);
		StyleConstants.setFontFamily(style, "Dialog");
		document.insertString(0,"Scenario Tracks Changed: \n", style);
		document.insertString(0,"Scenario Aircraft Modified: \n", style);
		document.insertString(0,"Scenario Population Blocks Changed: \n", style);
		document.insertString(0,"Scenario Population Forecast: \n", style);
		document.insertString(0,"Scenario Operation Forecast: \n", style);
		
		StyleConstants.setForeground(style, new Color(0,37,76));
		StyleConstants.setFontSize(style, 20);
		StyleConstants.setBold(style, true);
		StyleConstants.setFontFamily(style, "Dialog");
		document.insertString(0,"Scenario Title: \n", style);
		
		summary.setBackground(new Color(214, 217, 223));
		summary.setDocument(document);
		return summary;
	}

	public void showSummary(String text, int r) throws BadLocationException {
		// TODO Auto-generated method stub	
		summary.setText("");
		
		scenarioTitle = text;
		
		scenarioPopForecast = RunANGIM.summaryInfo.get(r)[1];
		
		scenarioOpForecast = RunANGIM.summaryInfo.get(r)[0];
		
		populationBlocksChanged = RunANGIM.summaryInfo.get(r)[2];
		
		scenarioTracksChanged = RunANGIM.summaryInfo.get(r)[4];
		
		scenarioAircraftChanged = RunANGIM.summaryInfo.get(r)[3];
		
		StyleContext context = new StyleContext();
		Style style = context.addStyle("Summary", null);
		
		StyleConstants.setForeground(style, new Color(0,37,76));
		StyleConstants.setFontSize(style, 20);
		StyleConstants.setBold(style, false);
		StyleConstants.setFontFamily(style, "Dialog");
		document.insertString(0,"Scenario Tracks Changed: "+scenarioTracksChanged+"\n", style);
		document.insertString(0,"Scenario Aircraft Modified: "+scenarioAircraftChanged+"\n", style);
		document.insertString(0,"Scenario Population Blocks Changed: "+populationBlocksChanged+"\n", style);
		document.insertString(0,"Scenario Population Forecast: "+scenarioPopForecast+"\n", style);
		document.insertString(0,"Scenario Operation Forecast: "+scenarioOpForecast+"\n", style);
		
		StyleConstants.setForeground(style, new Color(0,37,76));
		StyleConstants.setFontSize(style, 20);
		StyleConstants.setBold(style, true);
		StyleConstants.setFontFamily(style, "Dialog");
		document.insertString(0,"Scenario Title: "+scenarioTitle+"\n", style);
		
		summary.setBackground(new Color(214, 217, 223));
		summary.setDocument(document);
	}
}
