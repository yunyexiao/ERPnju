package dataservice;

import java.rmi.RemoteException;

import po.billpo.PurchaseReturnBillPO;

public interface PurchaseReturnBillDataService {
    
    boolean saveBill(PurchaseReturnBillPO purchaseBill) throws RemoteException;
    
    boolean deleteBill(String id) throws RemoteException;
    
    String getNewId() throws RemoteException;
    
    PurchaseReturnBillPO getBillById(String id) throws RemoteException;

}
