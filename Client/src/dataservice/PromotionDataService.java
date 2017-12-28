package dataservice;

import java.rmi.RemoteException;

import po.GroupDiscountPO;
import po.PromotionPO;
import po.RankPromotionPO;
import po.SumPromotionPO;

/**
 * �����йش������Եĳ־û�����
 * 
 * @author �Ҷ��
 */
public interface PromotionDataService {
    
    boolean add(GroupDiscountPO promotion) throws RemoteException;
    
    boolean add(RankPromotionPO promotion) throws RemoteException;
    
    boolean add(SumPromotionPO promotion) throws RemoteException;
    
    boolean delete(String id) throws RemoteException;
    
    /**
     * ����ĳ�����ڶ������еĸõȼ��Ĵ�������<p>
     * ����ָ�������ԵĿ�ʼ�������ڻ����from�������������ڻ����to
     * ��ͬ
     * <p>rank��Ϊ -1 ����ָ���еȼ��Ĳ���
     */
    boolean searchRankPromotion(String from, String to, int rank) throws RemoteException;
    
    /**
     * ������ĳ����Ч�����иõȼ��Ĵ�������<p>
     * ����ָ�������ԵĿ�ʼ�������ڻ����date�������������ڻ����date
     * ��ͬ
     * <p>rank��Ϊ -1 ����ָ���еȼ��Ĳ���
     */
    boolean searchRankPromotion(String date, int rank) throws RemoteException;
    
    boolean searchGroupDiscount(String from, String to) throws RemoteException;
    
    boolean searchGroupDiscount(String date) throws RemoteException;
    
    boolean searchSumPromotion(String from, String to) throws RemoteException;
    
    boolean searchSumPromotion(String date) throws RemoteException;
    
    PromotionPO findById(String id) throws RemoteException;

}
