package po.billpo;

import java.io.Serializable;
import java.util.ArrayList;
import po.PromotionPO;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class SalesBillPO extends BillPO implements Serializable{
	

	/**
	 * 
	 */
	 private static final long serialVersionUID = 2021998686222663097L;
	 private String customerId, customerName, salesManName, remark, promotionId;
	 private double beforeDiscount, discount, coupon, afterDiscount;
	 private PromotionPO ppo;
	 private ArrayList<SalesBillItemsPO> salesBillItems;
	 
	 public SalesBillPO(String date, String time, String id, String operatorId, String operatorName, int state,
			String customerId, String customerName, String salesManName, String remark, String promotionId,
			double beforeDiscount, double discount, double coupon, double afterDiscount, PromotionPO ppo,
			ArrayList<SalesBillItemsPO> salesBillItems) {
		super(date, time, id, operatorId, operatorName, state);
		this.customerId = customerId;
		this.customerName = customerName;
		this.salesManName = salesManName;
		this.remark = remark;
		this.promotionId = promotionId;
		this.beforeDiscount = beforeDiscount;
		this.discount = discount;
		this.coupon = coupon;
		this.afterDiscount = afterDiscount;
		this.ppo = ppo;
		this.salesBillItems = salesBillItems;
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


	public PromotionPO getPpo() {
		return ppo;
	}



	public void setPpo(PromotionPO ppo) {
		this.ppo = ppo;
	}

}
