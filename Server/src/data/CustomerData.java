package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CustomerDataService;
import po.CustomerPO;

public class CustomerData extends UnicastRemoteObject implements CustomerDataService {

	protected CustomerData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		
		int max=0,res=0;
		String newId;
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT CusID FROM CustomerInfo;");
			while(r.next()){
				int temp=0;
				temp=Integer.valueOf(r.getString("CusID"));
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
	public CustomerPO findById(String id) throws RemoteException {
		
		CustomerPO cpo=new CustomerPO();
		cpo.setId(id);
		String cusName=null,cusTel=null,cusAddress=null,cusCode=null,cusMail=null,cusSalesman=null;
		int cusRank=-1,cusType=-1;
		double cusReceiRange=0.0,cusReceivable=0.0,cusPayment=0.0;
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT CusName,CusRank,CusTel,CusAddress,"
			 		+ "CusCode,CusMail, CusReceiRange,CusReceivable,CusPayment,CusSalesman,"
			 		+ "CusType FROM CustomerInfo WHERE CusID=" + id +";");
			 while(r.next()){
				 cusName=r.getString("CusName");
				 cusRank=Integer.valueOf(r.getInt("CusRank"));
				 cusTel=r.getString("CusTel");
				 cusAddress=r.getString("CusAddress");
				 cusCode=r.getString("CusCode");
				 cusMail=r.getString("CusMail");
				 cusReceiRange=Double.valueOf(r.getInt("CusReceiRange"));
				 cusReceivable=Double.valueOf(r.getString("CusReceivable"));
				 cusPayment=Double.valueOf(r.getString("CusPayment"));
				 cusSalesman=r.getString("CusSalesman");
				 cusType=Integer.valueOf(r.getString("CusType"));	 
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		cpo.setName(cusName);
		cpo.setAddress(cusAddress);
		cpo.setMail(cusMail);
		cpo.setCode(cusCode);
		cpo.setPayment(cusPayment);
		cpo.setRank(cusRank);
		cpo.setReceivable(cusReceivable);
		cpo.setRecRange(cusReceiRange);
		cpo.setSalesman(cusSalesman);
		cpo.setTelNumber(cusTel);
		cpo.setType(cusType);
		
		
		return cpo;
	}

	@Override
	public boolean add(CustomerPO customer) throws RemoteException {
		
		String cusId=null,cusName=null,cusTel=null,cusAddress=null,cusCode=null,cusMail=null,cusSalesman=null;
		int cusRank=-1,cusType=-1;
		double cusReceiRange=0.0,cusReceivable=0.0,cusPayment=0.0;
		cusId=customer.getId();
		cusName=customer.getName();
		cusTel=customer.getTelNumber();
		cusAddress=customer.getAddress();
		cusCode=customer.getCode();
		cusMail=customer.getMail();
		cusSalesman=customer.getSalesman();
		cusRank=customer.getRank();
		cusType=customer.getType();
		cusReceiRange=customer.getRecRange();
		cusReceivable=customer.getReceivable();
		cusPayment=customer.getPayment();

		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("INSERT INTO CustomerInfo VALUES"
					+ "('"+cusId+"','"+cusName+"','"+cusRank+"','"+cusTel+"','"
					+cusAddress+"','"+cusCode+"','"+cusMail+"','"+cusReceiRange
					+"','"+cusReceivable+"','"+cusPayment+"','"+cusSalesman
					+"','"+cusType+"')");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("DELETE FROM CustomerInfo WHERE CusID="+id+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean update(CustomerPO customer) throws RemoteException {
		
		String cusId=null,cusName=null,cusTel=null,cusAddress=null,cusCode=null,cusMail=null,cusSalesman=null;
		int cusRank=-1,cusType=-1;
		double cusReceiRange=0.0,cusReceivable=0.0,cusPayment=0.0;
		cusId=customer.getId();
		cusName=customer.getName();
		cusTel=customer.getTelNumber();
		cusAddress=customer.getAddress();
		cusCode=customer.getCode();
		cusMail=customer.getMail();
		cusSalesman=customer.getSalesman();
		cusRank=customer.getRank();
		cusType=customer.getType();
		cusReceiRange=customer.getRecRange();
		cusReceivable=customer.getReceivable();
		cusPayment=customer.getPayment();
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE CustomerInfo SET "
					+ "CusName="+cusName+", CusRank="+cusRank+", CusTel="+cusTel+", CusAddress="+cusAddress
					+", CusCode="+cusCode+", CusMail="+cusMail+", CusReceiRange="+cusReceiRange
					+", CusReceivable="+cusReceivable+", CusPayment="+cusPayment+", CusSalesman="+cusSalesman
					+", CusType="+cusType+"WHERE CusID="+cusId+";");
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
				String cusId=null,cusName=null,cusTel=null,cusAddress=null,cusCode=null,cusMail=null,cusSalesman=null;
				int cusRank=-1,cusType=-1;
				double cusReceiRange=0.0,cusReceivable=0.0,cusPayment=0.0;
				CustomerPO cpo = new CustomerPO();
				cusId=r.getString("CusID");
				 cusName=r.getString("CusName");
				 cusRank=Integer.valueOf(r.getInt("CusRank"));
				 cusTel=r.getString("CusTel");
				 cusAddress=r.getString("CusAddress");
				 cusCode=r.getString("CusCode");
				 cusMail=r.getString("CusMail");
				 cusReceiRange=Double.valueOf(r.getInt("CusReceiRange"));
				 cusReceivable=Double.valueOf(r.getString("CusReceivable"));
				 cusPayment=Double.valueOf(r.getString("CusPayment"));
				 cusSalesman=r.getString("CusSalesman");
				 cusType=Integer.valueOf(r.getString("CusType"));	
				
				
				 cpo.setName(cusName);
				 cpo.setAddress(cusAddress);
				 cpo.setMail(cusMail);
				 cpo.setCode(cusCode);
				 cpo.setPayment(cusPayment);
				 cpo.setRank(cusRank);
				 cpo.setReceivable(cusReceivable);
				 cpo.setRecRange(cusReceiRange);
				 cpo.setSalesman(cusSalesman);
				 cpo.setTelNumber(cusTel);
				 cpo.setType(cusType);
				 cpos.add(cpo);	
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
		
		return null;
	}

}
