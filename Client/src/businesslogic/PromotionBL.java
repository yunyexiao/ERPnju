package businesslogic;

import blservice.PromotionBLService;
import dataservice.PromotionDataService;
import ds_stub.PromotionDs_stub;
import rmi.Rmi;
import vo.GroupDiscountVO;
import vo.PromotionVO;
import vo.RankPromotionVO;
import vo.SumPromotionVO;


public class PromotionBL implements PromotionBLService {
    
    private PromotionDataService promotionDs;

    public PromotionBL() {
        promotionDs = Rmi.flag ? Rmi.getRemote(PromotionDataService.class) : new PromotionDs_stub();
    }

    @Override
    public boolean add(GroupDiscountVO promotion) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean add(RankPromotionVO promotion) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean add(SumPromotionVO promotion) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchRankPromotion(String from, String to, int rank) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchRankPromotion(String date, int rank) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchGroupDiscount(String from, String to) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchGroupDiscount(String date) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchSumPromotion(String from, String to) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchSumPromotion(String date) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public PromotionVO findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
