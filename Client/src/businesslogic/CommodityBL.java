package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.CommodityBLService;
import blservice.infoservice.GetCategoryInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.CommodityDataService;
import ds_stub.CommodityDs_stub;
import po.CategoryPO;
import po.CommodityPO;
import presentation.component.MyTableModel;
import vo.CommodityVO;
import vo.UserVO;

/**
 * 商品模块的BL<br>
 * 与CategoryBL有直接依赖关系
 * @author 恽叶霄
 */
public class CommodityBL implements CommodityBLService{
    
    private CommodityDataService commodityDs;
    private GetCategoryInterface categoryInfo;
    private AddLogInterface addLog;
    private static final String[] columnNames = {"商品编号", "名称", "型号", "库存", "数量", "警戒值"
            , "所属分类编号", "所属分类名称", "进价", "售价", "最近进价", "最近售价"};

    public CommodityBL(UserVO user) {
        //commodityDs = Rmi.getRemote(CommodityDataService.class);
        commodityDs = new CommodityDs_stub();
        categoryInfo = new CategoryBL();
        addLog = new LogBL(user);
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
            if (commodityDs.delete(id)) {
            	addLog.add("删除商品", "删除的商品ID："+id);
            	return true;
            }
            return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public MyTableModel search(String type, String key) {
        try{
            ArrayList<CommodityPO> list = null;
            if(type.contains("分类编号")){
                list = commodityDs.getCommoditysBy("ComCateID", key, true);
            }else if(type.contains("编号")){
                list = commodityDs.getCommoditysBy("ComID", key, true);
            }else if(type.contains("分类名称")){
                list = searchByCateName(key);
            }else if(type.contains("名称")){
                list = commodityDs.getCommoditysBy("ComName", key, true);
            }
            String[][] data = new String[list.size()][columnNames.length];
            for(int i = 0; i < list.size(); i++){
                data[i] = getLine(list.get(i));
            }
            addLog.add("搜索商品", "搜索方式："+type+"  搜索关键词："+key);
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
            if (commodityDs.add(commodity.toPO())) {
            	addLog.add("新增商品", "新增商品的ID："+commodity.getId()+"商品名称："+commodity.getName());
            	return true;
            }
            return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean change(CommodityVO commodity) {
        try{
            if (commodityDs.update(commodity.toPO())) {
            	addLog.add("修改商品", "被修改的商品编号："+commodity.getId());
            	return true;
            }
            return false;
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
                , c.getAlarmNum() + "", c.getCategoryId(), categoryInfo.getCategory(c.getCategoryId()).getName(), c.getInPrice() + ""
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
	public boolean hasCommodity(String categoryId) {
		ArrayList<CommodityPO> commodities = getAllCommodities();
        for(CommodityPO commodity: commodities){
            if(commodity.getCategoryId().equals(categoryId))
                return true;
        }
        return false;
	}

}
