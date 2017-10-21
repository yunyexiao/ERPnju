package vo;

public enum UserType {
	ADMINISTRATOR("系统管理员", 4),KEEPER("库存管理员", 0);
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
