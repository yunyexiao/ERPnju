package data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dataservice.LogDataService;
import po.LogInfoPO;

public class LogData extends UnicastRemoteObject implements LogDataService{
	private static final long serialVersionUID = -6553376919948699787L;
	private String tableName="LogInfo";
	private String idName="LIID";
	
	public LogData() throws RemoteException {
		super();
	}
	
	@Override
	public boolean add(LogInfoPO log) throws RemoteException{
		String newId = SQLQueryHelper.getNewId(tableName, idName, "%08d");
		return SQLQueryHelper.add(tableName, newId, log.getTime(), log.getOperatorId(), log.getOperation(), log.getDetail());
	}

	@Override
	public ArrayList<LogInfoPO> getAllInfo() throws RemoteException{
		ArrayList<LogInfoPO> lips=new ArrayList<LogInfoPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM LogInfo;");
			 while(r.next()) lips.add(getLogInfo(r));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return lips;
	}

	/**
	 * �����ݿ���ʱ��洢Ϊdatetime���ͣ�������ʽΪxxxx-xx-xx xx:xx:xx:xxx
	 * Ҫ��������sql�е������ַ�
	 */
	@Override
	public ArrayList<LogInfoPO> getAllInfo(String startTime, String EndTime) throws RemoteException{
		ArrayList<LogInfoPO> lips=new ArrayList<LogInfoPO>();
		try{
			 Statement s = DataHelper.getInstance().createStatement();
			 ResultSet r = s.executeQuery("SELECT * FROM LogInfo WHERE LITime>'"+startTime+"' AND LITime<DATEADD(DAY,1,"+"'"+EndTime+"');");
			 while(r.next()) lips.add(getLogInfo(r));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return lips;
	}

	private LogInfoPO getLogInfo(ResultSet r) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return new LogInfoPO(df.format(r.getTimestamp("LITime")),r.getString("LIOperatorID"),r.getString("LIOperation"),r.getString("LIDetail"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
