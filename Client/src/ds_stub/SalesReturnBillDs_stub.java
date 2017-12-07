package ds_stub;

import java.rmi.RemoteException;

import dataservice.SalesReturnBillDataService;
import po.billpo.SalesReturnBillPO;


public class SalesReturnBillDs_stub implements SalesReturnBillDataService {

    @Override
    public boolean deleteBill(String id) throws RemoteException {
        System.out.println("sale return bill deleted in database: " + id);
        return true;
    }

    @Override
    public String getNewId() throws RemoteException {
        return "12345";
    }

    @Override
    public boolean saveBill(SalesReturnBillPO bill) throws RemoteException {
        System.out.println("sale return bill saved in database: " + bill.getId());
        return true;
    }

    @Override
    public SalesReturnBillPO getBillById(String id) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}
