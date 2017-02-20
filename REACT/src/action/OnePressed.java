package action;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JToggleButton;

public class OnePressed implements MouseListener {
	
	ArrayList<JToggleButton> list;
	public OnePressed(ArrayList<JToggleButton> list){
		this.list = list;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
        if(list.get(0).getModel().isPressed()){
        	
            list.get(1).getModel().setPressed(false);
            list.get(2).getModel().setPressed(false);
            list.get(3).getModel().setPressed(false);
            
        }
        else if (list.get(1).getModel().isPressed()){
        	
        	list.get(0).getModel().setPressed(false);
            list.get(2).getModel().setPressed(false);
            list.get(3).getModel().setPressed(false);
            
        }
        else if (list.get(2).getModel().isPressed()){
        	
        	list.get(0).getModel().setPressed(false);
            list.get(1).getModel().setPressed(false);
            list.get(3).getModel().setPressed(false);
        }
        else if (list.get(3).getModel().isPressed()){
        	
        	list.get(0).getModel().setPressed(false);
            list.get(1).getModel().setPressed(false);
            list.get(2).getModel().setPressed(false);
        }

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
