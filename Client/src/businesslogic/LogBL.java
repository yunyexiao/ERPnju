package businesslogic;

import java.util.ArrayList;

import blservice.LogBLService;
import businesslogic.inter.AddLogInterface;
import dataservice.LogDataService;
import po.LogInfoPO;
import presentation.component.MyTableModel;
import rmi.Rmi;

public class LogBL implements LogBLService, AddLogInterface {

	private LogDataService logData = Rmi.getRemote(LogDataService.class);
	
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
			//TODO ����Id��ȡ��ʵ�û���
			info[i][1] = logInfo.getOperatorId();
			info[i][2] = logInfo.getOperation();
			info[i][3] = logInfo.getDetail();		
		}
		return new MyTableModel(info, attributes);
	}

	@Override
	public boolean add(String time, String operatorId, String operation, String detail) {
		return logData.add(new LogInfoPO(time, operatorId, operation, detail));
	}
}
