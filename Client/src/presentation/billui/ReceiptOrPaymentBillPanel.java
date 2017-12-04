package presentation.billui;

import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import bl_stub.PaymentBillBL_stub;
import bl_stub.ReceiptBillBL_stub;
import blservice.billblservice.PaymentBillBLService;
import blservice.billblservice.ReceiptBillBLService;
import layout.TableLayout;
import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.ReceiptBillVO;

public class ReceiptOrPaymentBillPanel extends BillPanel {

	private ReceiptBillBLService receiptBillBL = new ReceiptBillBL_stub();
	private PaymentBillBLService paymentBillBL = new PaymentBillBL_stub();

	private JTextField billIdField, customerIdField, operaterField;//考虑之后把客户填写内容改为选择内容
	private JTable transferListTable = new JTable();
	private ButtonGroup typeButtonGroup;
	private boolean editable;
	
	public ReceiptOrPaymentBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);
		editable = true;
		operaterField.setText(this.getUser().getName());
	}

	public ReceiptOrPaymentBillPanel(UserVO user, ReceiptBillVO bill, ActionListener closeListener) {
		super(user, closeListener);
		billIdField.setText(bill.getAllId());
        operaterField.setText(bill.getOperator());
        customerIdField.setText(bill.getCustomerId());
        transferListTable.setModel(bill.getTableModel());
		if(bill.getState() == BillVO.COMMITED || bill.getState() == BillVO.PASS){
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
		
		JScrollPane transferListPane;
		JPanel headPanel, customerInfoPanel,centerPanel,transferButtonPanel;
		JButton customerChooseButton, transferChooseButton, transferDeleteButton, chooseFinishButton;
		
		JRadioButton receiptRadioButton = new JRadioButton("收款单");
	    JRadioButton paymentRadioButton = new JRadioButton("付款单");
	    receiptRadioButton.setSelected(true);
		JPanel typePanel = new JPanel();
		typePanel.add(receiptRadioButton);
		typePanel.add(paymentRadioButton);
		typeButtonGroup = new ButtonGroup();
		typeButtonGroup.add(receiptRadioButton);
		typeButtonGroup.add(paymentRadioButton);
		
		billIdField = new JTextField(10);
	/*	if (receiptRadioButton.isSelected()) {
			billIdField.setText(receiptBillBL.getNewId());
		}else {
			billIdField.setText(paymentBillBL.getNewId());
		}*/
		operaterField = new JTextField(10);
		customerIdField = new JTextField(10);
		customerIdField.setText("编号");
		
		billIdField.setEditable(false);
		operaterField.setEditable(false);
		customerIdField.setEditable(false);
		
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
		
		customerInfoPanel=new JPanel();
		double secondPanelSize[][]={
				{20,45,5,70,12,100,5,60,TableLayout.FILL},
				{8,25,TableLayout.FILL}
		};
		customerChooseButton=new JButton("选择");	
		customerInfoPanel.setLayout(new TableLayout(secondPanelSize));
		customerInfoPanel.add(new JLabel("客户"),"1,1");
		customerInfoPanel.add(customerIdField,"3,1");
		customerInfoPanel.add(new JLabel("--"),"4,1");
		customerInfoPanel.add(customerChooseButton,"5,1");
		
		String[] transferListAttributes={"银行账户", "转账金额", "备注"};
		String[][] transferInfo={{"mayun0001","100000","赎金"}};
		transferListTable = new JTable(new MyTableModel(transferInfo, transferListAttributes));
		transferListPane = new JScrollPane(transferListTable);

		transferChooseButton=new JButton("选择转账");
		transferDeleteButton=new JButton("删除转账");
		chooseFinishButton=new JButton("选择完成");
		
		transferButtonPanel=new JPanel();
		double forthPanelSize[][]={
				{85,TableLayout.FILL},
				{25,10,25,10,25,10,TableLayout.FILL},
		};
		transferButtonPanel.setLayout(new TableLayout(forthPanelSize));
		transferButtonPanel.add(transferChooseButton, "0,0");
		transferButtonPanel.add(transferDeleteButton, "0,2");
		transferButtonPanel.add(chooseFinishButton, "0,4");

		centerPanel=new JPanel();
		double centerPanelSize[][]={
				{20,0.8,20,0.2},
				{10,25,5,TableLayout.FILL}
		};
		centerPanel.setLayout(new TableLayout(centerPanelSize));
		centerPanel.add(new JLabel("转账列表"),"1,1");
		centerPanel.add(transferListPane, "1,3");
		centerPanel.add(transferButtonPanel, "3,3");
		
		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.1, 0.1, 0.5}	
		};
		billPanel.setLayout(new TableLayout(mainPanelSize));
		billPanel.add(headPanel, "0,0");
		billPanel.add(customerInfoPanel, "0,1");
		billPanel.add(centerPanel,"0,2");
	}

	@Override
	protected ActionListener getNewActionListener() {
		 return e -> {
	            int response = JOptionPane.showConfirmDialog(
	                null, "确认要新建一张收付款吗？", "提示", JOptionPane.YES_NO_OPTION);
	            if(response == 1) return;
	            clear();
	            billIdField.setText(receiptBillBL.getNewId());
	            operaterField.setText(getUser().getName());
	        };
	}

	@Override
	protected ActionListener getSaveActionListener() {
		 return e -> {
	            if(!editable) return;
	            ReceiptBillVO bill = getBill(BillVO.SAVED);
	            if(bill == null){
	                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
	                return;
	            }
	            if(receiptBillBL.saveBill(bill)){
	                JOptionPane.showMessageDialog(null, "单据已保存。");
	            }

	        };
	}

	@Override
	protected ActionListener getCommitActionListener() {
		return e -> {
            if(!editable) return;
            ReceiptBillVO bill = getBill(BillVO.COMMITED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
                return;
            }
            if(receiptBillBL.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "单据已提交。");
            }
        };
	}

	@Override
	protected boolean isCorrectable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * 获得单据VO
	 * @return
	 */
	public ReceiptBillVO getBill(int state) {
		if(!isCorrectable()) return null;
        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.YEAR) + ""
                    + c.get(Calendar.MONTH) + ""
                    + c.get(Calendar.DATE);
        String time = c.get(Calendar.HOUR_OF_DAY) + ""
                    + c.get(Calendar.MINUTE) + ""
                    + c.get(Calendar.SECOND);
        String id = billIdField.getText()
             , operater = operaterField.getText()
             , customerId = customerIdField.getText();
        MyTableModel model = (MyTableModel)transferListTable.getModel();
        return new ReceiptBillVO(date, time, id, operater, state, customerId, model);
    }

	protected void clear(){
        billIdField.setText("");
        operaterField.setText("");
        customerIdField.setText("");
        MyTableModel model = (MyTableModel) transferListTable.getModel();
        while(model.getRowCount() > 0){
            model.removeRow(0);
        }
    }
}

