package presentation.billui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import presentation.billui.choosewindow.CustomerChooseWin;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import vo.CustomerVO;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.ReceiptBillVO;

public class ReceiptOrPaymentBillPanel extends BillPanel {

	private ReceiptBillBLService receiptBillBL = new ReceiptBillBL_stub();
	private PaymentBillBLService paymentBillBL = new PaymentBillBL_stub();

	private JTextField billIdField, customerIdField, customerNameField, operaterField, sumField;
	private JTable transferListTable;
	private boolean editable;
	private ButtonGroup typeButtonGroup;
	private JDialog frame = new JDialog();

	
	public ReceiptOrPaymentBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);
		editable = true;
        this.billIdField.setText(receiptBillBL.getNewId());
		this.operaterField.setText(this.getUser().getName());
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
		JPanel headPanel, customerInfoPanel,centerPanel,transferButtonPanel, tailPanel;
		JButton customerChooseButton, transferChooseButton, transferDeleteButton, chooseFinishButton;
		
		headPanel=new JPanel();
		double firstPanelSize[][]={
				{20,55,5,150,20,40,5,150,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		billIdField = new JTextField(10);
		operaterField = new JTextField(10);
		customerIdField = new JTextField(10);
		customerNameField = new JTextField(10);
		customerIdField.setText("编号");
		customerNameField.setText("姓名");

		billIdField.setEditable(false);
		operaterField.setEditable(false);
		customerIdField.setEditable(false);
		customerNameField.setEditable(false);

		headPanel.setLayout(new TableLayout(firstPanelSize));
		headPanel.add(new JLabel("单据编号"),"1,1");
		headPanel.add(billIdField,"3,1");
		headPanel.add(new JLabel("操作人"),"5,1");
		headPanel.add(operaterField,"7,1");
		
		customerInfoPanel=new JPanel();
		double secondPanelSize[][]={
				{20,45,5,70,12,70,12,70,60,TableLayout.FILL},
				{8,25,TableLayout.FILL}
		};
		customerChooseButton=new JButton("选择");	
        customerChooseButton.addActionListener(e -> handleChooseCustomer());
		customerInfoPanel.setLayout(new TableLayout(secondPanelSize));
		customerInfoPanel.add(new JLabel("客户"),"1,1");
		customerInfoPanel.add(customerIdField,"3,1");
		customerInfoPanel.add(new JLabel("--"),"4,1");
		customerInfoPanel.add(customerNameField,"5,1");
		customerInfoPanel.add(customerChooseButton,"7,1");
		
		String[] transferListAttributes={"银行账户", "转账金额", "备注"};
		String[][] transferInfo={{"mayun0001","100000","赎金"}};
		transferListTable = new JTable(new MyTableModel(transferInfo,transferListAttributes));
		transferListPane = new JScrollPane(transferListTable);

		transferButtonPanel=new JPanel();
		double forthPanelSize[][]={
				{85,TableLayout.FILL},
				{25,10,25,10,25,10,TableLayout.FILL},
		};
		transferChooseButton=new JButton("选择转账");
        transferChooseButton.addActionListener(e -> addItem());
        transferDeleteButton=new JButton("删除转账");
        transferDeleteButton.addActionListener(e -> deleteItem());
        chooseFinishButton=new JButton("选择完成");
        chooseFinishButton.addActionListener(e -> sumUp());
		
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
		
		tailPanel = new JPanel();
		double[][] size = {
                {20.0, -2.0, 10.0, 100.0, -1.0, -1.0},
                {10.0, -1.0, 10.0, -1.0, -1.0}
        };
        tailPanel.setLayout(new TableLayout(size));
		sumField = new JTextField(20);
		sumField.setEditable(false);
		tailPanel.add(new JLabel("总额"),"1,1");
		tailPanel.add(sumField,"3,1");
		
		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.1, 0.1, 0.5,0.1,0.15}	
		};
		billPanel.setLayout(new TableLayout(mainPanelSize));
		billPanel.add(headPanel, "0,0");
		billPanel.add(customerInfoPanel, "0,1");
		billPanel.add(centerPanel,"0,2");
		billPanel.add(tailPanel, "0,4");
	}

	private void addItem(){
        if(!editable) return;
        String[] newRow = new InputTransferItemInfoWin().getRowData();
        if(newRow == null) return;
        MyTableModel model = (MyTableModel) transferListTable.getModel();
        model.addRow(newRow);
    }
	
    private void deleteItem(){
        if(!editable) return;
	    int row = transferListTable.getSelectedRow();
	    if(row < 0) return;
        ((MyTableModel)transferListTable.getModel()).removeRow(transferListTable.getSelectedRow());
    }
	
    private void sumUp(){
        if(!editable) return;
	    MyTableModel model = (MyTableModel)this.transferListTable.getModel();
        double total = 0;
	    for(int i = 0; i < model.getRowCount(); i++){
	        total += Double.parseDouble((String)model.getValueAt(i, 1));
	    }
	    sumField.setText(Double.toString(total));
    }
    
    private void handleChooseCustomer(){
        if(!editable) return;
        CustomerVO c = new CustomerChooseWin().getCustomer();
        if(c == null) return;
        customerIdField.setText(c.getId());
        customerNameField.setText(c.getName());
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
	
	@Override
	protected ActionListener getNewActionListener() {
		 return e -> {

				frame.setTitle("新建收付款单");
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				frame.setSize(screenSize.width/2, screenSize.height/2);
				frame.setLocation(screenSize.width/4, screenSize.height/4);
				frame.setResizable(false);
				
				JRadioButton receiptRadioButton = new JRadioButton("收款单");
				JRadioButton paymentRadioButton = new JRadioButton("付款单");
				//  receiptRadioButton.setSelected(true);
				JPanel typePanel = new JPanel();
				typePanel.add(receiptRadioButton);
				typePanel.add(paymentRadioButton);
				typePanel.setVisible(true);
				typeButtonGroup = new ButtonGroup();
				typeButtonGroup.add(receiptRadioButton);
				typeButtonGroup.add(paymentRadioButton);
				 
				JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JButton yesButton = new JButton("确定");
		        yesButton.addActionListener(f -> addPanel());
				JButton quitButton = new JButton("取消");
				quitButton.addActionListener(f -> frame.dispose());
				southPanel.add(yesButton);
				southPanel.add(quitButton);
				 
				double mainPanelSize[][]={
							{TableLayout.FILL,0.8,TableLayout.FILL},
							{0.4, 0.2, TableLayout.FILL}	
				};
				frame.setLayout(new TableLayout(mainPanelSize));
			    frame.add(typePanel, "1,1");
				frame.add(southPanel, "1,2");
				frame.setVisible(true);			 /*  int response = JOptionPane.showConfirmDialog(
	                null, "确认要新建一张收付款单吗？", "提示", JOptionPane.YES_NO_OPTION);
	            
	            if(response == 1) return;
	            clear();
	            billIdField.setText(receiptBillBL.getNewId());
	            operaterField.setText(getUser().getName());*/
	        };
	}

	protected void addPanel() {
		String text="";    
		Enumeration<AbstractButton> radioBtns= typeButtonGroup.getElements();    
		while (radioBtns.hasMoreElements()) {    
		    AbstractButton btn = radioBtns.nextElement();    
		    if(btn.isSelected()){    
		        text=btn.getText();    
		        break;    
		    }    
		}    
		
		if (text == "收款单") {
			clear();
			frame.dispose();
			billIdField.setText(receiptBillBL.getNewId());
            operaterField.setText(this.getUser().getName());
		} else if (text == "付款单") {
			clear();
			frame.dispose();
			billIdField.setText(paymentBillBL.getNewId());
            operaterField.setText(this.getUser().getName());
		}
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
		if (transferListTable.getRowCount() == 0) {new InfoWindow("没有选择转账条目");return false;}
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
        ReceiptBillVO receiptBillVO= new ReceiptBillVO(date, time, id, operater, state, customerId); 
        receiptBillVO.setTableModel(model);
        return receiptBillVO;
    }
}
