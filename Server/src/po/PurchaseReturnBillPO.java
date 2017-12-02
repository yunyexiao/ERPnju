package po;

import java.io.Serializable;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class PurchaseReturnBillPO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 2578319197720284508L;
	
	private String billId, supplierId, supplierName, operatorId, operatorName, remark, condition, time;
	private double sum;
	
	public PurchaseReturnBillPO(){};
	
	public PurchaseReturnBillPO(String billId, String supplierId, String supplierName, String operatorId,
			String operatorName, String remark, String condition, String time, double sum) {
		super();
		this.billId = billId;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.remark = remark;
		this.condition = condition;
		this.time = time;
		this.sum = sum;
	}
	
	
	
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}

}
