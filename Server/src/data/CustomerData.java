package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CustomerDataService;
import po.CustomerPO;

public class CustomerData extends UnicastRemoteObject implements CustomerDataService {
	private static final long serialVersionUID = -3647893693949919501L;
	private String tableName="CustomerInfo";
	private String idName="CusID";

	public CustomerData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		return SQLQueryHelper.getNewId(tableName, idName, "%06d");
	}

	@Override
	public CustomerPO findById(String id) throws RemoteException {
		try{
			ResultSet r=SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			r.next();
			return getCustomerPO(r);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean add(CustomerPO customer) throws RemoteException {
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r = s.executeUpdate("INSERT INTO CustomerInfo VALUES ('"
			        +customer.getId()+"','"
					+customer.getName()+"','"
			        +customer.getRank()+"','"
					+customer.getTelNumber()+"','"
					+customer.getAddress()+"','"
					+customer.getCode()+"','"
					+customer.getMail()+"','"
					+customer.getRecRange()+"','"
					+customer.getReceivable()+"','"
					+customer.getPayment()+"','"
					+customer.getSalesman()+"','"
					+customer.getType()+"','"
					+1+"')");
			if (r > 0) return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		return SQLQueryHelper.getFalseDeleteResult(tableName, "CusIsExist",idName, id);	
	}

	@Override
	public boolean update(CustomerPO customer) throws RemoteException {
	
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE CustomerInfo SET "
					+ "CusName='"+customer.getName()
					+"', CusRank='"+customer.getRank()
					+"', CusTel='"+customer.getTelNumber()
					+"', CusAddress='"+customer.getAddress()
					+"', CusCode='"+customer.getCode()
					+"', CusMail='"+customer.getMail()
					+"', CusReceiRange='"+customer.getRecRange()
					+"', CusReceivable='"+customer.getReceivable()
					+"', CusPayment='"+customer.getPayment()
					+"', CusSalesman='"+customer.getSalesman()
					+"', CusType='"+customer.getType()
					+"' WHERE CusID="+customer.getId()+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public ArrayList<CustomerPO> getAllCustomer() throws RemoteException {
		ArrayList<CustomerPO> cpos=new ArrayList<CustomerPO>();
		try {
		    Statement s = DataHelper.getInstance().createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM CustomerInfo");
			while(r.next()) if (r.getBoolean("CusIsExist")) cpos.add(getCustomerPO(r));
		}
		catch(Exception e) {
		   e.printStackTrace();
		   return null;
		}
		return cpos;
	}

	@Override
	public ArrayList<CustomerPO> getCustomersBy(String field, String content, boolean isfuzzy) throws RemoteException {
		ArrayList<CustomerPO> cpos=new ArrayList<CustomerPO>();
		ResultSet r=null;
		try{
			if(isfuzzy){
				Statement s = DataHelper.getInstance().createStatement();
				r = s.executeQuery("SELECT * FROM CustomerInfo WHERE "+field+" LIKE '%"+content+"%';"); 
			}
			else r = SQLQueryHelper.getRecordByAttribute(tableName, field, content);
			while(r.next()) if(r.getBoolean("CusIsExist")) cpos.add(getCustomerPO(r));	
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return cpos;
	}

	private CustomerPO getCustomerPO(ResultSet r) {
		try {
			return new CustomerPO(r.getString("CusID"),
					r.getString("CusName"),
					r.getString("CusTel"),
					r.getString("CusAddress"),
					r.getString("CusMail"),
					r.getString("CusCode"),
					r.getString("CusSalesman"),
					r.getInt("CusRank"),
					r.getInt("CusType"),
					r.getDouble("CusReceiRange"),
					r.getDouble("CusReceivable"),
					r.getDouble("CusPayment"),
					r.getBoolean("CusIsExist"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
