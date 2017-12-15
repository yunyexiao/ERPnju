package data;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class SQLQueryHelper {
	
	//��ȡ������id,���ӱ�ʶ
	public static String getNewBillIdByDay(String tableName,String idName){
		String newId=null;
		int num=0;
		
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR), month=now.get(Calendar.MONTH),  day=now.get(Calendar.DAY_OF_MONTH);
		String date=year+"-"+month+"-"+day;
		
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT "+idName+" FROM "+tableName+" WHERE generateTime>"
					+"'"+date+"' "+"AND generateTime<DATEADD(DAY,1,"+"'"+date+"');");
			while(r.next()){
				num++;
			}
			num++;
			
			newId=year+month+day+"-"+String.format("%5d", num);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return newId;	
	}
	
	//��˳������ͨid
	public static String getNewId(String tableName, String attributeName, String format){
		String newId=null;
		int max=0,res=0;
		try{
			Statement s=DataHelper.getInstance().createStatement();
			ResultSet r=s.executeQuery("SELECT "+attributeName+" FROM "+tableName+";");
			while(r.next())
			{
				int temp=0;
				temp=r.getInt(attributeName);
				//temp=Integer.valueOf(r.getString("SUID"));
				if(temp>max)max=temp;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		res=max+1;
		newId=String.format(format, res);
		
		return newId;
		
	}
	
	public static boolean getTrueDeleteResult(String tableName, String attributeName, String value){
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("DELETE FROM "+tableName+" WHERE "+attributeName+"='"+value+"';");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;		
	}
	
	public static boolean getFalseDeleteResult(String tableName, String flagName, String attributeName, String value){
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			int r=s.executeUpdate("UPDATE "+tableName+" SET "+flagName+"=0 WHERE "+attributeName+"='"+value+"';");
			if(r>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
		
	}
	
	public static ResultSet getRecordByAttribute(String tableName, String attributeName, String value){
		
		try{
			Statement s = DataHelper.getInstance().createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM "+tableName+" WHERE "+attributeName+"=" + value +";");	 
			return r;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
    }
	
}
