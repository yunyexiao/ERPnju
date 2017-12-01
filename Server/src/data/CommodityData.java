package data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CommodityDataService;
import po.CommodityPO;

public class CommodityData extends UnicastRemoteObject implements CommodityDataService {

	protected CommodityData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		
		int max=0,res=0;
		String newId;
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT ComID FROM CommodityInfo;");
			while(r.next()){
				int temp=0;
				temp=Integer.valueOf(r.getString("ComID"));
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
	public CommodityPO findById(String id) throws RemoteException {
		
		CommodityPO cpo=new CommodityPO();
		cpo.setId(id);
		String comName=null,comCateID=null,comCateName=null,comType=null,comStore=null;
		int comAlarmQuantity=0;
		long comQuantity=0;
		double comInPrice=0.0,comSalePrice=0.0,comRecInPrice=0.0,comRecSalePrice=0.0;
		try{
			 Statement s1 = DataHelper.getInstance().createStatement();
			 ResultSet r1= s1.executeQuery("SELECT ComName,ComCateID,ComType,ComStore,"
			 		+ "ComQuantity,ComInPrice, ComSalePrice,ComRecInPrice,ComRecSalePrice,ComAlarmQuantity,"
			 		+ " FROM CustomerInfo WHERE ComID=" + id +";");
			 while(r1.next()){
				 comName=r1.getString("ComName");
				 comCateID=r1.getString("ComCateID");
				 comType=r1.getString("ComType");
				 comStore=r1.getString("ComStore");
				 comQuantity=Integer.valueOf(r1.getString("ComQuantity"));
				 comInPrice=Double.valueOf(r1.getString("ComInPrice"));
				 comSalePrice=Double.valueOf(r1.getString("ComSalePrice"));
				 comRecInPrice=Double.valueOf(r1.getString("ComRecInPrice"));
				 comRecSalePrice=Double.valueOf(r1.getString("ComRecSalePrice"));
				 comAlarmQuantity=Integer.valueOf(r1.getString("ComAlarmQuantity"));
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
		
		String comID=null,comName=null,comCateID=null,comCateName=null,comType=null,comStore=null;
		long comAlarmQuantity=0,comQuantity=0;
		double comInPrice=0.0,comSalePrice=0.0,comRecInPrice=0.0,comRecSalePrice=0.0;
		comID=commodity.getId();
		comName=commodity.getName();
		comCateID=commodity.getCategoryId();
		comType=commodity.getType();
		comStore=commodity.getStore();
		comQuantity=commodity.getAmount();
		comAlarmQuantity=commodity.getAlarmNum();
		comInPrice=commodity.getInPrice();
		comSalePrice=commodity.getSalePrice();
		comRecInPrice=commodity.getRecentInPrice();
		comRecSalePrice=commodity.getRecentSalePrice();
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("INSERT INTO CommodityInfo VALUES"
					+ "('"+comID+"','"+comName+"','"+comCateID+"','"+comType+"','"
					+comStore+"','"+comQuantity+"','"+comInPrice+"','"+comSalePrice
					+"','"+comRecInPrice+"','"+comRecSalePrice+"','"+comAlarmQuantity
					+"','"+"')");
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
			int r=s.executeUpdate("DELETE FROM CommodityInfo WHERE ComID="+id+";");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
		
	}

	@Override
	public boolean update(CommodityPO commodity) throws RemoteException {
		
		String comID=null,comName=null,comCateID=null,comCateName=null,comType=null,comStore=null;
		long comAlarmQuantity=0,comQuantity=0;
		double comInPrice=0.0,comSalePrice=0.0,comRecInPrice=0.0,comRecSalePrice=0.0;
		comID=commodity.getId();
		comName=commodity.getName();
		comCateID=commodity.getCategoryId();
		comType=commodity.getType();
		comStore=commodity.getStore();
		comQuantity=commodity.getAmount();
		comAlarmQuantity=commodity.getAlarmNum();
		comInPrice=commodity.getInPrice();
		comSalePrice=commodity.getSalePrice();
		comRecInPrice=commodity.getRecentInPrice();
		comRecSalePrice=commodity.getRecentSalePrice();
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE CustomerInfo SET "
					+ "ComName="+comName+", ComCateID="+comCateID+", ComType="+comType+", ComStore="+comStore
					+", ComQuantity="+comQuantity+", ComInPrice="+comInPrice+", ComSalePrice="+comSalePrice
					+", ComRecInPrice="+comRecInPrice+", ComRecSalePrice="+comRecSalePrice+", ComAlarmQuantity="+comAlarmQuantity
					+"WHERE ComID="+comID+";");
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
				 String comID=null,comName=null,comCateID=null,comCateName=null,comType=null,comStore=null;
				 long comAlarmQuantity=0,comQuantity=0;
				 double comInPrice=0.0,comSalePrice=0.0,comRecInPrice=0.0,comRecSalePrice=0.0;
				 CommodityPO cpo = new CommodityPO();
				 comID=r1.getString("ComID");
				 comName=r1.getString("ComName");
				 comCateID=r1.getString("ComCateID");
				 comType=r1.getString("ComType");
				 comStore=r1.getString("ComStore");
				 comQuantity=Integer.valueOf(r1.getString("ComQuantity"));
				 comInPrice=Double.valueOf(r1.getString("ComInPrice"));
				 comSalePrice=Double.valueOf(r1.getString("ComSalePrice"));
				 comRecInPrice=Double.valueOf(r1.getString("ComRecInPrice"));
				 comRecSalePrice=Double.valueOf(r1.getString("ComRecSalePrice"));
				 comAlarmQuantity=Integer.valueOf(r1.getString("ComAlarmQuantity"));
				 cpo.setName(comName);
				 cpo.setCategoryId(comCateID);
				 cpo.setAlarmNum(comAlarmQuantity);
				 cpo.setAmount(comQuantity);
				 cpo.setInPrice(comInPrice);
				 cpo.setRecentInPrice(comRecInPrice);
				 cpo.setRecentSalePrice(comRecSalePrice);
				 cpo.setSalePrice(comSalePrice);
				 cpo.setType(comType);
				 cpo.setStore(comStore);
				 cpos.add(cpo);
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
	public ArrayList<CommodityPO> getCommoditysBy(String field, String content, boolean isfuzzy) throws RemoteException {
		
		return null;
	}

}
