package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.ChangeBillBLService;
import businesslogic.inter.AddLogInterface;
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

	private ChangeBillDataService changeBillDS = Rmi.flag ? Rmi.getRemote(ChangeBillDataService.class) : new ChangeBillDs_stub();
	private AddLogInterface addLog = new LogBL();
	private CommodityDataService commodityDS = Rmi.flag ? Rmi.getRemote(CommodityDataService.class) : new CommodityDs_stub();
	private boolean isOver = true;
	
	public ChangeBillBL(boolean isOver) {
		this.isOver = isOver;
	}
	
	private String getBillName() {return isOver?"���絥":"����";}
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
			if (changeBillDS.deleteBill(id, isOver)) {
            	addLog.add("ɾ��"+getBillName(), "ɾ����"+getBillName()+"���ݱ��Ϊ"+id);
            	return true;
            } else return false;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean saveBill(ChangeBillVO bill) {
		return saveBill(bill, "����"+getBillName(), "�����"+getBillName()+"���ݱ��Ϊ"+bill.getAllId());
	}
	private boolean saveBill(ChangeBillVO bill, String operation, String detail) {
		MyTableModel model = bill.getTableModel();
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		for (int i = 0; i < model.getRowCount(); i++) {
			String[] s = model.getValueAtRow(i);
			ChangeItem item = new ChangeItem(s[0], Integer.parseInt(s[2]), Integer.parseInt(s[3]));
			commodityList.add(item);
		}
		try {
			if (changeBillDS.saveBill(new ChangeBillPO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator(), bill.getState(), bill.getFlag(), commodityList))) {
            	addLog.add(operation, detail);
            	return true;
            } else return false;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean offsetBill(String id){
	    try{
            ChangeBillPO bill = changeBillDS.getBillById(id, isOver);
            ArrayList<ChangeItem> items = new ArrayList<>();
            bill.getCommodityList().forEach(i -> items.add(new ChangeItem(
                i.getCommodityId(), i.getChangedValue(), i.getOriginalValue())));
            ChangeBillPO offset = new ChangeBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId().split("-")[2]
                , bill.getOperator(), BillPO.PASS, bill.isOver(), items);
            if (changeBillDS.saveBill(offset)) {
            	addLog.add("���"+getBillName(), "������"+getBillName()+"���ݱ��Ϊ"+bill.getAllId());
            	return true;
            } else return false;
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
	            Timetools.getDate(), Timetools.getTime(), this.getNewId().split("-")[2], old.getOperator(),
	            BillVO.PASS, old.getFlag(), old.getTableModel()
	        );
	        return saveBill(copy, "��岢����"+getBillName(), "��岢���ƺ��µ�"+getBillName()+"���ݱ��Ϊ"+copy.getAllId());
	    }
	    return false;
	}

	@Override
	public boolean examineBill(String billId) {
        try{
            ChangeBillPO billPO = changeBillDS.getBillById(billId, isOver);
            ChangeBillVO billVO = BillTools.toChangeBillVO(billPO);
            ArrayList<ChangeItem> list = billPO.getCommodityList();
        	ArrayList<CommodityPO> commodityList = new ArrayList<CommodityPO>();
        	boolean flag = true;
            if (billPO.isOver()) { //���絥(�ж��߼����³���Ա�������״̬)
                for (int i = 0; i < list.size(); i++) {
                    CommodityPO commodityPO = commodityDS.findById(list.get(i).getCommodityId());
                    commodityPO.setAmount(commodityPO.getAmount() + (list.get(i).getChangedValue() - list.get(i).getOriginalValue()));
                    commodityList.add(commodityPO);
            	}
            }else { //����(�ж��߼����³���Ա�������״̬)
                for (int i = 0; i < list.size(); i++) {
                    CommodityPO commodityPO = commodityDS.findById(list.get(i).getCommodityId());
                    if (!commodityPO.setAmount(commodityPO.getAmount() - (list.get(i).getOriginalValue() - list.get(i).getChangedValue()))) flag = false;
                    commodityList.add(commodityPO);
                }
            }
            if(flag) {
            	for (CommodityPO c : commodityList) commodityDS.update(c);
            	billVO.setState(3);
                return saveBill(billVO, "���"+getBillName(), "ͨ����˵�"+getBillName()+"���ݱ��Ϊ"+billId);
            } else {
            	notPassBill(billId);
            	return false;
            }
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean notPassBill(String billId) {
        try{
            ChangeBillPO billPO = changeBillDS.getBillById(billId, isOver);
            ChangeBillVO billVO = BillTools.toChangeBillVO(billPO);
            billVO.setState(4);
            return saveBill(billVO, "���"+getBillName(), "���ݱ��Ϊ"+billId+"��"+getBillName()+"���δͨ��");
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public BillVO getBillById(String billId) {
		try {
			return BillTools.toChangeBillVO(changeBillDS.getBillById(billId, isOver));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

}
