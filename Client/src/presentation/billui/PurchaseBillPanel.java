package presentation.billui;

import javax.swing.JOptionPane;

import blservice.billblservice.PurchaseBillBLService;
import businesslogic.PurchaseBillBL;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.PurchaseBillVO;

/**
 * 进货单的面板
 * @author 恽叶霄
 */
public class PurchaseBillPanel extends CommonSaleBillPanel {
    
    private PurchaseBillBLService purchaseBl = new PurchaseBillBL();

    public PurchaseBillPanel(UserVO user) {
        super(user);
        this.billIdField.setText(purchaseBl.getNewId());
        this.operatorField.setText(user.getName());
    }
    
    public PurchaseBillPanel(UserVO user, PurchaseBillVO purchaseBill){
        super(user, purchaseBill);
    }

    @Override
    protected String getObjectType() {
        return "供应商";
    }

    @Override
    protected String getTableTitle() {
        return "进库商品列表";
    }

    public PurchaseBillVO getBill(int state){
        if(!isCorrectable()) return null;
        String date = getDate();
        String id = getId()
             , operater = operatorField.getText()
             , customerId = customerIdField.getText()
             , remark = remarkField.getText();
        MyTableModel model = (MyTableModel)goodsListTable.getModel();
        double sum = Double.parseDouble(sumField.getText());
        return new PurchaseBillVO(date, Timetools.getTime(), id, operater, state, customerId, model, remark, sum);
    }

	@Override
	protected void handleChooseCustomer() {
		handleChooseCustomer(true);
	}

	@Override
	public void newAction() {
		int response = JOptionPane.showConfirmDialog(
                null, "确认要新建一张进货单吗？", "提示", JOptionPane.YES_NO_OPTION);
        if(response == 1)return;
        clear();
        billIdField.setText(purchaseBl.getNewId());
        operatorField.setText(user.getName());
	}

	@Override
	public void saveAction() {
		PurchaseBillVO bill = getBill(BillVO.SAVED);
        if (bill != null && purchaseBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "单据已保存。");
	}

	@Override
	public void commitAction() {
		PurchaseBillVO bill = getBill(BillVO.COMMITED);
        if (bill != null && purchaseBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "单据已提交。");
	}
}
