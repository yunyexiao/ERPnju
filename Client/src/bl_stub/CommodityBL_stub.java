package bl_stub;

import blservice.CommodityBLService;
import blservice.infoservice.GetCommodityInterface;
import vo.CommodityVO;
import vo.MyTableModel;


public class CommodityBL_stub implements CommodityBLService, GetCommodityInterface {

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
        String[][] info = {{"000001", "װB���", "TBD", "A", "000001", "����ƾ�", "100", "20", "80", "240", "80", "240"}};
        System.out.println("commodity searched");
        return new MyTableModel(info, attributes); 
    }

    @Override
    public MyTableModel update() {
        String[] attributes = {"���", "����", "�ͺ�", "���", "����������", "������������", "����", "����ֵ", "����", "�ۼ�", "�������", "����ۼ�"};
        String[][] info = {{"000001", "װB���", "TBD", "A", "000001", "����ƾ�", "100", "20", "80", "240", "80", "240"}};
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

	@Override
	public CommodityVO getCommodity(String id) {
		return new CommodityVO("000001", "װB���", "TBD", "A", "001-20171126-00001", 100, 20, 80, 240, 80, 240);
	}

	@Override
	public boolean hasCommodity(String categoryId) {
		return false;
	}

    @Override
    public MyTableModel getCategoryCommodities(String categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

}
