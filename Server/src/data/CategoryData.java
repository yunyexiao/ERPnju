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
		// TODO Auto-generated method stub
		int max=0,res=0;
		String newId;
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT CateID FROM CategoryInfo;");
			while(r.next()){
				int temp=0;
				temp=Integer.valueOf(r.getString("CateID"));
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
	public CategoryPO findById(String id) throws RemoteException {
		// TODO Auto-generated method stub
		CategoryPO cpo=new CategoryPO();
		cpo.setId(id);
		String cateName=null,cateFatherId=null,cateFatherName=null;
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			ResultSet r1 = s1.executeQuery("SELECT CateName FROM CategoryInfo "
			 		+ "WHERE CateID=" + id +";");
			 while(r1.next()){
				 cateName=r1.getString("CateName");
			 }
			 
			 Statement s2 = DataHelper.getInstance().createStatement();
			 ResultSet r2 = s2.executeQuery("SELECT CateFather, CateName FROM CategoryRelation,CategoryInfo "
				 		+ "WHERE CategoryRelation.CateFather=CategoryInfo.CateID "
				 		+ "AND CategoryRelation.CateID=" + id +";");
			 while(r2.next()){
				 cateFatherId=r2.getString("CateFather");
				 cateFatherName=r2.getString("CateName");
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
		// TODO Auto-generated method stub
		String cateId=null,cateName=null,cateFatherId=null,cateFatherName=null;
		cateId=category.getId();
		cateName=category.getName();
		cateFatherId=category.getFatherId();
		cateFatherName=category.getFatherName();
		
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1=s1.executeUpdate("INSERT INTO CategoryInfo VALUES"
					+ "('"+cateId+"','"+cateName+"','"+"')");
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("INSERT INTO CategoryRelation VALUES"
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
		// TODO Auto-generated method stub
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1=s1.executeUpdate("DELETE FROM CategoryRelation WHERE CateID="+id+";");
			
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("DELETE FROM CategoryInfo WHERE CateID="+id+";");
			if(r1>0&&r2>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public boolean update(CategoryPO category) throws RemoteException {
		// TODO Auto-generated method stub
		String cateId=null,cateName=null,cateFatherId=null,cateFatherName=null;
		cateId=category.getId();
		cateName=category.getName();
		cateFatherId=category.getFatherId();
		cateFatherName=category.getFatherName();
		try{
			Statement s1 = DataHelper.getInstance().createStatement();
			int r1=s1.executeUpdate("UPDATE CategoryRelation SET "
					+ "CateFather="+cateFatherId+"WHERE CateID="+cateId+";");
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("UPDATE CategoryInfo SET "
					+ "CateName="+cateName+"WHERE CateID="+cateId+";");
			if(r1>0&&r2>0)return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public ArrayList<CategoryPO> getAllCategory() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<CategoryPO> cpos=new ArrayList<CategoryPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM CategoryInfo");
			 while(r.next()){
				 CategoryPO cpo=new CategoryPO();
				 cpo.setId(r.getString("CateID"));
				 cpo.setName(r.getString("CateName"));
				 cpos.add(cpo);
			 }
			 
			 for(int i=0;i<cpos.size();i++){
				 Statement s2 = DataHelper.getInstance().createStatement();
				 ResultSet r2 = s2.executeQuery("SELECT CateFather, CateName FROM CategoryRelation,CategoryInfo "
					 		+ "WHERE CategoryRelation.CateFather=CategoryInfo.CateID "
					 		+ "AND CategoryRelation.CateID=" +cpos.get(i).getId() +";");
				 cpos.get(i).setFatherId(r2.getString("CateFather"));
				 cpos.get(i).setFatherName(r2.getString("CateName"));
			 }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return cpos;
	}

}
