package rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
/**
 * ���ڻ�ȡԶ�̶���Ĺ�����
 * @author Ǯ��Ե
 *
 */
public class Rmi {
	
	public static boolean flag = true;
	public static final String ipAddress = "120.79.145.97";
	/**
	 * ��ȡ������Զ�̶���
	 * @warning ��ֹʹ�����������ݲ�ӿڣ������������ע�����
	 * @param c ���ͣ�����Ŀ��Ϊdataservice���ڵĽӿڣ� 
	 * @return ���ش˽ӿڵ�ʵ���ڷ�������Զ�̶���
	 */
	public static <T> T getRemote(Class<T> c) {
		try {
            @SuppressWarnings("unchecked")
			//�޸ķ�������ַ��
            T r =(T) Naming.lookup("rmi://"+ipAddress+":6667/" + c.getName());
            //T r =(T) Naming.lookup("rmi://127.0.0.1:6667/" + c.getName());
            //��Ҫ�޸�SMRMISocket�еķ���
            return r;
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();  
        }
		return null;
	}
	
	public static String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}
}
