package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CategoryDataService;
import po.CategoryPO;
public class CategoryData extends UnicastRemoteObject implements CategoryDataService{
	private String tableName="CategoryInfo";
	private String idName="CateID";

	protected CategoryData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		
        String newId=SQLQueryHelper.getNewId(tableName, idName, "%06d");
		
		return newId;
	}

	@Override
	public CategoryPO findById(String id) throws RemoteException {
		
		CategoryPO cpo = new CategoryPO();
		cpo.setId(id);
		String cateName = null,cateFatherId = null;
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			ResultSet r1 = s1.executeQuery("SELECT CateName FROM CategoryInfo "
			 		+ "WHERE CateID = " + id +";");
			 while(r1.next()){
				 cateName = r1.getString("CateName");
			 }
			 
			 Statement s2 = DataHelper.getInstance().createStatement();
			 ResultSet r2 = s2.executeQuery("SELECT CateFather FROM CategoryRelation WHERE CateID = " + id +";");
			 while(r2.next()){
				 cateFatherId = r2.getString("CateFather");
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		cpo.setName(cateName);
		cpo.setFatherId(cateFatherId);
	
		return cpo;
	}

	@Override
	public boolean add(CategoryPO category) throws RemoteException {
				
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1 = s1.executeUpdate("INSERT INTO CategoryInfo VALUES('"
			        +category.getId()+"','"
					+category.getName()+"','"
			        +1+"')");
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2 = s2.executeUpdate("INSERT INTO CategoryRelation VALUES('"
			        +category.getId()+"','"
					+category.getFatherId()+"','"
			        +1+"')");
			
			if(r1>0&&r2>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean delete(String id) throws RemoteException {
		
		try{
			
			boolean res1=SQLQueryHelper.getFalseDeleteResult("CategoryRelation", "CateIsExist",idName, id);
			boolean res2=SQLQueryHelper.getFalseDeleteResult("CategoryRelation", "CateIsExist","CateFather", id);
			boolean res3=SQLQueryHelper.getFalseDeleteResult(tableName, "CateIsExist",idName, id);
			
			if(res3&&(res1||res2))return true;
			
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean update(CategoryPO category) throws RemoteException {
		
		
		
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1 = s1.executeUpdate("UPDATE CategoryRelation SET "
					+ "CateFather = "+category.getFatherId()
					+"WHERE CateID = "+category.getId()+";");
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2 = s2.executeUpdate("UPDATE CategoryInfo SET "
					+ "CateName = "+category.getName()
					+"WHERE CateID = "+category.getId()+";");
			if(r1>0&&r2>0)return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public ArrayList<CategoryPO> getAllCategory() throws RemoteException {
		
		ArrayList<CategoryPO> cpos = new ArrayList<CategoryPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM CategoryInfo");
			 while(r.next()){
				 CategoryPO cpo = new CategoryPO();
				 boolean cateIsExist=false;
				 cateIsExist=r.getBoolean("CateIsExist");
				 if(cateIsExist){
				 cpo.setId(r.getString("CateID"));
				 cpo.setName(r.getString("CateName"));
				 
				 cpos.add(cpo);
				 }
			 }
			 
			 for(int i = 0;i<cpos.size();i++){
				 Statement s2 = DataHelper.getInstance().createStatement();
				 ResultSet r2 = s2.executeQuery("SELECT CateFather FROM CategoryRelation WHERE CateID= " +cpos.get(i).getId() +";");
				 cpos.get(i).setFatherId(r2.getString("CateFather"));
				
			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return cpos;
	}

	@Override
	public ArrayList<CategoryPO> getUsersBy(String field, String content, boolean isfuzzy) throws RemoteException {
		ArrayList<CategoryPO> cpos = new ArrayList<CategoryPO>();
		
		ResultSet r=null;
		
		try{
			if(isfuzzy){
				Statement s = DataHelper.getInstance().createStatement();
				r = s.executeQuery("SELECT * FROM CategoryInfo WHERE "+field+"LIKE '%"+content+"%';");
			}
			else if(!isfuzzy){
				r =SQLQueryHelper.getRecordByAttribute(tableName, field, content);
			}
			
			 while(r.next()){
				 CategoryPO cpo = new CategoryPO();
				 boolean cateIsExist=false;
				 cateIsExist=r.getBoolean("CateIsExist");
				 if(cateIsExist){
				 cpo.setId(r.getString("CateID"));
				 cpo.setName(r.getString("CateName"));
				 
				 cpos.add(cpo);
				 }
			 }
			 
			 for(int i = 0;i<cpos.size();i++){
				 Statement s2 = DataHelper.getInstance().createStatement();
				 ResultSet r2 = s2.executeQuery("SELECT CateFather FROM CategoryRelation WHERE CateID= " +cpos.get(i).getId() +";");
				 cpos.get(i).setFatherId(r2.getString("CateFather"));
				 
			 }
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
				
		return cpos;
	}

}
