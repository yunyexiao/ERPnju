package presentation.dataui.commodityui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blservice.CommodityBLService;
import presentation.component.MyTableModel;
import presentation.dataui.DataPanel;


public class CommodityDataPanel extends DataPanel {
    
    private CommodityBLService commodityBl;

    public CommodityDataPanel(CommodityBLService commodityBl, ActionListener closeListener) {
        super(commodityBl, closeListener);
        this.commodityBl = commodityBl;
    }

    @Override
    protected ActionListener getAddListener() {
        return new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCommodityWindow(commodityBl);
                updateTable();
            }
            
        };
    }

    @Override
    protected ActionListener getUpdateListener() {
        return new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel model = (MyTableModel)table.getModel();
                new UpdateCommodityWindow(commodityBl, model.getValueAtRow(table.getSelectedRow()));
                updateTable();
            }
            
        };
    }

    @Override
    protected ActionListener getSearchListener() {
        return new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel model = new SearchCommodityWindow(commodityBl).getModel();
                if(model != null) table.setModel(model);
            }
            
        };
    }

}
