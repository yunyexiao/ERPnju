package po.billpo;

import java.io.Serializable;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class PurchaseBillItemsPO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 6733898597204329266L;
	
	private String comId, remark;
	private int comQuantity;
	private double comPrice, comSum;
	
	public PurchaseBillItemsPO(String comId, String remark, int comQuantity, double comPrice, double comSum) {
		super();
		this.comId = comId;
		this.remark = remark;
		this.comQuantity = comQuantity;
		this.comPrice = comPrice;
		this.comSum = comSum;
	}
	
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public double getComSum() {
		return comSum;
	}
	public void setComSum(double comSum) {
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
