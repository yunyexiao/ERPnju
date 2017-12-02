package po;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class SalesBillPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2021998686222663097L;
	private String billId, customerId, customerName, salesManName, operatorId, operatorName, 
	remark, promotionId, condition, time;
	 private double beforeDiscount, discount, coupon, afterDiscount;
	 private PromotionPO ppo;
	 private ArrayList<SalesBillItemsPO> salesBillItems;
	 
	 public SalesBillPO(String billId, String customerId, String customerName, String salesManName, String operatorId,
			String operatorName, String remark, String promotionId, String condition, String time,
			double beforeDiscount, double discount, double coupon, double afterDiscount, PromotionPO ppo,
			ArrayList<SalesBillItemsPO> salesBillItems) {
		super();
		this.billId = billId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.salesManName = salesManName;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.remark = remark;
		this.promotionId = promotionId;
		this.condition = condition;
		this.time = time;
		this.beforeDiscount = beforeDiscount;
		this.discount = discount;
		this.coupon = coupon;
		this.afterDiscount = afterDiscount;
		this.ppo = ppo;
		this.salesBillItems = salesBillItems;
	}

	
	 
	 public SalesBillPO(){};
	 
	 
	 
	 public void setBillId(String billId){
		 this.billId=billId;
	 }
	 
	 public String getBillId(){
		 return billId;
	 }
	 
	 public void setCustomerId(String customerId){
		 this.customerId=customerId;
	 }
	 
	 public String getCustomerId(){
		 return customerId;
	 }

	 public void setSalesManName(String name){
		 this.salesManName=name;
	 }
	 
	 public String getSalesManName(){
		 return salesManName;
	 }

	 public void setOperatorId(String id){
		 this.operatorId=id;
	 }
	 
	 public String getOperatorId(){
		 return operatorId;
	 }

	 public void setRemark(String remark){
		 this.remark=remark;
	 }
	 
	 public String getRemark(){
		 return remark;
	 }
	 
	 public void setPromotionId(String id){
		 this.promotionId=id;
	 };
	 
	 public String getPromotionId(){
		 return promotionId;
	 }
	 
	 public void setTime(String time){
		 this.time=time;
	 }
	 
	 public String getTime(){
		 return time;
	 }
	 
	 public void setBeforeDiscount(double money){
		 this.beforeDiscount=money;
	 }
	 
	 public double getBeforeDiscount(){
		 return beforeDiscount;
	 }
	 
	 public void setDiscount(double dis){
		 this.discount=dis;
	 }
	 
	 public double getDiscount(){
		 return discount;
	 }
	 
	 public void setAfterDiscount(double money){
		 this.afterDiscount=money;
	 }
	 
	 public double getAfterDiscount(){
		 return afterDiscount;
	 }
	 
	 public void setCoupon(double cou){
		 this.coupon=cou;
	 }
	 
	 public double getCoupon(){
		 return coupon;
	 }
	 
	 public void setSalesBillItems(ArrayList<SalesBillItemsPO> sbis){
		 this.salesBillItems=sbis;
	 }
	 
	 public ArrayList<SalesBillItemsPO> getSalesBillItems(){
		 return salesBillItems;
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
	}



	public PromotionPO getPpo() {
		return ppo;
	}



	public void setPpo(PromotionPO ppo) {
		this.ppo = ppo;
	}

}
