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
	
	private JFrame mainWindow = new JFrame("灯具进销存管理系统-主界面");
	private JPanel buttonPanel = new JPanel();
	private ArrayList<JButton> buttonArray = new ArrayList<JButton>();
	private JLabel infoLabel = new JLabel("就绪");

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
		
		JLabel topLabel = new JLabel("标题");
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
	
	//根据用户身份设置按钮
	private void setButtonPanel() {
		String[][] nameData = 
			{{"商品分类管理", "商品管理", "库存查看", "库存盘点", "报溢/报损"}
			,{}};
		String[] name = nameData[user.getType().getNum()];
		for (int i = 0; i < name.length; i++) {
			JButton button = new JButton(name[i]);
			button.addActionListener(reChangeListener(name[i],this));
			buttonArray.add(button);
		}
		buttonArray.add(new JButton("退出"));
		for (JButton button : buttonArray) {
			buttonPanel.add(button);
		}
	}
	
	private ActionListener reChangeListener(String className, MainWindow mw) {
		switch (className) {
		case "商品分类管理" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {changePanel(new InitPanel(mw));	}
			};
		case "商品管理" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			};
		case "库存查看" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			};
		case "库存盘点" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			};
		case "报溢/报损" :
			return new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			};
		default:
			System.out.println("无效的字符串");
			return null;
		}
		
	}
	
	/**
	 * 将innerPanel改为另一个PanelInterface
	 * @author 钱美缘
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
	 * 不含参数的changePanel方法将innerPanel改为MainPanel
	 * @author 钱美缘
	 */
	public void changePanel() {
		changePanel(new MainPanel());
	}
}
