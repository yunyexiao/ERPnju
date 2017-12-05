package presentation.billui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import bl_stub.CashCostBillBL_stub;
import blservice.billblservice.CashCostBillBLService;
import businesslogic.CashCostBillBL;
import layout.TableLayout;
import presentation.billui.choosewindow.AccountChooseWin;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import vo.AccountVO;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.CashCostBillVO;
import vo.billvo.PurchaseBillVO;

public class CashCostBillPanel extends BillPanel {

	private CashCostBillBLService cashCostBillBL = new CashCostBillBL_stub();
	
	private JTextField billIdField, operatorField, accountIdField, sumField;
	private MyTableModel itemTable;
	private JTable itemListTable = new JTable(itemTable);
	private boolean editable;
	
	public CashCostBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);
        this.billIdField.setText(cashCostBillBL.getNewId());
        this.operatorField.setText(user.getId());
	}

	public CashCostBillPanel(UserVO user, ActionListener closeListener, CashCostBillVO cashCostBill) {
		super(user, closeListener);
        billIdField.setText(cashCostBill.getAllId());
        operatorField.setText(cashCostBill.getOperator());
        accountIdField.setText(cashCostBill.getAccountId());
        itemListTable.setModel(cashCostBill.getTableModel());
        if(cashCostBill.getState() == BillVO.COMMITED || cashCostBill.getState() == BillVO.PASS){
            editable = false;
        } else {
            editable = true;
        }
	}
	
	@Override
	protected void initBillPanel() {
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus风格，jdk6 update10版本以后的才会出现
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JScrollPane itemListPane;
		JPanel headPanel, accountInfoPanel, centerPanel, itemButtonPanel, southPanel;
		JButton accountChooseButton, itemChooseButton, itemDeleteButton, chooseFinishButton;
		
		headPanel=new JPanel();
		double firstPanelSize[][]={
				{20,55,5,150,20,40,5,150,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		billIdField = new JTextField(10);
		operatorField = new JTextField(10);
	
		billIdField.setEditable(false);
		operatorField.setEditable(false);
		
		headPanel.setLayout(new TableLayout(firstPanelSize));
		headPanel.add(new JLabel("单据编号"),"1,1");
		headPanel.add(billIdField,"3,1");
		headPanel.add(new JLabel("操作人"),"5,1");
		headPanel.add(operatorField,"7,1");
		
		accountInfoPanel=new JPanel();
		accountIdField = new JTextField(10);
		accountIdField.setText("编号");
		
		accountIdField.setEditable(false);
		double secondPanelSize[][]={
				{20,45,5,70,12,100,5,60,TableLayout.FILL},
				{8,25,TableLayout.FILL}
		};
		accountChooseButton=new JButton("选择");
		accountChooseButton.addActionListener(e -> handleChooseAccount());
		accountInfoPanel.setLayout(new TableLayout(secondPanelSize));
		accountInfoPanel.add(new JLabel("账户"),"1,1");
		accountInfoPanel.add(accountIdField,"3,1");
	    accountInfoPanel.add(accountChooseButton,"5,1");
		
		String[] itemListAttributes={"条目名", "金额", "备注"};
		String[][] itemInfo={{"买女装","10000","公款私用"}, {"大保健", "800", ""}};
		itemTable = new MyTableModel(itemInfo, itemListAttributes);
		itemListTable = new JTable(itemTable);
		itemListPane = new JScrollPane(itemListTable);
		
		itemButtonPanel=new JPanel();
		double forthPanelSize[][]={
				{85,TableLayout.FILL},
				{25,10,25,10,25,10,TableLayout.FILL},
		};
		itemChooseButton=new JButton("新建条目");
        itemChooseButton.addActionListener(e -> addItem());
		itemDeleteButton=new JButton("删除条目");
        itemDeleteButton.addActionListener(e -> deleteItem());
		chooseFinishButton=new JButton("选择完成");
		chooseFinishButton.addActionListener(e -> sumUp());

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
		
		southPanel = new JPanel();
		double[][] size = {
                {20.0, -2.0, 10.0, 100.0, -1.0, -1.0},
                {10.0, -1.0, 10.0, -1.0, -1.0}
        };
        southPanel.setLayout(new TableLayout(size));
		sumField = new JTextField(20);
		sumField.setEditable(false);
		southPanel.add(new JLabel("总额"),"1,1");
		southPanel.add(sumField,"3,1");
		
		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.1, 0.1, 0.5,0.1,0.15}	
		};
		billPanel.setLayout(new TableLayout(mainPanelSize));
		billPanel.add(headPanel, "0,0");
		billPanel.add(accountInfoPanel, "0,1");
		billPanel.add(centerPanel, "0,2");
		billPanel.add(southPanel, "0,4");

	}

	private void addItem(){
        if(!editable) return;
        String[] newRow = new InputCashCostItemInfoWin().getRowData();
        if(newRow == null) return;
        MyTableModel model = (MyTableModel) itemListTable.getModel();
        model.addRow(newRow);
    }
	
    private void deleteItem(){
        if(!editable) return;
	    int row = itemListTable.getSelectedRow();
	    if(row < 0) return;
        ((MyTableModel)itemListTable.getModel()).removeRow(itemListTable.getSelectedRow());
    }
	
    private void handleChooseAccount(){
        if(!editable) return;
        AccountVO a = new AccountChooseWin().getAccount();
        if(a == null) return;
        accountIdField.setText(a.getNumber());
     //   accountNameField.setText(a.getName());
    }
    
    protected void sumUp(){
        if(!editable) return;
	    MyTableModel model = (MyTableModel)this.itemListTable.getModel();
        double total = 0;
	    for(int i = 0; i < model.getRowCount(); i++){
	        total += Double.parseDouble((String)model.getValueAt(i, 1));
	    }
	    sumField.setText(Double.toString(total));
    }
    
    protected void clear(){
        billIdField.setText("");
        operatorField.setText("");
        accountIdField.setText("");
        sumField.setText("");
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
            billIdField.setText(cashCostBillBL.getNewId());
            operatorField.setText(this.getUser().getName());
        };
	}
	
	@Override
	protected ActionListener getSaveActionListener() {
		return e ->{
            if(!editable) return;
            CashCostBillVO bill = getBill(BillVO.SAVED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
                return;
            }
            if(cashCostBillBL.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "单据已保存。");
            }
        };
	}

	@Override
	protected ActionListener getCommitActionListener() {
		return e ->{
            if(!editable) return;
            CashCostBillVO bill = getBill(BillVO.COMMITED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
                return;
            }
            if(cashCostBillBL.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "单据已提交。");
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
	public CashCostBillVO getBill(int state) {
		if (! isCorrectable()) {
			return null;
		}
		Timetools.check();
		return new CashCostBillVO(Timetools.getDate(), Timetools.getTime(), cashCostBillBL.getNewId(), operatorField.getText(), state, accountIdField.getText());
	}
}
