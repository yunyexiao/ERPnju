package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import businesslogic.inter.AddLogInterface;
import businesslogic.inter.GiftBillCreation;
import dataservice.CommodityDataService;
import dataservice.CustomerDataService;
import dataservice.GiftBillDataService;
import ds_stub.CommodityDs_stub;
import ds_stub.CustomerDs_stub;
import ds_stub.GiftBillDs_stub;
import po.CommodityPO;
import po.billpo.BillPO;
import po.billpo.GiftBillPO;
import po.billpo.GiftItem;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import rmi.Rmi;


public class GiftBillBL implements GiftBillCreation, BillExamineService{
    
    private GiftBillDataService giftBillDs;
    private CommodityDataService commodityDs;
    private AddLogInterface logger;

    public GiftBillBL() {
        giftBillDs = Rmi.flag ? Rmi.getRemote(GiftBillDataService.class) : new GiftBillDs_stub();
        commodityDs = Rmi.flag ? Rmi.getRemote(CommodityDataService.class) : new CommodityDs_stub();

        logger = new LogBL();
    }

    @Override
    public boolean examineBill(String billId) {
        try{
            GiftBillPO billPO = giftBillDs.getById(billId);
            if(billPO == null){
                return false;
            }
            ArrayList<GiftItem> list = billPO.getGifts();
            for (int i = 0; i < list.size(); i++) {
            	GiftItem item = list.get(i);
            	CommodityPO commodityPO = commodityDs.findById(item.getComId());
            	if (commodityPO.getAmount() >= item.getNum() ) {
            		commodityDs.add(new CommodityPO(commodityPO.getId(), commodityPO.getName(), commodityPO.getType(), 
            				commodityPO.getStore(), commodityPO.getCategoryId(), commodityPO.getAmount() - item.getNum(), 
                			commodityPO.getAlarmNum(), commodityPO.getInPrice(), commodityPO.getSalePrice(), 
                    		commodityPO.getRecentInPrice(), commodityPO.getRecentSalePrice(), commodityPO.getExistFlag()));              
            	}else {
            		billPO.setState(4);
            		giftBillDs.add(billPO);
            		return false;
            	}
            } 
    		billPO.setState(BillPO.PASS);
    		logger.add("审核商品赠送单", "通过审核的商品赠送单编号：" + billId);
            return giftBillDs.add(billPO);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean notPassBill(String billId) {
        try{
            GiftBillPO bill = giftBillDs.getById(billId);
            if(bill == null){
                return false;
            }
            bill.setState(BillPO.NOTPASS);
            boolean success = giftBillDs.add(bill);
            if(success){
                logger.add("审核商品赠送单", "编号：" + billId + " 未通过审核。");
            }
            return success;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createAndCommit(MyTableModel gifts, String salesBillId, String customerId) {
        try{
            String date = Timetools.getDate();
            String time = Timetools.getTime();
            String id = giftBillDs.getNewId();
            String operatorId = "";
            int state = BillPO.COMMITED;
            ArrayList<GiftItem> items = GiftItemTools.toArrayList(gifts);
            GiftBillPO bill = new GiftBillPO(date, time, id, operatorId, state, items, salesBillId, customerId);
            boolean success = giftBillDs.add(bill);
            if(success){
                logger.add("新建一张商品赠送单", "单据编号：" + bill.getAllId());
            }
            return success;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

}
