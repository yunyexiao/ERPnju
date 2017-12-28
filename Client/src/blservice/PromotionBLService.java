package blservice;

import vo.GroupDiscountVO;
import vo.PromotionVO;
import vo.RankPromotionVO;
import vo.SumPromotionVO;

/**
 * �����������ص�ҵ���߼��ӿڶ���
 * 
 * @author �Ҷ��
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
