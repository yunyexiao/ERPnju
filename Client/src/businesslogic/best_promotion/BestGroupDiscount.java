package businesslogic.best_promotion;

import java.util.ArrayList;

import businesslogic.inter.IBestPromotion;
import presentation.component.MyTableModel;
import vo.PromotionVO;


public class BestGroupDiscount implements IBestPromotion {
    
    /** The goods a customer purchases. */
    private MyTableModel goods;

    public BestGroupDiscount(MyTableModel goods) {
        this.goods = goods;
    }

    @Override
    public ArrayList<PromotionVO> getBest() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getReduction() {
        // TODO Auto-generated method stub
        return 0;
    }

}
