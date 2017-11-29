package data;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.LogDataService;
import po.LogInfoPO;

public class LogData implements LogDataService{

	@Override
	public boolean add(LogInfoPO logInfo) {
		int max=0,res=0;
		String newId=null,logOperatorId=null,logOperation=null,logTime=null,logDetail=null;
		try{
			Statement s1=DataHelper.getInstance().createStatement();
			ResultSet r1=s1.executeQuery("SELECT LIID FROM LogInfo;");
			while(r1.next()){
				int temp=0;
				temp=Integer.valueOf(r1.getString("AccountID"));
				if(temp>max)max=temp;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		res=max+1;
		newId=String.format("%08d", res);//可能要换成long
		
		logOperatorId=logInfo.getOperatorId();
		logOperation=logInfo.getOperation();
		logTime=logInfo.getTime();
		logDetail=logInfo.getDetail();
		
		try{
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("INSERT INTO LogInfo VALUES"
					+ "('"+newId+"','"+logTime+"','"+logOperatorId+"','"+logOperation+"','"+logDetail+"')");
			if(r2>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public ArrayList<LogInfoPO> getAllInfo() {
		ArrayList<LogInfoPO> lips=new ArrayList<LogInfoPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM LogInfo");
			 while(r.next()){
				 String logId=null,logOperatorId=null,logOperation=null,logTime=null,logDetail=null;
				 
				 logId=r.getString("LIID");
				 logTime=r.getString("LITime");
				 logOperatorId=r.getString("LIOperatorID");
				 logOperation=r.getString("LIOperation");
				 logDetail=r.getString("LIDetail");
				 
				 LogInfoPO lip=new LogInfoPO(logTime,logOperatorId,logOperation,logDetail);
				 lips.add(lip);
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return lips;
	}

	@Override
	public ArrayList<LogInfoPO> getAllInfo(String startTime, String EndTime) {
		//在数据库中时间存储为datetime类型，参数格式为xxxx-xx-xx xx:xx:xx:xxx
		//要过滤特殊sql中的特殊字符
		ArrayList<LogInfoPO> lips=new ArrayList<LogInfoPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM LogInfo WHERE LITime BETWEEN "
			 +"'"+startTime+"'"+"AND"+"'"+EndTime+"';");
			 while(r.next()){
				 String logId=null,logOperatorId=null,logOperation=null,logTime=null,logDetail=null;
				 
				 logId=r.getString("LIID");
				 logTime=r.getString("LITime");
				 logOperatorId=r.getString("LIOperatorID");
				 logOperation=r.getString("LIOperation");
				 logDetail=r.getString("LIDetail");
				 
				 LogInfoPO lip=new LogInfoPO(logTime,logOperatorId,logOperation,logDetail);
				 lips.add(lip);
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return lips;
	}

}
