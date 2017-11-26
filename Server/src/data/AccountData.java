package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.AccountDataService;
import po.AccountPO;

public class AccountData extends UnicastRemoteObject implements AccountDataService{

	protected AccountData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		int max=0,res=0;
		String newId;
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT AccountID FROM AccountInfo;");
			while(r.next()){
				int temp=0;
				temp=Integer.valueOf(r.getString("AccountID"));
				if(temp>max)max=temp;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		res=max+1;
		newId=String.format("%06d", res);
		return newId;
	}

	@Override
	public AccountPO findById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		AccountPO apo=new AccountPO();
		apo.setId(id);
		String accountName=null;
		double accountMoney=0.0;
		try{
			Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT AccountName,AccountMoney FROM AccountInfo "
			 		+ "WHERE AccountID=" + id +";");
			 while(r.next()){
				 accountName=r.getString("AccountName");
				 accountMoney=Double.valueOf(r.getString("AccountMoney"));
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		apo.setName(accountName);
		apo.setMoney(accountMoney);
		return apo;
	}

	@Override
	public boolean add(AccountPO account) throws RemoteException {
		// TODO Auto-generated method stub
		String accountId=null,accountName=null;
		double accountMoney=0.0;
		accountId=account.getId();
		accountName=account.getName();
		accountMoney=account.getMoney();
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("INSERT INTO AccountInfo VALUES"
					+ "('"+accountId+"','"+accountName+"','"+accountMoney+"')");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("DELETE FROM AccountInfo WHERE AccountID="+id+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean update(AccountPO account) throws RemoteException {
		// TODO Auto-generated method stub
		String accountId=null,accountName=null;
		double accountMoney=0.0;
		accountId=account.getId();
		accountName=account.getName();
		accountMoney=account.getMoney();
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE CustomerInfo SET "
					+ "AccountName="+accountName+", AccountMoney="+accountMoney+"WHERE CusID="+accountId+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public ArrayList<AccountPO> getAllAccount() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<AccountPO> apos=new ArrayList<AccountPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM AccountInfo");
			 while(r.next()){
				 String accountId=null,accountName=null;
				 double accountMoney=0.0;
				 AccountPO apo=new AccountPO();
				 accountId=r.getString("AccountID");
				 accountName=r.getString("AccountName");
				 accountMoney=Double.valueOf(r.getString("AccountMoney"));
				 apo.setId(accountId);
				 apo.setName(accountName);
				 apo.setMoney(accountMoney);
				 apos.add(apo);
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return apos;
	}

}
