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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blservice.CustomerBLService;
import blservice.DataBLService;
import presentation.main.MainWindow;
import vo.CustomerVO;
import vo.UserType;
/**
 * 与通用数据模块对应的通用数据添加窗体</br>
 * 同样根据UserType判别类型，可留待修改
 * @author 钱美缘
 *
 */
public class AddWindow {
	private JFrame frame = new JFrame();
	private JButton yesButton = new JButton("确定");
	private JButton quitButton = new JButton("取消");
	
	public AddWindow(MainWindow mainWindow, DataBLService dataBL) {
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
		
		//TODO 根据不同用户设置不同的标题
		if (mainWindow.getUser().getType() == UserType.SALESMAN) {
			frame.setTitle("增加客户");
			yesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (((CustomerBLService) dataBL).add(new CustomerVO())) {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "添加信息成功", "系统", JOptionPane.INFORMATION_MESSAGE);
						mainWindow.setEnable(true);
					}	
				}
			});
		}
		
		//关闭窗体的适配器方法
		class WindowCloseListener extends WindowAdapter {
			@Override
		    public void windowClosing(WindowEvent e) {
				mainWindow.setEnable(true);
			}
		}
		frame.addWindowListener(new WindowCloseListener());
		
		frame.setVisible(true);
	}
}
