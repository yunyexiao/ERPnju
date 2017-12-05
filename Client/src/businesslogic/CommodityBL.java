package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.CommodityBLService;
import blservice.infoservice.GetCommodityInterface;
import dataservice.CommodityDataService;
import ds_stub.CommodityDs_stub;
import po.CategoryPO;
import po.CommodityPO;
import presentation.component.MyTableModel;
import vo.CommodityVO;

/**
 * ��Ʒģ���BL<br>
 * ��CategoryBL��ֱ��������ϵ
 * @author �Ҷ��
 */
public class CommodityBL implements CommodityBLService, GetCommodityInterface{
    
    private CommodityDataService commodityDs;
    private static final String[] columnNames = {"��Ʒ���", "����", "�ͺ�", "���", "����", "����ֵ"
            , "����������", "������������", "����", "�ۼ�", "�������", "����ۼ�"};

    public CommodityBL() {
        //commodityDs = RemoteHelper.getInstance().getCommodityDataService();
        commodityDs = new CommodityDs_stub();
    }

    @Override
    public String getNewId() {
        try {
            return commodityDs.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        try{
            return commodityDs.delete(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public MyTableModel search(String type, String key) {
        try{
            ArrayList<CommodityPO> list = null;
            if(type.contains("������")){
                list = commodityDs.getCommoditysBy("ComCateID", key, true);
            }else if(type.contains("���")){
                list = commodityDs.getCommoditysBy("ComID", key, true);
            }else if(type.contains("��������")){
                list = searchByCateName(key);
            }else if(type.contains("����")){
                list = commodityDs.getCommoditysBy("ComName", key, true);
            }
            String[][] data = new String[list.size()][columnNames.length];
            for(int i = 0; i < list.size(); i++){
                data[i] = getLine(list.get(i));
            }
            return new MyTableModel(data, columnNames);
        }catch(RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MyTableModel update() {
        try{
            ArrayList<CommodityPO> list = commodityDs.getAllCommodity();
            String[][] data = new String[list.size()][columnNames.length];
            for(int i = 0; i < data.length; i++){
                data[i] = getLine(list.get(i));
            }
            return new MyTableModel(data, columnNames);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean add(CommodityVO commodity) {
        try{
            return commodityDs.add(commodity.toPO());
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean change(CommodityVO commodity) {
        try{
            return commodityDs.update(commodity.toPO());
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<CommodityPO> getAllCommodities() {
        try {
            return commodityDs.getAllCommodity();
        } catch (RemoteException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String[] getLine(CommodityPO c){
        return new String[]{c.getId(), c.getName(), c.getType(), c.getStore(), c.getAmount() + ""
                , c.getAlarmNum() + "", c.getCategoryId(), c.getCategoryName(), c.getInPrice() + ""
                , c.getSalePrice() + "", c.getRecentInPrice() + "", c.getRecentSalePrice() + ""};
    }
    
    private ArrayList<CommodityPO> searchByCateName(String key) throws RemoteException{
        CategoryBL categoryBl = new CategoryBL();
        ArrayList<CategoryPO> categories = categoryBl.searchByName(key);
        ArrayList<CommodityPO> list = new ArrayList<>();
        for(int i = 0; i < categories.size(); i++){
            list.addAll(commodityDs
                .getCommoditysBy("ComCateID", categories.get(i).getId(), true));
        }
        return list;
    }

    @Override
    public CommodityVO getCommodity(String id) {
        try{
            CommodityPO c = commodityDs.findById(id);
            return new CommodityVO(c.getId(), c.getName(), c.getType()
                , c.getStore(), c.getCategoryId(), c.getCategoryName()
                , c.getAmount(), c.getAlarmNum(), c.getInPrice()
                , c.getSalePrice(), c.getRecentInPrice(), c.getRecentSalePrice());
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public boolean hasCommodity(String categoryId) {
		ArrayList<CommodityPO> commodities = getAllCommodities();
        for(CommodityPO commodity: commodities){
            if(commodity.getCategoryId().equals(categoryId))
                return true;
        }
        return false;
	}

}
