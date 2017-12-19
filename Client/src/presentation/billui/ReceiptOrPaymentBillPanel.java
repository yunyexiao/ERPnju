package presentation.billui;

import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.choosewindow.CustomerChooseWin;
import vo.CustomerVO;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.PaymentBillVO;
import vo.billvo.ReceiptBillVO;

public class ReceiptOrPaymentBillPanel extends BillPanel {

	private ReceiptBillBLService receiptBillBL = new ReceiptBillBL_stub();
	private PaymentBillBLService paymentBillBL = new PaymentBillBL_stub();

	private JTextField billIdField, customerIdField, customerNameField, operaterField, sumField;
	private JTable transferListTable;
	private JRadioButton receiptButton, paymentButton;
	
	public ReceiptOrPaymentBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);
        this.billIdField.setText(receiptBillBL.getNewId());
		this.operaterField.setText(this.getUser().getName());
	}

	public ReceiptOrPaymentBillPanel(UserVO user, ActionListener closeListener, ReceiptBillVO bill) {
		super(user, closeListener);
		billIdField.setText(bill.getAllId());
        operaterField.setText(bill.getOperator());
        customerIdField.setText(bill.getCustomerId());
        transferListTable.setModel(bill.getTableModel());
	}
	
	public ReceiptOrPaymentBillPanel(UserVO user, ActionListener closeListener, PaymentBillVO bill) {
		super(user, closeListener);
		billIdField.setText(bill.getAllId());
        operaterField.setText(bill.getOperator());
        customerIdField.setText(bill.getCustomerId());
        transferListTable.setModel(bill.getTableModel());
	}
	
	@Override
	protected void initBillPanel() {
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus���jdk6 update10�汾�Ժ�ĲŻ����
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JScrollPane transferListPane;
		JPanel headPanel, customerInfoPanel,centerPanel,transferButtonPanel;
		JButton customerChooseButton, transferChooseButton, transferDeleteButton;
		
		headPanel=new JPanel();
		double firstPanelSize[][]={
				{20,55,5,TableLayout.PREFERRED,20,55,5,TableLayout.PREFERRED,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		billIdField = new JTextField(15);
		operaterField = new JTextField(10);
		billIdField.setEditable(false);
		operaterField.setEditable(false);
		headPanel.setLayout(new TableLayout(firstPanelSize));
		headPanel.add(new JLabel("���ݱ��"),"1,1");
		headPanel.add(billIdField,"3,1");
		headPanel.add(new JLabel("������"),"5,1");
		headPanel.add(operaterField,"7,1");
		
		JPanel choosePanel = new JPanel();
		double choosePanelSize[][] = {
				{20,55,5,TableLayout.PREFERRED,20,TableLayout.PREFERRED,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		choosePanel.setLayout(new TableLayout(choosePanelSize));
		receiptButton = new JRadioButton("�տ");
		paymentButton = new JRadioButton("���");
		receiptButton.setSelected(true);
		receiptButton.addActionListener(e -> setBillId(true));
		paymentButton.addActionListener(e -> setBillId(false));
		choosePanel.add(new JLabel("��������"), "1,1");
		choosePanel.add(receiptButton, "3,1");
		choosePanel.add(paymentButton, "5,1");
		ButtonGroup chooseGroup = new ButtonGroup();
		chooseGroup.add(receiptButton);
		chooseGroup.add(paymentButton);
		
		customerInfoPanel=new JPanel();
		double secondPanelSize[][]={
				{20,45,5,70,12,70,12,70,60,TableLayout.FILL},
				{8,25,TableLayout.FILL}
		};
		customerIdField = new JTextField(10);
		customerNameField = new JTextField(10);
		customerIdField.setText("");
		customerNameField.setText("");
		customerIdField.setEditable(false);
		customerNameField.setEditable(false);
		customerChooseButton=new JButton("ѡ��");	
        customerChooseButton.addActionListener(e -> handleChooseCustomer());
		customerInfoPanel.setLayout(new TableLayout(secondPanelSize));
		customerInfoPanel.add(new JLabel("�ͻ�"),"1,1");
		customerInfoPanel.add(customerIdField,"3,1");
		customerInfoPanel.add(new JLabel("--"),"4,1");
		customerInfoPanel.add(customerNameField,"5,1");
		customerInfoPanel.add(customerChooseButton,"7,1");
		
		String[] transferListAttributes={"�����˻�", "ת�˽��", "��ע"};
		transferListTable = new JTable(new MyTableModel(null,transferListAttributes));
		transferListTable.getTableHeader().setReorderingAllowed(false);
		transferListPane = new JScrollPane(transferListTable);

		transferButtonPanel=new JPanel();
		double forthPanelSize[][]={
				{TableLayout.PREFERRED,TableLayout.FILL},
				{25,10,25,10,25,10,25,TableLayout.FILL},
		};
		sumField = new JTextField(8);
		sumField.setText("0.0");
		sumField.setEditable(false);
		transferChooseButton=new JButton("����ת��", new ImageIcon("resource/AddButton.png"));
        transferChooseButton.addActionListener(e -> addItem());
        transferDeleteButton=new JButton("ɾ��ת��", new ImageIcon("resource/DeleteButton.png"));
        transferDeleteButton.addActionListener(e -> deleteItem());
		transferButtonPanel.setLayout(new TableLayout(forthPanelSize));
		transferButtonPanel.add(transferChooseButton, "0,0");
		transferButtonPanel.add(transferDeleteButton, "0,2");
		transferButtonPanel.add(new JLabel("�ܶ�"),"0,4");
		transferButtonPanel.add(sumField,"0,6");

		centerPanel=new JPanel();
		double centerPanelSize[][]={
				{20,0.8,20,0.2},
				{10,25,5,TableLayout.FILL}
		};
		centerPanel.setLayout(new TableLayout(centerPanelSize));
		centerPanel.add(new JLabel("ת���б�"),"1,1");
		centerPanel.add(transferListPane, "1,3");
		centerPanel.add(transferButtonPanel, "3,3");
		
		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.08,0.08,0.08,0.6}	
		};
		billPanel.setLayout(new TableLayout(mainPanelSize));
		billPanel.add(headPanel, "0,0");
		billPanel.add(choosePanel, "0,1");
		billPanel.add(customerInfoPanel, "0,2");
		billPanel.add(centerPanel,"0,3");
	}

	private void addItem(){
        String[] newRow = new InputTransferItemInfoWin().getRowData();
        if(newRow != null) {
            MyTableModel model = (MyTableModel) transferListTable.getModel();
            for(int i = 0; i < model.getRowCount(); i++) {
            	if (newRow[0].equals(model.getValueAt(i, 1))) {new InfoWindow("��������д��˻�"); return;}
            }
            model.addRow(newRow);
            sumUp();
        }
    }
	
    private void deleteItem(){
	    int row = transferListTable.getSelectedRow();
	    if(row >= 0) {
	    	((MyTableModel)transferListTable.getModel()).removeRow(transferListTable.getSelectedRow());
	    	sumUp();
	    }
	    else new InfoWindow("��ѡ��ɾ����ת����Ϣ");
    }
	
    private void sumUp(){
	    MyTableModel model = (MyTableModel)this.transferListTable.getModel();
        double total = 0;
	    for(int i = 0; i < model.getRowCount(); i++){
	        total += Double.parseDouble((String)model.getValueAt(i, 1));
	    }
	    sumField.setText(Double.toString(total));
    }
    
    private void handleChooseCustomer(){
        CustomerVO c = new CustomerChooseWin().getCustomer();
        if(c == null) return;
        customerIdField.setText(c.getId());
        customerNameField.setText(c.getName());
    }

	protected void clear(){
		receiptButton.setSelected(true);
		billIdField.setText(receiptBillBL.getNewId());
        customerIdField.setText("");
        customerNameField.setText("");
        MyTableModel model = (MyTableModel) transferListTable.getModel();
        while(model.getRowCount() > 0){
            model.removeRow(0);
        }
        sumField.setText("0.0");
    }
	
	@Override
	protected ActionListener getNewActionListener() {
		 return e -> {
			 int response = JOptionPane.showConfirmDialog(null, "������ǰδ����Ĺ����½�һ�ŵ��ݣ�","����",JOptionPane.YES_NO_OPTION);
			 if (response == 0) {
				 clear();
			 }
	     };
	}

	
	@Override
	protected ActionListener getSaveActionListener() {
		 return e -> {
	            ReceiptBillVO bill = getBill(BillVO.SAVED);
	            if(bill != null && receiptBillBL.saveBill(bill)){
	                JOptionPane.showMessageDialog(null, "�����ѱ��档");
	            }
	        };
	}

	@Override
	protected ActionListener getCommitActionListener() {
		return e -> {
            ReceiptBillVO bill = getBill(BillVO.COMMITED);
            if(bill != null && receiptBillBL.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "�������ύ��");
            }
        };
	}

	@Override
	protected boolean isCorrectable() {
		if (transferListTable.getRowCount() == 0) new InfoWindow("û��ѡ��ת����Ŀ");
		else if ("".equals(customerIdField.getText())) new InfoWindow("��ѡ��ͻ�");
		else return true;
		return false;
	}
	
	/**
	 * ��õ���VO
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
        ReceiptBillVO receiptBillVO= new ReceiptBillVO(date, time, id, operater, state, customerId, model); 
        return receiptBillVO;
    }
	
	private void setBillId(boolean isReceipt) {
		if (isReceipt) billIdField.setText(receiptBillBL.getNewId());
		else billIdField.setText(paymentBillBL.getNewId());
	}
}
