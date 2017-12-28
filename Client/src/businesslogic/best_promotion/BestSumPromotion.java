package businesslogic.best_promotion;

import java.util.ArrayList;

import businesslogic.inter.IBestPromotion;
import vo.PromotionVO;


public class BestSumPromotion implements IBestPromotion {
    
    private double sum;

    public BestSumPromotion(double sum) {
        this.sum = sum;
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
