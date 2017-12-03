package po.billpo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class SalesReturnBillPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3028188485729244517L;
	private String billId, customerId, customerName, salesManName, operatorId, operatorName, remark, time ,condition;
	private double originalSum, returnSum;
	private ArrayList<SalesReturnBillItemsPO> salesReturnBillItems;
	
	public SalesReturnBillPO(String billId, String customerId, String customerName, String salesManName,
			String operatorId, String operatorName, String remark, String time, String condition, double originalSum,
			double returnSum, ArrayList<SalesReturnBillItemsPO> salesReturnBillItems) {
		super();
		this.billId = billId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.salesManName = salesManName;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.remark = remark;
		this.time = time;
		this.condition = condition;
		this.originalSum = originalSum;
		this.returnSum = returnSum;
		this.salesReturnBillItems = salesReturnBillItems;
	}

	
	
	public SalesReturnBillPO(){}
	
	


	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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




	public String getCustomerName() {
		return customerName;
	}




	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}




	public String getOperatorName() {
		return operatorName;
	}




	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}




	public String getCondition() {
		return condition;
	}




	public void setCondition(String condition) {
		this.condition = condition;
	};
	
}
