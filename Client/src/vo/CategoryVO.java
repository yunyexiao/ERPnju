package vo;

/**
 * ��Ʒ�����VO����,�ɸ������š���š��������
 * @author Ǯ��Ե
 */
public class CategoryVO {

	private String fatherId;
	private String id;
	private String name;
	/**
	 * ���캯��
	 * @param fatherId ��������
	 * @param id ���
	 * @param name ����
	 */
	public CategoryVO(String fatherId, String id, String name) {
		this.fatherId = fatherId;
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
}
