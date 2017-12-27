package businesslogic.best_promotion;

import java.util.ArrayList;

import businesslogic.inter.IBestPromotion;
import vo.PromotionVO;


public class BestRankPromotion implements IBestPromotion {
    
    private int rank;

    public BestRankPromotion(int rank) {
        this.rank = rank;
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
