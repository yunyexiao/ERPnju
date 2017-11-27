package presentation.dataui.commodityui;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import blservice.CommodityBLService;
import presentation.dataui.SearchWindow;

public class SearchCommodityWindow extends SearchWindow{
    
    public SearchCommodityWindow(CommodityBLService commodityBl) {
        super(commodityBl);
    }

    @Override
    protected ButtonGroup initTypeGroup() {
        JRadioButton idRadioButton = new JRadioButton("���������"),
                     nameRadioButton = new JRadioButton("����������"),
                     categoryIdRadioButton = new JRadioButton("����������������"),
                     categoryNameRadioButton = new JRadioButton("������������������");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(idRadioButton);
        typeGroup.add(nameRadioButton);
        typeGroup.add(categoryIdRadioButton);
        typeGroup.add(categoryNameRadioButton);
        return typeGroup;
    }
    
}
