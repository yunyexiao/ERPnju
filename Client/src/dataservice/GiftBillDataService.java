package dataservice;

import java.rmi.RemoteException;

import po.billpo.GiftBillPO;

/**
 * ��Ʒ���͵��ĳ־û����ݷ���
 * 
 * @author �Ҷ��
 */
public interface GiftBillDataService {
    
    boolean add(GiftBillPO bill) throws RemoteException;
    
    String getNewId() throws RemoteException;
    
    GiftBillPO getById(String id) throws RemoteException;

}
