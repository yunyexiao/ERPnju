package presentation.billui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import blservice.billblservice.SalesReturnBillBLService;
import businesslogic.SalesReturnBillBL;
import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.choosewindow.SalesBillChooseWin;
import presentation.tools.DoubleField;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.SalesReturnBillVO;

/**
 * �����˻������
 * @author �Ҷ��
 */
public class SalesReturnBillPanel extends CommonSaleBillPanel {
    
    private SalesReturnBillBLService saleReturnBl = new SalesReturnBillBL();
    private JTextField originalSBIdField;
    private DoubleField discountRateField, finalSumField;

    public SalesReturnBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.billIdField.setText(saleReturnBl.getNewId());
        this.operatorField.setText(user.getId());
    }

    public SalesReturnBillPanel(UserVO user, ActionListener closeListener, SalesReturnBillVO bill) {
        super(user, closeListener, bill);
        this.discountRateField.setText(bill.getDiscountRate() + "");
        this.originalSBIdField.setText(bill.getOriginalSBId());
        this.finalSumField.setText(bill.getSum() + "");
    }
    
    @Override
    protected int getCustomerType(){
        return 1;
    }

    @Override
    protected JPanel getCustomerPanel(){
        customerIdField = new JTextField(10);
        customerIdField.setEditable(false);
        customerNameField = new JTextField(10);
        customerNameField.setEditable(false);
        originalSBIdField = new JTextField(15);
        originalSBIdField.setEditable(false);
        discountRateField = new DoubleField(5);
        discountRateField.setEditable(false);
        JButton salesBillChooseButton = new JButton("ѡ��Դ���۵�");
        salesBillChooseButton.addActionListener(e -> handleChooseSb());
        double[][] size = {
                {20,45,5,70,12,100,5,60,10.0, -2.0, 10.0, -2.0
                    , 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0}, 
                {8, 25, -1.0}
        };
        JPanel customerPanel = new JPanel(new TableLayout(size));
		JButton customerChooseButton = new JButton("ѡ��");
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
        
        billPanel.add(southPanel, BorderLayout.SOUTH);
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
    protected ActionListener getNewActionListener() {
        return e -> {
            int response = JOptionPane.showConfirmDialog(
                null, "ȷ��Ҫ�½�һ�������˻�����", "��ʾ", JOptionPane.YES_NO_OPTION);
            if(response == 1) return;
            clear();
            billIdField.setText(saleReturnBl.getNewId());
            operatorField.setText(getUser().getName());
            setTime();
        };
    }

    @Override
    protected ActionListener getSaveActionListener() {
        return e -> {
            if(!editable) return;
            SalesReturnBillVO bill = getBill(BillVO.SAVED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "��Ϣ�д������±༭��");
                return;
            }
            if(saleReturnBl.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "�����ѱ��档");
            }
        };
    }

    @Override
    protected ActionListener getCommitActionListener() {
        return e -> {
            if(!editable) return;
            SalesReturnBillVO bill = getBill(BillVO.COMMITED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "��Ϣ�д������±༭��");
                return;
            }
            if(saleReturnBl.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "�������ύ��");
            }
        };
    }

    @Override
    protected void sumUp(){
        if(!editable) return;
        super.sumUp();
        double discountRate = discountRateField.getValue(),
               originalSum = sumField.getValue(),
               finalSum = originalSum * discountRate;
        this.finalSumField.setValue(finalSum);
    }

    @Override
    protected void addItem(){
        if(!editable) return;
        String[] newRow = new InputCommodityInfoWin().getRowData();
        if(newRow == null || newRow[5].equals("0")) return;
        // check if the sales bill contains that commodity
        // also check the amount
        if(!itemValid(newRow)) return;
        MyTableModel model = (MyTableModel) goodsListTable.getModel();
        model.addRow(newRow);
        sumUp();
    }

    private SalesReturnBillVO getBill(int state){
        if(!isCorrectable()) return null;
        String date = getDate(), time = getTime(), id = getId();
        String operater = operatorField.getText()
             , customerId = customerIdField.getText()
             , customerName = customerNameField.getText()
             , remark = remarkField.getText()
             , originalSBId = originalSBIdField.getText();
        MyTableModel model = (MyTableModel)goodsListTable.getModel();
        double discountRate = Double.parseDouble(discountRateField.getText()), 
               originalSum = Double.parseDouble(sumField.getText()),
               sum = Double.parseDouble(finalSumField.getText());
        return new SalesReturnBillVO(date, time, id, operater, state
            , customerId, customerName, model, remark, originalSBId
            , discountRate, originalSum, sum);
    }

    private void handleChooseSb(){
        if(!editable) return;
        if(customerIdField.getText().length() == 0){
            new InfoWindow("����ѡ��������^_^");
            return;
        }
        String[] info = new SalesBillChooseWin().getSalesBillInfo();
        if(info == null) return;
        originalSBIdField.setText(info[0]);
        discountRateField.setValue(Double.parseDouble(info[1]));
        sumUp();
    }

    private boolean itemValid(String[] item){
        // TODO
        return false;
    }

}
