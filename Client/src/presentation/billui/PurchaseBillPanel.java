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
 * �����������
 * @author �Ҷ��
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
        return "��Ӧ��";
    }

    @Override
    protected String getTableTitle() {
        return "������Ʒ�б�";
    }

    public PurchaseBillVO getBill(int state){
        if(!isCorrectable()) return null;
        String id = getId()
             , operater = user.getId()
             , customerId = customerIdField.getText()
             , remark = remarkField.getText();
        MyTableModel model = (MyTableModel)goodsListTable.getModel();
        double sum = Double.parseDouble(sumField.getText());
        return new PurchaseBillVO(Timetools.getDate(), Timetools.getTime(), id, operater, state, customerId, model, remark, sum);
    }

	@Override
	protected void handleChooseCustomer() {
		handleChooseCustomer(true);
	}

	@Override
	public void newAction() {
		int response = JOptionPane.showConfirmDialog(
                null, "ȷ��Ҫ�½�һ�Ž�������", "��ʾ", JOptionPane.YES_NO_OPTION);
        if(response == 1)return;
        clear();
        billIdField.setText(purchaseBl.getNewId());
        operatorField.setText(user.getName());
	}

	@Override
	public void saveAction() {
		PurchaseBillVO bill = getBill(BillVO.SAVED);
        if (bill != null && purchaseBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "�����ѱ��档");
	}

	@Override
	public void commitAction() {
		PurchaseBillVO bill = getBill(BillVO.COMMITED);
        if (bill != null && purchaseBl.saveBill(bill)) JOptionPane.showMessageDialog(null, "�������ύ��");
	}
}
