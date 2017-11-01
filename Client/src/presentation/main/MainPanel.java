package presentation.main;

import javax.swing.JButton;
import javax.swing.JPanel;

import presentation.PanelInterface;

public class MainPanel implements PanelInterface {
	private JPanel panel= new JPanel(); 
	
	@Override
	public boolean close() {
		return true;
	}

	public MainPanel() {
		panel.setVisible(true);
		panel.add(new JButton());
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}
}
