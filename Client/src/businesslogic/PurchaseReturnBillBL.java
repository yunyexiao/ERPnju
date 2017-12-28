package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.PurchaseReturnBillBLService;
import dataservice.CommodityDataService;
import dataservice.CustomerDataService;
import dataservice.PurchaseReturnBillDataService;
import ds_stub.CommodityDs_stub;
import ds_stub.CustomerDs_stub;
import ds_stub.PurchaseReturnBillDs_stub;
import po.CommodityPO;
import po.CustomerPO;
import po.billpo.BillPO;
import po.billpo.PurchaseBillPO;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.SalesItemsPO;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.PurchaseBillVO;
import vo.billvo.PurchaseReturnBillVO;


public class PurchaseReturnBillBL implements PurchaseReturnBillBLService, BillOperationService, BillExamineService {
    
    private PurchaseReturnBillDataService purchaseReturnBillDs;
    private CustomerDataService customerDs;
    private CommodityDataService commodityDs;

    
    public PurchaseReturnBillBL() {
        purchaseReturnBillDs = Rmi.flag ? Rmi.getRemote(PurchaseReturnBillDataService.class) : new PurchaseReturnBillDs_stub();
        customerDs = Rmi.flag ? Rmi.getRemote(CustomerDataService.class) : new CustomerDs_stub();
        commodityDs = Rmi.flag ? Rmi.getRemote(CommodityDataService.class) : new CommodityDs_stub();
    }

    @Override
    public String getNewId() {
        try{
            Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + ""
                        + c.get(Calendar.MONTH) + ""
                        + c.get(Calendar.DATE);
            return "JHTHD-" + date + "-" + purchaseReturnBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            // passed bills cannot be deleted, only can be offsetted
            PurchaseReturnBillPO bill = purchaseReturnBillDs.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;
            
            int length = id.length();
            return purchaseReturnBillDs.deleteBill(id.substring(length - 5, length));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(PurchaseReturnBillVO bill) {
        try{
            return purchaseReturnBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(PurchaseReturnBillVO bill) {
        try{
            return purchaseReturnBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<PurchaseReturnBillPO> getPurchaseReturnBillPOsByDate(String from, String to){
        try{
            return purchaseReturnBillDs.getBillsByDate(from, to);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean offsetBill(String id){
        try{
            PurchaseReturnBillPO bill = purchaseReturnBillDs.getBillById(id);
            ArrayList<SalesItemsPO> items = new ArrayList<>();
            bill.getPurchaseReturnBillItems().forEach(i -> items.add(new SalesItemsPO(
                i.getComId(), i.getComRemark(), -i.getComQuantity(), i.getComPrice(), -i.getComSum()
            )));
            return purchaseReturnBillDs.saveBill(new PurchaseReturnBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS, 
                bill.getSupplierId(), bill.getRemark(), -bill.getSum(), items
            ));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean copyBill(BillVO bill){
        if(bill instanceof PurchaseReturnBillVO){
            PurchaseReturnBillVO old = (PurchaseReturnBillVO) bill;
            PurchaseReturnBillVO copy = new PurchaseReturnBillVO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
                BillVO.PASS, old.getCustomerId(), old.getModel(), old.getRemark(), old.getSum()
            );
            return saveBill(copy);
        }
        return false;
    }

    private PurchaseReturnBillPO toPO(PurchaseReturnBillVO bill){
        ArrayList<SalesItemsPO> items = new ArrayList<>();
        for(int i = 0; i < bill.getModel().getRowCount(); i++){
            String[] row = bill.getModel().getValueAtRow(i);
            int num = Integer.parseInt(row[5]);
            double price = Double.parseDouble(row[4]),
                   sum = Double.parseDouble(row[6]);
            items.add(new SalesItemsPO(
                row[0], row[7], num, price, sum));
        }
        return new PurchaseReturnBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), bill.getRemark(), bill.getSum(), items);
    }

	@Override
	public boolean examineBill(String billId) {
        try{
        	PurchaseReturnBillPO billPO = purchaseReturnBillDs.getBillById(billId);
            PurchaseReturnBillVO billVO = BillTools.toPurchaseReturnBillVO(purchaseReturnBillDs.getBillById(billId));
            ArrayList<SalesItemsPO> list = billPO.getPurchaseReturnBillItems();
            CustomerPO customerPO = customerDs.findById(billPO.getSupplierId());
            
            //如果应收>=退款总额的话，则更新应收；否则应收直接清零，并增加应付
            
            if ((customerPO.getReceivable() - billPO.getSum()) >= 0) {
            	customerDs.add(new CustomerPO(customerPO.getId(), customerPO.getName(), customerPO.getTelNumber(),
            			customerPO.getAddress(), customerPO.getMail(), customerPO.getCode(), customerPO.getSalesman(),
            			customerPO.getRank(), customerPO.getType(), customerPO.getRecRange(), customerPO.getReceivable()
            			- billPO.getSum(), customerPO.getPayment(), customerPO.getExistFlag()));
            }else {
            	customerDs.add(new CustomerPO(customerPO.getId(), customerPO.getName(), customerPO.getTelNumber(),
            			customerPO.getAddress(), customerPO.getMail(), customerPO.getCode(), customerPO.getSalesman(),
            			customerPO.getRank(), customerPO.getType(), customerPO.getRecRange(), 0, customerPO.getPayment() + billPO.getSum() - customerPO.getReceivable(), customerPO.getExistFlag()));
            }
            for (int i = 0; i < list.size(); i++) {
            	CommodityPO commodityPO = commodityDs.findById(list.get(i).getComId());
            	if (commodityPO.getAmount() >= list.get(i).getComQuantity()) {
            		commodityDs.add(new CommodityPO(commodityPO.getId(), commodityPO.getName(), commodityPO.getType(), 
                    		commodityPO.getStore(), commodityPO.getCategoryId(), commodityPO.getAmount() - list.get(i).getComQuantity(), 
                    		commodityPO.getAlarmNum(), commodityPO.getInPrice(), commodityPO.getSalePrice(), 
                    		commodityPO.getRecentInPrice(), commodityPO.getRecentSalePrice(), commodityPO.getExistFlag()));              
            	}else {
            		billVO.setState(4);
            		billPO.setState(4);
            		saveBill(billVO);
            		return false;
            	}   	
            }
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
        	 PurchaseReturnBillVO billVO = BillTools.toPurchaseReturnBillVO(purchaseReturnBillDs.getBillById(billId));
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
			return BillTools.toPurchaseReturnBillVO(purchaseReturnBillDs.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
