package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vo.UserVO;

public class MainWindow {
	private UserVO user;
	private PanelInterface innerPanel;
	
	private JFrame mainWindow = new JFrame("�ƾ߽��������ϵͳ-������");
	private JPanel buttonPanel = new JPanel();
	private ArrayList<JButton> buttonArray = new ArrayList<JButton>();
	private JLabel infoLabel = new JLabel("����");

	public MainWindow(UserVO user) {
		this.user = user;
		
		//set size of form according to screen's size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainWindow.setSize(screenSize.width, screenSize.height-50);
		
		//other settings and layout
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLayout(new BorderLayout());
		
		buttonPanel.setPreferredSize(new Dimension((int) (0.15 * mainWindow.getWidth()), 0));
		buttonPanel.setLayout(new GridLayout(11,1,25,5));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel topLabel = new JLabel("����");
		topLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		topLabel.setPreferredSize(new Dimension(0, (int) (0.13 * mainWindow.getHeight())));
		
		innerPanel = new MainPanel(user);
		
		//add components
		setButtonPanel();
		mainWindow.add(topLabel, BorderLayout.NORTH);
		mainWindow.add(buttonPanel, BorderLayout.WEST);
		mainWindow.add((Component) innerPanel, BorderLayout.CENTER);
		mainWindow.add(infoLabel, BorderLayout.SOUTH);
		mainWindow.setVisible(true);
	}
	
	//�����û�������ð�ť
	private void setButtonPanel() {
		String[][] nameData = 
			{{"��Ʒ�������", "��Ʒ����", "���鿴", "����̵�", "����/����"}
			,{}};
		String[] name = nameData[user.getType().getNum()];
		for (int i = 0; i < name.length; i++) {
			buttonArray.add(new JButton(name[i]));
		}
		buttonArray.add(new JButton("�˳�"));
		for (JButton button : buttonArray) {
			buttonPanel.add(button);
		}
	}
	
	private void changePanel(PanelInterface panelImpl) {
		if (innerPanel.close()) {
			innerPanel = panelImpl;
			innerPanel.init(user);
		}
	}
}
