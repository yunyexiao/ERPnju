package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.ChangeBillBLService;
import dataservice.ChangeBillDataService;
import dataservice.CommodityDataService;
import ds_stub.ChangeBillDs_stub;
import ds_stub.CommodityDs_stub;
import po.CommodityPO;
import po.billpo.BillPO;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.ChangeBillVO;

public class ChangeBillBL implements ChangeBillBLService, BillOperationService, BillExamineService{

	private ChangeBillDataService changeBillDS;
	private CommodityDataService commodityDS;
	private boolean isOver = true;
	
	public ChangeBillBL(boolean isOver) {
		this.isOver = isOver;
		changeBillDS = Rmi.flag ? Rmi.getRemote(ChangeBillDataService.class) : new ChangeBillDs_stub();
		commodityDS = Rmi.flag ? Rmi.getRemote(CommodityDataService.class) : new CommodityDs_stub();		
	}
	
	@Override
	public String getNewId() {
		try {
			return (isOver?"BYD-":"BSD-")+Timetools.getDate()+"-"+changeBillDS.getNewId(isOver);
		} catch (RemoteException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public boolean deleteBill(String id) {
		try {
			return changeBillDS.deleteBill(id);
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(ChangeBillVO bill) {
		MyTableModel model = bill.getTableModel();
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		for (int i = 0; i < model.getRowCount(); i++) {
			String[] s = model.getValueAtRow(i);
			ChangeItem item = new ChangeItem(s[0], Integer.parseInt(s[2]), Integer.parseInt(s[3]));
			commodityList.add(item);
		}
		try {
			return changeBillDS.saveBill(new ChangeBillPO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator(), bill.getState(), bill.getFlag(), commodityList));
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean offsetBill(String id){
	    try{
            ChangeBillPO bill = changeBillDS.getBillById(id);
            ArrayList<ChangeItem> items = new ArrayList<>();
            bill.getCommodityList().forEach(i -> items.add(new ChangeItem(
                i.getCommodityId(), i.getChangedValue(), i.getOriginalValue())));
            ChangeBillPO offset = new ChangeBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId()
                , bill.getOperator(), BillPO.PASS, bill.getFlag(), items);
            return changeBillDS.saveBill(offset);
	    }catch(RemoteException e){
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean copyBill(BillVO bill){
	    if(bill instanceof ChangeBillVO){
	        ChangeBillVO old = (ChangeBillVO) bill;
	        ChangeBillVO copy = new ChangeBillVO(
	            Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
	            BillVO.PASS, old.getFlag(), old.getTableModel()
	        );
	        return saveBill(copy);
	    }
	    return false;
	}

	@Override
	public boolean examineBill(String billId) {
        try{
            ChangeBillPO billPO = changeBillDS.getBillById(billId);
            ChangeBillVO billVO = BillTools.toChangeBillVO(billPO);
            ArrayList<ChangeItem> list = billPO.getCommodityList();
            if (billPO.getFlag()) { //报溢单(判断逻辑导致程序员进入混乱状态)
                for (int i = 0; i < list.size(); i++) {
                    CommodityPO commodityPO = commodityDS.findById(list.get(i).getCommodityId());
            		commodityDS.add(new CommodityPO(commodityPO.getId(), commodityPO.getName(), commodityPO.getType(), 
            				commodityPO.getStore(), commodityPO.getCategoryId(), commodityPO.getAmount() + (list.get(i).getChangedValue() - list.get(i).getOriginalValue()), 
                			commodityPO.getAlarmNum(), commodityPO.getInPrice(), commodityPO.getSalePrice(), 
                    		commodityPO.getRecentInPrice(), commodityPO.getRecentSalePrice(), commodityPO.getExistFlag()));              
                }
            }else { //报损单(判断逻辑导致程序员进入混乱状态)
                for (int i = 0; i < list.size(); i++) {
                    CommodityPO commodityPO = commodityDS.findById(list.get(i).getCommodityId());
                    if (commodityPO.getAmount() - (list.get(i).getOriginalValue() - list.get(i).getChangedValue()) >= 0) {
                    	commodityDS.add(new CommodityPO(commodityPO.getId(), commodityPO.getName(), commodityPO.getType(), 
                        		commodityPO.getStore(), commodityPO.getCategoryId(), commodityPO.getAmount() - (list.get(i).getOriginalValue() - list.get(i).getChangedValue()), 
                        		commodityPO.getAlarmNum(), commodityPO.getInPrice(), commodityPO.getSalePrice(), 
                        		commodityPO.getRecentInPrice(), commodityPO.getRecentSalePrice(), commodityPO.getExistFlag()));              
                    }else {
                        billPO.setState(4);
                        billVO.setState(4);
                        saveBill(billVO);
                    	return false;
                    }
                }
            }

            billPO.setState(3);
            billVO.setState(3);
            return saveBill(billVO);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean notPassBill(String billId) {
        try{
            ChangeBillPO billPO = changeBillDS.getBillById(billId);
            ChangeBillVO billVO = BillTools.toChangeBillVO(billPO);
            billPO.setState(4);
            billVO.setState(4);
            return saveBill(billVO);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public BillVO getBillById(String billId) {
		try {
			return BillTools.toChangeBillVO(changeBillDS.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

}
