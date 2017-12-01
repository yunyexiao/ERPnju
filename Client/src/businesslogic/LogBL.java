package businesslogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import blservice.LogBLService;
import blservice.infoservice.GetUserInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.LogDataService;
import po.LogInfoPO;
import presentation.component.MyTableModel;
import rmi.Rmi;

public class LogBL implements LogBLService, AddLogInterface {

	private LogDataService logData = Rmi.getRemote(LogDataService.class);
	private GetUserInterface getUserInfo = new UserBL();
	
	@Override
	public MyTableModel getLogInfo() {
		return listToTable(logData.getAllInfo());
	}

	@Override
	public MyTableModel searchByTime(String startTime, String endTime) {
		return listToTable(logData.getAllInfo(startTime, endTime));
	}

	/**
	 * ����־��¼�б�ת��Ϊ���
	 * @param list �����ݲ��ȡ����־��¼list
	 * @return ���ģ��
	 */
	private MyTableModel listToTable(ArrayList<LogInfoPO> list) {
		String[] attributes={"����ʱ��","����Ա","��������","����"};
		if (list == null) {
			String[][] info = {{"", "", "", ""}};
			return new MyTableModel(info, attributes);
		}
		
		String[][] info = new String[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			LogInfoPO logInfo= list.get(i);
			info[i][0] = logInfo.getTime();
			info[i][1] = getUserInfo.getUser(logInfo.getOperatorId()).getName();
			info[i][2] = logInfo.getOperation();
			info[i][3] = logInfo.getDetail();		
		}
		return new MyTableModel(info, attributes);
	}

	@Override
	public boolean add(String operatorId, String operation, String detail) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		String time = sdf.format(dt);
		return logData.add(new LogInfoPO(time, operatorId, operation, detail));
	}
}
