package presentation.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.PanelInterface;

/**
 * 单独的Listener类，便于绑定接口
 * @author 钱美缘
 */
public class Listener implements ActionListener{

	private MainWindow mainWindow;
	private PanelInterface panelImpl;
	/**
	 * 
	 * @param mw 传入的MainWindow引用，用于调用changePanel方法
	 * @param panelImpl 传入的Panel接口，用于作为changePanel方法的参数
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
