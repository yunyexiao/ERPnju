package vo;

import java.util.ArrayList;

import businesslogic.GiftItemTools;
import po.RankPromotionPO;
import po.billpo.GiftItem;
import presentation.component.MyTableModel;

/**
 * ��Թ˿͵ȼ��Ĵ�������
 * 
 * @author �Ҷ��
 */
public class RankPromotionVO extends PromotionVO {
    
    private int rank;
    private MyTableModel gifts;
    private double coupon;

    public RankPromotionVO(String id, String from, String to, double reduction, 
        double coupon, int rank, MyTableModel gifts) {
        super(id, from, to, gifts);
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

    public double getCoupon() {
        return coupon;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(super.toString());
        if(gifts != null){
            buffer.append("����Ʒ��");
            int rows = gifts.getRowCount();
            for(int i = 0; i < rows; i++){
                buffer.append(gifts.getValueAt(i, 1));
                buffer.append('*');
                buffer.append(gifts.getValueAt(i, 4));
                buffer.append(',');
            }
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        if(reduction > 0){
            buffer.append("�����ۣ�");
            buffer.append(reduction);
        }
        if(coupon > 0){
            buffer.append("������ȯ��");
            buffer.append(coupon);
        }
        return buffer.toString();
    }

    @Override
    public RankPromotionPO toPO() {
        ArrayList<GiftItem> gifts = GiftItemTools.toArrayList(this.gifts);
        return new RankPromotionPO(this.getId(), this.getFromDate(), this.getToDate(), rank, gifts, reduction, coupon);
    }

}
