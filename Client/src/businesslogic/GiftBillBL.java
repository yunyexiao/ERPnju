package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import businesslogic.inter.AddLogInterface;
import businesslogic.inter.GiftBillCreation;
import dataservice.GiftBillDataService;
import ds_stub.GiftBillDs_stub;
import po.billpo.BillPO;
import po.billpo.GiftBillPO;
import po.billpo.GiftItem;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import rmi.Rmi;


public class GiftBillBL implements GiftBillCreation, BillExamineService{
    
    private GiftBillDataService giftBillDs;
    private AddLogInterface logger;

    public GiftBillBL() {
        giftBillDs = Rmi.flag ? Rmi.getRemote(GiftBillDataService.class) : new GiftBillDs_stub();
        logger = new LogBL();
    }

    @Override
    public boolean examineBill(String billId) {
        try{
            GiftBillPO bill = giftBillDs.getById(billId);
            if(bill == null){
                return false;
            }
            bill.setState(BillPO.PASS);
            boolean success = giftBillDs.add(bill);
            if(success){
                logger.add("�����Ʒ���͵�", "ͨ����˵���Ʒ���͵���ţ�" + billId);
            }
            return success;
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
                logger.add("�����Ʒ���͵�", "��ţ�" + billId + " δͨ����ˡ�");
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
                logger.add("�½�һ����Ʒ���͵�", "���ݱ�ţ�" + bill.getAllId());
            }
            return success;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

}
