package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import bl_stub.CustomerBL_stub;
import blservice.billblservice.SaleBillBLService;
import dataservice.SalesBillDataService;
import ds_stub.SalesBillDs_stub;
import po.billpo.SalesBillItemsPO;
import po.billpo.SalesBillPO;
import presentation.component.MyTableModel;
import vo.CommodityVO;
import vo.billvo.SalesBillVO;

/**
 * @author 恽叶霄
 */
public class SalesBillBL implements SaleBillBLService {
    
    private SalesBillDataService saleBillDs;
    
    public SalesBillBL(){
        saleBillDs = new SalesBillDs_stub();
    }

    @Override
    public String getNewId() {
        try{
            return saleBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            return saleBillDs.deleteBill(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(SalesBillVO bill) {
        try{
            return saleBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(SalesBillVO bill) {
        try{
            return saleBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SalesBillVO getBill(String id) {
        try{
            return toVO(saleBillDs.getBillById(id));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }
    
    private SalesBillPO toPO(SalesBillVO bill){
        MyTableModel model = bill.getModel();
        ArrayList<SalesBillItemsPO> items = new ArrayList<>();
        for(int i = 0; i < model.getRowCount(); i++){
            String[] row = model.getValueAtRow(i);
            int num = Integer.parseInt(row[5]);
            double price = Double.parseDouble(row[4]),
                   sum = Double.parseDouble(row[6]);
            items.add(new SalesBillItemsPO(row[0], row[7], num, price, sum));
        }
        // TODO promotionId not considered here
        return new SalesBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getCustomerId(), bill.getCustomerName()
            , bill.getRemark(), "", bill.getBeforeDiscount()
            , bill.getDiscount(), bill.getCoupon(), bill.getSum()
            , items);
    }
    
    private SalesBillVO toVO(SalesBillPO bill){
        String[] columnNames = {"编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        ArrayList<SalesBillItemsPO> items = bill.getSalesBillItems();
        String[][] data = new String[items.size()][];
        for(int i = 0; i < data.length; i++){
            data[i] = getRow(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        String date = bill.getDate(),
               time = bill.getTime(),
               id = bill.getId(),
               operatorId = bill.getOperator(),
               cusId = bill.getCustomerId(),
               cusName = new CustomerBL_stub().getCustomer(cusId)
                           .getName(),
               remark = bill.getRemark();
        int state = bill.getState();
        double sum = bill.getAfterDiscount(),
               beforeDiscount = bill.getBeforeDiscount(),
               discount = bill.getDiscount(),
               coupon = bill.getCoupon();
        return new SalesBillVO(date, time, id, operatorId, state
            , cusId, cusName, model, remark, sum, beforeDiscount
            , discount, coupon);
    }
    
    private String[] getRow(SalesBillItemsPO item){
        String id = item.getComId();
        CommodityVO c = new CommodityBL().getCommodity(id);
        String name = c.getName(),
               type = c.getType(),
               store = c.getStore(),
               price = item.getComPrice() + "",
               num = item.getComQuantity() + "",
               sum = item.getComSum() + "",
               remark = item.getComRemark();
        return new String[]{
                id, name, type, store, price, num, sum, remark
        };
    }

}
