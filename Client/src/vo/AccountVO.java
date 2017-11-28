package vo;

import po.AccountPO;

public class AccountVO {

	private String name;
	private String number;
	private int money;
	
	public AccountVO(String name, String number, int money) {
		this.name = name;
		this.number = number;
		this.money = money;
	}
	
	public String getName() {return name;}
	public String getNumber() {return number;}
	public int getMoney() {return money;}
	
	public AccountPO toPO() {
		AccountPO accountPO = new AccountPO (number, name, money);
		return accountPO;
	}
}
