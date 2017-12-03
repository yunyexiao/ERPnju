package po.billpo;

import java.io.Serializable;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class PurchaseReturnBillItemsPO implements Serializable{
 /**
	 * 
	 */
private static final long serialVersionUID = -313378607159900293L;
	
	private String comId, comName, comSum, remark;
	private int comQuantity;
	private double comPrice;
	
	public PurchaseReturnBillItemsPO(){};
	
	public PurchaseReturnBillItemsPO(String comId, String comName, String comSum, String remark, int comQuantity,
			double comPrice) {
		super();
		this.comId = comId;
		this.comName = comName;
		this.comSum = comSum;
		this.remark = remark;
		this.comQuantity = comQuantity;
		this.comPrice = comPrice;
	}
	
	
	
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getComSum() {
		return comSum;
	}
	public void setComSum(String comSum) {
		this.comSum = comSum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getComQuantity() {
		return comQuantity;
	}
	public void setComQuantity(int comQuantity) {
		this.comQuantity = comQuantity;
	}
	public double getComPrice() {
		return comPrice;
	}
	public void setComPrice(double comPrice) {
		this.comPrice = comPrice;
	}

}
