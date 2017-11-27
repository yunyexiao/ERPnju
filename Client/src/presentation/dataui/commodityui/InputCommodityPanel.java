package presentation.dataui.commodityui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import layout.TableLayout;
import vo.CommodityVO;

@SuppressWarnings("serial")
class InputCommodityPanel extends JPanel{
    
    private JTextField[] textFields;

    public InputCommodityPanel(String[] data) {
        super();
        double[] rows = new double[25];
        rows[0] = TableLayout.FILL;
        for(int i = 0; i < 12; i++){
            rows[2 * i + 1] = TableLayout.PREFERRED;
            rows[2 * i + 2] = 10.0;
        }
        rows[rows.length - 1] = TableLayout.FILL;
        double[][] size = {{TableLayout.FILL, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, TableLayout.FILL}, rows};
        this.setLayout(new TableLayout(size));
        
        String[] labelTexts = {"商品编号", "商品名称", "商品型号", "商品所属库存", "商品数量", "商品警戒值"
                , "商品所属分类编号", "商品所属分类名称", "商品进价", "商品售价", "商品最近进价", "商品最近售价"};
        JLabel[] labels = new JLabel[labelTexts.length];
        for(int i = 0; i < labels.length; i++){
            labels[i] = new JLabel(labelTexts[i]);
            this.add(labels[i], "1 " + (2 * i + 1));
        }
        
        textFields = new JTextField[labels.length];
        for(int i = 0; i < textFields.length; i++){
            textFields[i] = new JTextField();
            if(data.length > i && data[i] != null)
                textFields[i].setText(data[i]);
            this.add(textFields[i], "3 " + (2 * i + 1));
        }
        textFields[0].setEditable(false);
   }

    public CommodityVO getCommodityVO(){
        String id = textFields[0].getText(),
               name = textFields[1].getText(),
               type = textFields[2].getText(),
               store = textFields[3].getText(),
               categoryId = textFields[6].getText(),
               categoryName = textFields[7].getText();
        long amount = Long.parseLong(textFields[4].getText()),
             alarm = Long.parseLong(textFields[5].getText());
        double inPrice = Double.parseDouble(textFields[8].getText()),
               salePrice = Double.parseDouble(textFields[9].getText()),
               recentInPrice = Double.parseDouble(textFields[10].getText()),
               recentSalePrice = Double.parseDouble(textFields[11].getText());
        return new CommodityVO(id, name, type, store, categoryId, categoryName
            , amount, alarm, inPrice, salePrice, recentInPrice, recentSalePrice);
    }

}
