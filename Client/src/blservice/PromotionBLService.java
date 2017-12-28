package blservice;

import presentation.component.MyTableModel;
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
    
    MyTableModel searchRankPromotion(String from, String to, int rank);
    
    MyTableModel searchRankPromotion(String date, int rank);
    
    MyTableModel searchGroupDiscount(String from, String to);
    
    MyTableModel searchGroupDiscount(String date);
    
    MyTableModel searchSumPromotion(String from, String to);
    
    MyTableModel searchSumPromotion(String date);
    
    PromotionVO findById(String id);

}
