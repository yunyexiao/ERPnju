package ds_stub;

import java.rmi.RemoteException;

import dataservice.PurchaseReturnBillDataService;
import dataservice.PurchaseReturnBillPO;


public class PurchaseReturnBillDs_stub implements PurchaseReturnBillDataService {

    @Override
    public boolean deleteBill(String id) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getNewId() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean saveBill(PurchaseReturnBillPO purchaseBill) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public PurchaseReturnBillPO getBillById(String id) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}
