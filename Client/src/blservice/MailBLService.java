package blservice;

import java.util.ArrayList;

import vo.MailVO;
import vo.UserVO;

public interface MailBLService {
	/**
	 * �����ʼ�
	 * @param fromId ������id���
	 * @param toId �ռ���id���
	 * @param content �ʼ���������
	 * @return �Ƿ��ͳɹ�
	 */
	public boolean saveMail(String fromId, String toId, String content);
	/**
	 * Ⱥ���ʼ�
	 * @param fromId ������id���
	 * @param type �ռ������
	 * @param content �ʼ���������
	 * @return �Ƿ��ͳɹ�
	 */
	public boolean saveMail(String fromId, int type, String content);
	/**
	 * ��һ���ʼ���Ϊ�Ѷ�
	 * @param mail �Ѷ����ʼ�
	 * @return �Ƿ����óɹ�
	 */
	public boolean readMail(MailVO mail);
	/**
	 * �õ�һ���û��յ��������ʼ��б������Ѷ���δ����
	 * @param user �־û��� �û�
	 * @return �ʼ��б�
	 */
	public ArrayList<MailVO> getMailList(UserVO user);
}
