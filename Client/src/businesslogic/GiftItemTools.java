package businesslogic;

import java.util.ArrayList;

import blservice.infoservice.GetCommodityInterface;
import po.billpo.GiftItem;
import presentation.component.MyTableModel;
import vo.CommodityVO;

/**
 * ��GiftItem �� MyTableModel �໥ת��
 * 
 * @author �Ҷ��
 */
public final class GiftItemTools {

    private GiftItemTools() {}
    
    public static ArrayList<GiftItem> toArrayList(MyTableModel gifts){
        ArrayList<GiftItem> result = new ArrayList<>();
        int rows = gifts.getRowCount();
        for(int i = 0; i < rows; i++){
            String comId = String.valueOf(gifts.getValueAt(i, 0));
            int num = Integer.parseInt(gifts.getValueAt(i, 4).toString());
            double price = Double.parseDouble(gifts.getValueAt(i, 3).toString());
            result.add(new GiftItem(comId, num, price));
        }
        return result;
    }

    public static MyTableModel toModel(ArrayList<GiftItem> gifts){
        String[] columnNames = {"��Ʒ���", "����", "�ͺ�", "����", "����"};
        GetCommodityInterface commodityInfo = new CommodityBL();
        int size = gifts.size();
        String[][] data = new String[size][];
        for(int i = 0; i < size; i++){
            GiftItem item = gifts.get(i);
            String id = item.getComId();
            CommodityVO com = commodityInfo.getCommodity(id);
            String name = com.getName();
            String type = com.getType();
            data[i] = new String[]{id, name, type, item.getNum() + "", item.getPrice() + ""};
        }
        return new MyTableModel(data, columnNames);
    }

}
