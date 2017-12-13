package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.PurchaseBillDataService;
import po.billpo.BillPO;
import po.billpo.PurchaseBillItemsPO;
import po.billpo.PurchaseBillPO;


public class PurchaseBillDs_stub implements PurchaseBillDataService {
    
    private static final ArrayList<PurchaseBillPO> BILLS = new ArrayList<>();

    public PurchaseBillDs_stub() {
        if(BILLS.size() > 0) return;
        
        ArrayList<PurchaseBillItemsPO> items1 = new ArrayList<>();
        items1.add(new PurchaseBillItemsPO("000001", "", 50, 100, 5000));
        items1.add(new PurchaseBillItemsPO("000002", "", 100, 100, 10000));
        
        BILLS.add(new PurchaseBillPO(
            "2017-12-05", "19:23:55", "12345", null
            , BillPO.PASS, null, null, 15000, items1
        ));
        
        ArrayList<PurchaseBillItemsPO> items2 = new ArrayList<>();
        items2.add(new PurchaseBillItemsPO("000003", "", 50, 200, 10000));
        
        BILLS.add(new PurchaseBillPO(
            "2017-12-01", "08:30:02", "12000", null
            , BillPO.PASS, null, null, 10000, items2
        ));
        
        ArrayList<PurchaseBillItemsPO> items3 = new ArrayList<>();
        items3.add(new PurchaseBillItemsPO("000004", "", 100, 100, 10000));
        items3.add(new PurchaseBillItemsPO("000003", "", 200, 200, 40000));
        
        BILLS.add(new PurchaseBillPO(
            "2017-12-08", "11:24:51", "32100", null
            , BillPO.COMMITED, null, "rarara", 50000, items3
        ));
        
        ArrayList<PurchaseBillItemsPO> items4 = new ArrayList<>();
        items4.add(new PurchaseBillItemsPO("000002", "", 30, 100, 3000));
        
        BILLS.add(new PurchaseBillPO(
            "2017-12-09", "12:08:21", "98765", null
            , BillPO.SAVED, null, "°Â¶÷", 3000, items4
        ));
    }

    @Override
    public boolean saveBill(PurchaseBillPO purchaseBill) {
        BILLS.add(purchaseBill);
        System.out.println("purchase bill saved in database: " + purchaseBill.getId());
        return true;
    }

    @Override
    public boolean deleteBill(String id) {
        for(PurchaseBillPO bill: BILLS){
            if(bill.getId().equals(id))
                BILLS.remove(bill);
        }
        System.out.println("purchase bill deleted in database: " + id);
        return true;
    }

    @Override
    public String getNewId() {
        return "00123";
    }

    @Override
    public PurchaseBillPO getBillById(String id) {
        return BILLS.get(0);
    }

    @Override
    public ArrayList<PurchaseBillPO> getBillsBy(String field, String key, boolean isfuzzy) throws RemoteException {
        ArrayList<PurchaseBillPO> result = new ArrayList<>();
        result.add(BILLS.get(1));
        result.add(BILLS.get(2));
        return result;
    }

    @Override
    public ArrayList<PurchaseBillPO> getBillsByDate(String from, String to) throws RemoteException {
        ArrayList<PurchaseBillPO> result = new ArrayList<>();
        result.add(BILLS.get(0));
        result.add(BILLS.get(3));
        return result;
    }

}
