package bl_stub;

import blservice.CommodityBLService;
import presentation.component.MyTableModel;
import vo.CommodityVO;


public class CommodityBL_stub implements CommodityBLService {

    public CommodityBL_stub() {}

    @Override
    public String getNewId() {
        return "002-20171127-002";
    }

    @Override
    public boolean delete(String id) {
        System.out.println("commodity deleted: " + id);
        return true;
    }

    @Override
    public MyTableModel search(String type, String key) {
        String[] attributes = {"���", "����", "�ͺ�", "���", "����������", "������������", "����", "����ֵ", "����", "�ۼ�", "�������", "����ۼ�"};
        String[][] info = {{"002-20171126-00001", "װB���", "TBD", "A", "001-20171126-00001", "����ƾ�", "100", "20", "80", "240", "80", "240"}};
        System.out.println("commodity searched");
        return new MyTableModel(info, attributes); 
    }

    @Override
    public MyTableModel update() {
        String[] attributes = {"���", "����", "�ͺ�", "���", "����������", "������������", "����", "����ֵ", "����", "�ۼ�", "�������", "����ۼ�"};
        String[][] info = {{"002-20171126-00001", "װB���", "TBD", "A", "001-20171126-00001", "����ƾ�", "100", "20", "80", "240", "80", "240"}};
        System.out.println("commodity updated");
        return new MyTableModel(info, attributes); 
    }

    @Override
    public boolean add(CommodityVO commodity) {
        System.out.println("commodity added: " + commodity.getId());
        return true;
    }

    @Override
    public boolean change(CommodityVO commodity) {
        System.out.println("commodity changed: " + commodity.getId());
        return true;
    }

}
