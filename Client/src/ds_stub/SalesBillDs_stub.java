package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.SalesBillDataService;
import po.billpo.BillPO;
import po.billpo.SalesBillPO;


public class SalesBillDs_stub implements SalesBillDataService {
    
    private ArrayList<SalesBillPO> bills;
    
    public SalesBillDs_stub(){
        bills = new ArrayList<>();
        bills.add(new SalesBillPO(
            "20171205", "19:23:55", "12345", null, BillPO.PASS
            , null, null, null, null, 15000, 0, 0, 9500, null
        ));
        bills.add(new SalesBillPO(
            "20171201", "08:30:02", "12000", null, BillPO.PASS
            , null, null, null, null, 10000, 0, 0, 8000, null
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
        bills.add(bill);
        System.out.println("sales bill saved in database: " + bill.getId());
        return true;
    }

    @Override
    public SalesBillPO getBillById(String id) throws RemoteException {
        return bills.get(0);
    }

    @Override
    public ArrayList<SalesBillPO> getBillsBy(String field, String key, boolean isFuzzy) throws RemoteException {
        return bills;
    }

}
