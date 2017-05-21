package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import controllers.MainView;

public class RepaintListener extends Observable{

	
	public static void actionPerformed() {
		MainView.getCanvas().repaint();
		MainView.layerView.update();
	}

}
