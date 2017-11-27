package presentation.dataui.commodityui;

import java.awt.BorderLayout;

import blservice.CommodityBLService;
import presentation.dataui.FatherWindow;
import vo.CommodityVO;

public class AddCommodityWindow extends FatherWindow {
    
    private InputCommodityPanel centerPanel;
    private CommodityBLService commodityBl;

    public AddCommodityWindow(CommodityBLService commodityBl) {
        super();
        this.commodityBl = commodityBl;
        frame.setTitle("增加商品");
        centerPanel = new InputCommodityPanel(new String[]{commodityBl.getNewId(), null, null, null, null, null});
        frame.add(centerPanel, BorderLayout.CENTER);
        
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    @Override
    protected boolean taskFinished() {
        CommodityVO commodity = centerPanel.getCommodityVO();
        return commodity != null && commodityBl.add(commodity);
    }

    @Override
    protected String getSuccessMsg() {
        return "商品已添加";
    }

}
