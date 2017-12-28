package blservice;

import vo.GroupDiscountVO;
import vo.PromotionVO;
import vo.RankPromotionVO;
import vo.SumPromotionVO;

/**
 * 与促销策略相关的业务逻辑接口定义
 * 
 * @author 恽叶霄
 */
public interface PromotionBLService {
    
    boolean add(GroupDiscountVO promotion);
    
    boolean add(RankPromotionVO promotion);
    
    boolean add(SumPromotionVO promotion);
    
    boolean delete(String id);
    
    boolean searchRankPromotion(String from, String to, int rank);
    
    boolean searchRankPromotion(String date, int rank);
    
    boolean searchGroupDiscount(String from, String to);
    
    boolean searchGroupDiscount(String date);
    
    boolean searchSumPromotion(String from, String to);
    
    boolean searchSumPromotion(String date);
    
    PromotionVO findById(String id);

}
