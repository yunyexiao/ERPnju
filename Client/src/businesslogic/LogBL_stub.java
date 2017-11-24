package businesslogic;

import blservice.LogBLService;
import presentation.component.MyTableModel;

public class LogBL_stub implements LogBLService {

	@Override
	public MyTableModel getLogInfo() {
		String[] attributes={"����ʱ��","����Ա","��������","����"};
		String[][] info={{"2017-11-06 12:48:32","��","�½�������","��������"},
				{"2017-11-06 12:32:32","��","������Ʒ","��������"}};
		System.out.println("�õ�������¼");
		return new MyTableModel(info, attributes);
	}

	@Override
	public MyTableModel searchByTime(String startTime, String endTime) {
		String[] attributes={"����ʱ��","����Ա","��������","����"};
		String[][] info={{"2017-11-06 12:48:32","��","�½�������","��������"}};
		System.out.println("�õ�������¼");
		return new MyTableModel(info, attributes);
	}

}
