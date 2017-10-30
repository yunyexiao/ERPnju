package presentation.bill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import blservice.BillBLService;
import businesslogic.BillBL_stub;
import layout.TableLayout;
import presentation.main.MainWindow;
import presentation.PanelInterface;
import presentation.component.TopButtonPanel;
import vo.UserVO;

public class BillPanel implements PanelInterface {
	private MainWindow mainWindow;
	private UserVO user;
	private BillBLService billBL;
	private JPanel panel = new JPanel();
	private TopButtonPanel buttonPanel = new TopButtonPanel();
	private CentrePanel mainPanel;

	public BillPanel(MainWindow mainwindow, int type, boolean editable){
		this.mainWindow = mainwindow;
		this.user = this.mainWindow.getUser();
		billBL = new BillBL_stub(user);
		mainPanel = new CentrePanel(CentrePanel.PURCHASE, editable);
		mainPanel.setPanel(billBL.getTableModel());
		
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel.setLayout(new TableLayout(size));
		
		class CloseListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainwindow.changePanel();
			}
		}
		
		buttonPanel.addButton("�½�", new ImageIcon("resource/New.png"), new CloseListener());
		buttonPanel.addButton("����", new ImageIcon("resource/Save.png"), new CloseListener());
		buttonPanel.addButton("�ύ", new ImageIcon("resource/Commit.png"), new CloseListener());
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), new CloseListener());
		panel.add(buttonPanel.getPanel(), "0, 0");
		panel.add(mainPanel.getPanel(), "0, 1");
	}
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void init(UserVO user) {
		// TODO Auto-generated method stub

	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
