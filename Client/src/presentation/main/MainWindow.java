package presentation.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import layout.TableLayout;
import presentation.PanelInterface;
import vo.UserVO;

public class MainWindow {
	private UserVO user;
	private PanelInterface innerPanel;
	
	private JFrame mainWindow = new JFrame("灯具进销存管理系统-主界面");
	private LeftButtonPanel buttonPanel;
	private static JLabel infoLabel = new JLabel("就绪", JLabel.CENTER);

	public MainWindow(UserVO user) {
		this.user = user;
		innerPanel = new MainPanel(this);
		
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
		
		JLabel topLabel = new JLabel("标题");
		topLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		topLabel.setPreferredSize(new Dimension(0, (int) (0.13 * mainWindow.getHeight())));
		
		double[][] size = {{0.15,TableLayout.FILL,0.15},{TableLayout.FILL}};
		JPanel infoPanel = new JPanel(new TableLayout(size));
		JLabel noUseLabel = new JLabel();
		JLabel timeLabel = new JLabel("", JLabel.CENTER);
		noUseLabel.setBorder(BorderFactory.createLoweredBevelBorder());
		infoLabel.setBorder(BorderFactory.createLoweredBevelBorder());
		timeLabel.setBorder(BorderFactory.createLoweredBevelBorder());
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				timeLabel.setText(sdf.format(new Date()) + "  ");
			}
		};
		timer.schedule(task, 0, 1000L);
		infoPanel.add(infoLabel, "0,0");
		infoPanel.add(noUseLabel, "1,0");
		infoPanel.add(timeLabel, "2,0");
		
		//add components
		mainWindow.add(topLabel, BorderLayout.NORTH);
		mainWindow.add(buttonPanel, BorderLayout.WEST);
		mainWindow.add(innerPanel.getPanel(), BorderLayout.CENTER);
		mainWindow.add(infoPanel, BorderLayout.SOUTH);
		mainWindow.setVisible(true);
		ImageIcon image = new ImageIcon("resource/Caption.png");
        Image img = image.getImage();  
        img = img.getScaledInstance(topLabel.getWidth(), topLabel.getHeight(), Image.SCALE_DEFAULT);  
        image.setImage(img);
		topLabel.setIcon(image);
		buttonPanel.setBackground();
		//((MainPanel) innerPanel).setBackground();
		
		SwingUtilities.updateComponentTreeUI(mainWindow);
	}
	/**
	 * 将innerPanel改为另一个PanelInterface
	 * @author 钱美缘
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
	 * 不含参数的changePanel方法将innerPanel改为MainPanel
	 * @author 钱美缘
	 */
	public void changePanel() {
		changePanel(new MainPanel(this));
	}
	
	/**
	 * 
	 * @return 返回主窗口保存的UserVO对象
	 */
	public UserVO getUser() {
		return user;
	}
	/**
	 * 设置窗体是否可用
	 * @param flag 想要设置的状态
	 */
	public void setEnable(boolean flag) {
		mainWindow.setEnabled(flag);
	}
	
	public static void setInfo(String text) {
		infoLabel.setText(text);
	}
	
	public static void setInfo() {
		infoLabel.setText("就绪");
	}
	/**
	 * 关闭窗口
	 */
	protected void close() {
		int response = JOptionPane.showConfirmDialog(null, "将放弃当前未保存的工作，确认要退出？", "Warnning", JOptionPane.YES_NO_OPTION);
		if (response == 0) System.exit(1);
	}
}
