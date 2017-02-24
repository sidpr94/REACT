package action;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
	
public class PressedAction implements MouseListener {
	
	JPanel panel;
	ArrayList<JToggleButton> list;
	public PressedAction(){}
	public PressedAction(JPanel panel, ArrayList<JToggleButton> list){
		this.panel = panel;
		this.list = list;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JToggleButton btn = (JToggleButton) e.getSource();
		if(list.get(0) == e.getSource()){
			if(btn.isSelected()){
				if(!panel.getComponent(8).isVisible()){
					panel.getComponent(8).setVisible(true);
					panel.revalidate();
					panel.repaint();
				}
			}else{
				panel.getComponent(8).setVisible(false);
				panel.revalidate();
				panel.repaint();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
