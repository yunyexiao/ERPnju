package data;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import dataservice.UserDataService;
import po.UserPO;

public class UserData implements UserDataService {

	@Override
	public String getNewId() throws RemoteException {
		int max=0,res=0;
		String newId;
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT SUID FROM SystemUser;");
			while(r.next())
			{
				int temp=0;
				temp=Integer.valueOf(r.getString("SUID"));
				if(temp>max)max=temp;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		res=max+1;
		newId=String.format("%04d", res);
		return newId;
	}

	@Override
	public UserPO findById(String id) throws RemoteException {
		UserPO upo = new UserPO();
		upo.setUserId(id);
		String userName = null,userPwd = null,userDept = null,userSex = null,userBirth = null,userTel = null;
		int userRank = -1,userAge,birthYear;
		  
		try {
		    Statement s = DataHelper.getInstance().createStatement();
			ResultSet r = s.executeQuery("SELECT SUName,SUPwd,SUDept,SURank,SUSex,SUBirth,SUTel FROM SystemUser WHERE SUID=" + id +";");
			while(r.next()) {
				userName=r.getString("SUName");
				userPwd=r.getString("SUPwd");
				userDept=r.getString("SUDept");
				userRank=Integer.valueOf(r.getString("SURank"));
				userSex=r.getString("SUSex");
				userTel=r.getString("SUTel");
				userBirth=r.getString("SUBirth");
			}	
		 }
		 catch(Exception e) {
		   e.printStackTrace();
		   return null;
		 }
		
		birthYear=Integer.valueOf(userBirth);
		Calendar now = Calendar.getInstance(); 
		userAge=now.get(Calendar.YEAR)-birthYear;
		
		upo.setUserAge(userAge);
		upo.setUserDept(userDept);
		upo.setUserName(userName);
		upo.setUserPwd(userPwd);
		upo.setUserSex(userSex);
		upo.setUserTelNumber(userTel);
		upo.setUsertype(userRank);
		
		return upo;
		
	}

	@Override
	public boolean add(UserPO user) throws RemoteException {
		String userId=null,userName = null,userPwd = null,userDept = null,userSex = null,userTel = null;
		int userRank = -1,userAge,birthYear,userBirth = 0;
		userId=user.getUserId();
		userName=user.getUserName();
		userPwd=user.getUserPwd();
		userDept=user.getUserDept();
		userSex=user.getUserSex();
		userAge=user.getUserAge();
		userTel=user.getUserTelNumber();
		userRank=user.getUsertype();
		Calendar now = Calendar.getInstance(); 
		userBirth=now.get(Calendar.YEAR)-userAge;
		
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r =s.executeUpdate("INSERT INTO SystemUser VALUES"
					+ "('"+userId+"','"+userName+"','"+userPwd+"','"+userDept+"','"+userRank+"','"+userSex+"','"+userBirth+"','"+userTel+"')");
			
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return true;
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("DELETE FROM SystemUser WHERE SUID="+id+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean update(UserPO user) throws RemoteException {
		String userId=null,userName = null,userPwd = null,userDept = null,userSex = null,userTel = null;
		int userRank = -1,userAge,birthYear,userBirth = 0;
		userId=user.getUserId();
		userName=user.getUserName();
		userPwd=user.getUserPwd();
		userDept=user.getUserDept();
		userSex=user.getUserSex();
		userAge=user.getUserAge();
		userTel=user.getUserTelNumber();
		userRank=user.getUsertype();
		Calendar now = Calendar.getInstance(); 
		userBirth=now.get(Calendar.YEAR)-userAge;
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE SystemUser SET "
					+ "SUName="+userName+", SUPwd="+userPwd+", SUDept="+userDept+", SURank="+userRank
					+", SUSex="+userSex+", SUBirth="+userBirth+", SUTel="+userTel+"WHERE SUID="+userId+";");
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
				String userId=null,userName = null,userPwd = null,userDept = null,userSex = null,userBirth = null,userTel = null;
				int userRank = -1,userAge,birthYear;
				UserPO upo = new UserPO();
				userId=r.getString("SUID");
				userName=r.getString("SUName");
				userPwd=r.getString("SUPwd");
				userDept=r.getString("SUDept");
				userRank=Integer.valueOf(r.getString("SURank"));
				userSex=r.getString("SUSex");
				userTel=r.getString("SUTel");
				userBirth=r.getString("SUBirth");
				
				birthYear=Integer.valueOf(userBirth);
				Calendar now = Calendar.getInstance(); 
				userAge=now.get(Calendar.YEAR)-birthYear;
				
				upo.setUserId(userId);
				upo.setUserAge(userAge);
				upo.setUserDept(userDept);
				upo.setUserName(userName);
				upo.setUserPwd(userPwd);
				upo.setUserSex(userSex);
				upo.setUserTelNumber(userTel);
				upo.setUsertype(userRank);
				upos.add(upo);	
			}	
		 }
		 catch(Exception e) {
		   e.printStackTrace();
		   return null;
		 }
		return upos;
	}

}
