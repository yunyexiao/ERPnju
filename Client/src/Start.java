import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import presentation.LoginWindow;
import rmi.RemoteHelper;

public class Start {
	private RemoteHelper remoteHelper;

	private void linkToServer() {
		try {
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://172.25.181.218:8887/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private Start() {
		linkToServer();
		new LoginWindow();
	}
	
	public static void main(String[] args) {
		//new Start();
		new LoginWindow();
	}

}
