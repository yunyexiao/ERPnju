package blservice.billblservice;

public interface BillCreateBLService {

	/**
	 * ����������Id[XXX-yyyyMMdd-00000]
	 * @return ��һ�����ݵ�Id
	 */
	public String getNewId();
	/**
	 * ɾ��һ�ŵ��ݣ����ύ״̬�ĵ��ݲ���ɾ����
	 * @param id ��Ҫɾ���ĵ���id[Ӧ����һ��������id]
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteBill(String id);
}
