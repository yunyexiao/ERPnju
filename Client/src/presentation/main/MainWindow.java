package presentation.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import presentation.PanelInterface;
import vo.UserVO;

public class MainWindow {
	private UserVO user;
	private PanelInterface innerPanel;
	
	private JFrame mainWindow = new JFrame("�ƾ߽��������ϵͳ-������");
	private LeftButtonPanel buttonPanel;
	private JLabel infoLabel = new JLabel("����");

	public MainWindow(UserVO user) {
		this.user = user;
		innerPanel = new MainPanel();
		
		buttonPanel = new LeftButtonPanel(this);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
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
		
		JLabel topLabel = new JLabel("����");
		ImageIcon icon = new ImageIcon("resource/Caption.png");
		topLabel.setIcon(icon);
		topLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		topLabel.setPreferredSize(new Dimension(0, (int) (0.13 * mainWindow.getHeight())));
		
		//add components
		mainWindow.add(topLabel, BorderLayout.NORTH);
		mainWindow.add(buttonPanel, BorderLayout.WEST);
		mainWindow.add(innerPanel.getPanel(), BorderLayout.CENTER);
		mainWindow.add(infoLabel, BorderLayout.SOUTH);
		mainWindow.setVisible(true);
		
		SwingUtilities.updateComponentTreeUI(mainWindow);
	}
	/**
	 * ��innerPanel��Ϊ��һ��PanelInterface
	 * @author Ǯ��Ե
	 */
	protected void changePanel(PanelInterface panelImpl) {
		if (innerPanel.close()) {
			mainWindow.remove(innerPanel.getPanel());
			innerPanel = panelImpl;
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
	
	/**
	 * 
	 * @return ���������ڱ����UserVO����
	 */
	public UserVO getUser() {
		return user;
	}
	/**
	 * ���ô����Ƿ����
	 * @param flag ��Ҫ���õ�״̬
	 */
	public void setEnable(boolean flag) {
		mainWindow.setEnabled(flag);
	}
}
