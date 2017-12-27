package po;

import java.util.ArrayList;

import po.billpo.GiftItem;

/**
 * ���ݹ˿͵ȼ����ƶ��Ĵ�������
 * 
 * @author �Ҷ��
 */
public class RankPromotionPO extends PromotionPO {
    
    private int rank;
    private ArrayList<GiftItem> gifts;
    private double reduction, coupon;
    
    public RankPromotionPO(){
        this(null, null, null, 0, null, 0.0, 0.0);
    }

    public RankPromotionPO(String id, String fromDate, String toDate, int rank, 
        ArrayList<GiftItem> gifts, double reduction, double coupon) {
        super(id, fromDate, toDate);
        this.rank = rank;
        this.gifts = gifts;
        this.reduction = reduction;
        this.coupon = coupon;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ArrayList<GiftItem> getGifts() {
        return gifts;
    }

    public void setGifts(ArrayList<GiftItem> gifts) {
        this.gifts = gifts;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public double getCoupon() {
        return coupon;
    }

    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }

}
