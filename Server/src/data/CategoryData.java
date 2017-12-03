package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.CategoryDataService;
import po.CategoryPO;
public class CategoryData extends UnicastRemoteObject implements CategoryDataService{

	protected CategoryData() throws RemoteException {
		super();
	}

	@Override
	public String getNewId() throws RemoteException {
		
		int max = 0,res = 0;
		String newId;
		try{
			Statement s = DataHelper.getInstance().createStatement();
			ResultSet r = s.executeQuery("SELECT CateID FROM CategoryInfo;");
			while(r.next()){
				int temp = 0;
				//temp = Integer.valueOf(r.getString("CateID"));
				temp=r.getInt("CateID");
				if(temp>max)max = temp;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		res = max+1;
		newId = String.format("%06d", res);
		return newId;
	}

	@Override
	public CategoryPO findById(String id) throws RemoteException {
		
		CategoryPO cpo = new CategoryPO();
		cpo.setId(id);
		String cateName = null,cateFatherId = null,cateFatherName = null;
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			ResultSet r1 = s1.executeQuery("SELECT CateName FROM CategoryInfo "
			 		+ "WHERE CateID = " + id +";");
			 while(r1.next()){
				 cateName = r1.getString("CateName");
			 }
			 
			 Statement s2 = DataHelper.getInstance().createStatement();
			 ResultSet r2 = s2.executeQuery("SELECT CateFather, CateName FROM CategoryRelation,CategoryInfo "
				 		+ "WHERE CategoryRelation.CateFather = CategoryInfo.CateID "
				 		+ "AND CategoryRelation.CateID = " + id +";");
			 while(r2.next()){
				 cateFatherId = r2.getString("CateFather");
				 cateFatherName = r2.getString("CateName");
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		cpo.setName(cateName);
		cpo.setFatherId(cateFatherId);
		cpo.setFatherName(cateFatherName);
		return cpo;
	}

	@Override
	public boolean add(CategoryPO category) throws RemoteException {
		
		String cateId = null,cateName = null,cateFatherId = null,cateFatherName = null;
		cateId = category.getId();
		cateName = category.getName();
		cateFatherId = category.getFatherId();
		cateFatherName = category.getFatherName();
		
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1 = s1.executeUpdate("INSERT INTO CategoryInfo VALUES"
					+ "('"+cateId+"','"+cateName+"','"+1+"')");
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2 = s2.executeUpdate("INSERT INTO CategoryRelation VALUES"
					+ "('"+cateId+"','"+cateFatherId+"','"+"')");
			
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
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1 = s1.executeUpdate("UPDATE CategoryRelation SET CateIsExist=0 WHERE CateID="+id+";");
			
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2 = s2.executeUpdate("UPDATE CategoryRelation SET CateIsExist=0 WHERE CateFather="+id+";");
			
			Statement s3 = DataHelper.getInstance().createStatement();
			int r3 = s3.executeUpdate("UPDATE CategoryInfo SET CateIsExist=0 WHERE CateID="+id+";");
			if(r1>0&&(r2>0||r3>0))return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean update(CategoryPO category) throws RemoteException {
		
		String cateId = null,cateName = null,cateFatherId = null,cateFatherName = null;
		cateId = category.getId();
		cateName = category.getName();
		cateFatherId = category.getFatherId();
		cateFatherName = category.getFatherName();
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1 = s1.executeUpdate("UPDATE CategoryRelation SET "
					+ "CateFather = "+cateFatherId+"WHERE CateID = "+cateId+";");
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2 = s2.executeUpdate("UPDATE CategoryInfo SET "
					+ "CateName = "+cateName+"WHERE CateID = "+cateId+";");
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
				 ResultSet r2 = s2.executeQuery("SELECT CateFather, CateName FROM CategoryRelation,CategoryInfo "
					 		+ "WHERE CategoryRelation.CateFather = CategoryInfo.CateID "
					 		+ "AND CategoryRelation.CateID = " +cpos.get(i).getId() +";");
				 cpos.get(i).setFatherId(r2.getString("CateFather"));
				 cpos.get(i).setFatherName(r2.getString("CateName"));
			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return cpos;
	}

	@Override
	public ArrayList<CategoryPO> getUsersBy(String field, String content, boolean isfuzzy) throws RemoteException {
		ArrayList<CategoryPO> cpos = new ArrayList<CategoryPO>();
		
		if(isfuzzy){
			try{
				 Statement s = DataHelper.getInstance().createStatement();
				 ResultSet r = s.executeQuery("SELECT * FROM CategoryInfo WHERE "+field+"LIKE '%"+content+"%';");
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
					 ResultSet r2 = s2.executeQuery("SELECT CateFather, CateName FROM CategoryRelation,CategoryInfo "
						 		+ "WHERE CategoryRelation.CateFather = CategoryInfo.CateID "
						 		+ "AND CategoryRelation.CateID = " +cpos.get(i).getId() +";");
					 cpos.get(i).setFatherId(r2.getString("CateFather"));
					 cpos.get(i).setFatherName(r2.getString("CateName"));
				 }
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(!isfuzzy){
			try{
				 Statement s = DataHelper.getInstance().createStatement();
				 ResultSet r = s.executeQuery("SELECT * FROM CategoryInfo WHERE "+field+"LIKE '"+content+"';");
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
					 ResultSet r2 = s2.executeQuery("SELECT CateFather, CateName FROM CategoryRelation,CategoryInfo "
						 		+ "WHERE CategoryRelation.CateFather = CategoryInfo.CateID "
						 		+ "AND CategoryRelation.CateID = " +cpos.get(i).getId() +";");
					 cpos.get(i).setFatherId(r2.getString("CateFather"));
					 cpos.get(i).setFatherName(r2.getString("CateName"));
				 }
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return cpos;
	}

}
