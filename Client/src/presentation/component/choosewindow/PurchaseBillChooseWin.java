package presentation.component.choosewindow;

import blservice.billblservice.BillBLService;
import blservice.billblservice.PurchaseBillBLService;
import businesslogic.BillBL;
import businesslogic.PurchaseBillBL;
import presentation.component.MyTableModel;
import vo.billvo.PurchaseBillVO;


public class PurchaseBillChooseWin extends BillChooseWin {
    
    private PurchaseBillBLService purchaseBillBl;
    private BillBLService billBL;
    private PurchaseBillVO bill;

    public PurchaseBillChooseWin(String customerId) {
        super(customerId);
        frame.setVisible(true);
    }

    @Override
    protected void initBLService() {
        purchaseBillBl = new PurchaseBillBL();
        billBL = new BillBL();
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
        bill = billBL.getPurchaseBill(id);
    }
    
    public PurchaseBillVO getPurchaseBill(){
        return this.bill;
    }

}
