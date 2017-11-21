package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CommodityPO;

public interface CommodityDataService {
	/**
	 * ������Ʒʱ����ȡ��ƷӦ�����е�Ψһid
	 * @return ��һ����ƷӦ�����е�id [id��ʽ��]
	 * @throws RemoteException
	 */
	public String getNewId() throws RemoteException;
	/**
	 * ������Ʒ��ID����һ��CommodityPO����<br/>
	 * �Ҳ����ͷ���һ��null...
	 * @param id ��Ʒ��id [id��ʽ��]
	 * @return ���ҵ���CommodityPO����
	 * @throws RemoteException
	 */
	public CommodityPO findById(String id) throws RemoteException;
	/**
	 * �����ݿ�������һ��CommodityPO����
	 * @param commodity ����õ�CommodityPO����
	 * @return �Ƿ����ӳɹ�(���ݿ��д�г�����false)(id�Ѿ����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean add(CommodityPO commodity) throws RemoteException;
	/**
	 * �����ݿ���ɾ��һ��CommodityPO����
	 * @param id ��Ҫɾ������Ʒ��Ψһid [id��ʽ��]
	 * @return �Ƿ�ɾ���ɹ�(���ݿ�����г�����false)(id�����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean delete(String id) throws RemoteException;
	/**
	 * �������ݿ���һ���Ѿ����ڵ�CommodityPO����
	 * @param commodity ��Ҫ���µ�CommodityPO����
	 * @return �Ƿ���³ɹ�(���ݿ�����г�����false)(id�����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean update(CommodityPO commodity) throws RemoteException;
	/**
	 * ��ȡ���ݿ�������δ��ɾ����CommodityPO�����б�
	 * @return ���ݿ�������δ��ɾ����CommodityPO����
	 * @throws RemoteException
	 */
	public ArrayList<CommodityPO> getAllCommodity() throws RemoteException;
}
