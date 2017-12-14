package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CustomerDataService;
import po.CustomerPO;

public class CustomerData extends UnicastRemoteObject implements CustomerDataService {
	private String tableName="CustomerInfo";
	private String idName="CusID";

	public CustomerData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		
        String newId=SQLQueryHelper.getNewId(tableName, idName, "%06d");
		
		return newId;
	}

	@Override
	public CustomerPO findById(String id) throws RemoteException {
		
		CustomerPO cpo=new CustomerPO();
		cpo.setId(id);
		try{
			 //Statement s = DataHelper.getInstance().createStatement();
			 //ResultSet r = s.executeQuery("SELECT CusName,CusRank,CusTel,CusAddress,"
			 		//+ "CusCode,CusMail, CusReceiRange,CusReceivable,CusPayment,CusSalesman,"
			 		//+ "CusType FROM CustomerInfo WHERE CusID=" + id +";");
			ResultSet r=SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			 while(r.next()){
				 cpo.setId(r.getString("CusID"));
				 cpo.setName(r.getString("CusName"));
		         cpo.setAddress(r.getString("CusAddress"));
		         cpo.setMail(r.getString("CusMail"));
		         cpo.setCode(r.getString("CusCode"));
		         cpo.setPayment(r.getDouble("CusPayment"));
		         cpo.setRank(r.getInt("CusRank"));
		         cpo.setReceivable(r.getDouble("CusReceivable"));
		         cpo.setRecRange(r.getDouble("CusReceiRange"));
		         cpo.setSalesman(r.getString("CusSalesman"));
		         cpo.setTelNumber(r.getString("CusTel"));
		         cpo.setType(r.getInt("CusType"));
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return cpo;
	}

	@Override
	public boolean add(CustomerPO customer) throws RemoteException {
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("INSERT INTO CustomerInfo VALUES ('"
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
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		
        boolean res=SQLQueryHelper.getFalseDeleteResult(tableName, "CusIsExist",idName, id);
		
		return res;	
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
			while(r.next()) {
				
				boolean cusIsExist=r.getBoolean("CusIsExist");
				CustomerPO cpo = new CustomerPO();
				
				if(cusIsExist){
					 cpo.setId(r.getString("CusID"));
					 cpo.setName(r.getString("CusName"));
			         cpo.setAddress(r.getString("CusAddress"));
			         cpo.setMail(r.getString("CusMail"));
			         cpo.setCode(r.getString("CusCode"));
			         cpo.setPayment(r.getDouble("CusPayment"));
			         cpo.setRank(r.getInt("CusRank"));
			         cpo.setReceivable(r.getDouble("CusReceivable"));
			         cpo.setRecRange(r.getDouble("CusReceiRange"));
			         cpo.setSalesman(r.getString("CusSalesman"));
			         cpo.setTelNumber(r.getString("CusTel"));
			         cpo.setType(r.getInt("CusType"));
				 
				     cpos.add(cpo);	
				}
			}	
		 }
		 catch(Exception e) {
		   e.printStackTrace();
		   return null;
		 }
		return cpos;
	}

	@Override
	public ArrayList<CustomerPO> getUsersBy(String field, String content, boolean isfuzzy) throws RemoteException {
		 ArrayList<CustomerPO> cpos=new ArrayList<CustomerPO>();
		 
		 ResultSet r=null;
		 try{
			 if(isfuzzy){
				 Statement s = DataHelper.getInstance().createStatement();
				 r = s.executeQuery("SELECT * FROM CustomerInfo WHERE "+field+"LIKE '%"+content+"%';"); 
			 }
			 else if(!isfuzzy){
				 r=SQLQueryHelper.getRecordByAttribute(tableName, field, content);
			 }
			 
			 while(r.next()) {
					
					boolean cusIsExist=r.getBoolean("CusIsExist");
					CustomerPO cpo = new CustomerPO();
					
					if(cusIsExist){
						 cpo.setId(r.getString("CusID"));
						 cpo.setName(r.getString("CusName"));
				         cpo.setAddress(r.getString("CusAddress"));
				         cpo.setMail(r.getString("CusMail"));
				         cpo.setCode(r.getString("CusCode"));
				         cpo.setPayment(r.getDouble("CusPayment"));
				         cpo.setRank(r.getInt("CusRank"));
				         cpo.setReceivable(r.getDouble("CusReceivable"));
				         cpo.setRecRange(r.getDouble("CusReceiRange"));
				         cpo.setSalesman(r.getString("CusSalesman"));
				         cpo.setTelNumber(r.getString("CusTel"));
				         cpo.setType(r.getInt("CusType"));
					 
					 cpos.add(cpo);	
					}
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }

		return cpos;
	}

}
