package presentation.main;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.BillBLService;
import businesslogic.BillBL_stub;
import layout.TableLayout;
import presentation.PanelInterface;
import vo.UserVO;

public class MainPanel implements PanelInterface {
	//private MainWindow mainWindow;
	private final double[][] size = {{0.88,0.12},{0.4, 0.6}};
	private BillBLService billBL;
	private JPanel panel= new JPanel(new TableLayout(size)); 
	private JPanel infoPanel = new JPanel();
	private JLabel infoLabel;
	private JTable table;

	public MainPanel(MainWindow mainWindow) {
		UserVO user = mainWindow.getUser();
		billBL = new BillBL_stub(user);
		
		infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		String htmltxt = "<html><span style=\"font-size:32px;\"><em>欢迎使用灯具进销存管理系统:</em></span><br/> "
				+ "<span style=\"font-size:20px;\">您当前的登录身份为：" + user.getType().getName() + "</span><br/>" 
				+ "<span style=\"font-size:20px;\">您的姓名： " + user.getName() + "</span></html>";
		infoLabel = new JLabel(htmltxt);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoPanel.add(infoLabel);
		
		MainPanelTable model = new MainPanelTable(billBL.getBillInfo(), new String[]{"制定时间","单据编号","单据类型","状态"});
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		
		JPanel buttonPanel=new JPanel();
		JButton billChangeButton = new JButton("修改单据", new ImageIcon("resource/FreshButton.png"));
		JButton billDeleteButton = new JButton("删除单据", new ImageIcon("resource/DeleteButton.png"));
		double forthPanelSize[][]={
				{10,TableLayout.FILL,10},
				{10,30,10,30,TableLayout.FILL},
		};
		buttonPanel.setLayout(new TableLayout(forthPanelSize));
		buttonPanel.add(billChangeButton, "1,1");
		buttonPanel.add(billDeleteButton, "1,3");
		
		panel.setVisible(true);
		panel.add(infoPanel, "0,0,1,0");
		panel.add(scrollPane, "0,1");
		panel.add(buttonPanel, "1,1");
	}

	@Override
	public boolean close() {
		return true;
	}
	
	@Override
	public JPanel getPanel() {
		return panel;
	}
}
