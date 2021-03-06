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
import blservice.billblservice.BillOperationService;
import businesslogic.BillBL;
import businesslogic.BillOperationBL;
import businesslogic.UserBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.billui.BillPanelHelper;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.dataui.userui.UpdateUserWindow;
import vo.UserType;
import vo.UserVO;
import vo.billvo.BillVO;

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
	private BillOperationService billOperationBL;
	private JPanel panel= new JPanel(new TableLayout(size)); 
	private JPanel infoPanel = new JPanel();
	private JPanel bPanel = new JPanel();
	private JLabel infoLabel;
	private JTable table;

	public MainPanel(MainWindow mainWindow) {
		UserVO user = mainWindow.getUser();
		billBL = new BillBL();
		billOperationBL = new BillOperationBL();
		
		infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		String htmltxt = "<html><span style=\"font-size:32px;\">欢迎使用灯具进销存管理系统:</span><br/> "
				+ "<span style=\"font-size:16px;\">&emsp;&emsp;您当前的登录身份为：" + user.getType().getName() + "</span><br/>" 
				+ "<span style=\"font-size:16px;\">&emsp;&emsp;您的姓名： " + user.getName() + "</span></html>";
		infoLabel = new JLabel(htmltxt);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoPanel.add(infoLabel);
		infoPanel.setOpaque(false);
		
		bPanel.setLayout(new TableLayout(
				new double[][]{{TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL},
					{TableLayout.PREFERRED, TableLayout.FILL}}));
		JButton changeButton = new JButton("修改个人信息");
		changeButton.addActionListener(e->{
			new UpdateUserWindow(new UserBL(user), new String[]{
					user.getId(),user.getName(),user.getType().getName(),user.getRankName(),user.getPwd(),user.getSex(),""+user.getAge(),user.getTelNumber()}, true);
		});
		bPanel.add(changeButton, "1,0");
		bPanel.setOpaque(false);
		
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
			billDeleteButton.addActionListener(e->{
				String info = (String) tabelModel.getValueAt(table.getSelectedRow(), 1);
				BillVO bill = billBL.getBill(info);
				if (bill.getState() == BillVO.SAVED) {
					if (billOperationBL.deleteBill(info)) {
						table.setModel(billBL.getBillTable(user));
						new InfoWindow("删除成功");
					} else new InfoWindow("删除失败");
				} else {
					new InfoWindow("该状态单据无法删除");
				}
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
		panel.add(bPanel, "1,0");
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
