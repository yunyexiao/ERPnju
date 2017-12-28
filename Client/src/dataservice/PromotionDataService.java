package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

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
    ArrayList<RankPromotionPO> searchRankPromotion(String from, String to, int rank) throws RemoteException;
    
    /**
     * ������ĳ����Ч�����иõȼ��Ĵ�������<p>
     * ����ָ�������ԵĿ�ʼ�������ڻ����date�������������ڻ����date
     * ��ͬ
     * <p>rank��Ϊ -1 ����ָ���еȼ��Ĳ���
     */
    ArrayList<RankPromotionPO> searchRankPromotion(String date, int rank) throws RemoteException;
    
    ArrayList<GroupDiscountPO> searchGroupDiscount(String from, String to) throws RemoteException;
    
    ArrayList<GroupDiscountPO> searchGroupDiscount(String date) throws RemoteException;
    
    ArrayList<SumPromotionPO> searchSumPromotion(String from, String to) throws RemoteException;
    
    ArrayList<SumPromotionPO> searchSumPromotion(String date) throws RemoteException;
    
    PromotionPO findById(String id) throws RemoteException;

}
