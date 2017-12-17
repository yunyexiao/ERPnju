package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import bl_stub.CustomerBL_stub;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.SalesReturnBillBLService;
import dataservice.SalesReturnBillDataService;
import ds_stub.SalesReturnBillDs_stub;
import po.billpo.BillPO;
import po.billpo.SalesItemsPO;
import po.billpo.SalesReturnBillPO;
import presentation.component.MyTableModel;
import presentation.tools.Timetools;
import vo.CommodityVO;
import vo.billvo.BillVO;
import vo.billvo.SalesBillVO;
import vo.billvo.SalesReturnBillVO;


public class SalesReturnBillBL implements SalesReturnBillBLService, BillOperationService{
    
    private SalesReturnBillDataService salesReturnBillDs;
    
    public SalesReturnBillBL(){
        salesReturnBillDs = new SalesReturnBillDs_stub();
    }

    @Override
    public String getNewId() {
        try{
            Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + ""
                        + c.get(Calendar.MONTH) + ""
                        + c.get(Calendar.DATE);
            return "XSTHD-" + date + "-" + salesReturnBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            // passed bills cannot be deleted, only can be offsetted
            SalesReturnBillPO bill = salesReturnBillDs.getBillById(id);
            if(bill.getState() == BillPO.PASS) return false;
            
            int length = id.length();
            return salesReturnBillDs.deleteBill(id.substring(length - 5, length));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(SalesReturnBillVO bill) {
        try{
            return salesReturnBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(SalesReturnBillVO bill) {
        try{
            return salesReturnBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SalesReturnBillVO getBill(String id) {
        try{
            return toVO(salesReturnBillDs.getBillById(id));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SalesReturnBillPO> getBillPOsByDate(String from, String to){
        try{
            return salesReturnBillDs.getBillsByDate(from, to);
        }catch(RemoteException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean offsetBill(String id){
        try{
            SalesReturnBillPO bill = salesReturnBillDs.getBillById(id);
            ArrayList<SalesItemsPO> items = new ArrayList<>();
            bill.getSalesReturnBillItems().forEach(i -> items.add(new SalesItemsPO(
                i.getComId(), i.getComRemark(), -i.getComQuantity(), i.getComPrice(), -i.getComSum()
            )));
            return salesReturnBillDs.saveBill(new SalesReturnBillPO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), bill.getOperator(), BillPO.PASS,
                bill.getCustomerId(), bill.getSalesManName(), bill.getRemark(), bill.getOriginalSBId(), 
                -bill.getOriginalSum(), -bill.getReturnSum(), items
            ));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean copyBill(BillVO bill){
        if(bill instanceof SalesReturnBillVO){
            SalesReturnBillVO old = (SalesReturnBillVO) bill;
            SalesReturnBillVO copy = new SalesReturnBillVO(
                Timetools.getDate(), Timetools.getTime(), this.getNewId(), old.getOperator(),
                BillVO.PASS, old.getCustomerId(), old.getCustomerName(), old.getModel(), old.getRemark(), 
                old.getOriginalSBId(), old.getDiscountRate(), old.getOriginalSum(), old.getSum()
            );
            return saveBill(copy);
        }
        return false;
    }

    private SalesReturnBillPO toPO(SalesReturnBillVO bill){
        String date = bill.getDate(),
               time = bill.getTime(),
               id = bill.getId(),
               operatorId = bill.getOperator(),
               customerId = bill.getCustomerId(),
               salesManName = bill.getCustomerName(),
               remark = bill.getRemark(),
               originalSBId = bill.getOriginalSBId();
        int state = bill.getState();
        double originalSum = bill.getOriginalSum(),
               returnSum = bill.getSum();
        ArrayList<SalesItemsPO> items = new ArrayList<>();
        MyTableModel model = bill.getModel();
        for(int i = 0; i < model.getRowCount(); i++){
            items.add(getItem(model.getValueAtRow(i)));
        }
        return new SalesReturnBillPO(date, time, id, operatorId
            , state, customerId, salesManName, remark, originalSBId
            , originalSum, returnSum, items);
    }
    
    private SalesReturnBillVO toVO(SalesReturnBillPO bill){
        String date = bill.getDate(),
               time = bill.getTime(),
               id = bill.getId(),
               operator = bill.getOperator();
        int state = bill.getState();
        String customerId = bill.getCustomerId(),
               customerName = new CustomerBL_stub()
                   .getCustomer(customerId).getName(),
               remark = bill.getRemark(),
               originalSBId = bill.getOriginalSBId();
        double discountRate = getDiscountRate(originalSBId),
               originalSum = bill.getOriginalSum(),
               sum = bill.getReturnSum();
        String[] columnName = {"商品编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        ArrayList<SalesItemsPO> items = bill.getSalesReturnBillItems();
        String[][] data = new String[items.size()][];
        for(int i = 0; i < items.size(); i++){
            data[i] = getArray(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnName);
        return new SalesReturnBillVO(date, time, id, operator
            , state, customerId, customerName, model, remark
            , originalSBId, discountRate, originalSum, sum);
    }

    private SalesItemsPO getItem(String[] data){
        String id = data[0], remark = data[7];
        int num = Integer.parseInt(data[5]);
        double price = Double.parseDouble(data[4]),
               sum = Double.parseDouble(data[6]);
        return new SalesItemsPO(
            id, remark, num, price, sum);
    }

    private String[] getArray(SalesItemsPO item){
        String id = item.getComId();
        CommodityVO commodity = new CommodityBL().getCommodity(id);
        String name = commodity.getName(),
               type = commodity.getType(),
               store = commodity.getStore(),
               remark = item.getComRemark();
        double price = item.getComPrice(), 
               sum = item.getComSum();
        int num = item.getComQuantity();
        return new String[]{
                id, name, type, store, price + ""
                , num + "", sum + "", remark
        };
    }

    private double getDiscountRate(String salesBillId){
        SalesBillVO salesBill = new SalesBillBL().getBill(salesBillId);
        return salesBill.getSum() / salesBill.getBeforeDiscount();
    }

}
