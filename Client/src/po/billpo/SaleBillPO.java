package po.billpo;

import java.util.ArrayList;

public class SaleBillPO extends BillPO {

	private String customerId;
	private ArrayList<CommdityItem> commodityList;
	
	public SaleBillPO(String date, String time, String id, String operator, int state, String customerId, ArrayList<CommdityItem> commodityList) {
		super(date, time, id, operator, state);
		this.customerId = customerId;
		this.commodityList = commodityList;
	}

	public String getCustomerId() {
		return customerId;
	}
	
	public ArrayList<CommdityItem> getCommodityList() {
		return commodityList;
	}
}
