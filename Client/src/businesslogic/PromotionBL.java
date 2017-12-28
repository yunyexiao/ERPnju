package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.PromotionBLService;
import businesslogic.inter.AddLogInterface;
import businesslogic.inter.IPromotionSearch;
import dataservice.PromotionDataService;
import ds_stub.PromotionDs_stub;
import po.GroupDiscountPO;
import po.PromotionPO;
import po.RankPromotionPO;
import po.SumPromotionPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.GroupDiscountVO;
import vo.PromotionVO;
import vo.RankPromotionVO;
import vo.SumPromotionVO;


public class PromotionBL implements PromotionBLService, IPromotionSearch{
    
    private PromotionDataService promotionDs;
    private AddLogInterface logger;

    public PromotionBL() {
        promotionDs = Rmi.flag ? Rmi.getRemote(PromotionDataService.class) : new PromotionDs_stub();
        logger = new LogBL();
    }

    @Override
    public boolean add(GroupDiscountVO promotion) {
        try{
            boolean success = promotionDs.add(promotion.toPO());
            if(success){
                logger.add("���һ����ϴ�������", "��ţ�" + promotion.getId());
            }
            return success;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(RankPromotionVO promotion) {
        try{
            boolean success = promotionDs.add(promotion.toPO());
            if(success){
                logger.add("���һ���ȼ���������", "��ţ�" + promotion.getId());
            }
            return success;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(SumPromotionVO promotion) {
        try{
            boolean success = promotionDs.add(promotion.toPO());
            if(success){
                logger.add("���һ���ܶ��������", "��ţ�" + promotion.getId());
            }
            return success;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try{
            boolean success = promotionDs.delete(id);
            if(success){
                logger.add("ɾ��һ����������", "��ţ�" + id);
            }
            return success;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PromotionVO findById(String id) {
        try{
            PromotionPO po = promotionDs.findById(id);
            return toVO(po);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel searchRankPromotion(String from, String to, int rank) {
        try{
            ArrayList<RankPromotionPO> promotions = promotionDs.searchRankPromotion(from, to, rank);
            return toModel(promotions);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel searchRankPromotion(String date, int rank) {
        try{
            return toModel(promotionDs.searchRankPromotion(date, rank));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel searchGroupDiscount(String from, String to) {
        try{
            return toModel(promotionDs.searchGroupDiscount(from, to));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel searchGroupDiscount(String date) {
        try{
            return toModel(promotionDs.searchGroupDiscount(date));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel searchSumPromotion(String from, String to) {
        try{
            return toModel(promotionDs.searchSumPromotion(from, to));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel searchSumPromotion(String date) {
        try{
            return toModel(promotionDs.searchSumPromotion(date));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<GroupDiscountPO> searchGroupDiscountPO(String date) {
        try{
            return promotionDs.searchGroupDiscount(date);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<RankPromotionPO> searchRankPromotionPO(String date, int rank) {
        try{
            return promotionDs.searchRankPromotion(date, rank);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<SumPromotionPO> searchSumPromotionPO(String date) {
        try{
            return promotionDs.searchSumPromotion(date);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public PromotionVO toVO(PromotionPO po){
        if(po instanceof GroupDiscountPO){
            GroupDiscountPO gpo = (GroupDiscountPO)po;
            return new GroupDiscountVO(gpo.getId(), gpo.getFromDate(), 
                gpo.getToDate(), gpo.getReduction(), gpo.getGroup());
        }else if(po instanceof RankPromotionPO){
            RankPromotionPO rpo = (RankPromotionPO)po;
            return new RankPromotionVO(rpo.getId(), rpo.getFromDate(), rpo.getToDate(), 
                rpo.getReduction(), rpo.getCoupon(), rpo.getRank(), GiftItemTools.toModel(rpo.getGifts()));
        }else{
            SumPromotionPO spo = (SumPromotionPO)po;
            return new SumPromotionVO(spo.getId(), spo.getFromDate(), spo.getToDate(), 
                spo.getStartPrice(), spo.getEndPrice(), spo.getCoupon(), GiftItemTools.toModel(spo.getGifts()));
        }
    }
   
    private MyTableModel toModel(ArrayList<? extends PromotionPO> promotions){
        String[] columnNames = {"���", "��Ч����", "ʧЧ����"};
        int size = promotions.size();
        String[][] data = new String[size][];
        for(int i = 0; i < size; i++){
            PromotionPO po = promotions.get(i);
            data[i] = new String[]{po.getId(), po.getFromDate(), po.getToDate()};
        }
        return new MyTableModel(data, columnNames);
    }

}
