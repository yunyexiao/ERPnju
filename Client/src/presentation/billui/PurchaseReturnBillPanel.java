package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bl_stub.PurchaseReturnBillBL_stub;
import blservice.billblservice.PurchaseReturnBillBLService;
import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.choosewindow.PurchaseBillChooseWin;
import presentation.tools.Timetools;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.PurchaseBillVO;
import vo.billvo.PurchaseReturnBillVO;

/**
 * �����˻������
 * @author �Ҷ��
 */
public class PurchaseReturnBillPanel extends CommonSaleBillPanel {
    
    private PurchaseReturnBillBLService purchaseReturnBl = new PurchaseReturnBillBL_stub();
    private JTextField originalPBIdField;
    private PurchaseBillVO originalPB;

    public PurchaseReturnBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.billIdField.setText(purchaseReturnBl.getNewId());
        this.operatorField.setText(user.getName());
    }

    public PurchaseReturnBillPanel(UserVO user, ActionListener closeListener, PurchaseReturnBillVO saleBill) {
        super(user, closeListener, saleBill);
    }

    @Override
    protected String getObjectType() {
        return "��Ӧ��";
    }

    @Override
    protected String getTableTitle() {
        return "������Ʒ�б�";
    }

    @Override
    protected JPanel getCustomerPanel(){
        customerIdField = new JTextField(10);
        customerIdField.setEditable(false);
        customerNameField = new JTextField(10);
        customerNameField.setEditable(false);
        originalPBIdField = new JTextField(15);
        originalPBIdField.setEditable(false);
        JButton purchaseBillChooseButton = new JButton("ѡ��Դ������");
        purchaseBillChooseButton.addActionListener(e -> handleChoosePb());
        JButton customerChooseButton = new JButton("ѡ��");
        customerChooseButton.addActionListener(e -> handleChooseCustomer());
        
        double[][] size = {
                {20.0, -2.0, 10.0, -2.0, -2.0, -2.0, 20.0
                    , -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0},
                {8, -2.0, -1.0}
        };
        JPanel customerPanel = new JPanel(new TableLayout(size));
        customerPanel.add(new JLabel(getObjectType()), "1 1");
		customerPanel.add(customerIdField,"3,1");
		customerPanel.add(new JLabel("--"),"4,1");
		customerPanel.add(customerNameField,"5,1");
		customerPanel.add(customerChooseButton,"7,1");
		customerPanel.add(new JLabel("Դ������"), "9 1");
		customerPanel.add(originalPBIdField, "11 1");
		customerPanel.add(purchaseBillChooseButton, "13 1");

        return customerPanel;
    }

    @Override
    protected ActionListener getNewActionListener() {
        return e -> {
            int response = JOptionPane.showConfirmDialog(
                null, "ȷ��Ҫ�½�һ�Ž����˻�����", "��ʾ", JOptionPane.YES_NO_OPTION);
            if(response == 1) return;
            clear();
            billIdField.setText(purchaseReturnBl.getNewId());
            operatorField.setText(getUser().getName());
        };
    }

    @Override
    protected ActionListener getSaveActionListener() {
        return e -> {
        	PurchaseReturnBillVO bill = getBill(BillVO.SAVED);
            if (bill != null && purchaseReturnBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "�����ѱ��档");
        };
    }

    @Override
    protected ActionListener getCommitActionListener() {
    	return e ->{
    		PurchaseReturnBillVO bill = getBill(BillVO.COMMITED);
            if (bill != null && purchaseReturnBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "�������ύ��");
        };
    }

    public PurchaseReturnBillVO getBill(int state){
        if(!isCorrectable()) return null;
        String date = getDate(), id = getId();
        String operater = operatorField.getText()
             , customerId = customerIdField.getText()
             , customerName = customerNameField.getText()
             , remark = remarkField.getText();
        MyTableModel model = (MyTableModel)goodsListTable.getModel();
        double sum = Double.parseDouble(sumField.getText());
        return new PurchaseReturnBillVO(date, Timetools.getTime(), id, operater, state
            , customerId, customerName, model, remark, sum);
    }

    private void handleChoosePb(){
        if(!editable) return;
        String customerId = customerIdField.getText();
        if(customerId.length() == 0){
            new InfoWindow("����ѡ�������^_^");
            return;
        }
        originalPB = new PurchaseBillChooseWin(customerId).getPurchaseBill();
        if(originalPB == null) return;
        originalPBIdField.setText(originalPB.getAllId());
    }

	@Override
	protected void handleChooseCustomer() {
		handleChooseCustomer(true);
	}


}
