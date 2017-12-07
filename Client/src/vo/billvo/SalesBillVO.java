package vo.billvo;

import presentation.component.MyTableModel;

public class SalesBillVO extends MarketBillVO {

	private double beforeDiscount, discount, coupon;
	
	public SalesBillVO(String date, String time, String id
	    , String operator, int state, String customerId
	    , String customerName, MyTableModel model
	    , String remark, double beforeDiscount, double discount
	    , double coupon, double sum) {
		super(date, time, id, operator, state, customerId
		    , customerName, model, remark, sum);
		this.beforeDiscount = beforeDiscount;
		this.discount = discount;
		this.coupon = coupon;
	}
	
    @Override
    protected String getPrefix() {
        return "XSD";
    }

    public double getBeforeDiscount() {
        return beforeDiscount;
    }

    public double getDiscount() {
        return discount;
    }
    
    public double getCoupon(){
        return coupon;
    }

}
