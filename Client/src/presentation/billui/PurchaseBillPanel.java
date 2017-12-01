package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bl_stub.PurchaseBillBL_stub;
import blservice.billblservice.SaleBillBLService;
import vo.UserVO;

/**
 * �����������
 * @author �Ҷ��
 */
public class PurchaseBillPanel extends CommonSaleBillPanel {
    
    private SaleBillBLService purchaseBl = new PurchaseBillBL_stub();

    public PurchaseBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.billIdField.setText(purchaseBl.getNewId());
        this.operaterField.setText(user.getName());
    }
    
//    public ImportBillPanel(UserVO user, ActionListener closeListener, ImportBillVO importBill){
//        super(user, closeListener, importBill);
//    }
//
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
            int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�½�һ�����۵���", "��ʾ", JOptionPane.YES_NO_OPTION);
            if(response == 1)return;
            initBillPanel();
            billIdField.setText(purchaseBl.getNewId());
            operaterField.setText(this.getUser().getName());
        };
    }

    @Override
    protected ActionListener getSaveActionListener() {
        return e ->{
            // TODO
//            PurchaseBillVO bill = getBill(BillVO.DRAFT);
//            if(bill == null){
//                JOptionPane.showMessageDialog(null, "��Ϣ�д������±༭��");
//                return;
//            }
//            purchaseBl.saveBill(bill);
        };
    }

    @Override
    protected ActionListener getCommitActionListener() {
        return e ->{
            // TODO
        };
    }

    @Override
    protected boolean isCorrectable() {
        return true;
    }

//    private PurchaseBillVO getBill(int state){
//        if(!isCorrectable()) return null;
//        // TODO
//        return null;
//    }
//
}
