package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.AccountPO;
import po.CommodityPO;
import po.CustomerPO;

public interface InitDataService {

	/**
	 * ��ø��ڳ���Ϣ����ʼʱ��
	 * @return
	 */
	public String[] getInitInfo() throws RemoteException;
	/**
	 * ��õ�ǰ���ף����û�оͷ���null
	 * @return
	 */
	public String getYear() throws RemoteException;
	
	public ArrayList<CommodityPO> getCommodityInfo(String year) throws RemoteException;
	
	public ArrayList<CustomerPO> getCustomerInfo(String year) throws RemoteException;
	
	public ArrayList<AccountPO> getAccountInfo(String year) throws RemoteException;
	/**
	 * �ڳ���Ϣ��ʼ��
	 * @return
	 */
	public boolean initNewOne() throws RemoteException;
}
