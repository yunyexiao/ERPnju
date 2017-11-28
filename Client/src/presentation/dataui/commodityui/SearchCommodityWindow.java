package presentation.dataui.commodityui;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import blservice.CommodityBLService;
import presentation.dataui.SearchWindow;

public class SearchCommodityWindow extends SearchWindow{
    
    public SearchCommodityWindow(CommodityBLService commodityBl) {
        super(commodityBl);
        frame.setTitle("��ѯ��Ʒ");
        frame.setSize(600, 200);
        frame.setVisible(true);
    }

    @Override
    protected ButtonGroup initTypeGroup() {
        JRadioButton idRadioButton = new JRadioButton("���������"),
                     nameRadioButton = new JRadioButton("����������"),
                     categoryIdRadioButton = new JRadioButton("����������������"),
                     categoryNameRadioButton = new JRadioButton("������������������");
        idRadioButton.setSelected(true);
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(idRadioButton);
        typeGroup.add(nameRadioButton);
        typeGroup.add(categoryIdRadioButton);
        typeGroup.add(categoryNameRadioButton);
        return typeGroup;
    }
    
}
