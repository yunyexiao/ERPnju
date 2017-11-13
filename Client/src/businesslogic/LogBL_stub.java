package businesslogic;

import javax.swing.table.DefaultTableModel;

import blservice.LogBLService;

public class LogBL_stub implements LogBLService {

	@Override
	public DefaultTableModel getLogInfo() {
		String[] attributes={"操作时间","操作员","操作类型","详情"};
		String[][] info={{"2017-11-06 12:48:32","他","新建进货单","…………"},
				{"2017-11-06 12:32:32","他","增加商品","…………"}};
		System.out.println("得到操作记录");
		return new DefaultTableModel(info, attributes);
	}

	@Override
	public DefaultTableModel searchByTime(String startTime, String endTime) {
		String[] attributes={"操作时间","操作员","操作类型","详情"};
		String[][] info={{"2017-11-06 12:48:32","他","新建进货单","…………"}};
		System.out.println("得到操作记录");
		return new DefaultTableModel(info, attributes);
	}

}
