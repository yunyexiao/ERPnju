package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.AccountDataService;
import po.AccountPO;

public class AccountData extends UnicastRemoteObject implements AccountDataService{

	private static final long serialVersionUID = 2621222134047070486L;
	private String tableName = "AccountInfo";
	private String idName = "AccountID";

	public AccountData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		return SQLQueryHelper.getNewId(tableName, idName, "%06d");
	}

	@Override
	public AccountPO findById(String id) throws RemoteException {
		try{
			ResultSet r = SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			return getAccountPO(r);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean add(AccountPO account) throws RemoteException {
		return SQLQueryHelper.add(tableName, account.getId(), account.getName(), account.getMoney(), 1);
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		return SQLQueryHelper.getFalseDeleteResult(tableName, "AccountIsExist",idName, id);	
	}

	@Override
	public boolean update(AccountPO account) throws RemoteException {
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r = s.executeUpdate("UPDATE CustomerInfo SET "
					+ "AccountName = '"+account.getName()
					+"', AccountMoney = '"+account.getMoney()
					+"' WHERE AccountID = "+account.getId()+";");
			if(r > 0) return true;
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
			while(r.next()) if(r.getBoolean("AccountIsExist")) apos.add(getAccountPO(r));
			return apos;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<AccountPO> getAccountsBy(String field, String content, boolean isfuzzy) throws RemoteException {
		ArrayList<AccountPO> apos = new ArrayList<AccountPO>();
		ResultSet r = null;
		try{
			if(isfuzzy){
				Statement s = DataHelper.getInstance().createStatement();
			    r = s.executeQuery("SELECT * FROM AccountInfo WHERE "+field+" LIKE '%"+content+"%';");
			}
			else r = SQLQueryHelper.getRecordByAttribute(tableName, field, content);
			while(r.next()) if(r.getBoolean("AccountIsExist")) apos.add(getAccountPO(r));
			return apos;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private AccountPO getAccountPO(ResultSet r) {
		try {
			return new AccountPO(r.getString("AccountID"), r.getString("AccountName"), r.getDouble("AccountMoney"), r.getBoolean("AccountIsExist"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
