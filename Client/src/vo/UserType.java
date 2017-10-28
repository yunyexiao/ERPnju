package vo;

public enum UserType {
	ADMIN("系统管理员", 4),KEEPER("库存管理员", 0),SALESMAN("进货销售人员",1),ACCOUNTANT("财务人员",2),GM("总经理",3);
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
