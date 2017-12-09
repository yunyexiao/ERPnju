package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import bl_stub.CustomerBL_stub;
import blservice.billblservice.PurchaseReturnBillBLService;
import blservice.infoservice.GetCommodityInterface;
import dataservice.PurchaseReturnBillDataService;
import ds_stub.PurchaseReturnBillDs_stub;
import po.billpo.BillPO;
import po.billpo.PurchaseReturnBillItemsPO;
import po.billpo.PurchaseReturnBillPO;
import presentation.component.MyTableModel;
import vo.CommodityVO;
import vo.CustomerVO;
import vo.billvo.PurchaseReturnBillVO;


public class PurchaseReturnBillBL implements PurchaseReturnBillBLService {
    
    private PurchaseReturnBillDataService purchaseReturnBillDs;

    public PurchaseReturnBillBL() {
        purchaseReturnBillDs = new PurchaseReturnBillDs_stub();
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

    @Override
    public PurchaseReturnBillVO getBill(String id) {
        try{
            PurchaseReturnBillPO bill = purchaseReturnBillDs.getBillById(id);
            return toVO(bill);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }
    
    private PurchaseReturnBillPO toPO(PurchaseReturnBillVO bill){
        ArrayList<PurchaseReturnBillItemsPO> items = new ArrayList<>();
        for(int i = 0; i < bill.getModel().getRowCount(); i++){
            String[] row = bill.getModel().getValueAtRow(i);
            int num = Integer.parseInt(row[5]);
            double price = Double.parseDouble(row[4]),
                   sum = Double.parseDouble(row[6]);
            items.add(new PurchaseReturnBillItemsPO(
                row[0], row[7], num, price, sum));
        }
        return new PurchaseReturnBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), bill.getRemark(), bill.getSum(), items);
    }

    private PurchaseReturnBillVO toVO(PurchaseReturnBillPO bill){
        String[] columnNames = {"商品编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        ArrayList<PurchaseReturnBillItemsPO> items = bill.getPurchaseReturnBillItems();
        String[][] data = new String[columnNames.length][items.size()];
        for(int i = 0; i < data.length; i++){
            data[i] = toArray(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        // TODO replace the stub with the real one
        CustomerVO customer = new CustomerBL_stub().getCustomer(bill.getSupplierId());
        return new PurchaseReturnBillVO(bill.getDate(), bill.getTime(), bill.getId()
            , bill.getOperator(), bill.getState(), bill.getSupplierId()
            , customer.getName(), model, bill.getRemark(), bill.getSum());
    }
    
    private String[] toArray(PurchaseReturnBillItemsPO item){
        GetCommodityInterface comInfo = new CommodityBL();
        CommodityVO c = comInfo.getCommodity(item.getComId());
        double price = item.getComPrice();
        int num = item.getComQuantity();
        double sum = price * num;
        return new String[]{c.getId(), c.getName(), c.getType()
                , c.getStore(), price + "", num + "", sum + ""
                , item.getRemark()};
    }


}
