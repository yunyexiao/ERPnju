package ds_stub;

import java.rmi.RemoteException;

import dataservice.GiftBillDataService;
import po.billpo.GiftBillPO;


public class GiftBillDs_stub implements GiftBillDataService {

    public GiftBillDs_stub() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean add(GiftBillPO bill) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getNewId() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GiftBillPO getById(String id) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}
