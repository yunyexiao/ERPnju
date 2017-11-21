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
 * ��ͨ������ģ���Ӧ��ͨ��������Ӵ���</br>
 * ͬ������UserType�б����ͣ��������޸�
 * @author Ǯ��Ե
 *
 */
public class AddWindow extends FatherWindow{
	
	public AddWindow(MainWindow mainWindow, DataBL dataBL) {
		super(mainWindow);
		
		//TODO ���ݲ�ͬ�û����ò�ͬ�ı���Ͱ�ť������-------------------------------
		//System.out.println(dataBL.getSubClass());
		if (dataBL.getSubClass() == CustomerBLService.class) {
			frame.setTitle("���ӿͻ�");
			yesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO CustomerVO�Ĵ���
					if (((CustomerBLService) dataBL).add(new CustomerVO())) {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "��ӿͻ���Ϣ�ɹ�", "ϵͳ", JOptionPane.INFORMATION_MESSAGE);
						mainWindow.setEnable(true);
					}	
				}
			});
		}
		else if (dataBL.getSubClass() == UserBL_stub.class) {
			frame.setTitle("�����û�");
			UserDataPanel centerPanel = new UserDataPanel();
			frame.add(centerPanel, BorderLayout.CENTER);
			
			yesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					UserVO user = centerPanel.getUserVO();
					if (user != null || dataBL.add(user)) {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "��ӿͻ���Ϣ�ɹ�", "ϵͳ", JOptionPane.INFORMATION_MESSAGE);
						mainWindow.setEnable(true);
					}	
				}
			});
		}
		//-----------------------------------------------------
		frame.setVisible(true);
	}
}

