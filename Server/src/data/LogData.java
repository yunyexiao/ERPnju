package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dataservice.LogDataService;
import po.LogInfoPO;

public class LogData extends UnicastRemoteObject implements LogDataService{
	public LogData() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private String tableName="LogInfo";
	private String idName="LIID";

	@Override
	public boolean add(LogInfoPO logInfo) throws RemoteException{
		
		String newId=SQLQueryHelper.getNewId(tableName, idName, "%08d");
			
		try{
			Statement s2 = DataHelper.getInstance().createStatement();
			int r2=s2.executeUpdate("INSERT INTO LogInfo VALUES('"
			        +newId+"','"
					+logInfo.getTime()+"','"
			        +logInfo.getOperatorId()+"','"
					+logInfo.getOperation()+"','"
			        +logInfo.getDetail()+"');");
			if(r2>0)return true;
		}catch(Exception e){
			  e.printStackTrace();
			   return false;
		}
		return false;
	}

	@Override
	public ArrayList<LogInfoPO> getAllInfo() throws RemoteException{
		ArrayList<LogInfoPO> lips=new ArrayList<LogInfoPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM LogInfo;");
			 while(r.next()){
				  
				 LogInfoPO lip=new LogInfoPO(r.getString("LITime"),
						 r.getString("LIOperatorID"),
						 r.getString("LIOperation"),
						 r.getString("LIDetail"));
				 lips.add(lip);
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return lips;
	}

	@Override
	public ArrayList<LogInfoPO> getAllInfo(String startTime, String EndTime) throws RemoteException{
		//在数据库中时间存储为datetime类型，参数格式为xxxx-xx-xx xx:xx:xx:xxx
		//要过滤特殊sql中的特殊字符
		ArrayList<LogInfoPO> lips=new ArrayList<LogInfoPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 //ResultSet r = s.executeQuery("SELECT * FROM LogInfo WHERE LITime BETWEEN "
			// +"'"+startTime+"'"+"AND"+"'"+EndTime+"';");
			 ResultSet r = s.executeQuery("SELECT * FROM LogInfo WHERE LITime>'"+startTime+"' AND LITime<DATEADD(DAY,1,"+"'"+EndTime+"');");
			 while(r.next()){
		
				 LogInfoPO lip=new LogInfoPO(r.getString("LITime"),
						 r.getString("LIOperatorID"),
						 r.getString("LIOperation"),
						 r.getString("LIDetail"));
				 lips.add(lip);
			 }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return lips;
	}

}
