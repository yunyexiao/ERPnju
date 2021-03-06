package rmi;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import data.AccountData;
import data.CategoryData;
import data.CommodityData;
import data.CustomerData;
import data.LogData;
import data.UserData;

public class RemoteHelper {

	public RemoteHelper() {
		try {
			LocateRegistry.createRegistry(8887);
			bind(new UserData());
			bind(new LogData());
			bind(new CategoryData());
			bind(new CommodityData());
			bind(new CustomerData());
			bind(new AccountData());
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
			System.out.println(remote.getClass().getInterfaces()[0].getName());
			Naming.bind("rmi://localhost:8887/" + remote.getClass().getInterfaces()[0].getName(), remote);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("绑定" + remote.getClass().getName() + "出现问题");
		}
	}
}
