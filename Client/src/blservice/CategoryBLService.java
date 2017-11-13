package blservice;

import javax.swing.tree.DefaultTreeModel;

import vo.CategoryVO;
/**
 * ��Ʒ�������ҵ���߼��ӿ�
 * @author Ǯ��Ե
 *
 */
public interface CategoryBLService {

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
	public boolean delete(CategoryVO category);
	/**
	 * �޸�һ����Ʒ�������Ϣ��ֻ�ܸ����ơ�������
	 * @param category ����㴫�ݵ�VO����
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean change(CategoryVO category);
}
