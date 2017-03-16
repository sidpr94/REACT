package action;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTabbedPane;

import GUI.SwitchBox;

public class WhatIf implements MouseListener {
	SwitchBox selet;
	JTabbedPane sPane;
	public WhatIf(SwitchBox sbx,JTabbedPane s){
		this.selet = sbx;
		this.sPane = s;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(selet.isSelected()){
			sPane.setEnabled(false);
			for(int i = 0; i < sPane.getComponentCount();i++){
				sPane.getComponent(i).setEnabled(false);
			}
		}else{
			sPane.setEnabled(true);
			for(int i = 0; i < sPane.getComponentCount();i++){
				sPane.getComponent(i).setEnabled(false);
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
