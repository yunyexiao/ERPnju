package presentation.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.main.MainWindow;
/**
 * ���ر���Panel�ļ�����������Ϊһ����</br>
 * Ҳ��д�ɵ���ģʽ����ã������������˼��
 * @author Ǯ��Ե
 */
public class CloseListener implements ActionListener {

	private static MainWindow mw;
	
	public CloseListener(MainWindow mainWindow) {
		mw = mainWindow;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		mw.changePanel();
	}

}
