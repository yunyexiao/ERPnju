package presentation.billui;

import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import vo.CommodityVO;
import vo.billvo.SalesBillVO;


public class SalesReturnItemInputWin extends InputCommodityInfoWin {
    
    private SalesBillVO bill;
    
    public SalesReturnItemInputWin(SalesBillVO bill){
        super();
        this.bill = bill;
        frame.setVisible(true);
    }

    @Override
    protected boolean isPriceEditable() {
        return false;
    }

    @Override
    protected void setPrice(CommodityVO commodity) {
        MyTableModel model = bill.getModel();
        for(int i = 0; i < model.getRowCount(); i++){
            String[] row = model.getValueAtRow(i);
            if(row[0].equals(commodity.getId())){
                fields[4].setText(row[4]);
                return;
            }
        }
        for(int i = 0; i < 4; i++){
            fields[i].setText("");
        }
        new InfoWindow("源销售单中不存在该商品@_@");
    }

}
