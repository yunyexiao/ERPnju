package vo;

public enum UserType {
	ADMIN("ϵͳ����Ա", 4),KEEPER("������Ա", 0),SALESMAN("����������Ա",1),ACCOUNTANT("������Ա",2),GM("�ܾ���",3);
	private String name;
	private int num;
	
	private UserType(String name, int num) {
		this.name = name;
		this.num = num;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public String getName() {
		return this.name;
	}
	/**
	 * ��intת��ΪUserType
	 * @param n ���ݿ���洢�����ͱ�ʶ
	 * @return UserTypeö�������
	 */
	public static UserType getType(int n) {
		switch (n) {
		case 0 : return KEEPER;
		case 1 : return SALESMAN;
		case 2 : return ACCOUNTANT;
		case 3 : return GM;
		case 4 : return ADMIN;
		default : return null;
		}
	}
}
