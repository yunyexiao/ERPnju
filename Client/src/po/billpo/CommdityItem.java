package po.billpo;

public class CommdityItem {

	private String commodityId;
	private double price;
	private int num;
	private String remark;
	/**
	 * �б������洢��ʱ�ĵ���
	 * @param commodityId ��Ʒ��id
	 * @param price ��Ʒ�ĵ���
	 * @param num ��Ʒ�Ĺ�������
	 * @param remark ��ע
	 */
	public CommdityItem(String commodityId, double price, int num, String remark) {
		this.commodityId = commodityId;
		this.price = price;
		this.num = num;
		this.remark = remark;
	}
	
	public String getCommodityId() {
		return this.commodityId;
	}

	public double getPrice() {
		return this.price;
	}

	public int getNum() {
		return this.num;
	}

	public String getRemark() {
		return this.remark;
	}

}
