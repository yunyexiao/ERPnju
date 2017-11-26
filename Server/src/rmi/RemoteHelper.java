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
	 * ע��һ��Զ�̶���
	 * @param remote ��Ҫ�󶨵Ľӿ�
	 */
	private void bind(Remote remote) {
		try {
			LocateRegistry.createRegistry(8887);
			System.out.println(remote.getClass().getInterfaces()[0].getName());
			Naming.bind("rmi://localhost:8887/" + remote.getClass().getInterfaces()[0].getName(), remote);
		} catch (Exception e) {
			System.out.println("��" + remote.getClass().getName() + "��������");
		}
	}
}
