package vo;

public enum UserType {
	ADMINISTRATOR("ϵͳ����Ա", 4),KEEPER("������Ա", 0);
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
