package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import dataservice.UserDataService;
import po.UserPO;

public class UserData extends UnicastRemoteObject implements UserDataService {
	private String tableName="SystemUser";
	private String idName="SUID";

	public UserData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		
        String newId=SQLQueryHelper.getNewId(tableName, idName, "%04d");
		
		return newId;
	}

	@Override
	public UserPO findById(String id) throws RemoteException {
		
		UserPO upo = new UserPO();
		int userAge,birthYear = 0;
		upo.setUserId(id);
		
		try {
		    //Statement s = DataHelper.getInstance().createStatement();
			//ResultSet r = s.executeQuery("SELECT SUName,SUPwd,SUDept,SURank,SUSex,SUBirth,SUTel "
					//+ "FROM SystemUser WHERE SUID=" + id +";");
			ResultSet r = SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			while(r.next()) {
				
				birthYear=r.getInt("SUBirth");
				upo.setUserName(r.getString("SUName"));
		        upo.setUserPwd(r.getString("SUPwd"));
		        upo.setUserSex(r.getString("SUSex"));
		        upo.setUserTelNumber(r.getString("SUTel"));
		        upo.setUsertype(r.getInt("SUDept"));
		        upo.setUserRank(r.getInt("SURank"));
			}	
		 }
		 catch(Exception e) {
		   e.printStackTrace();
		   return null;
		 }
		
		
		Calendar now = Calendar.getInstance(); 
		userAge=now.get(Calendar.YEAR)-birthYear;
		
		upo.setUserAge(userAge);
		
		return upo;	
	}

	@Override
	public boolean add(UserPO user) throws RemoteException {
				
		int userBirth = 0;
		
		Calendar now = Calendar.getInstance(); 
		userBirth=now.get(Calendar.YEAR)-user.getUserAge();
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("INSERT INTO SystemUser VALUES('"
			        +user.getUserId()+"','"
					+user.getUserName()+"','"
			        +user.getUserPwd()+"','"
					+user.getUsertype()+"','"
					+user.getUserRank()+"','"
					+user.getUserSex()+"','"
					+userBirth+"','"
					+user.getUserTelNumber()+"','"
					+1+"')");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		
        boolean res=SQLQueryHelper.getFalseDeleteResult(tableName, "SUIsExist",idName, id);
		
		return res;
	}

	@Override
	public boolean update(UserPO user) throws RemoteException {
		
		int userBirth = 0;
		
		Calendar now = Calendar.getInstance(); 
		userBirth=now.get(Calendar.YEAR)-user.getUserAge();
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE SystemUser SET "
					+ "SUName="+user.getUserName()
					+", SUPwd="+user.getUserPwd()
					+", SUDept="+user.getUsertype()
					+", SURank="+user.getUserRank()
					+", SUSex="+user.getUserSex()
					+", SUBirth="+userBirth
					+", SUTel="+user.getUserTelNumber()
					+"WHERE SUID="+user.getUserId()+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		
		return false;
		
	}

	@Override
	public ArrayList<UserPO> getAllUser() throws RemoteException {
		
		ArrayList<UserPO> upos=new ArrayList<UserPO>();
		
		try {
		    Statement s = DataHelper.getInstance().createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM SystemUser");
			while(r.next()) {
				
				int userAge;
				boolean userIsExist=r.getBoolean("SUIsExist");				
				UserPO upo = new UserPO();
							
				Calendar now = Calendar.getInstance(); 
				userAge=now.get(Calendar.YEAR)-r.getInt("SUBirth");
				
				if(userIsExist){
				upo.setUserId(r.getString("SUID"));
				upo.setUserAge(userAge);
				upo.setUserName(r.getString("SUName"));
		        upo.setUserPwd(r.getString("SUPwd"));
		        upo.setUserSex(r.getString("SUSex"));
		        upo.setUserTelNumber(r.getString("SUTel"));
		        upo.setUsertype(r.getInt("SUDept"));
		        upo.setUserRank(r.getInt("SURank"));
				
				upos.add(upo);	
				}
			}	
		 }
		 catch(Exception e) {
		   e.printStackTrace();
		   return null;
		 }
		return upos;
	}

	@Override
	public ArrayList<UserPO> getUsersBy(String field, String content, boolean isfuzzy) throws RemoteException {
		ArrayList<UserPO> upos=new ArrayList<UserPO>();
		if(isfuzzy){
			try {
			    Statement s = DataHelper.getInstance().createStatement();
				ResultSet r = s.executeQuery("SELECT * FROM SystemUser WHERE "+field+"LIKE '%"+content+"%';");
				while(r.next()) {
					int userAge;
					boolean userIsExist=r.getBoolean("SUIsExist");				
					UserPO upo = new UserPO();
								
					Calendar now = Calendar.getInstance(); 
					userAge=now.get(Calendar.YEAR)-r.getInt("SUBirth");
					
					if(userIsExist){
					upo.setUserId(r.getString("SUID"));
					upo.setUserAge(userAge);
					upo.setUserName(r.getString("SUName"));
			        upo.setUserPwd(r.getString("SUPwd"));
			        upo.setUserSex(r.getString("SUSex"));
			        upo.setUserTelNumber(r.getString("SUTel"));
			        upo.setUsertype(r.getInt("SUDept"));
			        upo.setUserRank(r.getInt("SURank"));
					
					upos.add(upo);	
					}
				}	
			 }
			 catch(Exception e) {
			   e.printStackTrace();
			   return null;
			 }
		}
		else if(!isfuzzy){
			try {
			    //Statement s = DataHelper.getInstance().createStatement();
				//ResultSet r = s.executeQuery("SELECT * FROM SystemUser WHERE "+field+"LIKE '"+content+"';");
				ResultSet r=SQLQueryHelper.getRecordByAttribute(tableName, field, content);
				while(r.next()) {
					int userAge;
					boolean userIsExist=r.getBoolean("SUIsExist");				
					UserPO upo = new UserPO();
								
					Calendar now = Calendar.getInstance(); 
					userAge=now.get(Calendar.YEAR)-r.getInt("SUBirth");
					
					if(userIsExist){
					upo.setUserId(r.getString("SUID"));
					upo.setUserAge(userAge);
					upo.setUserName(r.getString("SUName"));
			        upo.setUserPwd(r.getString("SUPwd"));
			        upo.setUserSex(r.getString("SUSex"));
			        upo.setUserTelNumber(r.getString("SUTel"));
			        upo.setUsertype(r.getInt("SUDept"));
			        upo.setUserRank(r.getInt("SURank"));
					
					upos.add(upo);	
					}
				}	
			 }
			 catch(Exception e) {
			   e.printStackTrace();
			   return null;
			 }
		}
		return upos;
	}

}
