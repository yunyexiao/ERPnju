package presentation.billui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import blservice.billblservice.CashCostBillBLService;
import businesslogic.CashCostBillBL;
import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import vo.UserVO;
import vo.billvo.CashCostBillVO;

public class CashCostBillPanel extends BillPanel {

	private CashCostBillBLService cashCostBillBL;
	
	private JTextField billIdField, operaterField, accountIdField;
	private JTable itemListTable = new JTable();
	
	public CashCostBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initBillPanel() {
		cashCostBillBL = new CashCostBillBL();
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus风格，jdk6 update10版本以后的才会出现
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JScrollPane itemListPane;
		JPanel headPanel, accountInfoPanel, centerPanel, itemButtonPanel;
		JButton accountChooseButton, itemChooseButton, itemDeleteButton, chooseFinishButton;
		
		billIdField = new JTextField(10);
		operaterField = new JTextField(10);
		accountIdField = new JTextField(10);
		accountIdField.setText("编号");
		
		billIdField.setEditable(false);
		operaterField.setEditable(false);
		accountIdField.setEditable(false);
		
		headPanel=new JPanel();
		double firstPanelSize[][]={
				{20,55,5,150,20,40,5,150,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		headPanel.setLayout(new TableLayout(firstPanelSize));
		headPanel.add(new JLabel("单据编号"),"1,1");
		headPanel.add(billIdField,"3,1");
		headPanel.add(new JLabel("操作人"),"5,1");
		headPanel.add(operaterField,"7,1");
		
		accountInfoPanel=new JPanel();
		double secondPanelSize[][]={
				{20,45,5,70,12,100,5,60,TableLayout.FILL},
				{8,25,TableLayout.FILL}
		};
		accountChooseButton=new JButton("选择");	
		accountInfoPanel.setLayout(new TableLayout(secondPanelSize));
		accountInfoPanel.add(new JLabel("客户"),"1,1");
		accountInfoPanel.add(accountIdField,"3,1");
	    accountInfoPanel.add(accountChooseButton,"5,1");
		
		String[] itemListAttributes={"条目名", "金额", "备注"};
		String[][] itemInfo={{"买女装","10000","公款私用"}, {"大保健", "800", ""}};
		itemListTable = new JTable(new MyTableModel(itemInfo, itemListAttributes));
		itemListPane = new JScrollPane(itemListTable);

		itemChooseButton=new JButton("新建条目");
		//todo:新建条目window
		itemDeleteButton=new JButton("删除条目");
		chooseFinishButton=new JButton("选择完成");
		
		itemButtonPanel=new JPanel();
		double forthPanelSize[][]={
				{85,TableLayout.FILL},
				{25,10,25,10,25,10,TableLayout.FILL},
		};
		itemButtonPanel.setLayout(new TableLayout(forthPanelSize));
		itemButtonPanel.add(itemChooseButton, "0,0");
		itemButtonPanel.add(itemDeleteButton, "0,2");
		itemButtonPanel.add(chooseFinishButton, "0,4");

		centerPanel=new JPanel();
		double centerPanelSize[][]={
				{20,0.8,20,0.2},
				{10,25,5,TableLayout.FILL}
		};
		centerPanel.setLayout(new TableLayout(centerPanelSize));
		centerPanel.add(new JLabel("条目列表"),"1,1");
		centerPanel.add(itemListPane, "1,3");
		centerPanel.add(itemButtonPanel, "3,3");
		
		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.1, 0.1, 0.5}	
		};
		billPanel.setLayout(new TableLayout(mainPanelSize));
		billPanel.add(headPanel, "0,0");
		billPanel.add(accountInfoPanel, "0,1");
		billPanel.add(centerPanel,"0,2");

	}

	@Override
	protected ActionListener getNewActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			}
		};
	}

	@Override
	protected ActionListener getSaveActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CashCostBillVO bill = getBill();
				if (bill != null) {
					bill.setState(CashCostBillVO.SAVED);
					if (cashCostBillBL.saveBill(bill)) new InfoWindow("单据保存成功");
					else new InfoWindow("单据保存失败");
				}
			}
		};
	}

	@Override
	protected ActionListener getCommitActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CashCostBillVO bill = getBill();
				if (bill != null) {
					bill.setState(CashCostBillVO.COMMITED);
					if (cashCostBillBL.saveBill(bill)) new InfoWindow("单据提交成功");
					else new InfoWindow("单据提交失败");
				}
			}
		};
	}

	@Override
	protected boolean isCorrectable() {
		//todo:不知道是否还应该加些什么
		if (itemListTable.getRowCount() == 0) {new InfoWindow("没有选择条目");return false;}
		return true;

	}

	/**
	 * 获得单据VO
	 * @return
	 */
	public CashCostBillVO getBill() {
		if (! isCorrectable()) {
			return null;
		}
		Timetools.check();
		return new CashCostBillVO(Timetools.getDate(), Timetools.getTime(), cashCostBillBL.getNewId(), operaterField.getText(), CashCostBillVO.DRAFT, accountIdField.getText());
	}
}
