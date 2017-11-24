package businesslogic;

import java.util.ArrayList;

import blservice.LogBLService;
import dataservice.LogDataService;
import po.LogInfoPO;
import presentation.component.MyTableModel;
import rmi.RemoteHelper;

public class LogBL implements LogBLService {

	private LogDataService logData = RemoteHelper.getInstance().getLogDataService();
	
	@Override
	public MyTableModel getLogInfo() {
		return listToTable(logData.getAllInfo());
	}

	@Override
	public MyTableModel searchByTime(String startTime, String endTime) {
		return listToTable(logData.getAllInfo(startTime, endTime));
	}

	/**
	 * 将日志记录列表转换为表格
	 * @param list 从数据层获取的日志记录list
	 * @return 表格模型
	 */
	private MyTableModel listToTable(ArrayList<LogInfoPO> list) {
		String[] attributes={"操作时间","操作员","操作类型","详情"};
		if (list == null) {
			String[][] info = {{"", "", "", ""}};
			return new MyTableModel(info, attributes);
		}
		
		String[][] info = new String[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			LogInfoPO logInfo= list.get(i);
			info[i][0] = logInfo.getTime();
			//TODO 根据Id获取真实用户名
			info[i][1] = logInfo.getOperatorId();
			info[i][2] = logInfo.getOperation();
			info[i][3] = logInfo.getDetail();		
		}
		return new MyTableModel(info, attributes);
	}
}
