package presentation.billui;

import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JOptionPane;

import blservice.billblservice.PurchaseBillBLService;
import businesslogic.PurchaseBillBL;
import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.PurchaseBillVO;

/**
 * 进货单的面板
 * @author 恽叶霄
 */
public class PurchaseBillPanel extends CommonSaleBillPanel {
    
    private PurchaseBillBLService purchaseBl = new PurchaseBillBL();

    public PurchaseBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.billIdField.setText(purchaseBl.getNewId());
        this.operatorField.setText(user.getId());
    }
    
    public PurchaseBillPanel(UserVO user, ActionListener closeListener, PurchaseBillVO purchaseBill){
        super(user, closeListener, purchaseBill);
    }

    @Override
    protected String getObjectType() {
        return "供应商";
    }

    @Override
    protected String getTableTitle() {
        return "进库商品列表";
    }

    @Override
    protected ActionListener getNewActionListener() {
        return e -> {
            int response = JOptionPane.showConfirmDialog(
                null, "确认要新建一张进货单吗？", "提示", JOptionPane.YES_NO_OPTION);
            if(response == 1)return;
            clear();
            billIdField.setText(purchaseBl.getNewId());
            operatorField.setText(this.getUser().getName());
        };
    }

    @Override
    protected ActionListener getSaveActionListener() {
        return e ->{
            if(!editable) return;
            PurchaseBillVO bill = getBill(BillVO.SAVED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
                return;
            }
            if(purchaseBl.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "单据已保存。");
            }
        };
    }

    @Override
    protected ActionListener getCommitActionListener() {
        return e ->{
            if(!editable) return;
            PurchaseBillVO bill = getBill(BillVO.COMMITED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
                return;
            }
            if(purchaseBl.saveBill(bill)){
                JOptionPane.showMessageDialog(null, "单据已提交。");
            }
        };
    }

    private PurchaseBillVO getBill(int state){
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
        return new PurchaseBillVO(date, time, id, operater, state
            , customerId, customerName, model, remark, sum);
    }

}
