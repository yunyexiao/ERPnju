package ds_stub;

import java.rmi.RemoteException;

import dataservice.PromotionDataService;
import po.GroupDiscountPO;
import po.PromotionPO;
import po.RankPromotionPO;
import po.SumPromotionPO;


public class PromotionDs_stub implements PromotionDataService {

    public PromotionDs_stub() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean add(GroupDiscountPO promotion) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean add(RankPromotionPO promotion) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean add(SumPromotionPO promotion) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(String id) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchRankPromotion(String from, String to, int rank) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchRankPromotion(String date, int rank) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchGroupDiscount(String from, String to) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchGroupDiscount(String date) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchSumPromotion(String from, String to) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchSumPromotion(String date) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public PromotionPO findById(String id) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}
