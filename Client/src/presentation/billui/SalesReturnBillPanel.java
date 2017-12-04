package presentation.billui;

import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JOptionPane;

import bl_stub.SaleReturnBillBL_stub;
import blservice.billblservice.SaleReturnBillBLService;
import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.MarketBillVO;
import vo.billvo.SalesReturnBillVO;

/**
 * �����˻������
 * @author �Ҷ��
 */
public class SalesReturnBillPanel extends CommonSaleBillPanel {
    
    private SaleReturnBillBLService saleReturnBl = new SaleReturnBillBL_stub();

    public SalesReturnBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.billIdField.setText(saleReturnBl.getNewId());
        this.operatorField.setText(user.getId());
    }

    public SalesReturnBillPanel(UserVO user, ActionListener closeListener, MarketBillVO bill) {
        super(user, closeListener, bill);
    }

    @Override
    protected String getObjectType() {
        return "������";
    }

    @Override
    protected String getTableTitle() {
        return "�����Ʒ�б�";
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

    private SalesReturnBillVO getBill(int state){
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
             , remark = remarkField.getText()
             , originalSBId = null;
        // TODO original SBId and sum to be completed
        MyTableModel model = (MyTableModel)goodsListTable.getModel();
        double originalSum = Double.parseDouble(sumField.getText()),
               sum = 0.0;
        return new SalesReturnBillVO(date, time, id, operater, state
            , customerId, customerName, model, remark, originalSBId
            , originalSum, sum);
    }

}
