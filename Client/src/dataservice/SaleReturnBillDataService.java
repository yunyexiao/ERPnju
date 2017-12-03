package dataservice;

import java.rmi.RemoteException;

import po.billpo.SalesReturnBillPO;

public interface SaleReturnBillDataService {
    
    boolean saveBill(SalesReturnBillPO purchaseBill) throws RemoteException;
    
    boolean deleteBill(String id) throws RemoteException;
    
    String getNewId() throws RemoteException;
    
    SalesReturnBillPO getBillById(String id) throws RemoteException;

}
