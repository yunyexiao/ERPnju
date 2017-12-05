package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import blservice.CategoryBLService;
import blservice.infoservice.GetCategoryInterface;
import dataservice.CategoryDataService;
import ds_stub.CategoryDs_stub;
import po.CategoryPO;
import vo.CategoryVO;

/**
 * 商品分类的BL<br>
 * 与CommodityBL有直接依赖关系
 * @author 恽叶霄*/
public class CategoryBL implements CategoryBLService, GetCategoryInterface{
    
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
        DefaultTreeModel model = new DefaultTreeModel(null);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new CategoryVO("","000000","所有分类"));
        model.setRoot(root);
        try{
            ArrayList<CategoryPO> list = categoryDs.getAllCategory();
            ArrayList<CategoryVO> volist = new ArrayList<CategoryVO>();
            for(CategoryPO category: list) {
            	volist.add(new CategoryVO(category.getFatherId(), category.getId(), category.getName()));
            }
            ArrayList<DefaultMutableTreeNode> nodeList = new ArrayList<DefaultMutableTreeNode>();
            nodeList.add(root);
            for(CategoryVO category: volist) {
            	for(DefaultMutableTreeNode node : nodeList) {
            		CategoryVO c = (CategoryVO)node.getUserObject();
            		if(category.getFatherId().equals(c.getId())){
            			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(category);
            			node.add(newNode);
            			nodeList.add(newNode);
            			break;
            		}
            	}
            }
            return model;
        }catch(RemoteException e){
            e.printStackTrace();
            return model;
        }
    }

    @Override
    public boolean add(CategoryVO category) {
        try{
            return categoryDs.add(category.toPO());
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try{
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

    @Override
    public CategoryVO getCategory(String id){
	    try{
		    CategoryPO cat = categoryDs.findById(id);
		    return new CategoryVO(cat.getFatherId(), cat.getFatherName()
				    , cat.getId(), cat.getName());
	    }catch(RemoteException e){
		    e.printStackTrace();
		    return null;
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
/*  //no use code
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
*/
    public boolean hasContent(String id){
        //if(hasCommodity(id)) return true;
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

	@Override
	public CategoryVO findById(String id) {
		try{
			CategoryPO category = categoryDs.findById(id);
			if (category == null) return null;
            return new CategoryVO(category.getFatherId(),category.getId(),category.getName());
        }catch(RemoteException e){
            return null;
        }
	}

}
