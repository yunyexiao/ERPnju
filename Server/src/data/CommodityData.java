package data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CommodityDataService;
import po.CommodityPO;

public class CommodityData extends UnicastRemoteObject implements CommodityDataService {
	private static final long serialVersionUID = -54758905450277134L;
	private String tableName="CommodityInfo";
	private String idName="ComID";

	public CommodityData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		return SQLQueryHelper.getNewId(tableName, idName, "%06d");
	}

	@Override
	public CommodityPO findById(String id) throws RemoteException {
		try{
			ResultSet r=SQLQueryHelper.getRecordByAttribute(tableName, idName, id);
			return getCommodityPO(r);		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public boolean add(CommodityPO c) throws RemoteException {
		return SQLQueryHelper.add(tableName, c.getId(), c.getName(), c.getCategoryId(), c.getType(), c.getStore(),
				c.getAmount(), c.getInPrice(), c.getSalePrice(), c.getRecentInPrice(), c.getRecentSalePrice(), c.getAlarmNum(), 1);
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		return SQLQueryHelper.getFalseDeleteResult(tableName, "ComIsExist",idName, id);
	}

	@Override
	public boolean update(CommodityPO commodity) throws RemoteException {
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE CommodityInfo SET "
					+ "ComName='"+commodity.getName()
					+"', ComCateID='"+commodity.getCategoryId()
					+"', ComType='"+commodity.getType()
					+"', ComStore='"+commodity.getStore()
					+"', ComQuantity='"+commodity.getAmount()
					+"', ComInPrice='"+commodity.getInPrice()
					+"', ComSalePrice='"+commodity.getSalePrice()
					+"', ComRecInPrice='"+commodity.getRecentInPrice()
					+"', ComRecSalePrice='"+commodity.getRecentSalePrice()
					+"', ComAlarmQuantity='"+commodity.getAlarmNum()
					+"' WHERE ComID ="
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
			ResultSet r = s1.executeQuery("SELECT * FROM CommodityInfo");
			while(r.next()) if(r.getBoolean("ComIsExist"))  cpos.add(getCommodityPO(r));
			return cpos;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<CommodityPO> getCommoditysBy(String field, String content, boolean isfuzzy) throws RemoteException {
		ArrayList<CommodityPO> cpos=new ArrayList<CommodityPO>();
		ResultSet r=null;
		try{
			if(isfuzzy){
				Statement s1 = DataHelper.getInstance().createStatement();
				r = s1.executeQuery("SELECT * FROM CommodityInfo WHERE "+field+" LIKE '%"+content+"%';");
			}
			else r = SQLQueryHelper.getRecordByAttribute(tableName, field, content);
			while(r.next()) if(r.getBoolean("ComIsExist"))  cpos.add(getCommodityPO(r));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return cpos;
	}

	private CommodityPO getCommodityPO(ResultSet r) {
		try {
			return new CommodityPO(r.getString("ComID"),
					r.getString("ComName"),
					r.getString("ComType"),
					r.getString("ComStore"),
					r.getString("ComCateID"),
					r.getLong("ComQuantity"),
					r.getInt("ComAlarmQuantity"),
					r.getDouble("ComInPrice"),
					r.getDouble("ComSalePrice"),
					r.getDouble("ComRecInPrice"),
			 		r.getDouble("ComRecSalePrice"),
			 		r.getBoolean("ComIsExist"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
