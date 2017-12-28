package businesslogic.inter;

import java.util.ArrayList;

import po.GroupDiscountPO;
import po.PromotionPO;
import po.RankPromotionPO;
import po.SumPromotionPO;
import vo.PromotionVO;

/**
 * �ṩ��<code>IBestPromotion</code>��ʵ���ߵĽӿ�
 * @author �Ҷ��
 */
public interface IPromotionSearch {
    
    ArrayList<GroupDiscountPO> searchGroupDiscountPO(String date);
    
    ArrayList<RankPromotionPO> searchRankPromotionPO(String date, int rank);
    
    ArrayList<SumPromotionPO> searchSumPromotionPO(String date);
    
    PromotionVO toVO(PromotionPO po);

}
