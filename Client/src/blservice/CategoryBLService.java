package blservice;

import java.rmi.RemoteException;

import javax.swing.tree.DefaultTreeModel;

import vo.CategoryVO;
/**
 * ��Ʒ�������ҵ���߼��ӿ�
 * @author Ǯ��Ե
 *
 */
public interface CategoryBLService {

	/**
     * ����һ���µ���Ʒ���
     * @return ����Ʒ���
     */
    public String getNewId();
    /**
	 * ������Ʒ���������ID����һ��CategoryVO���󣬲��������Ʒ�����Ƿ�ɾ��<br/>
	 * �Ҳ����ͷ���һ��null...
	 * @param id ��Ʒ�����id [id��ʽ��6λ����]
	 * @return ���ҵ���CategoryVO����
	 * @throws RemoteException
	 */
	public CategoryVO findById(String id);
	/**
	 * �������ݲ��PO������������չʾ����ʾ
	 * @return ������ʾ��TreeModel
	 */
	public DefaultTreeModel getModel();
	/**
	 * ����һ���ڵ�</br>
	 * ֻ��Ҷ�ڵ�ķ�����������Ʒ</br>
	 * һ������������Ʒ�Ͳ����ڸ÷�����������ӷ���
	 * @param category ����㴫�ݵ�VO����
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean add(CategoryVO category);
	/**
	 * ɾ��һ���ڵ�</br>
	 * ����ô���������Ʒ�ܲ���ɾ����������
	 * @param category ����㴫�ݵ�VO����
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean delete(String id);
	/**
	 * �޸�һ����Ʒ�������Ϣ��ֻ�ܸ����ơ�������
	 * @param category ����㴫�ݵ�VO����
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean change(CategoryVO category);
	/**
	 * ����ID���Ҹ��ڵ����Ƿ����ӽڵ�
	 * @param id
	 * @return 
	 */
	public boolean hasContent(String id);
}
