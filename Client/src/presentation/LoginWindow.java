package presentation;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import bl_stub.UserBL_stub;
import blservice.LoginBLService;
import layout.TableLayout;
import presentation.main.MainWindow;
import vo.UserVO;

public class LoginWindow {
	private JFrame loginWindow = new JFrame("灯具进销存管理系统-登录界面");
	private JTextField nameField = new JTextField();
	private JPasswordField keyField = new JPasswordField();
	private JButton buttonA = getButton("取消");
	private JButton buttonB = getButton("登录");
	private LoginBLService loginBL = new UserBL_stub();
	
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
		double[][] size = {{0.15,0.15,0.05,TableLayout.FILL,0.05,0.15,0.15},
				{0.2,0.2,border,0.2,border,0.23,0.17}};
		
		//add components
		JPanel panel = (JPanel)loginWindow.getContentPane();
		panel.setLayout(new TableLayout(size));
		panel.setOpaque(false);
        panel.add(getLabel("用户："), "1, 1, r");
        panel.add(nameField, "2, 1, 5, 1");
        panel.add(getLabel("密码："), "1, 3, r");
        panel.add(keyField,"2, 3, 5, 3");
        panel.add(buttonA, "1, 5, 2, 5");
        panel.add(buttonB, "4, 5, 5, 5");
        
        ImageIcon image = new ImageIcon("resource/LoginBG" + (int)(Math.random() * 5) + ".jpg");   
        Image img = image.getImage();  
        img = img.getScaledInstance(loginWindow.getWidth(), loginWindow.getHeight(), Image.SCALE_DEFAULT);  
        image.setImage(img); 
		JLabel label = new JLabel(image);
		label.setBounds(0,0,loginWindow.getWidth(),loginWindow.getHeight());
		loginWindow.getLayeredPane().add(label ,new Integer(Integer.MIN_VALUE)); 
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
				UserVO user = loginBL.getUser(nameField.getText(), new String(keyField.getPassword()));
				if (user == null) {
					nameField.setText("");
					keyField.setText("");
					JOptionPane.showMessageDialog(null, "用户名或密码不正确，请重新输入", "系统消息", JOptionPane.ERROR_MESSAGE);
				} else {
					MainWindow mainWindow = new MainWindow(user);
					loginWindow.dispose();
				}
			}
		});
	}
	

	private JButton getButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("华文行楷", Font.PLAIN, 20));
		return button;
	}
	
	private JLabel getLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("华文行楷", Font.PLAIN, 18));
		return label;
	}
}