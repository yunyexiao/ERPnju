package blservice;

import presentation.component.MyTableModel;

public interface LogBLService {

	/**
	 * �õ����в�����¼
	 * @return ֱ�����ڱ����ʾ��TableModel
	 */
	public MyTableModel getLogInfo();
	/**
	 * ����ʱ����������������¼
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return ֱ�����ڱ����ʾ��TableModel
	 */
	public MyTableModel searchByTime(String startTime, String endTime);
	
}
