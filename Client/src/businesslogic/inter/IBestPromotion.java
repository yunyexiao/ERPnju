package businesslogic.inter;

import java.util.ArrayList;

import vo.PromotionVO;

/**
 * 特定种类的促销策略的最优解
 * 
 * @author 恽叶霄
 */
public interface IBestPromotion {
    
    ArrayList<PromotionVO> getBest();
    
    double getReduction();

}
