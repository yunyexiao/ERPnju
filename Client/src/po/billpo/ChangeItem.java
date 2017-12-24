package po.billpo;

import java.io.Serializable;

public class ChangeItem implements Serializable{

	private String commodityId;
	private int originalValue;
	private int changedValue;
	
	public String getCommodityId() {
		return commodityId;
	}

	public int getOriginalValue() {
		return originalValue;
	}

	public int getChangedValue() {
		return changedValue;
	}

	public ChangeItem(String commodityId, int a, int b) {
		this.commodityId = commodityId;
		this.originalValue = a;
		this.changedValue = b;
	}
}
