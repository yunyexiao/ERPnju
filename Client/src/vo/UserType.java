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
}
