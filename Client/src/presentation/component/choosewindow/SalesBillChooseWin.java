package presentation.component.choosewindow;

import blservice.billblservice.SalesBillBLService;
import businesslogic.SalesBillBL;
import presentation.component.MyTableModel;
import vo.billvo.SalesBillVO;


public class SalesBillChooseWin extends BillChooseWin {
    
    private SalesBillBLService salesBillBl;
    private SalesBillVO bill;

    public SalesBillChooseWin(String customerId) {
        super(customerId);
        frame.setVisible(true);
    }

    @Override
    protected void initBLService() {
        salesBillBl = new SalesBillBL();
    }

    @Override
    protected MyTableModel getInitModel() {
        return salesBillBl.getFinishedBills(customerId);
    }

    @Override
    protected MyTableModel search(String type, String key) {
        return salesBillBl.search(type, key);
    }

    @Override
    protected MyTableModel getBillByDate(String from, String to) {
        return salesBillBl.getBillsByDate(from, to);
    }

    @Override
    protected void setBill(String id) {
        bill = salesBillBl.getBill(id);
    }
    
    public SalesBillVO getSalesBill(){
        return bill;
    }

}