package vo;

public class UserVO {
	private String name;
	//private String key;
	private UserType type;
	
	public UserVO(String name, UserType type) {
		this.name = name;
		//this.key = key;
		this.type = type;
	}
	
	public UserType getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
}
