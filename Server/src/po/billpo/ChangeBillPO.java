package po.billpo;

import java.io.Serializable;
import java.util.ArrayList;

public class ChangeBillPO extends BillPO implements Serializable{

	private boolean isOver;

	private ArrayList<ChangeItem> commodityList;
	
	public ChangeBillPO(){};
	
	public ChangeBillPO(String date, String time, String id, String operatorId, int state, boolean isOver,
			ArrayList<ChangeItem> commodityList) {
		super(date, time, id, operatorId, state);
		this.isOver = isOver;
		this.commodityList = commodityList;
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

	public ArrayList<ChangeItem> getCommodityList() {
		return commodityList;
	}

	public void setCommodityList(ArrayList<ChangeItem> commodityList) {
		this.commodityList = commodityList;
	}

	@Override
	public String getAllId() {
		String s = isOver ? "BYD-" : "BSD-";
		return s + this.getDate() + "-" + this.getId();	
	}
}
