package presentation.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.PanelInterface;

/**
 * ������Listener�࣬���ڰ󶨽ӿ�
 * @author Ǯ��Ե
 */
public class Listener implements ActionListener{

	private MainWindow mainWindow;
	private PanelInterface panelImpl;
	/**
	 * 
	 * @param mw �����MainWindow���ã����ڵ���changePanel����
	 * @param panelImpl �����Panel�ӿڣ�������ΪchangePanel�����Ĳ���
	 */
	public Listener(MainWindow mw, PanelInterface panelImpl) {
		mainWindow = mw;
		this.panelImpl =  panelImpl;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.changePanel(panelImpl);
	}
}
