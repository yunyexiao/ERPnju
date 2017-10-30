package presentation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import layout.TableLayout;
import presentation.main.MainWindow;
import vo.UserType;
import vo.UserVO;

public class LoginWindow {
	private JFrame loginWindow = new JFrame("灯具进销存管理系统-登录界面");
	private JTextField nameField = new JTextField();
	private JTextField keyField = new JTextField();
	private JButton buttonA = new JButton("取消");
	private JButton buttonB = new JButton("登录");
	
	public LoginWindow() {
    	try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
        JFrame.setDefaultLookAndFeelDecorated(true);
        
		//set size of form according to screen's size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		loginWindow.setSize((int) (screenSize.width * 0.3), (int) (screenSize.height * 0.27));
		loginWindow.setLocation(screenSize.width / 2 - loginWindow.getWidth() / 2, screenSize.height / 2 - loginWindow.getHeight() / 2);
		
		//other setting
		loginWindow.setResizable(false);
		loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginWindow.setIconImage(new ImageIcon("resource/LoginIcon.png").getImage());
		int border = 15;
		double[][] size = {{0.15,0.15,TableLayout.FILL,0.15,0.15},
				{0.2,0.2,border,0.2,border,0.2,0.2}};
		loginWindow.setLayout(new TableLayout(size));
		
		//add components
		loginWindow.add(new JLabel("用户名："), "1, 1, r");
		loginWindow.add(nameField, "2, 1,3,1");
		loginWindow.add(new JLabel("密码： "), "1, 3, r");
		loginWindow.add(keyField,"2, 3, 3, 3");
		loginWindow.add(buttonA, "1, 5");
		loginWindow.add(buttonB, "3, 5");
		loginWindow.setVisible(true);
		
		//add listener
		buttonA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		buttonB.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow mainwindow = new MainWindow(new UserVO("他", UserType.SALESMAN));
				loginWindow.dispose();
			}
		});
	}
}
