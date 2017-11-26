package rmi;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import data.LoginData;

public class RemoteHelper {

	public RemoteHelper() {
		try {
			bind(new LoginData());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 注册一个远程对象
	 * @param remote 需要绑定的接口
	 */
	private void bind(Remote remote) {
		try {
			LocateRegistry.createRegistry(8887);
			System.out.println(remote.getClass().getInterfaces()[0].getName());
			Naming.bind("rmi://localhost:8887/" + remote.getClass().getInterfaces()[0].getName(), remote);
		} catch (Exception e) {
			System.out.println("绑定" + remote.getClass().getName() + "出现问题");
		}
	}
}
