package po.billpo;

import java.util.ArrayList;

public class ChangeBillPO extends BillPO {

	/*判断报溢或者报损*/
	private boolean isOver;
	private ArrayList<ChangeItem> commodityList;
	
	public ChangeBillPO(String date, String time, String id, String operator, int state, boolean isOver,
			ArrayList<ChangeItem> commodityList) {
		super(date, time, id, operator, state);
		this.isOver = isOver;
		this.commodityList = commodityList;
	}
	
	public boolean getFlag() {
		return isOver;
	}

	public ArrayList<ChangeItem> getCommodityList() {
		return commodityList;
	}

	@Override
	public String getAllId() {
		String s = isOver ? "BYD-" : "BSD-";
		return s + this.getDate() + "-" + this.getId();
	}
	
}
