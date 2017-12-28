package vo;

import presentation.component.MyTableModel;

/**
 * 针对顾客等级的促销策略
 * 
 * @author 恽叶霄
 */
public class RankPromotionVO extends PromotionVO {
    
    private int rank;
    private MyTableModel gifts;
    private double reduction, coupon;

    public RankPromotionVO(String id, String from, String to, double reduction, 
        double coupon, int rank, MyTableModel gifts) {
        super(id, from, to);
        this.rank = rank;
        this.gifts = gifts;
        this.reduction = reduction;
        this.coupon = coupon;
    }

    public int getRank() {
        return rank;
    }

    public MyTableModel getGifts() {
        return gifts;
    }

    public double getReduction() {
        return reduction;
    }

    public double getCoupon() {
        return coupon;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
