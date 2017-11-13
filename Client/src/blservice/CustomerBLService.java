package blservice;

import vo.CustomerVO;

public interface CustomerBLService extends DataBLService {

	/**
	 * ����һ���ͻ���Ϣ�ļ�¼
	 * @param customer �ͻ�VO
	 * @return �Ƿ�ɹ����
	 */
	public boolean add(CustomerVO customer);
	/**
	 * ����һ���ͻ���Ϣ
	 * @param customer �ͻ�VO
	 * @return ����Ѵ��ڲ��ҳɹ����ķ���true
	 */
	public boolean change(CustomerVO customer);
	
}
