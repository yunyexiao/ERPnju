package presentation.billui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import blservice.billblservice.SalesReturnBillBLService;
import businesslogic.SalesReturnBillBL;
import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.component.choosewindow.SalesBillChooseWin;
import presentation.tools.DoubleField;
import presentation.tools.Timetools;
import vo.MyTableModel;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.SalesBillVO;
import vo.billvo.SalesReturnBillVO;

/**
 * �����˻������
 * @author �Ҷ��
 */
public class SalesReturnBillPanel extends CommonSaleBillPanel {
    
    private SalesReturnBillBLService saleReturnBl = new SalesReturnBillBL();
    private SalesBillVO originalSB;
    private JTextField originalSBIdField;
    private DoubleField discountRateField, finalSumField;
    private JButton salesBillChooseButton;

    public SalesReturnBillPanel(UserVO user) {
        super(user);
        this.billIdField.setText(saleReturnBl.getNewId());
        this.operatorField.setText(user.getName());
    }

    public SalesReturnBillPanel(UserVO user, SalesReturnBillVO bill) {
        super(user, bill);
        this.discountRateField.setText(bill.getDiscountRate() + "");
        this.originalSBIdField.setText(bill.getOriginalSBId());
        this.finalSumField.setText(bill.getSum() + "");
    }

	public void setEditable(boolean b) {
		super.setEditable(b);
		salesBillChooseButton.setEnabled(b);
		customerChooseButton.setEnabled(b);
	}
	
    @Override
    protected JPanel getCustomerPanel(){
        customerIdField = new JTextField(10);
        customerIdField.setEditable(false);
        customerNameField = new JTextField(10);
        customerNameField.setEditable(false);
        originalSBIdField = new JTextField(15);
        originalSBIdField.setEditable(false);
        discountRateField = new DoubleField(1.0, 5, 1.0);
        discountRateField.setEditable(false);
        salesBillChooseButton = new JButton("ѡ��Դ���۵�");
        salesBillChooseButton.addActionListener(e -> handleChooseSb());
        double[][] size = {
                {20,45,5,70,12,100,5,60,10.0, -2.0, 10.0, -2.0
                    , 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0}, 
                {8, 25, -1.0}
        };
        JPanel customerPanel = new JPanel(new TableLayout(size));
		customerChooseButton = new JButton("ѡ��");
		customerChooseButton.addActionListener(e -> handleChooseCustomer());
		customerPanel.add(new JLabel(getObjectType()),"1,1");
		customerPanel.add(customerIdField,"3,1");
		customerPanel.add(new JLabel("--"),"4,1");
		customerPanel.add(customerNameField,"5,1");
		customerPanel.add(customerChooseButton,"7,1");
		customerPanel.add(new JLabel("Դ���۵�"), "9 1");
		customerPanel.add(originalSBIdField, "11 1");
		customerPanel.add(new JLabel("�ۿ���"), "13 1");
		customerPanel.add(discountRateField, "15 1");
		customerPanel.add(salesBillChooseButton, "17 1");
        return customerPanel;
    }

    @Override
    protected void initSouth(){
        remarkField = new JTextField(10);
        sumField = new DoubleField(10);
        sumField.setEditable(false);
        finalSumField = new DoubleField(10);
        finalSumField.setEditable(false);
        
        double[][] size = {
                {20.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0
                    , -2.0, -1.0, -1.0},
                {-2.0, 10.0, -2.0}
        };
        JPanel southPanel = new JPanel(new TableLayout(size));
        southPanel.add(new JLabel("��ע"), "1 0");
        southPanel.add(remarkField, "2 0 8 0");
        southPanel.add(new JLabel("ԭ�ܶ�"), "1 2");
        southPanel.add(sumField, "3 2");
        southPanel.add(new JLabel("ʵ���ܶ�"), "5 2");
        southPanel.add(finalSumField, "7 2");
        
        this.add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    protected String getObjectType() {
        return "������";
    }

    @Override
    protected String getTableTitle() {
        return "�ؿ���Ʒ�б�";
    }
    @Override
    protected void sumUp(){
        super.sumUp();
        double discountRate = discountRateField.getValue(),
               originalSum = sumField.getValue(),
               finalSum = originalSum * discountRate;
        this.finalSumField.setValue(finalSum);
    }
    @Override
    protected void handleAddItem(){
        String[] newRow = new InputCommodityInfoWin().getRowData();
        if(newRow == null || newRow[5].equals("0")) return;
        // check if the sales bill contains that commodity, also check the amount
        if(!itemValid(newRow)) return;
        addItem(newRow);
    }
    @Override
    protected void clear(){
        super.clear();
        originalSB = null;
        originalSBIdField.setText("");
        discountRateField.setValue(1.0);
        finalSumField.setValue(0.0);
    }

    public SalesReturnBillVO getBill(int state){
        if(!isCorrectable()) return null;
        String id = bill == null ? saleReturnBl.getNewId().split("-")[2] : getId();
        String operater = operatorField.getText()
             , customerId = customerIdField.getText()
             , remark = remarkField.getText()
             , originalSBId = originalSBIdField.getText();
        MyTableModel model = (MyTableModel)goodsListTable.getModel();
        double discountRate = Double.parseDouble(discountRateField.getText()), 
               originalSum = Double.parseDouble(sumField.getText()),
               sum = Double.parseDouble(finalSumField.getText());
        return new SalesReturnBillVO(
        	bill == null ? Timetools.getDate() : bill.getDate(),
        	bill == null ? Timetools.getTime() : bill.getTime(), 
        	id, operater, state, customerId, model, remark, originalSBId
            , discountRate, originalSum, sum);
    }

    private void handleChooseSb(){
        String customerId = customerIdField.getText();
        if(customerId.length() == 0){
            new InfoWindow("����ѡ��������^_^");
            return;
        }
        originalSB = new SalesBillChooseWin(customerId).getSalesBill();
        if(originalSB == null) return;
        originalSBIdField.setText(originalSB.getAllId());
        discountRateField.setValue(originalSB.getSum() / originalSB.getBeforeDiscount());
        sumUp();
    }

    private boolean itemValid(String[] item){
        String id = item[0];
        int amount = Integer.parseInt(item[5]) + getAmountInModel(
            (MyTableModel)goodsListTable.getModel(), id);
        int originalAmount = getAmountInModel(originalSB.getModel(), id);
        if(originalAmount == 0){
            new InfoWindow("Դ���۵����޴���Ʒ@_@");
            return false;
        } else if(amount > originalAmount){
            new InfoWindow("��Ʒ��������@_@");
            return false;
        }
        return true;
    }
    
    private int getAmountInModel(MyTableModel model, String id){
        int amount = 0;
        for(int i = 0; i < model.getRowCount(); i++){
            String[] row = model.getValueAtRow(i);
            if(row[0].equals(id)){
                amount += Integer.parseInt(row[5]);
            }
        }
        return amount;
    }

	@Override
	protected void handleChooseCustomer() {
		handleChooseCustomer(false);
	}

	@Override
	public void newAction() {
		int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�½�һ�������˻�����", "��ʾ", JOptionPane.YES_NO_OPTION);
        if(response == 1) return;
        clear();
        billIdField.setText(saleReturnBl.getNewId());
        operatorField.setText(user.getName());
	}

	@Override
	public void saveAction() {
		SalesReturnBillVO bill = getBill(BillVO.SAVED);
        if (bill != null && saleReturnBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "�����ѱ��档");
	}

	@Override
	public void commitAction() {
		SalesReturnBillVO bill = getBill(BillVO.COMMITED);
        if (bill != null && saleReturnBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "�������ύ��");
	}

}
