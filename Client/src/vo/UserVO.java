package vo;

public class UserVO {
	private String name;
	private String id;
	private String sex;
	private String telNumber;
	private int age;
	//private String key;
	private UserType type;
	/**
	 * 构造函数……
	 * @param name 姓名
	 * @param type 用户类别
	 * @param id 编号
	 * @param sex 性别
	 * @param telNumber 电话
	 * @param age 年龄
	 */
	public UserVO(String name, UserType type, String id, String sex, String telNumber, int age) {
		this.name = name;
		//this.key = key;
		this.type = type;
		this.id = id;
		this.sex = sex;
		this.telNumber = telNumber;
		this.age = age;
	}
	
	public UserType getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
}
