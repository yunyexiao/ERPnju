package rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
/**
 * 用于获取远程对象的工具类
 * @author 钱美缘
 *
 */
public class Rmi {
	
	public static boolean flag = false;
	/**
	 * 获取服务器远程对象
	 * @warning 禁止使用其他非数据层接口，否则服务器无注册对象
	 * @param c 类型（本项目中为dataservice包内的接口） 
	 * @return 返回此接口的实现在服务器的远程对象
	 */
	public static <T> T getRemote(Class<T> c) {
		try {
			System.out.println(c.getName());
            @SuppressWarnings("unchecked")
			//XXX 修改服务器地址处
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
