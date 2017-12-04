package ds_stub;

import java.rmi.RemoteException;

import dataservice.SalesBillDataService;
import po.billpo.SalesBillPO;


public class SalesBillDs_stub implements SalesBillDataService {

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
        System.out.println("sales bill saved in database: " + bill.getId());
        return true;
    }

    @Override
    public SalesBillPO getBillById(String id) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}
