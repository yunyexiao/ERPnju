package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import bl_stub.CustomerBL_stub;
import blservice.billblservice.SaleReturnBillBLService;
import dataservice.SalesReturnBillDataService;
import po.billpo.SalesReturnBillItemsPO;
import po.billpo.SalesReturnBillPO;
import presentation.component.MyTableModel;
import vo.CommodityVO;
import vo.billvo.SalesReturnBillVO;


public class SaleReturnBillBL implements SaleReturnBillBLService {
    
    private SalesReturnBillDataService saleReturnBillDs;

    @Override
    public String getNewId() {
        try{
            return saleReturnBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            return saleReturnBillDs.deleteBill(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(SalesReturnBillVO bill) {
        try{
            return saleReturnBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(SalesReturnBillVO bill) {
        try{
            return saleReturnBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SalesReturnBillVO getBill(String id) {
        try{
            return toVO(saleReturnBillDs.getBillById(id));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
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
        ArrayList<SalesReturnBillItemsPO> items = new ArrayList<>();
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
        double originalSum = bill.getOriginalSum(),
               sum = bill.getReturnSum();
        String[] columnName = {"商品编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        String[][] data = new String[columnName.length][];
        ArrayList<SalesReturnBillItemsPO> items = bill.getSalesReturnBillItems();
        for(int i = 0; i < items.size(); i++){
            data[i] = getArray(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnName);
        return new SalesReturnBillVO(date, time, id, operator
            , state, customerId, customerName, model, remark
            , originalSBId, originalSum, sum);
    }

    private SalesReturnBillItemsPO getItem(String[] data){
        String id = data[0], remark = data[7];
        int num = Integer.parseInt(data[5]);
        double price = Double.parseDouble(data[4]),
               sum = Double.parseDouble(data[6]);
        return new SalesReturnBillItemsPO(
            id, remark, num, price, sum);
    }

    private String[] getArray(SalesReturnBillItemsPO item){
        String id = item.getComId();
        CommodityVO commodity = new CommodityBL().getCommodity(id);
        String name = commodity.getName(),
               type = commodity.getType(),
               store = commodity.getStore(),
               remark = item.getRemark();
        double price = item.getComPrice(), 
               sum = item.getComSum();
        int num = item.getComQuantity();
        return new String[]{
                id, name, type, store, price + ""
                , num + "", sum + "", remark
        };
    }

}
