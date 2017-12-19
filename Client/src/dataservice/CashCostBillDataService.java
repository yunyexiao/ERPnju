package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.billpo.CashCostBillPO;

public interface CashCostBillDataService {

	public boolean saveBill(CashCostBillPO bill) throws RemoteException;;

	public boolean deleteBill(String billid) throws RemoteException;;

	public String getNewId() throws RemoteException;;
	
	public CashCostBillPO getBillById(String id) throws RemoteException;
	
    ArrayList<CashCostBillPO> getBillsByDate(String from, String to) throws RemoteException;

}