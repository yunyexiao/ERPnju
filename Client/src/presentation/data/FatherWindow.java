package presentation.data;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import presentation.main.MainWindow;

public class FatherWindow {

	protected JDialog frame = new JDialog();
	protected JButton yesButton = new JButton("确定");
	private JButton quitButton = new JButton("取消");
	
	protected FatherWindow(MainWindow mainWindow) {
		frame.setModal(true);
		
		//设置窗体大小及位置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width/2, screenSize.height/2);
		frame.setLocation(screenSize.width/4, screenSize.height/4);
		
		//设置界面布局
		frame.setLayout(new BorderLayout());
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.RIGHT);
		JPanel southPanel = new JPanel(f);
		southPanel.add(yesButton);
		southPanel.add(quitButton);
		frame.add(southPanel, BorderLayout.SOUTH);
		
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.setEnable(true);
				frame.dispose();
			}			
		});
		
		//关闭窗体的适配器方法
		class WindowCloseListener extends WindowAdapter {
			@Override
		    public void windowClosing(WindowEvent e) {
				mainWindow.setEnable(true);
			}
		}
		frame.addWindowListener(new WindowCloseListener());
	}
}
