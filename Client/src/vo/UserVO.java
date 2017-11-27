package vo;

import po.UserPO;

public class UserVO {
	private String name;
	private String id;
	private String sex;
	private String telNumber;
	private int age;
	private String key;
	private UserType type;
	private String dept;
	/**
	 * ���캯������
	 * @param name ����
	 * @param key ����
	 * @param type �û����
	 * @param id ���
	 * @param sex �Ա�
	 * @param telNumber �绰
	 * @param age ����
	 */
	public UserVO(String name, String key, UserType type, String id, String sex, String telNumber, int age) {
		this.name = name;
		this.key = key;
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
	
	public String getPwd() {
		return this.key;
	}
	
	public String getDept() {
		return this.dept;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getSex() {
		return this.sex;
	}
	
	public String getTelNumber() {
		return this.telNumber;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public UserPO toPO() {
		UserPO userPO = new UserPO(id, name, " ", " ", sex, telNumber, type.getNum(), age);
		return userPO;
	}
}
