package businesslogic.inter;

public interface AddLogInterface {

	/**
	 * �����ݿ������һ��������¼
	 * @param time ����ʱ��
	 * @param operatorId ����Աid
	 * @param operation ��������
	 * @param detail ����
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean add(String time, String operatorId, String operation, String detail);
	
}
