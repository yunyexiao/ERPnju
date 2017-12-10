package businesslogic;

import java.util.ArrayList;
import java.util.TreeMap;

import blservice.InventoryDynamicBLService;
import blservice.infoservice.GetCommodityInterface;
import po.billpo.PurchaseBillItemsPO;
import po.billpo.PurchaseBillPO;
import po.billpo.PurchaseReturnBillItemsPO;
import po.billpo.PurchaseReturnBillPO;
import po.billpo.SalesBillItemsPO;
import po.billpo.SalesBillPO;
import po.billpo.SalesReturnBillItemsPO;
import po.billpo.SalesReturnBillPO;
import presentation.component.MyTableModel;
import vo.CommodityVO;

/**
 * @author 恽叶霄
 */
public class InventoryDynamicBL implements InventoryDynamicBLService {
    
    private String[] columnNames = {"商品编号", "名称", "型号", "库存", "进货数量", "进货总额"
                    , "进货退货数量", "进货退货总额", "销售数量", "销售总额", "销售退货数量", "销售退货总额"
                    , "总入库数量", "总入库金额", "总出库数量", "总出库金额", "数量合计", "金额合计"};

    public InventoryDynamicBL() {}

    @Override
    public MyTableModel getDynamic(String from, String to) {
        ArrayList<PurchaseBillPO> purchaseBills 
            = new PurchaseBillBL().getBillPOsByDate(from, to);
        ArrayList<PurchaseReturnBillPO> purchaseReturnBills 
            = new PurchaseReturnBillBL().getPurchaseReturnBillPOsByDate(from, to);
        ArrayList<SalesBillPO> salesBills
            = new SalesBillBL().getBillPOsByDate(from, to);
        ArrayList<SalesReturnBillPO> salesReturnBills
            = new SalesReturnBillBL().getBillPOsByDate(from, to);

        ItemMap items = new ItemMap();
        purchaseBills.forEach(b-> b.getPurchaseBillItems().forEach(i-> items.addItem(i)));
        purchaseReturnBills.forEach(b-> b.getPurchaseReturnBillItems().forEach(i-> items.addItem(i)));
        salesBills.forEach(b-> b.getSalesBillItems().forEach(i -> items.addItem(i)));
        salesReturnBills.forEach(b-> b.getSalesReturnBillItems().forEach(i-> items.addItem(i)));

        return items.toModel();
    }
    
    @Override
    public MyTableModel getDefault(){
        return new MyTableModel(null, columnNames);
    }
    
    private static class Item {

        private static final GetCommodityInterface COMMODITYBL = new CommodityBL();
        private String id, name, type, store;
        private int purchaseNum, purchaseReturnNum, salesNum, salesReturnNum
                    , importNum, exportNum, totalNum;
        private double purchaseSum, purchaseReturnSum, salesSum, salesReturnSum
                    , importSum, exportSum, totalSum;
        
        public Item(String id){
            this.id = id;
            CommodityVO commodity = COMMODITYBL.getCommodity(id);
            this.name = commodity.getName();
            this.type = commodity.getType();
            this.store = commodity.getStore();
        }
        
        public void addPurchase(int num, double sum){
            purchaseNum += num;
            purchaseSum += sum;
        }
        
        public void addPurchaseReturn(int num, double sum){
            purchaseReturnNum += num;
            purchaseReturnSum += sum;
        }
        
        public void addSales(int num, double sum){
            salesNum += num;
            salesSum += sum;
        }
        
        public void addSalesReturn(int num, double sum){
            salesReturnNum += num;
            salesReturnSum += sum;
        }
        
        private void summary(){
            importNum = purchaseNum + salesReturnNum;
            exportNum = salesNum + purchaseReturnNum;
            importSum = purchaseSum + salesReturnSum;
            exportSum = salesSum + purchaseReturnSum;
            totalNum = importNum - exportNum;
            totalSum = importSum - exportSum;
        }
        
        public String[] toStringArray(){
            summary();
            return new String[]{id, name, type, store, purchaseNum + "", purchaseSum + ""
                    , purchaseReturnNum + "", purchaseReturnSum + "", salesNum + "", salesSum + ""
                    , salesReturnNum + "", salesReturnSum + "", importNum + "", importSum + ""
                    , exportNum + "", exportSum + "", totalNum + "", totalSum + ""};
        }

    }

    private class ItemMap{

        private TreeMap<String, Item> map;
        
        public ItemMap(){
            map = new TreeMap<>();
        }
        
        public void addItem(PurchaseBillItemsPO billItem){
            String id = billItem.getComId();
            if(map.containsKey(id)){
                map.get(id).addPurchase(billItem.getComQuantity(), billItem.getComSum());
            } else {
                Item item = new Item(id);
                item.addPurchase(billItem.getComQuantity(), billItem.getComSum());
                map.put(id, item);
            }
        }
        
        public void addItem(PurchaseReturnBillItemsPO billItem){
            String id = billItem.getComId();
            if(map.containsKey(id)){
                map.get(id).addPurchaseReturn(billItem.getComQuantity(), billItem.getComSum());
            } else {
                Item item = new Item(id);
                item.addPurchaseReturn(billItem.getComQuantity(), billItem.getComSum());
                map.put(id, item);
            }
        }
        
        public void addItem(SalesBillItemsPO billItem){
            String id = billItem.getComId();
            if(map.containsKey(id)){
                map.get(id).addSales(billItem.getComQuantity(), billItem.getComSum());
            } else {
                Item item = new Item(id);
                item.addSales(billItem.getComQuantity(), billItem.getComSum());
                map.put(id, item);
            }
        }
        
        public void addItem(SalesReturnBillItemsPO billItem){
            String id = billItem.getComId();
            if(map.containsKey(id)){
                map.get(id).addSalesReturn(billItem.getComQuantity(), billItem.getComSum());
            } else {
                Item item = new Item(id);
                item.addSalesReturn(billItem.getComQuantity(), billItem.getComSum());
                map.put(id, item);
            }
        }
        
        public MyTableModel toModel(){
            String[][] data = new String[map.size()][];
            for(int i = 0; i < data.length; i++){
                data[i] = map.pollFirstEntry().getValue().toStringArray();
            }
            return new MyTableModel(data, columnNames);
        }

    }

}
