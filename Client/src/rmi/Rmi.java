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
	
	public static boolean flag = false;
	/**
	 * ��ȡ������Զ�̶���
	 * @warning ��ֹʹ�����������ݲ�ӿڣ������������ע�����
	 * @param c ���ͣ�����Ŀ��Ϊdataservice���ڵĽӿڣ� 
	 * @return ���ش˽ӿڵ�ʵ���ڷ�������Զ�̶���
	 */
	public static <T> T getRemote(Class<T> c) {
		try {
			System.out.println(c.getName());
            @SuppressWarnings("unchecked")
			//XXX �޸ķ�������ַ��
            T r =(T) Naming.lookup("rmi://172.25.178.176:8887/" + c.getName());
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
