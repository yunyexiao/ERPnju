package ds_stub;

import java.rmi.RemoteException;

import dataservice.PaymentBillDataService;
import po.billpo.PaymentBillPO;

public class PaymentBillDs_stub implements PaymentBillDataService {
	public PaymentBillDs_stub() {}
	
	@Override
	public boolean saveBill(PaymentBillPO paymentBill) throws RemoteException {
        System.out.println("payment bill saved in database: " + paymentBill.getId());
        return true;
	}

	@Override
	public boolean deleteBill(String id) throws RemoteException {
		System.out.println("payment bill deleted in database: " + id);
        return true;
	}

	@Override
	public String getNewId() throws RemoteException {
		return "00123";
	}

	@Override
	public PaymentBillPO getBillById(String id) throws RemoteException {
		return null;
	}

}
