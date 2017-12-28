package businesslogic;

import java.util.ArrayList;

import po.billpo.GiftItem;
import presentation.component.MyTableModel;

/**
 * 将GiftItem 与 MyTableModel 相互转换
 * 
 * @author 恽叶霄
 */
public final class GiftItemTools {

    private GiftItemTools() {}
    
    public static ArrayList<GiftItem> toArrayList(MyTableModel gifts){
        ArrayList<GiftItem> result = new ArrayList<>();
        int rows = gifts.getRowCount();
        for(int i = 0; i < rows; i++){
            String comId = String.valueOf(gifts.getValueAt(i, 0));
            int num = Integer.parseInt(gifts.getValueAt(i, 1).toString());
            double price = Double.parseDouble(gifts.getValueAt(i, 2).toString());
            result.add(new GiftItem(comId, num, price));
        }
        return result;
    }

    public static MyTableModel toModel(ArrayList<GiftItem> gifts){
        String[] columnNames = {"商品编号", "数量", "单价"};
        int size = gifts.size();
        String[][] data = new String[size][];
        for(int i = 0; i < size; i++){
            GiftItem item = gifts.get(i);
            data[i] = new String[]{item.getComId(), item.getNum() + "", item.getPrice() + ""};
        }
        return new MyTableModel(data, columnNames);
    }

}
