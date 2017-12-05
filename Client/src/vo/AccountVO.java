package vo;

import po.AccountPO;

public class AccountVO {

	private String name;
	private String number;
	private double money;
	
	public AccountVO(String number, String name, double money) {
		this.number = number;
		this.name = name;
		this.money = money;
	}
	
	public String getName() {return name;}
	public String getNumber() {return number;}
	public double getMoney() {return money;}
	
	public AccountPO toPO() {
		AccountPO accountPO = new AccountPO (number, name, money, true);
		return accountPO;
	}
}
