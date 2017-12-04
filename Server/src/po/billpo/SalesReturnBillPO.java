package po.billpo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class SalesReturnBillPO extends BillPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3028188485729244517L;
	private String customerId, salesManName,remark, originalSBId;
	private double originalSum, returnSum;
	private ArrayList<SalesReturnBillItemsPO> salesReturnBillItems;
	
	public SalesReturnBillPO(String date, String time, String id, String operatorId, int state, String customerId,
			String salesManName, String remark, String originalSBId, double originalSum, double returnSum,
			ArrayList<SalesReturnBillItemsPO> salesReturnBillItems) {
		super(date, time, id, operatorId, state);
		this.customerId = customerId;
		this.salesManName = salesManName;
		this.remark = remark;
		this.originalSBId = originalSBId;
		this.originalSum = originalSum;
		this.returnSum = returnSum;
		this.salesReturnBillItems = salesReturnBillItems;
	}
		
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSalesManName() {
		return salesManName;
	}

	public void setSalesManName(String salesManName) {
		this.salesManName = salesManName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getOriginalSum() {
		return originalSum;
	}

	public void setOriginalSum(double originalSum) {
		this.originalSum = originalSum;
	}

	public double getReturnSum() {
		return returnSum;
	}

	public void setReturnSum(double returnSum) {
		this.returnSum = returnSum;
	}

	public ArrayList<SalesReturnBillItemsPO> getSalesReturnBillItems() {
		return salesReturnBillItems;
	}

	public void setSalesReturnBillItems(ArrayList<SalesReturnBillItemsPO> salesReturnBillItems) {
		this.salesReturnBillItems = salesReturnBillItems;
	}

	public String getOriginalSBId() {
		return originalSBId;
	}

	public void setOriginalSBId(String originalSBId) {
		this.originalSBId = originalSBId;
	}
	
}
