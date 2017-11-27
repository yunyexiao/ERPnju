package vo;

import po.CategoryPO;

/**
 * ��Ʒ�����VO����,�ɸ������š���š��������
 * @author Ǯ��Ե
 */
public class CategoryVO {

	private String fatherId, fatherName;
	private String id;
	private String name;
	/**
	 * ���캯��
	 * @param fatherId ��������
	 * @param id ���
	 * @param name ����
	 */
	public CategoryVO(String fatherId, String fatherName, String id, String name) {
		this.fatherId = fatherId;
		this.fatherName = fatherName;
		this.id = id;
		this.name = name;
	}
	/**
	 * @return ���ظ�������
	 */
	public String getFatherId() {
		return fatherId;
	}
	/**
	 * @return ���ظ���������
	 */
	public String getFatherName(){
	    return fatherName;
	}
	/**
	 * @return ���ر��
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return ��������
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString(){
	    return name;
	}

	public CategoryPO toPO(){
	    return new CategoryPO(id, name, fatherId, fatherName);
	}
}
