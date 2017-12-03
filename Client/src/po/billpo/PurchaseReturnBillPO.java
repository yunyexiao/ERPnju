package po.billpo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class PurchaseReturnBillPO extends BillPO implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 2578319197720284508L;
	
	private String  supplierId, supplierName, remark;
	private double sum;
	private ArrayList<PurchaseReturnBillItemsPO> purchaseReturnBillItems;
	
	public PurchaseReturnBillPO(String date, String time, String id, String operatorId, String operatorName, int state,
			String supplierId, String supplierName, String remark, double sum,
			ArrayList<PurchaseReturnBillItemsPO> purchaseReturnBillItems) {
		super(date, time, id, operatorId, operatorName, state);
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.remark = remark;
		this.sum = sum;
		this.purchaseReturnBillItems = purchaseReturnBillItems;
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
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}

	public ArrayList<PurchaseReturnBillItemsPO> getPurchaseReturnBillItems() {
		return purchaseReturnBillItems;
	}

	public void setPurchaseReturnBillItems(ArrayList<PurchaseReturnBillItemsPO> purchaseReturnBillItems) {
		this.purchaseReturnBillItems = purchaseReturnBillItems;
	}

}
