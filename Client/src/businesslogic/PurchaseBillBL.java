package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.PurchaseBillBLService;
import dataservice.PurchaseBillDataService;
import ds_stub.PurchaseBillDs_stub;
import po.billpo.BillPO;
import po.billpo.PurchaseBillPO;
import po.billpo.SalesItemsPO;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import rmi.Rmi;
import vo.billvo.BillVO;
import vo.billvo.PurchaseBillVO;

/**
 * @author 恽叶霄
 */
public class PurchaseBillBL implements PurchaseBillBLService, BillOperationService, BillExamineService{
    
    private PurchaseBillDataService purchaseBillDs;

    public PurchaseBillBL() {
        purchaseBillDs = Rmi.flag ? Rmi.getRemote(PurchaseBillDataService.class) : new PurchaseBillDs_stub();
    }

    @Override
    public String getNewId() {
        try {
            Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + ""
                        + c.get(Calendar.MONTH) + ""
                        + c.get(Calendar.DATE);
            return "JHD-" + date + "-" + purchaseBillDs.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            // passed bills cannot be deleted, only can be offsetted
            PurchaseBillPO bill = purchaseBillDs.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;

            int length = id.length();
            return purchaseBillDs.deleteBill(id.substring(length - 5, length));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(PurchaseBillVO bill) {
        try{
            return purchaseBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(PurchaseBillVO bill) {
        try{
            return purchaseBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public MyTableModel getFinishedBills(String customerId) {
        try{
            String field = "CONCAT(PBCondition,',',PBCustomerID)";
            String key = BillPO.PASS + "," + customerId;
            ArrayList<PurchaseBillPO> bills = purchaseBillDs.getBillsBy(field, key, true);
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
            if("按编号搜索".equals(type)){
                field = "PBID";
            }
            ArrayList<PurchaseBillPO> bills = purchaseBillDs.getBillsBy(field, key, true);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel getBillByDate(String from, String to) {
        try{
            ArrayList<PurchaseBillPO> bills = purchaseBillDs.getBillsByDate(from, to);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean offsetBill(String id){
        try{
            PurchaseBillPO bill = purchaseBillDs.getBillById(id);
            ArrayList<SalesItemsPO> items = new ArrayList<>();
            bill.getPurchaseBillItems().forEach(i -> items.add(new SalesItemsPO(
                i.getComId(), i.getComRemark(), -i.getComQuantity(), i.getComPrice(), -i.getComSum()
            )));
            return purchaseBillDs.saveBill(new PurchaseBillPO(
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
        if(bill instanceof PurchaseBillVO){
            PurchaseBillVO old = (PurchaseBillVO) bill;
            PurchaseBillVO copy = new PurchaseBillVO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
                BillVO.PASS, old.getCustomerId(), old.getModel(), old.getRemark(), old.getSum()
            );
            return saveBill(copy);
        }
        return false;
    }

    @Override
    public boolean examineBill(String id){
        try{
            PurchaseBillVO billVO = BillTools.toPurchaseBillVO(purchaseBillDs.getBillById(id));
            billVO.setState(3);
            return saveBill(billVO);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean notPassBill(String id){
        try{
            PurchaseBillVO billVO = BillTools.toPurchaseBillVO(purchaseBillDs.getBillById(id));
            billVO.setState(4);
            return saveBill(billVO);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * This method is not intended for personal use
     */
    public ArrayList<PurchaseBillPO> getBillPOsByDate(String from, String to){
        try{
            return purchaseBillDs.getBillsByDate(from, to);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private PurchaseBillPO toPO(PurchaseBillVO bill){
        ArrayList<SalesItemsPO> items = new ArrayList<>();
        for(int i = 0; i < bill.getModel().getRowCount(); i++){
            String[] row = bill.getModel().getValueAtRow(i);
            int num = Integer.parseInt(row[5]);
            double price = Double.parseDouble(row[4]),
                   sum = Double.parseDouble(row[6]);
            items.add(new SalesItemsPO(
                row[0], row[7], num, price, sum));
        }
        return new PurchaseBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), bill.getRemark()
            , bill.getSum(), items);
    }

    private MyTableModel toModel(ArrayList<PurchaseBillPO> bills){
        String[] columnNames = {"制定时间", "单据编号"};
        String[][] data = new String[bills.size()][columnNames.length];
        for(int i = 0; i < data.length; i++){
            PurchaseBillPO purchaseBill = bills.get(i);
            data[i][0] = purchaseBill.getDate() + " " + purchaseBill.getTime();
            data[i][1] = "JHD-" + purchaseBill.getDate() + "-" + purchaseBill.getId(); 
        }
        return new MyTableModel(data, columnNames);
    }

	@Override
	public BillVO getBillById(String billId) {
		try {
			return BillTools.toPurchaseBillVO(purchaseBillDs.getBillById(billId));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}


}
