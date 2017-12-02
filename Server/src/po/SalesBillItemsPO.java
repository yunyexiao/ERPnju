package po;

import java.io.Serializable;

/**
 * Create time: 2017/12/01<br>
 * Last update time: 2017/12/01<br>
 * <br>销售单相关的PO类
 * @author 万嘉雯
 */

public class SalesBillItemsPO implements Serializable{
	
	private static final long serialVersionUID = -7983830700017283896L;
	
	private String comId, comName, comRemark;
	private int comQuantity;
	private double comPrice,comSum;
	
	public SalesBillItemsPO(String comId, String comName, String comRemark, int comQuantity, double comPrice,
			double comSum) {
		super();
		this.comId = comId;
		this.comName = comName;
		this.comRemark = comRemark;
		this.comQuantity = comQuantity;
		this.comPrice = comPrice;
		this.comSum = comSum;
	}

	
	
	public SalesBillItemsPO(){};
	
	
	
	public void setComId(String id){
		this.comId=id;
	}
	
	public String getComId(){
		return comId;
	}
	
	public void setComSum(double sum){
		this.comSum=sum;
	}
	
	public double getComSum(){
		return comSum;
	}
	
	public void setComRemark(String remark){
		this.comRemark=remark;
	}
	
	public String getComRemark(){
		return comRemark;
	}
	
	public void setComQuantity(int quantity){
		this.comQuantity=quantity;
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



	public String getComName() {
		return comName;
	}



	public void setComName(String comName) {
		this.comName = comName;
	}
}
