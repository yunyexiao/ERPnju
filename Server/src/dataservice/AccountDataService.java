package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.AccountPO;

public interface AccountDataService {

	/**
	 * �����˻�ʱ����ȡ�˻�Ӧ�����е�Ψһid
	 * @return ��һ���˻�Ӧ�����е�id [id��ʽ����λ�����ַ�������0001]
	 * @throws RemoteException
	 */
	public String getNewId() throws RemoteException;
	/**
	 * �����˻���ID����һ��AccountPO����<br/>
	 * �Ҳ����ͷ���һ��null...
	 * @param id �˻���id [id��ʽ����λ�����ַ�������0001]
	 * @return ���ҵ���AccountPO����
	 * @throws RemoteException
	 */
	public AccountPO findById(String id) throws RemoteException;
	/**
	 * �����ݿ�������һ��AccountPO����
	 * @param account ����õ�AccountPO����
	 * @return �Ƿ����ӳɹ�(���ݿ��д�г�����false)(id�Ѿ����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean add(AccountPO account) throws RemoteException;
	/**
	 * �����ݿ���ɾ��һ��AccountPO����
	 * @param id ��Ҫɾ�����˻���Ψһid [id��ʽ����λ�����ַ�������0001]
	 * @return �Ƿ�ɾ���ɹ�(���ݿ�����г�����false)(id�����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean delete(String id) throws RemoteException;
	/**
	 * �������ݿ���һ���Ѿ����ڵ�AccountPO����
	 * @param account ��Ҫ���µ�AccountPO����
	 * @return �Ƿ���³ɹ�(���ݿ�����г�����false)(id�����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean update(AccountPO account) throws RemoteException;
	/**
	 * ��ȡ���ݿ�������δ��ɾ����AccountPO�����б�
	 * @return ���ݿ�������δ��ɾ����AccountPO����
	 * @throws RemoteException
	 */
	public ArrayList<AccountPO> getAllAccount() throws RemoteException;
}
