package presentation.billui;

import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JOptionPane;

import bl_stub.PurchaseReturnBillBL_stub;
import blservice.billblservice.PurchaseReturnBillBLService;
import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.PurchaseReturnBillVO;
import vo.billvo.SalesBillVO;

/**
 * �����˻������
 * @author �Ҷ��
 */
public class PurchaseReturnBillPanel extends CommonSaleBillPanel {
    
    private PurchaseReturnBillBLService purchaseReturnBl = new PurchaseReturnBillBL_stub();

    public PurchaseReturnBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.billIdField.setText(purchaseReturnBl.getNewId());
        this.operatorField.setText(user.getId());
    }

    public PurchaseReturnBillPanel(UserVO user, ActionListener closeListener, SalesBillVO saleBill) {
        super(user, closeListener, saleBill);
        // TODO Auto-generated constructor stub
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
            if(!editable) return;
            PurchaseReturnBillVO bill = getBill(BillVO.SAVED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "��Ϣ�д������±༭��");
                return;
            }
            if(purchaseReturnBl.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "�����ѱ��档");
            }

        };
    }

    @Override
    protected ActionListener getCommitActionListener() {
        return e -> {
            if(!editable) return;
            PurchaseReturnBillVO bill = getBill(BillVO.COMMITED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "��Ϣ�д������±༭��");
                return;
            }
            if(purchaseReturnBl.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "�������ύ��");
            }
        };
    }

    private PurchaseReturnBillVO getBill(int state){
        if(!isCorrectable()) return null;
        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.YEAR) + ""
                    + c.get(Calendar.MONTH) + ""
                    + c.get(Calendar.DATE);
        String time = c.get(Calendar.HOUR_OF_DAY) + ""
                    + c.get(Calendar.MINUTE) + ""
                    + c.get(Calendar.SECOND);
        String id = billIdField.getText()
             , operater = operatorField.getText()
             , customerId = customerIdField.getText()
             , customerName = customerNameField.getText()
             , remark = remarkField.getText();
        MyTableModel model = (MyTableModel)goodsListTable.getModel();
        double sum = Double.parseDouble(sumField.getText());
        return new PurchaseReturnBillVO(date, time, id, operater, state
            , customerId, customerName, model, remark, sum);
    }


}
