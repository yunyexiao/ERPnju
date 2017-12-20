package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import presentation.component.choosewindow.AccountChooseWin;
import presentation.tools.Timetools;
import vo.AccountVO;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.CashCostBillVO;

public class CashCostBillPanel extends BillPanel {

	private CashCostBillBLService cashCostBillBL = new CashCostBillBL();
	
	private JTextField billIdField, operatorField, accountIdField, sumField;
	private JButton accountChooseButton, itemChooseButton, itemDeleteButton;
	private JTable itemListTable;
	
	public CashCostBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);
        this.billIdField.setText(cashCostBillBL.getNewId());
        this.operatorField.setText(user.getName());
	}

	public CashCostBillPanel(UserVO user, ActionListener closeListener, CashCostBillVO cashCostBill) {
		super(user, closeListener, cashCostBill);
        billIdField.setText(cashCostBill.getAllId());
        operatorField.setText(cashCostBill.getOperator());
        accountIdField.setText(cashCostBill.getAccountId());
        itemListTable.setModel(cashCostBill.getTableModel());
        sumUp();
	}
	
	@Override
	protected void initBillPanel() {
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus风格，jdk6 update10版本以后的才会出现
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JScrollPane itemListPane;
		JPanel headPanel, accountInfoPanel, centerPanel, itemButtonPanel;
		
		double firstPanelSize[][]={
				{20,55,5,TableLayout.PREFERRED,20,55,5,TableLayout.PREFERRED,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		billIdField = new JTextField(15);
		operatorField = new JTextField(10);
		billIdField.setEditable(false);
		operatorField.setEditable(false);
		headPanel = new JPanel(new TableLayout(firstPanelSize));
		headPanel.add(new JLabel("单据编号"),"1,1");
		headPanel.add(billIdField,"3,1");
		headPanel.add(new JLabel("操作人"),"5,1");
		headPanel.add(operatorField,"7,1");
		
		accountIdField = new JTextField(20);
		accountIdField.setText("");
		accountIdField.setEditable(false);
		double secondPanelSize[][]={
				{20,55,5,TableLayout.PREFERRED,5,TableLayout.PREFERRED,TableLayout.FILL},
				{8,25,TableLayout.FILL}
		};
		accountChooseButton=new JButton("选择");
		accountChooseButton.addActionListener(e -> handleChooseAccount());
		accountInfoPanel = new JPanel(new TableLayout(secondPanelSize));
		accountInfoPanel.add(new JLabel("账户"),"1,1");
		accountInfoPanel.add(accountIdField,"3,1");
	    accountInfoPanel.add(accountChooseButton,"5,1");
		
		String[] itemListAttributes={"条目名", "金额", "备注"};
		itemListTable = new JTable(new MyTableModel(null, itemListAttributes));
		itemListTable.getTableHeader().setReorderingAllowed(false);
		itemListPane = new JScrollPane(itemListTable);
		
		double forthPanelSize[][]={
				{TableLayout.PREFERRED,TableLayout.FILL},
				{25,10,25,10,25,10,25,TableLayout.FILL},
		};
		itemChooseButton=new JButton("新建条目", new ImageIcon("resource/AddButton.png"));
        itemChooseButton.addActionListener(e -> addItem());
		itemDeleteButton=new JButton("删除条目", new ImageIcon("resource/DeleteButton.png"));
        itemDeleteButton.addActionListener(e -> deleteItem());
		sumField = new JTextField(10);
		sumField.setEditable(false);
		sumField.setText("0.0");
		itemButtonPanel = new JPanel(new TableLayout(forthPanelSize));
		itemButtonPanel.add(itemChooseButton, "0,0");
		itemButtonPanel.add(itemDeleteButton, "0,2");
		itemButtonPanel.add(new JLabel("总额："), "0,4");
		itemButtonPanel.add(sumField, "0,6");

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
				{0.08, 0.08, 0.6, TableLayout.FILL}
		};
		billPanel.setLayout(new TableLayout(mainPanelSize));
		billPanel.add(headPanel, "0,0");
		billPanel.add(accountInfoPanel, "0,1");
		billPanel.add(centerPanel, "0,2");

	}

	private void addItem(){
        String[] newRow = new InputCashCostItemInfoWin().getRowData();
        if (newRow != null) {
            MyTableModel model = (MyTableModel) itemListTable.getModel();
            model.addRow(newRow);
        	sumUp();
        } 
    }
	
    private void deleteItem(){
	    int row = itemListTable.getSelectedRow();
	    if(row >= 0) {
	    	((MyTableModel)itemListTable.getModel()).removeRow(itemListTable.getSelectedRow());
	    	sumUp();
	    } else new InfoWindow("请选择删除的条目");
    }
	
    private void handleChooseAccount(){
        AccountVO a = new AccountChooseWin().getAccount();
        if(a == null) return;
        accountIdField.setText(a.getNumber());
    }
    
    protected void sumUp(){
	    MyTableModel model = (MyTableModel)this.itemListTable.getModel();
        double total = 0;
	    for(int i = 0; i < model.getRowCount(); i++){
	        total += Double.parseDouble((String)model.getValueAt(i, 1));
	    }
	    sumField.setText(Double.toString(total));
    }
    
    protected void clear(){
        billIdField.setText(cashCostBillBL.getNewId());
        accountIdField.setText("");
        sumField.setText("0.0");
        MyTableModel model = (MyTableModel) itemListTable.getModel();
        while(model.getRowCount() > 0){
            model.removeRow(0);
        }
    }
    
	@Override
	protected ActionListener getNewActionListener() {
		return e -> {
            int response = JOptionPane.showConfirmDialog(
                null, "确认要新建一张现金费用单吗？", "提示", JOptionPane.YES_NO_OPTION);
            if(response == 1)return;
            clear();
        };
	}
	
	@Override
	protected ActionListener getSaveActionListener() {
		return e ->{
            CashCostBillVO bill = getBill(BillVO.SAVED);
            if (bill != null && cashCostBillBL.saveBill(bill)) JOptionPane.showMessageDialog(null, "单据已保存。");
        };
	}

	@Override
	protected ActionListener getCommitActionListener() {
		return e ->{
            CashCostBillVO bill = getBill(BillVO.COMMITED);
            if (bill != null && cashCostBillBL.saveBill(bill)) JOptionPane.showMessageDialog(null, "单据已提交。");
        };
	}
	@Override
	protected boolean isCorrectable() {
		if (itemListTable.getRowCount() == 0) new InfoWindow("没有选择条目");
		else if ("".equals(accountIdField.getText())) new InfoWindow("没有选择账户");
		else return true;
		return false;
	}
	@Override
	protected void setEditable(boolean b) {
		super.setEditable(b);
		accountChooseButton.setEnabled(b);
		itemChooseButton.setEnabled(b);
		itemDeleteButton.setEnabled(b);
	}
	/**
	 * 获得单据VO
	 * @return
	 */
	public CashCostBillVO getBill(int state) {
		if (! isCorrectable()) return null;
		Timetools.check();
		return new CashCostBillVO(Timetools.getDate(), Timetools.getTime(), cashCostBillBL.getNewId(), operatorField.getText(), state, accountIdField.getText(),(MyTableModel) itemListTable.getModel());
	}
}
