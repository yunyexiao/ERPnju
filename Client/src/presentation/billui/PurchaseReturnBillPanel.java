package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import bl_stub.PurchaseReturnBillBL_stub;
import blservice.billblservice.PurchaseReturnBillBLService;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.PurchaseReturnBillVO;
import vo.billvo.SalesBillVO;

/**
 * 进货退货单面板
 * @author 恽叶霄
 */
public class PurchaseReturnBillPanel extends CommonSaleBillPanel {
    
    private PurchaseReturnBillBLService purchaseReturnBl = new PurchaseReturnBillBL_stub();

    public PurchaseReturnBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.billIdField.setText(purchaseReturnBl.getNewId());
        this.operatorField.setText(user.getName());
    }

    public PurchaseReturnBillPanel(UserVO user, ActionListener closeListener, SalesBillVO saleBill) {
        super(user, closeListener, saleBill);
    }

    @Override
    protected String getObjectType() {
        return "供应商";
    }

    @Override
    protected String getTableTitle() {
        return "出库商品列表";
    }

    @Override
    protected ActionListener getNewActionListener() {
        return e -> {
            int response = JOptionPane.showConfirmDialog(
                null, "确认要新建一张进货退货单吗？", "提示", JOptionPane.YES_NO_OPTION);
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
            if (bill != null && purchaseReturnBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "单据已保存。");
        };
    }

    @Override
    protected ActionListener getCommitActionListener() {
    	return e ->{
    		PurchaseReturnBillVO bill = getBill(BillVO.COMMITED);
            if (bill != null && purchaseReturnBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "单据已提交。");
        };
    }

    private PurchaseReturnBillVO getBill(int state){
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

	@Override
	protected void handleChooseCustomer() {
		handleChooseCustomer(true);
	}


}
