package blservice;

import vo.GroupDiscountVO;
import vo.MyTableModel;
import vo.PromotionVO;
import vo.RankPromotionVO;
import vo.SumPromotionVO;

/**
 * �����������ص�ҵ���߼��ӿڶ���
 * 
 * @author �Ҷ��
 */
public interface PromotionBLService {
    /**
     * �����һ���������Եı��
     * @return һ������Ϊ6���ַ���
     */
    String getNewId();
    /** ����һ����ϲ���*/
    boolean add(GroupDiscountVO promotion);
    /** ����һ���ȼ�����*/
    boolean add(RankPromotionVO promotion);
    /** ����һ���ܶ��������*/
    boolean add(SumPromotionVO promotion);
    /** ɾ��һ����������*/
    boolean delete(String id);
    /** �������ڷ�Χ�͵ȼ������ȼ�����*/
    MyTableModel searchRankPromotion(String from, String to, int rank);
    /** ����ĳһ��͵ȼ������ȼ�����*/
    MyTableModel searchRankPromotion(String date, int rank);
    /** �������ڷ�Χ������Ʒ��ϲ���*/
    MyTableModel searchGroupDiscount(String from, String to);
    /** ����ĳһ��������Ʒ��ϲ���*/
    MyTableModel searchGroupDiscount(String date);
    /** �������ڷ�Χ�����ܶ����*/
    MyTableModel searchSumPromotion(String from, String to);
    /** ����ĳһ�������ܶ����*/
    MyTableModel searchSumPromotion(String date);
    /** ɾ��һ����ϲ���*/
    PromotionVO findById(String id);

}
