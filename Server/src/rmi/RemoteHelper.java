package rmi;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import data.LoginData;

public class RemoteHelper {

	public RemoteHelper() {
		//bind("data.LoginData");
		try {
			bind(new LoginData());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/*
	private boolean bind(String className) {
		try {
			Class<?> c = Class.forName(className);
			Object t = c.newInstance();
			LocateRegistry.createRegistry(8080);
			System.out.println(c.getInterfaces()[0].getName());
			Naming.bind("rmi://localhost:8080/" + c.getInterfaces()[0].getName(), (Remote) t);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	*/
	private void bind(Remote remote) {
		try {
			LocateRegistry.createRegistry(8887);
			System.out.println(remote.getClass().getName());
			Naming.bind("rmi://localhost:8887/" + remote.getClass().getName(), remote);
		} catch (Exception e) {
			System.out.println("绑定" + remote.getClass().getName() + "出现问题");
		}
	}
}
