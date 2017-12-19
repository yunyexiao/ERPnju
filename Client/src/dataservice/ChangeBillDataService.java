package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.billpo.ChangeBillPO;

public interface ChangeBillDataService extends Remote{

	public ChangeBillPO getBillById(String id) throws RemoteException;
	
	public boolean saveBill(ChangeBillPO bill) throws RemoteException;
	
	public boolean deleteBill(String id) throws RemoteException;
	/**
	 * 返回当天单据的最新编号，即完整ID的最后一部分！不是全部！
	 * @param isOver 判断是否为报溢单-true
	 * @return
	 */
	public String getNewId(boolean isOver) throws RemoteException;
	
}
