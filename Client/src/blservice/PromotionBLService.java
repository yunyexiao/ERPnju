package blservice;

import presentation.component.MyTableModel;
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
    
    MyTableModel searchRankPromotion(String from, String to, int rank);
    
    MyTableModel searchRankPromotion(String date, int rank);
    
    MyTableModel searchGroupDiscount(String from, String to);
    
    MyTableModel searchGroupDiscount(String date);
    
    MyTableModel searchSumPromotion(String from, String to);
    
    MyTableModel searchSumPromotion(String date);
    
    PromotionVO findById(String id);

}
