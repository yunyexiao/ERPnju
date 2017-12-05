package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.AccountDataService;
import po.AccountPO;

public class AccountData extends UnicastRemoteObject implements AccountDataService{
	private String tableName="AccountInfo";
	private String idName="AccountID";

	protected AccountData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		
		String newId=SQLQueryHelper.getNewId(tableName, idName, "%06d");
		
		return newId;
	}

	@Override
	public AccountPO findById(String id) throws RemoteException {
		AccountPO apo = new AccountPO();
		apo.setId(id);
		
		try{
			//Statement s = DataHelper.getInstance().createStatement();
			 //ResultSet r = s.executeQuery("SELECT AccountName,AccountMoney FROM AccountInfo WHERE AccountID = " + id +";");
			 ResultSet r=SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			 while(r.next()){
				 apo.setName(r.getString("AccountName"));
		         apo.setMoney(r.getDouble("AccountMoney"));
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return apo;
	}

	@Override
	public boolean add(AccountPO account) throws RemoteException {
	
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r = s.executeUpdate("INSERT INTO AccountInfo VALUES ('"
			        +account.getId()+"','"
					+account.getName()+"','"
			        +account.getMoney()+
			        1+"')");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		
        boolean res=SQLQueryHelper.getFalseDeleteResult(tableName, "AccountIsExist",idName, id);
		
		return res;	
	}

	@Override
	public boolean update(AccountPO account) throws RemoteException {
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r = s.executeUpdate("UPDATE CustomerInfo SET "
					+ "AccountName = "+account.getName()
					+", AccountMoney = "+account.getMoney()
					+" WHERE AccountID = "+account.getId()+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public ArrayList<AccountPO> getAllAccount() throws RemoteException {
		ArrayList<AccountPO> apos = new ArrayList<AccountPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM AccountInfo");
			 while(r.next()){
				
				 boolean accountIsExist=r.getBoolean("AccountIsExist");
				 AccountPO apo = new AccountPO();
				 
				 if(accountIsExist){
				 apo.setId(r.getString("AccountID"));
				 apo.setName(r.getString("AccountName"));
				 apo.setMoney(r.getDouble("AccountMoney"));
				 
				 apos.add(apo);
				 }
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return apos;
	}

	@Override
	public ArrayList<AccountPO> getUsersBy(String field, String content, boolean isfuzzy) throws RemoteException {
		ArrayList<AccountPO> apos = new ArrayList<AccountPO>();
		if(isfuzzy){
			try{
				 Statement s = DataHelper.getInstance().createStatement();
				 ResultSet r = s.executeQuery("SELECT * FROM AccountInfo WHERE "+field+"LIKE '%"+content+"%';");
				 while(r.next()){
					 boolean accountIsExist=r.getBoolean("AccountIsExist");
					 AccountPO apo = new AccountPO();
					 
					 if(accountIsExist){
					 apo.setId(r.getString("AccountID"));
					 apo.setName(r.getString("AccountName"));
					 apo.setMoney(r.getDouble("AccountMoney"));
					 
					 apos.add(apo);
					 }
				 }
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		else if(!isfuzzy){
			try{
				 //Statement s = DataHelper.getInstance().createStatement();
				 //ResultSet r = s.executeQuery("SELECT * FROM AccountInfo WHERE "+field+"LIKE '"+content+"';");
				 ResultSet r =SQLQueryHelper.getRecordByAttribute(tableName, field, content);
				 while(r.next()){
					 boolean accountIsExist=r.getBoolean("AccountIsExist");
					 AccountPO apo = new AccountPO();
					 
					 if(accountIsExist){
					 apo.setId(r.getString("AccountID"));
					 apo.setName(r.getString("AccountName"));
					 apo.setMoney(r.getDouble("AccountMoney"));
					 
					 apos.add(apo);
					 }
				 }
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		
		return apos;
	}

}
