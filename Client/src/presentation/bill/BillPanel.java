package presentation.bill;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import blservice.BillBLService;
import businesslogic.BillBL_stub;
import layout.TableLayout;
import presentation.main.MainWindow;
import presentation.PanelInterface;
import presentation.component.CloseListener;
import presentation.component.Listener_stub;
import presentation.component.TopButtonPanel;
import vo.BillType;
import vo.UserVO;

public class BillPanel implements PanelInterface {
	private MainWindow mainWindow;
	private UserVO user;
	private BillBLService billBL;
	private JPanel panel = new JPanel();
	private TopButtonPanel buttonPanel = new TopButtonPanel();
	private CentrePanel mainPanel;

	public BillPanel(MainWindow mainwindow, BillType type, boolean editable){
		this.mainWindow = mainwindow;
		this.user = this.mainWindow.getUser();
		billBL = new BillBL_stub(user);
		mainPanel = new CentrePanel(type, editable);
		mainPanel.setPanel(billBL.getTableModel());
		
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel.setLayout(new TableLayout(size));
		
		buttonPanel.addButton("新建", new ImageIcon("resource/New.png"), new Listener_stub());
		buttonPanel.addButton("保存", new ImageIcon("resource/Save.png"), new Listener_stub());
		buttonPanel.addButton("提交", new ImageIcon("resource/Commit.png"), new Listener_stub());
		buttonPanel.addButton("关闭", new ImageIcon("resource/Close.png"), new CloseListener(mainWindow));
		panel.add(buttonPanel.getPanel(), "0, 0");
		panel.add(mainPanel.getPanel(), "0, 1");
	}
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
