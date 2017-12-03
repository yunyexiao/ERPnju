package vo.billvo;

public abstract class BillVO {

	public static int DRAFT = 0;
	public static int SAVED = 1;
	public static int COMMITED = 2;
	public static int PASS = 3;
	public static int NOTPASS = 4;
	
	private String date;
	private String time;
	private String id;
	private String operator;
	private int state;
	
	/**
	 * BillVO�Ĺ��췽��
	 * @param date �ƶ�����[yyyyMMdd]
	 * @param time �ƶ�ʱ��
	 * @param id ÿһ��֮�ڵı��[00000]
	 * @param operator ������Ա��id
	 * @param state ����״̬��ʹ�ñ���ľ�̬��Ա����
	 */
	public BillVO(String date, String time, String id, String operator, int state) {
		this.date = date;
		this.time = time;
		this.id = id;
		this.operator = operator;
		this.state = state;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getId() {
		return id;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int n) {

		state = n;

	}
	/**
	 * Bill�������ʵ�ִ˷������Ի�ȡ��������������ĵ���id��������ʾ��
	 * @return �����ĵ���id
	 */
	abstract public String getAllId();
}
