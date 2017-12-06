package vo;

import po.CustomerPO;

public class CustomerVO {
	private String name;
	private String id;
	private String telNumber;
	private String address;
	private String code;
	private String mail;
	private String salesman;
	private int type;
	private int rank;
	private double recRange;
	private double receivable;
	private double payment;
	/**
	 * ���캯������
	 * @param id ���
	 * @param name ����
	 * @param type �û����
	 * @param rank �û�����
	 * @param telNumber �绰
	 * @param address ��ַ
	 * @param code �ʱ�
	 * @param mail ����
	 * @param recRange Ӧ�ն��
	 * @param receivable Ӧ��
	 * @param payment Ӧ��
	 * @param salesman Ĭ��ҵ��Ա
	 */
	public CustomerVO (String id, String name, int type, int rank, String telNumber, String address, String code, String mail, double recRange, double receivable, double payment, String salesman) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.rank = rank;
		this.telNumber = telNumber;
		this.address = address;
		this.code = code;
		this.mail = mail;
		this.recRange = recRange;
		this.receivable = receivable;
		this.payment = payment;
		this.salesman = salesman;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getType() {
		return this.type;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public String getTelNumber() {
		return this.telNumber;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getCode() {
		return this.getCode();
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public double getRecRange() {
		return this.recRange;
	}
	
	public double getReceivable() {
		return this.receivable;
	}
	
	public double getPayment() {
		return this.payment;
	}
	
	public String getSalesman() {
		return this.salesman;
	}
	
	public CustomerPO toPO() {
		CustomerPO customerPO = new CustomerPO(id, name, telNumber, address, mail, code, salesman,
				rank, type, recRange, receivable, payment, true);
		return customerPO;
	}
}
