package businesslogic;

import javax.swing.table.DefaultTableModel;

import blservice.LogBLService;

public class LogBL_stub implements LogBLService {

	@Override
	public DefaultTableModel getLogInfo() {
		String[] attributes={"����ʱ��","����Ա","��������","����"};
		String[][] info={{"2017-11-06 12:48:32","��","�½�������","��������"},
				{"2017-11-06 12:32:32","��","������Ʒ","��������"}};
		System.out.println("�õ�������¼");
		return new DefaultTableModel(info, attributes);
	}

	@Override
	public DefaultTableModel searchByTime(String startTime, String endTime) {
		String[] attributes={"����ʱ��","����Ա","��������","����"};
		String[][] info={{"2017-11-06 12:48:32","��","�½�������","��������"}};
		System.out.println("�õ�������¼");
		return new DefaultTableModel(info, attributes);
	}

}
