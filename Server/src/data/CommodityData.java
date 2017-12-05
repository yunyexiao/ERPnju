package data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CommodityDataService;
import po.CommodityPO;

public class CommodityData extends UnicastRemoteObject implements CommodityDataService {
	
	private String tableName="CommodityInfo";
	private String idName="ComID";

	protected CommodityData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {

		String newId=SQLQueryHelper.getNewId(tableName, idName, "%06d");
		
		return newId;
		
	}

	@Override
	public CommodityPO findById(String id) throws RemoteException {
		
		CommodityPO cpo=new CommodityPO();
		cpo.setId(id);
		String comName=null,comCateID=null,comCateName=null,comType=null,comStore=null;
		int comAlarmQuantity=0;
		long comQuantity=0;
		double comInPrice=0.0,comSalePrice=0.0,comRecInPrice=0.0,comRecSalePrice=0.0;
		try{
			 //Statement s1 = DataHelper.getInstance().createStatement();
			 //ResultSet r1= s1.executeQuery("SELECT * FROM CustomerInfo WHERE ComID=" + id +";");
			ResultSet r1=SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			 while(r1.next()){
				 comName=r1.getString("ComName");
				 comCateID=r1.getString("ComCateID");
				 comType=r1.getString("ComType");
				 comStore=r1.getString("ComStore");
				 comQuantity=r1.getLong("ComQuantity");
				 comInPrice=r1.getInt("ComInPrice");
				 comSalePrice=r1.getDouble("ComSalePrice");
				 comRecInPrice=r1.getDouble("ComRecInPrice");
				 comRecSalePrice=r1.getDouble("ComRecSalePrice");
				 comAlarmQuantity=r1.getInt("ComAlarmQuantity");
			 }
			 
			 Statement s2 = DataHelper.getInstance().createStatement();
			 ResultSet r2=s2.executeQuery("SELECT CateName FROM CategoryInfo,CommodityInfo "
			 		+ "WHERE CategoryInfo.CateID=CommodityInfo.ComCateID AND CommodityInfo.ComID="+id+";");
			 while(r2.next()){
				 comCateName=r2.getString("CateName");
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		cpo.setName(comName);
		cpo.setCategoryId(comCateID);
		cpo.setCategoryName(comCateName);
		cpo.setAlarmNum(comAlarmQuantity);
		cpo.setAmount(comQuantity);
		cpo.setInPrice(comInPrice);
		cpo.setRecentInPrice(comRecInPrice);
		cpo.setRecentSalePrice(comRecSalePrice);
		cpo.setSalePrice(comSalePrice);
		cpo.setType(comType);
		cpo.setStore(comStore);
		
		return cpo;
	}

	@Override
	public boolean add(CommodityPO commodity) throws RemoteException {
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("INSERT INTO CommodityInfo VALUES('"
			        +commodity.getId()+"','"
					+commodity.getName()+"','"
			        +commodity.getCategoryId()+"','"
					+commodity.getType()+"','"
					+commodity.getStore()+"','"
					+commodity.getAmount()+"','"
					+commodity.getInPrice()+"','"
					+commodity.getSalePrice()+"','"
					+commodity.getRecentInPrice()+"','"
					+commodity.getRecentSalePrice()+"','"
					+commodity.getAlarmNum()+"','"
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
		
		boolean res=SQLQueryHelper.getFalseDeleteResult(tableName, "ComIsExist",idName, id);
		
		return res;
		
	}

	@Override
	public boolean update(CommodityPO commodity) throws RemoteException {
	
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE CustomerInfo SET "
					+ "ComName="+commodity.getName()
					+", ComCateID="+commodity.getCategoryId()
					+", ComType="+commodity.getType()
					+", ComStore="+commodity.getStore()
					+", ComQuantity="+commodity.getAmount()
					+", ComInPrice="+commodity.getInPrice()
					+", ComSalePrice="+commodity.getSalePrice()
					+", ComRecInPrice="+commodity.getRecentInPrice()
					+", ComRecSalePrice="+commodity.getRecentSalePrice()
					+", ComAlarmQuantity="+commodity.getAlarmNum()
					+"WHERE ComID="
					+commodity.getId()+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public ArrayList<CommodityPO> getAllCommodity() throws RemoteException {
		
		ArrayList<CommodityPO> cpos=new ArrayList<CommodityPO>();
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			ResultSet r1 = s1.executeQuery("SELECT * FROM CommodityInfo");
			while(r1.next()){
				 
				 boolean comIsExist=false;
				 CommodityPO cpo = new CommodityPO();
				 comIsExist=r1.getBoolean("ComIsExist");
				 
				 if(comIsExist){
					 cpo.setId(r1.getString("ComID"));
				     cpo.setName(r1.getString("ComName"));
				     cpo.setCategoryId(r1.getString("ComCateID"));
				     cpo.setAlarmNum(r1.getInt("ComAlarmQuantity"));
				     cpo.setAmount(r1.getLong("ComQuantity"));
				     cpo.setInPrice(r1.getInt("ComInPrice"));
				     cpo.setRecentInPrice(r1.getDouble("ComRecInPrice"));
				     cpo.setRecentSalePrice(r1.getDouble("ComRecSalePrice"));
				     cpo.setSalePrice(r1.getDouble("ComSalePrice"));
				     cpo.setType(r1.getString("ComType"));
				     cpo.setStore(r1.getString("ComStore"));
				 
				     cpos.add(cpo);
				 }
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		for(int i=0;i<cpos.size();i++){
			try{
				 Statement s2 = DataHelper.getInstance().createStatement();
				 ResultSet r2=s2.executeQuery("SELECT CateName FROM CategoryInfo,CommodityInfo "
				 		+ "WHERE CategoryInfo.CateID=CommodityInfo.ComCateID AND CommodityInfo.ComID="+cpos.get(i).getId()+";");
				 while(r2.next()){
					 cpos.get(i).setCategoryName(r2.getString("CateName"));
				 }
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return cpos;
	}

	@Override
	public ArrayList<CommodityPO> getUsersBy(String field, String content, boolean isfuzzy) throws RemoteException {
		
		ArrayList<CommodityPO> cpos=new ArrayList<CommodityPO>();
		
		if(isfuzzy){
			
			try{
				Statement s1 = DataHelper.getInstance().createStatement();
				ResultSet r1 = s1.executeQuery("SELECT * FROM CommodityInfo WHERE "+field+"LIKE '%"+content+"%';");
				while(r1.next()){
		
					 boolean comIsExist=r1.getBoolean("ComIsExist");
					 CommodityPO cpo = new CommodityPO();
					 
					 if(comIsExist){
						 cpo.setId(r1.getString("ComID"));
					     cpo.setName(r1.getString("ComName"));
					     cpo.setCategoryId(r1.getString("ComCateID"));
					     cpo.setAlarmNum(r1.getInt("ComAlarmQuantity"));
					     cpo.setAmount(r1.getLong("ComQuantity"));
					     cpo.setInPrice(r1.getInt("ComInPrice"));
					     cpo.setRecentInPrice(r1.getDouble("ComRecInPrice"));
					     cpo.setRecentSalePrice(r1.getDouble("ComRecSalePrice"));
					     cpo.setSalePrice(r1.getDouble("ComSalePrice"));
					     cpo.setType(r1.getString("ComType"));
					     cpo.setStore(r1.getString("ComStore"));
					 
					     cpos.add(cpo);
					 }
				}
				
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			
			for(int i=0;i<cpos.size();i++){
				try{
					 Statement s2 = DataHelper.getInstance().createStatement();
					 ResultSet r2=s2.executeQuery("SELECT CateName FROM CategoryInfo,CommodityInfo "
					 		+ "WHERE CategoryInfo.CateID=CommodityInfo.ComCateID AND CommodityInfo.ComID="+cpos.get(i).getId()+";");
					 while(r2.next()){
						 cpos.get(i).setCategoryName(r2.getString("CateName"));
					 }
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
		}
		else if(!isfuzzy){
			try{
				//Statement s1 = DataHelper.getInstance().createStatement();
				ResultSet r1=SQLQueryHelper.getRecordByAttribute(tableName, field, content);
				//r1 = s1.executeQuery("SELECT * FROM CommodityInfo WHERE "+field+"LIKE '"+content+"';");
				while(r1.next()){
					 
					 boolean comIsExist=r1.getBoolean("ComIsExist");
					 CommodityPO cpo = new CommodityPO();
					 
					 if(comIsExist){
						 cpo.setId(r1.getString("ComID"));
					     cpo.setName(r1.getString("ComName"));
					     cpo.setCategoryId(r1.getString("ComCateID"));
					     cpo.setAlarmNum(r1.getInt("ComAlarmQuantity"));
					     cpo.setAmount(r1.getLong("ComQuantity"));
					     cpo.setInPrice(r1.getInt("ComInPrice"));
					     cpo.setRecentInPrice(r1.getDouble("ComRecInPrice"));
					     cpo.setRecentSalePrice(r1.getDouble("ComRecSalePrice"));
					     cpo.setSalePrice(r1.getDouble("ComSalePrice"));
					     cpo.setType(r1.getString("ComType"));
					     cpo.setStore(r1.getString("ComStore"));
					 
					     cpos.add(cpo);
					 }
				}
				
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			
			for(int i=0;i<cpos.size();i++){
				try{
					 Statement s2 = DataHelper.getInstance().createStatement();
					 ResultSet r2=s2.executeQuery("SELECT CateName FROM CategoryInfo,CommodityInfo "
					 		+ "WHERE CategoryInfo.CateID=CommodityInfo.ComCateID AND CommodityInfo.ComID="+cpos.get(i).getId()+";");
					 while(r2.next()){
						 cpos.get(i).setCategoryName(r2.getString("CateName"));
					 }
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
			
		}
		return cpos;
	}

}
