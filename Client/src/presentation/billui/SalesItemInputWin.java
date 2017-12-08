package presentation.billui;

import vo.CommodityVO;


public class SalesItemInputWin extends InputCommodityInfoWin {
    
    public SalesItemInputWin(){
        super();
        frame.setVisible(true);
    }

    @Override
    protected boolean isPriceEditable() {
        return true;
    }

    @Override
    protected void setPrice(CommodityVO commodity) {
        fields[4].setText(commodity.getSalePrice() + "");
    }

}
