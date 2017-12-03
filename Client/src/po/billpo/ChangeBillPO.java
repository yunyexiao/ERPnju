package po.billpo;

import java.util.ArrayList;

import vo.billvo.ChangeBillVO;

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
	
	public ChangeBillPO(ChangeBillVO bill, boolean isOver, ArrayList<ChangeItem> commodityList) {
		super(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator(), bill.getState());
		this.isOver = isOver;
		this.commodityList = commodityList;
	}
	
	public boolean getFlag() {
		return isOver;
	}

	public ArrayList<ChangeItem> getCommodityList() {
		return commodityList;
	}
	
}
