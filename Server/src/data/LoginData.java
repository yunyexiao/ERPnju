package data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;

import dataservice.LoginDataService;
import po.UserPO;

public class LoginData extends UnicastRemoteObject implements LoginDataService {

	public LoginData() throws RemoteException {
		super();
	}

	@Override
	public UserPO findById(String id) throws RemoteException {
		UserPO upo = new UserPO();
		upo.setUserId(id);
		upo.setUserAge(0);
		
		try {
		    Statement s = DataHelper.getInstance().createStatement();
			ResultSet r = s.executeQuery("SELECT SUName,SUPwd,SURank,SUSex,SUBirth,SUTel FROM SystemUser WHERE SUID=" + id +";");
			while(r.next()) {
				upo.setUserName(r.getString("SUName"));
				upo.setUserPwd(r.getString("SUPwd"));
				upo.setUsertype(Integer.valueOf(r.getString("SURank")));
				upo.setUserSex(r.getString("SUSex"));
				upo.setUserTelNumber(r.getString("SUTel"));
			}	
		 }
		 catch(Exception e) {
		   e.printStackTrace();
		   return null;
		 }
		
		return upo;
	}

}
