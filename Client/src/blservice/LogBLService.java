package blservice;

import javax.swing.table.DefaultTableModel;

public interface LogBLService {

	/**
	 * �õ����в�����¼
	 * @return ֱ�����ڱ����ʾ��TableModel
	 */
	public DefaultTableModel getLogInfo();
	/**
	 * ����ʱ����������������¼
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @return ֱ�����ڱ����ʾ��TableModel
	 */
	public DefaultTableModel searchByTime(String startTime, String endTime);
	
}
