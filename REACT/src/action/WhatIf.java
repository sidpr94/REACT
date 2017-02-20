package action;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

import GUI.SwitchBox;

public class WhatIf implements MouseListener {
	SwitchBox selet;
	ArrayList<JToggleButton> list;
	ButtonGroup group;
	
	public WhatIf(SwitchBox sbx, ArrayList<JToggleButton> list, ButtonGroup group){
		this.selet = sbx;
		this.list = list;
		this.group = group;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(selet.isSelected()){
			for(int i = 0; i < list.size(); i++){
				list.get(i).setEnabled(false);
				group.clearSelection();
			}
		}else{
			for(int i = 0; i < list.size(); i++){
				list.get(i).setEnabled(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
