package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import presentation.inventory.*;
import vo.UserVO;

public class MainWindow {
	private UserVO user;
	private PanelInterface innerPanel = new MainPanel();
	
	private JFrame mainWindow = new JFrame("�ƾ߽��������ϵͳ-������");
	private JPanel buttonPanel = new JPanel();
	private ArrayList<JButton> buttonArray = new ArrayList<JButton>();
	private JLabel infoLabel = new JLabel("����");

	public MainWindow(UserVO user) {
		this.user = user;
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
        JFrame.setDefaultLookAndFeelDecorated(true);
        
		//set size of form according to screen's size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainWindow.setSize(screenSize.width, screenSize.height-50);
		
		//other settings and layout
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLayout(new BorderLayout());
		
		buttonPanel.setPreferredSize(new Dimension((int) (0.15 * mainWindow.getWidth()), 0));
		buttonPanel.setLayout(new GridLayout(11, 1, 5, 5));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel topLabel = new JLabel("����");
		topLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		topLabel.setPreferredSize(new Dimension(0, (int) (0.13 * mainWindow.getHeight())));
		
		//add components
		setButtonPanel();
		innerPanel.init(user);
		mainWindow.add(topLabel, BorderLayout.NORTH);
		mainWindow.add(buttonPanel, BorderLayout.WEST);
		mainWindow.add(innerPanel.getPanel(), BorderLayout.CENTER);
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
			JButton button = new JButton(name[i]);
			button.addActionListener(reChangeListener(name[i],this));
			buttonArray.add(button);
		}
		buttonArray.add(new JButton("�˳�"));
		for (JButton button : buttonArray) {
			buttonPanel.add(button);
		}
	}
	
	private ActionListener reChangeListener(String className, MainWindow mw) {
		switch (className) {
		case "��Ʒ�������" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {changePanel(new InitPanel(mw));	}
			};
		case "��Ʒ����" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			};
		case "���鿴" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			};
		case "����̵�" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			};
		case "����/����" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			};
		default:
			System.out.println("��Ч���ַ���");
			return null;
		}
		
	}
	
	/**
	 * ��innerPanel��Ϊ��һ��PanelInterface
	 * @author Ǯ��Ե
	 */
	private void changePanel(PanelInterface panelImpl) {
		if (innerPanel.close()) {
			mainWindow.remove(innerPanel.getPanel());
			innerPanel = panelImpl;
			innerPanel.init(user);
			mainWindow.add(innerPanel.getPanel(), BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(mainWindow);
		}
	}

	/**
	 * ����������changePanel������innerPanel��ΪMainPanel
	 * @author Ǯ��Ե
	 */
	public void changePanel() {
		changePanel(new MainPanel());
	}
}
