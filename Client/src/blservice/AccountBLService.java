package blservice;

import vo.AccountVO;

public interface AccountBLService extends DataBLService {

	/**
	 * ����һ���˻���Ϣ�ļ�¼
	 * @param account �˻�VO
	 * @return �Ƿ�ɹ����
	 */
	public boolean add(AccountVO account);
	/**
	 * ����һ���˻���Ϣ
	 * @param account �˻�VO
	 * @return ����Ѵ��ڲ��ҳɹ����ķ���true
	 */
	public boolean change(AccountVO account);
	
}
