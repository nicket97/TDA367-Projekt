package model.core.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.MainView;

public class RepaintListener{

	
	public static void actionPerformed() {
		MainView.getCanvas().repaint();
		MainView.layerView.update();
	}

}
