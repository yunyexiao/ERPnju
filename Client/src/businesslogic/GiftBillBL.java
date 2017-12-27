package businesslogic;

import blservice.billblservice.BillExamineService;
import businesslogic.inter.GiftBillCreation;
import dataservice.GiftBillDataService;
import ds_stub.GiftBillDs_stub;
import presentation.component.MyTableModel;
import rmi.Rmi;


public class GiftBillBL implements GiftBillCreation, BillExamineService {
    
    private GiftBillDataService giftBillDs;

    public GiftBillBL() {
        giftBillDs = Rmi.flag ? Rmi.getRemote(GiftBillDataService.class) : new GiftBillDs_stub();
    }

    @Override
    public boolean examineBill(String billId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean notPassBill(String billId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean createAndCommit(MyTableModel gifts, String salesBillId) {
        // TODO Auto-generated method stub
        return false;
    }

}
