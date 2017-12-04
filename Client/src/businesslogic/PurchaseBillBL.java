package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.PurchaseBillBLService;
import dataservice.PurchaseBillDataService;
import ds_stub.PurchaseBillDs_stub;
import po.billpo.PurchaseBillItemsPO;
import po.billpo.PurchaseBillPO;
import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.PurchaseBillVO;

/**
 * @author 恽叶霄
 */
public class PurchaseBillBL implements PurchaseBillBLService {
    
    private PurchaseBillDataService purchaseBillDs;

    public PurchaseBillBL() {
        purchaseBillDs = new PurchaseBillDs_stub();
    }

    @Override
    public String getNewId() {
        try {
            return purchaseBillDs.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            return purchaseBillDs.deleteBill(id);
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
    public PurchaseBillVO getBill(String id) {
        try{
            PurchaseBillPO bill = purchaseBillDs.getBillById(id);
            return toVO(bill);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }
    
    private PurchaseBillPO toPO(PurchaseBillVO bill){
        ArrayList<PurchaseBillItemsPO> items = new ArrayList<>();
        for(int i = 0; i < bill.getModel().getRowCount(); i++){
            String[] row = bill.getModel().getValueAtRow(i);
            int num = Integer.parseInt(row[5]);
            double price = Double.parseDouble(row[4]);
            items.add(new PurchaseBillItemsPO(row[0], row[1], row[6], row[7], num, price));
        }

        UserVO user = new UserBL().getUser(bill.getOperator());
        return new PurchaseBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), user.getName()
            , bill.getState(), bill.getCustomerId(), bill.getCustomerName()
            , bill.getRemark(), bill.getSum(), items);
    }

    private PurchaseBillVO toVO(PurchaseBillPO bill){
        String[] columnNames = {"商品编号", "名称", "型号", "库存", "单价", "数量", "总价", "备注"};
        ArrayList<PurchaseBillItemsPO> items = bill.getPurchaseBillItems();
        String[][] data = new String[columnNames.length][items.size()];
        for(int i = 0; i < data.length; i++){
            data[i] = toArray(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        return new PurchaseBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperatorId()
            , bill.getState(), bill.getSupplierId(), bill.getSupplierName()
            , model, bill.getRemark(), bill.getSum());
    }
    
    private String[] toArray(PurchaseBillItemsPO item){
        // TODO the type and store to complete
        return new String[]{item.getComId(), item.getComName()
                , "", "", item.getComPrice() + ""
                , item.getComQuantity() + "", item.getComSum()
                , item.getRemark()};
    }

}
