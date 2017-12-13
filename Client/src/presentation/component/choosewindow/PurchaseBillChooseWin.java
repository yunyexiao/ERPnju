package presentation.component.choosewindow;

import blservice.billblservice.PurchaseBillBLService;
import businesslogic.PurchaseBillBL;
import presentation.component.MyTableModel;
import vo.billvo.PurchaseBillVO;


public class PurchaseBillChooseWin extends BillChooseWin {
    
    private PurchaseBillBLService purchaseBillBl;
    private PurchaseBillVO bill;

    public PurchaseBillChooseWin(String customerId) {
        super(customerId);
        frame.setVisible(true);
    }

    @Override
    protected void initBLService() {
        purchaseBillBl = new PurchaseBillBL();
    }

    @Override
    protected MyTableModel getInitModel() {
        return purchaseBillBl.getFinishedBills(customerId);
    }

    @Override
    protected MyTableModel search(String type, String key) {
        return purchaseBillBl.search(type, key);
    }

    @Override
    protected MyTableModel getBillByDate(String from, String to) {
        return purchaseBillBl.getBillByDate(from, to);
    }

    @Override
    protected void setBill(String id) {
        bill = purchaseBillBl.getBill(id);
    }
    
    public PurchaseBillVO getPurchaseBill(){
        return this.bill;
    }

}
