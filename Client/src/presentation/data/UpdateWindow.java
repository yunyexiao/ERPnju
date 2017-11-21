package presentation.data;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import businesslogic.DataBL;
import businesslogic.UserBL_stub;
import presentation.main.MainWindow;
import vo.UserVO;

public class UpdateWindow extends FatherWindow {

	public UpdateWindow(MainWindow mainWindow, DataBL dataBL, String[] data) {
		super(mainWindow);
		
		if (dataBL.getSubClass() == UserBL_stub.class) {
			frame.setTitle("�޸��û�");
			UserDataPanel centerPanel = new UserDataPanel(data);
			frame.add(centerPanel, BorderLayout.CENTER);
			
			yesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					UserVO user = centerPanel.getUserVO();
					if (user != null || dataBL.change(user)) {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "�޸Ŀͻ���Ϣ�ɹ�", "ϵͳ", JOptionPane.INFORMATION_MESSAGE);
						mainWindow.setEnable(true);
					}	
				}
			});
		}
	}

}
