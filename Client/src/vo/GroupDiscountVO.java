package vo;

import java.util.ArrayList;

/**
 * ��Ʒ��Ͻ��۵Ĵ�������VO
 * 
 * @author �Ҷ��
 */
public class GroupDiscountVO extends PromotionVO {
    
    private ArrayList<String> group;
    private double reduction;

    public GroupDiscountVO(String id, String from, String to, double reduction, ArrayList<String> group) {
        super(id, from, to);
        this.reduction = reduction;
        this.group = group;
    }
    
    public double getReduction(){
        return reduction;
    }
    
    public ArrayList<String> getGroup(){
        return group;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
