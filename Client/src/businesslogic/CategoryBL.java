package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import blservice.CategoryBLService;
import dataservice.CategoryDataService;
import ds_stub.CategoryDs_stub;
import po.CategoryPO;
import po.CommodityPO;
import vo.CategoryVO;

/**
 * 商品分类的BL<br>
 * 与CommodityBL有直接依赖关系
 * @author 恽叶霄*/
public class CategoryBL implements CategoryBLService {
    
    private CategoryDataService categoryDs;

    public CategoryBL() {
        //categoryDs = RemoteHelper.getInstance().getCategoryDataService();
        categoryDs = new CategoryDs_stub();
    }

    @Override
    public String getNewId() {
        try{
            return categoryDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DefaultTreeModel getModel() {
        try{
            ArrayList<CategoryPO> list = categoryDs.getAllCategory();
            DefaultTreeModel model = new DefaultTreeModel(null);
            String fatherId = "";
            DefaultMutableTreeNode root = null;
            for(CategoryPO category: list)if(category.getFatherId().equals(fatherId)){
                root = new DefaultMutableTreeNode(new CategoryVO(fatherId
                    , category.getFatherName(), category.getId(), category.getName()));
                model.setRoot(root);
                break;
            }
            list.remove(root.getUserObject());
            addChildren(list, root);
            return model;
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean add(CategoryVO category) {
        try{
            if(hasCommodity(category.getId())) return false;
            return categoryDs.add(category.toPO());
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try{
            if(hasContent(id)) return false;
            return categoryDs.delete(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean change(CategoryVO category) {
        try{
            return categoryDs.update(category.toPO());
        }catch(RemoteException e){
            return false;
        }
    }
    
    public ArrayList<CategoryPO> searchByName(String name){
        try {
            return categoryDs.getCategorysBy("CateName", name, true);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addChildren(ArrayList<CategoryPO> list, DefaultMutableTreeNode fatherNode){
        String fatherId = ((CategoryVO)fatherNode.getUserObject()).getId();
        ArrayList<CategoryPO> children = new ArrayList<>();
        for(CategoryPO category: list)if(category.getFatherId().equals(fatherId)){
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new CategoryVO(fatherId
                , category.getFatherName(), category.getId(), category.getName()));
            children.add(category);
            fatherNode.add(node);
        }
        for(CategoryPO category: children)list.remove(category);
        children = null;
        int count = fatherNode.getChildCount();
        for(int i = 0; i < count; i++){
            addChildren(list, (DefaultMutableTreeNode)fatherNode.getChildAt(i));
        }
    }

    private boolean hasCommodity(String categoryId){
        ArrayList<CommodityPO> commodities = new CommodityBL().getAllCommodities();
        for(CommodityPO commodity: commodities){
            if(commodity.getCategoryId().equals(categoryId))
                return true;
        }
        return false;
    }

    private boolean hasContent(String id){
        if(hasCommodity(id)) return true;
        try {
            ArrayList<CategoryPO> categories = categoryDs.getAllCategory();
            for(CategoryPO category: categories){
                if(category.getFatherId().equals(id))
                    return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

}
