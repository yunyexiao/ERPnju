package businesslogic.inter;

import java.util.ArrayList;

import vo.PromotionVO;

/**
 * �ض�����Ĵ������Ե����Ž�
 * 
 * @author �Ҷ��
 */
public interface IBestPromotion {
    
    ArrayList<PromotionVO> getBest();
    
    double getReduction();

}
