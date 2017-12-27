package dataservice;

import java.rmi.RemoteException;

import po.billpo.GiftBillPO;

/**
 * 商品赠送单的持久化数据服务
 * 
 * @author 恽叶霄
 */
public interface GiftBillDataService {
    
    boolean add(GiftBillPO bill) throws RemoteException;
    
    String getNewId() throws RemoteException;
    
    GiftBillPO getById(String id) throws RemoteException;

}
