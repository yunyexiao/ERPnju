package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.AccountPO;
import po.CommodityPO;
import po.CustomerPO;

public interface InitDataService extends Remote{

	/**
	 * ��ø��ڳ���Ϣ����ʼʱ��
	 * @return
	 */
	public String[] getInitInfo() throws RemoteException;
	/**
	 * ��õ�ǰ���ף����û�оͷ���null
	 * @return
	 */
	//��ȡ���������
	public String getYear() throws RemoteException;
	
	public ArrayList<CommodityPO> getCommodityInfo(String year) throws RemoteException;
	
	public ArrayList<CustomerPO> getCustomerInfo(String year) throws RemoteException;
	
	public ArrayList<AccountPO> getAccountInfo(String year) throws RemoteException;
	/**
	 * �ڳ���Ϣ��ʼ�������������������ű�����ݸ���һ��ת�浽�ڳ�����
	 * @return
	 */
	public boolean initNewOne() throws RemoteException;
}
