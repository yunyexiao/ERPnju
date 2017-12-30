package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.MailPO;
import po.UserPO;

public interface MailDataService extends Remote{
	/**�����ݿⱣ��һ���ʼ�*/
	public boolean saveMail(MailPO mail) throws RemoteException;
	/**���ĳλ�û��������ʼ���Ϣ*/
	public ArrayList<MailPO> getMailList(UserPO user) throws RemoteException;
	/**��ĳ���ʼ���Ϊ�Ѷ�*/
	public boolean readMail(MailPO mail) throws RemoteException;
}
