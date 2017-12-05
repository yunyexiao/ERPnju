package po.billpo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>���۵���ص�PO��
 * @author �����
 */

public class SalesReturnBillPO extends BillPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3028188485729244517L;
	private String customerId, salesManName,remark, originalSBId;
	private double originalSum, returnSum;
	private ArrayList<SalesItemsPO> salesBillItems;
	
	public SalesReturnBillPO(String date, String time, String id, String operatorId, int state, String customerId,
			String salesManName, String remark, String originalSBId, double originalSum, double returnSum,
			ArrayList<SalesItemsPO> salesBillItems) {
		super(date, time, id, operatorId, state);
		this.customerId = customerId;
		this.salesManName = salesManName;
		this.remark = remark;
		this.originalSBId = originalSBId;
		this.originalSum = originalSum;
		this.returnSum = returnSum;
		this.salesBillItems = salesBillItems;
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

	public ArrayList<SalesItemsPO> getSalesBillItems() {
		return salesBillItems;
	}

	public void setSalesReturnBillItems(ArrayList<SalesItemsPO> salesBillItems) {
		this.salesBillItems = salesBillItems;
	}

	public String getOriginalSBId() {
		return originalSBId;
	}

	public void setOriginalSBId(String originalSBId) {
		this.originalSBId = originalSBId;
	}
	
}
