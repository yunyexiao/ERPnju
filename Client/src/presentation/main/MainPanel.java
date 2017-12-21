package presentation.main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.billblservice.BillBLService;
import businesslogic.BillBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.billui.BillPanelHelper;
import presentation.billui.CashCostBillPanel;
import presentation.billui.ChangeBillPanel;
import presentation.billui.PurchaseBillPanel;
import presentation.billui.PurchaseReturnBillPanel;
import presentation.billui.ReceiptOrPaymentBillPanel;
import presentation.billui.SalesBillPanel;
import presentation.billui.SalesReturnBillPanel;
import presentation.component.MyTableModel;
import vo.UserType;
import vo.UserVO;

/**
 * 主界面（单据管理）界面类，分两种情况<br/>
 * 对于单据制定人员，显示未完成的单据……<br/>
 * 对于总经理，显示等待审批的单据（仅能显示）<br/>
 * 对于系统管理员。。。待定
 * @author 钱美缘
 *
 */
public class MainPanel implements PanelInterface {

	private final double[][] size = {{0.88,0.12},{0.4, 0.6}};
	private BillBLService billBL;
	private JPanel panel= new JPanel(new TableLayout(size)); 
	private JPanel infoPanel = new JPanel();
	private JLabel infoLabel;
	private JTable table;

	public MainPanel(MainWindow mainWindow) {
		UserVO user = mainWindow.getUser();
		billBL = new BillBL();
		
		infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		String htmltxt = "<html><span style=\"font-size:32px;\">欢迎使用灯具进销存管理系统:</span><br/> "
				+ "<span style=\"font-size:16px;\">&emsp;&emsp;您当前的登录身份为：" + user.getType().getName() + "</span><br/>" 
				+ "<span style=\"font-size:16px;\">&emsp;&emsp;您的姓名： " + user.getName() + "</span></html>";
		infoLabel = new JLabel(htmltxt);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoPanel.add(infoLabel);
		infoPanel.setOpaque(false);
		
		if (user.getType() != UserType.ADMIN) {
			MyTableModel tabelModel = billBL.getBillTable(user);
			table = new JTable(tabelModel);
			table.getTableHeader().setReorderingAllowed(false);
			JScrollPane scrollPane = new JScrollPane(table);
			
			ActionListener closeListener = new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                mainWindow.changePanel();
	            }
			};
			
			JPanel buttonPanel=new JPanel();
			JButton billChangeButton = new JButton("修改单据", new ImageIcon("resource/FreshButton.png"));
			JButton billDeleteButton = new JButton("删除单据", new ImageIcon("resource/DeleteButton.png"));
			BillPanelHelper.user = user;
			BillPanelHelper.closeListener = closeListener;
			billChangeButton.addActionListener(e->{
				String[] info = tabelModel.getValueAtRow(table.getSelectedRow());
				mainWindow.changePanel(BillPanelHelper.create(billBL.getBill(info[1])));
			});
			double forthPanelSize[][]={
					{10,TableLayout.FILL,10},
					{10,30,10,30,TableLayout.FILL}
			};
			buttonPanel.setLayout(new TableLayout(forthPanelSize));
			buttonPanel.add(billChangeButton, "1,1");
			buttonPanel.add(billDeleteButton, "1,3");

			panel.add(scrollPane, "0,1");
			panel.add(buttonPanel, "1,1");
		} else {
			JPanel p = new JPanel();
			p.setBackground(Color.WHITE);
			panel.add(p, "0,1,1,1");
		}
		panel.setVisible(true);
		panel.add(infoPanel, "0,0,1,0");
	}

	@Override
	public boolean close() {
		return true;
	}
	
	@Override
	public JPanel getPanel() {
		return panel;
	}
	
	public void setBackground() {
		ImageIcon image = new ImageIcon("resource/MainBG.jpg");   
        Image img = image.getImage();  
        img = img.getScaledInstance(infoPanel.getWidth(), infoPanel.getHeight(), Image.SCALE_DEFAULT);  
        image.setImage(img);
		panel.add(new JLabel(image), "0,0,1,0");
	}
}
