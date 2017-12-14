package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.SalesBillDataService;
import po.billpo.BillPO;
import po.billpo.SalesItemsPO;
import po.billpo.SalesBillPO;


public class SalesBillDs_stub implements SalesBillDataService {
    
    private static final ArrayList<SalesBillPO> BILLS = new ArrayList<>();
    
    public SalesBillDs_stub(){
        if(BILLS.size() > 0) return;
        
        ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 50, 100, 5000));
        items1.add(new SalesItemsPO("000002", "", 100, 100, 10000));

        BILLS.add(new SalesBillPO(
            "2017-12-05", "19:23:55", "12345", null, BillPO.PASS
            , null, null, null, null, 15000, 500, 5000, 9500, items1
        ));
        
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 50, 200, 10000));
        
        BILLS.add(new SalesBillPO(
            "2017-12-01", "08:30:02", "12000", null, BillPO.PASS
            , null, null, null, null, 10000, 1000, 1000, 8000, items2
        ));
        
        ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000004", "", 100, 100, 10000));
        items3.add(new SalesItemsPO("000003", "", 200, 200, 40000));
        
        BILLS.add(new SalesBillPO(
            "2017-12-08", "11:24:51", "32100", null, BillPO.COMMITED
            , null, "rarara", null, "12300", 50000, 1000, 1000, 48000, items3
        ));
        
        ArrayList<SalesItemsPO> items4 = new ArrayList<>();
        items4.add(new SalesItemsPO("000002", "", 30, 100, 3000));

        BILLS.add(new SalesBillPO(
            "2017-12-09", "12:08:21", "98765", null, BillPO.SAVED
            , null, "°Â¶÷", null, null, 3000, 300, 500, 2200, items4
        ));
    }

    @Override
    public boolean deleteBill(String billid) throws RemoteException {
        System.out.println("sales bill deleted in database: " + billid);
        return true;
    }

    @Override
    public String getNewId() throws RemoteException {
        return "00123";
    }

    @Override
    public boolean saveBill(SalesBillPO bill) throws RemoteException {
        BILLS.add(bill);
        System.out.println("sales bill saved in database: " + bill.getId());
        return true;
    }

    @Override
    public SalesBillPO getBillById(String id) throws RemoteException {
        return BILLS.get(0);
    }

    @Override
    public ArrayList<SalesBillPO> getBillsBy(String field, String key, boolean isFuzzy) throws RemoteException {
        ArrayList<SalesBillPO> result = new ArrayList<>();
        result.add(BILLS.get(0));
        result.add(BILLS.get(3));
        return result;
    }

    @Override
    public ArrayList<SalesBillPO> getBillByDate(String from, String to) throws RemoteException {
        ArrayList<SalesBillPO> result = new ArrayList<>();
        result.add(BILLS.get(2));
        result.add(BILLS.get(1));
        return result;
    }

}
