package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import blservice.billblservice.PurchaseBillBLService;
import businesslogic.PurchaseBillBL;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.PurchaseBillVO;

/**
 * �����������
 * @author �Ҷ��
 */
public class PurchaseBillPanel extends CommonSaleBillPanel {
    
    private PurchaseBillBLService purchaseBl = new PurchaseBillBL();

    public PurchaseBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.billIdField.setText(purchaseBl.getNewId());
        this.operatorField.setText(user.getName());
    }
    
    public PurchaseBillPanel(UserVO user, ActionListener closeListener, PurchaseBillVO purchaseBill){
        super(user, closeListener, purchaseBill);
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
    protected ActionListener getNewActionListener() {
        return e -> {
            int response = JOptionPane.showConfirmDialog(
                null, "ȷ��Ҫ�½�һ�Ž�������", "��ʾ", JOptionPane.YES_NO_OPTION);
            if(response == 1)return;
            clear();
            billIdField.setText(purchaseBl.getNewId());
            operatorField.setText(this.getUser().getName());
        };
    }

    @Override
    protected ActionListener getSaveActionListener() {
        return e ->{
            PurchaseBillVO bill = getBill(BillVO.SAVED);
            if (bill != null && purchaseBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "�����ѱ��档");
        };
    }

    @Override
    protected ActionListener getCommitActionListener() {
        return e ->{
            PurchaseBillVO bill = getBill(BillVO.COMMITED);
            if (bill != null && purchaseBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "�������ύ��");
        };
    }

    private PurchaseBillVO getBill(int state){
        if(!isCorrectable()) return null;
        String date = getDate();
        String id = getId()
             , operater = operatorField.getText()
             , customerId = customerIdField.getText()
             , customerName = customerNameField.getText()
             , remark = remarkField.getText();
        MyTableModel model = (MyTableModel)goodsListTable.getModel();
        double sum = Double.parseDouble(sumField.getText());
        return new PurchaseBillVO(date, Timetools.getTime(), id, operater, state
            , customerId, customerName, model, remark, sum);
    }

	@Override
	protected void handleChooseCustomer() {
		handleChooseCustomer(true);
	}

}
