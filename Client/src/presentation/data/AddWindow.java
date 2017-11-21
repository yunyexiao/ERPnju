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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blservice.CustomerBLService;
import businesslogic.DataBL;
import businesslogic.UserBL_stub;
import presentation.main.MainWindow;
import vo.CustomerVO;
import vo.UserVO;
/**
 * 与通用数据模块对应的通用数据添加窗体</br>
 * 同样根据UserType判别类型，可留待修改
 * @author 钱美缘
 *
 */
public class AddWindow extends FatherWindow{
	
	public AddWindow(MainWindow mainWindow, DataBL dataBL) {
		super(mainWindow);
		
		//TODO 根据不同用户设置不同的标题和按钮监听器-------------------------------
		//System.out.println(dataBL.getSubClass());
		if (dataBL.getSubClass() == CustomerBLService.class) {
			frame.setTitle("增加客户");
			yesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO CustomerVO的创建
					if (((CustomerBLService) dataBL).add(new CustomerVO())) {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "添加客户信息成功", "系统", JOptionPane.INFORMATION_MESSAGE);
						mainWindow.setEnable(true);
					}	
				}
			});
		}
		else if (dataBL.getSubClass() == UserBL_stub.class) {
			frame.setTitle("增加用户");
			UserDataPanel centerPanel = new UserDataPanel();
			frame.add(centerPanel, BorderLayout.CENTER);
			
			yesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					UserVO user = centerPanel.getUserVO();
					if (user != null || dataBL.add(user)) {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "添加客户信息成功", "系统", JOptionPane.INFORMATION_MESSAGE);
						mainWindow.setEnable(true);
					}	
				}
			});
		}
		//-----------------------------------------------------
		frame.setVisible(true);
	}
}

