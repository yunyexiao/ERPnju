package po.billpo;

import java.io.Serializable;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class SalesReturnBillItemsPO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 8054870015595499405L;
	private String comId, remark;
	private int comQuantity;
	private double comPrice,comSum;
	
	public SalesReturnBillItemsPO(String comId, String remark, int comQuantity, double comPrice, double comSum) {
		super();
		this.comId = comId;
		this.remark = remark;
		this.comQuantity = comQuantity;
		this.comPrice = comPrice;
		this.comSum = comSum;
	}
		
	public void setComId(String id){
		this.comId=id;
	};
	
	public String getComId(){
		return comId;
	}
	
	public void setRemark(String remark){
		this.remark=remark;
	}
	
	public String getRemark(){
		return remark;
	}
	
	public void setComQuantity(int quan){
		this.comQuantity=quan;
	}
	
	public int getComQuantity(){
		return comQuantity;
	}
	
	public void setComPrice(double price){
		this.comPrice=price;
	}
	
	public double getComPrice(){
		return comPrice;
	}
	
	public void setComSum(double sum){
		this.comSum=sum;
	}
	
	public double getComSum(){
		return comSum;
	}

	
}
