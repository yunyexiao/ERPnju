package presentation;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import vo.UserVO;

public class MainPanel extends JPanel implements PanelInterface {
	@Override
	public boolean close() {
		return false;
	}

	@Override
	public void init(UserVO user) {
		// TODO Auto-generated method stub

	}

	public MainPanel(UserVO user) {
		setVisible(true);
		add(new JButton());
	}
}
