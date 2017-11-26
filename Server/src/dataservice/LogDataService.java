package dataservice;

import java.rmi.Remote;
import java.util.ArrayList;

import po.LogInfoPO;

public interface LogDataService extends Remote{

	/**
	 * ����һ��������¼
	 * @param logInfo ���ӵĲ�����¼
	 * @return �Ƿ����ӳɹ�
	 */
	public boolean add(LogInfoPO logInfo);
	/**
	 * ������еĲ�����־��¼
	 * @return ȫ���Ĳ�����Ϣ
	 */
	public ArrayList<LogInfoPO> getAllInfo();
	/**
	 * ���һ��ʱ��֮�ڵ����в�����¼
	 * @param startTime ��ʼ����
	 * @param EndTime ��������
	 * @return һ��ʱ��֮�ڵ����в�����¼
	 */
	public ArrayList<LogInfoPO> getAllInfo(String startTime, String EndTime);
}
