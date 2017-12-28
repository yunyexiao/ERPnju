package vo;

import presentation.component.MyTableModel;

/**
 * 针对消费总额制定的促销策略
 * 
 * @author 恽叶霄
 */
public class SumPromotionVO extends PromotionVO {
    
    private double startPrice, endPrice;
    private double coupon;
    private MyTableModel gifts;

    public SumPromotionVO(String id, String from, String to, double startPrice, 
        double endPrice, double coupon, MyTableModel gifts) {
        super(id, from, to);
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.coupon = coupon;
        this.gifts = gifts;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public double getCoupon() {
        return coupon;
    }

    public MyTableModel getGifts() {
        return gifts;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
