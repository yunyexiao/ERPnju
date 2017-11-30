package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CustomerPO;

public interface CustomerDataService extends Remote{

	/**
	 * �����ͻ�ʱ����ȡ�ͻ�Ӧ�����е�Ψһid
	 * @return ��һ���ͻ�Ӧ�����е�id
	 * @throws RemoteException
	 */
	public String getNewId() throws RemoteException;
	/**
	 * ���ݿͻ���ID����һ��CustomerPO���󣬲�������û��Ƿ�ɾ��<br/>
	 * �Ҳ����ͷ���һ��null...
	 * @param id �ͻ���id
	 * @return ���ҵ���CustomerPO����
	 * @throws RemoteException
	 */
	public CustomerPO findById(String id) throws RemoteException;
	/**
	 * �����ݿ�������һ��CustomerPO����
	 * @param customer ����õ�CustomerPO����
	 * @return �Ƿ����ӳɹ�(���ݿ��д�г�����false)(id�Ѿ����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean add(CustomerPO customer) throws RemoteException;
	/**
	 * �����ݿ���ɾ��һ��CustomerPO����
	 * @param id ��Ҫɾ���Ŀͻ���Ψһid
	 * @return �Ƿ�ɾ���ɹ�(���ݿ�����г�����false)(id�����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean delete(String id) throws RemoteException;
	/**
	 * �������ݿ���һ���Ѿ����ڵ�CustomerPO����
	 * @param customer ��Ҫ���µ�CustomerPO����
	 * @return �Ƿ���³ɹ�(���ݿ�����г�����false)(id�����ڷ���false)
	 * @throws RemoteException
	 */
	public boolean update(CustomerPO customer) throws RemoteException;
	/**
	 * ��ȡ���ݿ�������δ��ɾ����CustomerPO�����б�
	 * @return ���ݿ�������δ��ɾ����CustomerPO����
	 * @throws RemoteException
	 */
	public ArrayList<CustomerPO> getAllCustomer() throws RemoteException;
	/**
	 * ��ѯ����������CustomerPO��¼
	 * @param field ��ѯ���ֶ�������Ҫ�����ݿⱣ��һ�£�
	 * @param content ��ѯ�����ݣ��Ƿ��ַ��ɿͻ��˹��ˣ�
	 * @param isfuzzy �Ƿ��ʴ�ģ�����ң�true��ʾ�ʴţ��������ģ���
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<CustomerPO> getUsersBy(String field, String content, boolean isfuzzy) throws RemoteException;
}
