package presentation.dataui.commodityui;

import java.awt.BorderLayout;

import blservice.CommodityBLService;
import presentation.dataui.FatherWindow;
import vo.CommodityVO;

public class UpdateCommodityWindow extends FatherWindow {
    
    private InputCommodityPanel centerPanel;
    private CommodityBLService commodityBl;

    public UpdateCommodityWindow(CommodityBLService commodityBl, String[] rowData) {
        super();
        this.commodityBl = commodityBl;
        frame.setTitle("�޸���Ʒ");
        centerPanel = new InputCommodityPanel(rowData);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    protected boolean taskFinished() {
        CommodityVO commodity = centerPanel.getCommodityVO();
        return commodity != null && commodityBl.change(commodity);
    }

    @Override
    protected String getSuccessMsg() {
        return "��Ʒ��Ϣ�Ѹ���";
    }

}
