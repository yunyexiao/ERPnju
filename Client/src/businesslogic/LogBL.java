package businesslogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import blservice.LogBLService;
import blservice.infoservice.GetUserInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.LogDataService;
import ds_stub.LogDs_stub;
import po.LogInfoPO;
import presentation.main.MainWindow;
import rmi.Rmi;
import vo.MyTableModel;
import vo.UserVO;

public class LogBL implements LogBLService, AddLogInterface {

	private LogDataService logData = Rmi.flag ? Rmi.getRemote(LogDataService.class) : new LogDs_stub();
	private UserVO user;
	
	public LogBL(UserVO user) {
		this.user = user;
	}
	public LogBL() {
		this.user = MainWindow.getUser();
	}
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
		GetUserInterface getUserInfo = new UserBL();
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
	public boolean add(String operation, String detail) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		String time = sdf.format(dt);
		return logData.add(new LogInfoPO(time, user.getId(), operation, detail));
	}
}
