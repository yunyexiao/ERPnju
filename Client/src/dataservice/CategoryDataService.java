package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CategoryPO;

public interface CategoryDataService {

	/**
	 * ������Ʒ����ʱ����ȡ��ƷӦ�����е�Ψһid
	 * @return ��һ����ƷӦ�����е�id [id��ʽ��]
	 * @throws RemoteException
	 */
	public String getNewId() throws RemoteException;
	/**
	 * ������Ʒ�����ID����һ��CategoryPO����<br/>
	 * �Ҳ����ͷ���һ��null...
	 * @param id ��Ʒ�����id [id��ʽ��]
	 * @return ���ҵ���CategoryPO����
	 * @throws RemoteException
	 */
	public CategoryPO findById(String id) throws RemoteException;
	/**
	 * �����ݿ�������һ��CategoryPO����
	 * @param category ����õ�CategoryPO����
	 * @return �Ƿ����ӳɹ�(���ݿ��д�г�����false)(id�Ѿ����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean add(CategoryPO category) throws RemoteException;
	/**
	 * �����ݿ���ɾ��һ��CategoryPO����
	 * @param id ��Ҫɾ������Ʒ�����Ψһid [id��ʽ����λ�����ַ�������0001]
	 * @return �Ƿ�ɾ���ɹ�(���ݿ�����г�����false)(id�����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean delete(String id) throws RemoteException;
	/**
	 * �������ݿ���һ���Ѿ����ڵ�CategoryPO����
	 * @param category ��Ҫ���µ�CategoryPO����
	 * @return �Ƿ���³ɹ�(���ݿ�����г�����false)(id�����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean update(CategoryPO category) throws RemoteException;
	/**
	 * ��ȡ���ݿ�������δ��ɾ����CategoryPO�����б�
	 * @return ���ݿ�������δ��ɾ����CategoryPO����
	 * @throws RemoteException
	 */
	public ArrayList<CategoryPO> getAllCategory() throws RemoteException;
}
