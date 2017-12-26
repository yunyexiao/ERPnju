package rmi;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import data.*;

public class RemoteHelper {

	public RemoteHelper() {
		try {
			LocateRegistry.createRegistry(8887);
			bind(new UserData());
			bind(new LogData());
			bind(new CategoryData());
			bind(new CommodityData());
			bind(new CustomerData());
			bind(new AccountData());
			bind(new BillSearchData());
			bind(new CashCostBillData());
			bind(new ChangeBillData());
			bind(new PaymentBillData());
			bind(new PurchaseBillData());
			bind(new PurchaseReturnBillData());
			bind(new ReceiptBillData());
			bind(new SaleReturnBillData());
			bind(new SalesBillData());
			
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ע��һ��Զ�̶���
	 * @param remote ��Ҫ�󶨵Ľӿ�
	 */
	private void bind(Remote remote) {
		try {
			System.out.println(remote.getClass().getInterfaces()[0].getName());
			Naming.bind("rmi://localhost:8887/" + remote.getClass().getInterfaces()[0].getName(), remote);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��" + remote.getClass().getName() + "��������");
		}
	}
}
