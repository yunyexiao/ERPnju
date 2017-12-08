package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import bl_stub.CustomerBL_stub;
import blservice.billblservice.SalesBillBLService;
import dataservice.SalesBillDataService;
import ds_stub.SalesBillDs_stub;
import po.billpo.BillPO;
import po.billpo.SalesBillItemsPO;
import po.billpo.SalesBillPO;
import presentation.component.MyTableModel;
import vo.CommodityVO;
import vo.billvo.BillVO;
import vo.billvo.SalesBillVO;

/**
 * @author 恽叶霄
 */
public class SalesBillBL implements SalesBillBLService {
    
    private SalesBillDataService saleBillDs;
    
    public SalesBillBL(){
        saleBillDs = new SalesBillDs_stub();
    }

    @Override
    public String getNewId() {
        try{
            Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + ""
                        + c.get(Calendar.MONTH) + ""
                        + c.get(Calendar.DATE);
            return "XSD-" + date + "-" + saleBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            // this is the complete id: "XSD-..."
            return saleBillDs.deleteBill(id.split("-")[2]);
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
    
    @Override
    public MyTableModel getFinishedBills(){
        return search("按状态搜索", BillVO.PASS + "");
    }
    
    public MyTableModel getFinishedBills(String customerId){
        try{
            String field = "CONCAT(SBCondition,',',SBCustomerID)";
            String key = BillPO.PASS + "," + customerId;
            ArrayList<SalesBillPO> bills = saleBillDs.getBillsBy(field, key, true);
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
                field = "SBID";
            } // other searching methods not yet considered
            ArrayList<SalesBillPO> bills = saleBillDs.getBillsBy(field, key, true);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel getBillsByDate(String from, String to){
        try{
            ArrayList<SalesBillPO> bills = saleBillDs.getBillByDate(from, to);
            return toModel(bills);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }
    
    private MyTableModel toModel(ArrayList<SalesBillPO> bills){
        String[] columnNames = {"制定时间", "单据编号"};
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
        String[][] data;
        if(items == null){
            data = null;
        }else{
            data = new String[items.size()][];
            for(int i = 0; i < data.length; i++){
                data[i] = getRow(items.get(i));
            }
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
            , cusId, cusName, model, remark, beforeDiscount
            , discount, coupon, sum);
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
