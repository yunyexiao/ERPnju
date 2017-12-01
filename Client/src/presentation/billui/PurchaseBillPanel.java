package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bl_stub.PurchaseBillBL_stub;
import blservice.billblservice.SaleBillBLService;
import vo.UserVO;

/**
 * 进货单的面板
 * @author 恽叶霄
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
        return "供应商";
    }

    @Override
    protected String getTableTitle() {
        return "进货商品列表";
    }

    @Override
    protected ActionListener getNewActionListener() {
        return e -> {
            int response = JOptionPane.showConfirmDialog(null, "确认要新建一张销售单吗？", "提示", JOptionPane.YES_NO_OPTION);
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
//                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
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
