package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import po.billpo.PurchaseBillPO;

public interface PurchaseBillDataService extends Remote{
    
    boolean saveBill(PurchaseBillPO purchaseBill) throws RemoteException;
    
    boolean deleteBill(String id) throws RemoteException;
    
    String getNewId() throws RemoteException;
    
    PurchaseBillPO getBillById(String id) throws RemoteException;

}