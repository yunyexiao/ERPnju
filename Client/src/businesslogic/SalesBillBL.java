package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.SalesBillBLService;
import blservice.infoservice.GetCustomerInterface;
import dataservice.CommodityDataService;
import dataservice.CustomerDataService;
import dataservice.SalesBillDataService;
import ds_stub.CommodityDs_stub;
import ds_stub.CustomerDs_stub;
import ds_stub.SalesBillDs_stub;
import po.CommodityPO;
import po.CustomerPO;
import po.billpo.BillPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesItemsPO;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.SalesBillVO;

/**
 * @author ã¢Ò¶Ïö
 */
public class SalesBillBL implements SalesBillBLService, BillOperationService, BillExamineService {
    
    private SalesBillDataService salesBillDs;
    private CustomerDataService customerDs;
    private CommodityDataService commodityDs;
    private GetCustomerInterface customerInfo = new CustomerBL();
    
    public SalesBillBL(){
        salesBillDs = Rmi.flag ? Rmi.getRemote(SalesBillDataService.class) : new SalesBillDs_stub();
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
            return "XSD-" + date + "-" + salesBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            // passed bills cannot be deleted, only can be offsetted
            SalesBillPO bill = salesBillDs.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;

            int length = id.length();
            return salesBillDs.deleteBill(id.substring(length - 5, length));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(SalesBillVO bill) {
        try{
            return salesBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(SalesBillVO bill) {
        try{
            return salesBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public MyTableModel getFinishedBills(){
        return search("°´×´Ì¬ËÑË÷", BillVO.PASS + "");
    }
    
    public MyTableModel getFinishedBills(String customerId){
        try{
            String field = "CONCAT(SBCondition,',',SBCustomerID)";
            String key = BillPO.PASS + "," + customerId;
            ArrayList<SalesBillPO> bills = salesBillDs.getBillsBy(field, key, true);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel search(String type, String key) {
        try{
            String field = null;
            if("°´±àºÅËÑË÷".equals(type)){
                field = "SBID";
            } // other searching methods not yet considered
            ArrayList<SalesBillPO> bills = salesBillDs.getBillsBy(field, key, true);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel getBillsByDate(String from, String to){
        try{
            ArrayList<SalesBillPO> bills = salesBillDs.getBillByDate(from, to);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<SalesBillPO> getBillPOsByDate(String from, String to){
        try{
            return salesBillDs.getBillByDate(from, to);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean offsetBill(String id){
        try{
            SalesBillPO bill = salesBillDs.getBillById(id);
            ArrayList<SalesItemsPO> items = new ArrayList<>();
            bill.getSalesBillItems().forEach(i -> items.add(new SalesItemsPO(
                i.getComId(), i.getComRemark(), -i.getComQuantity(), i.getComPrice(), -i.getComSum()
            )));
            return salesBillDs.saveBill(new SalesBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS,
                bill.getCustomerId(), bill.getSalesManName(), bill.getRemark(), bill.getPromotionId(),
                -bill.getBeforeDiscount(), -bill.getDiscount(), -bill.getCoupon(), -bill.getAfterDiscount(), items
            ));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean copyBill(BillVO bill){
        if(bill instanceof SalesBillVO){
            SalesBillVO old = (SalesBillVO) bill;
            SalesBillVO copy = new SalesBillVO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
                BillVO.PASS, old.getCustomerId(), old.getModel(),
                old.getRemark(), old.getBeforeDiscount(), old.getDiscount(), old.getCoupon(), old.getSum()
            );
            return saveBill(copy);
        }
        return false;
    }

    private MyTableModel toModel(ArrayList<SalesBillPO> bills){
        String[] columnNames = {"ÖÆ¶¨Ê±¼ä", "µ¥¾Ý±àºÅ"};
        String[][] data = new String[bills.size()][columnNames.length];
        for(int i = 0; i < bills.size(); i++){
            SalesBillPO salesBill = bills.get(i);
            data[i][0] = salesBill.getDate() + " " + salesBill.getTime();
            data[i][1] = "XSD-" + salesBill.getDate() + "-" + salesBill.getId();
        }
        return new MyTableModel(data, columnNames);
    }

    private SalesBillPO toPO(SalesBillVO bill){
        MyTableModel model = bill.getModel();
        ArrayList<SalesItemsPO> items = new ArrayList<>();
        for(int i = 0; i < model.getRowCount(); i++){
            String[] row = model.getValueAtRow(i);
            int num = Integer.parseInt(row[5]);
            double price = Double.parseDouble(row[4]),
                   sum = Double.parseDouble(row[6]);
            items.add(new SalesItemsPO(row[0], row[7], num, price, sum));
        }
        // TODO promotionId not considered here
        return new SalesBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), customerInfo.getCustomer(bill.getCustomerId()).getSalesman(), bill.getRemark(), "", bill.getBeforeDiscount()
            , bill.getDiscount(), bill.getCoupon(), bill.getSum()
            , items);
    }
    
	@Override
	public boolean examineBill(String billId) {
        try{
        	SalesBillPO billPO = salesBillDs.getBillById(billId);
            SalesBillVO billVO = BillTools.toSalesBillVO(salesBillDs.getBillById(billId));
            ArrayList<SalesItemsPO> list = billPO.getSalesBillItems();
            
            CustomerPO customerPO = customerDs.findById(billPO.getCustomerId());
            customerDs.add(new CustomerPO(customerPO.getId(), customerPO.getName(), customerPO.getTelNumber(),
        			customerPO.getAddress(), customerPO.getMail(), customerPO.getCode(), customerPO.getSalesman(),
        			customerPO.getRank(), customerPO.getType(), customerPO.getRecRange(), customerPO.getReceivable(),
        			customerPO.getPayment() + billPO.getAfterDiscount(), customerPO.getExistFlag()));
            
            for (int i = 0; i < list.size(); i++) {
            	CommodityPO commodityPO = commodityDs.findById(list.get(i).getComId());
            	if (commodityPO.getAmount() >= list.get(i).getComQuantity()) {
            		commodityDs.add(new CommodityPO(commodityPO.getId(), commodityPO.getName(), commodityPO.getType(), 
                    		commodityPO.getStore(), commodityPO.getCategoryId(), commodityPO.getAmount() - list.get(i).getComQuantity(), 
                    		commodityPO.getAlarmNum(), commodityPO.getInPrice(), commodityPO.getSalePrice(), 
                    		commodityPO.getRecentInPrice(), commodityPO.getRecentSalePrice(), commodityPO.getExistFlag()));              
            	}else {
            		billPO.setState(4);
                    billVO.setState(4);
                    salesBillDs.saveBill(billPO);
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
        	SalesBillVO billVO = BillTools.toSalesBillVO(salesBillDs.getBillById(billId));
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
			return BillTools.toSalesBillVO(salesBillDs.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
