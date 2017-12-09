package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import blservice.CategoryBLService;
import blservice.infoservice.GetCategoryInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.CategoryDataService;
import ds_stub.CategoryDs_stub;
import po.CategoryPO;
import rmi.Rmi;
import vo.CategoryVO;
import vo.UserVO;

/**
 * ��Ʒ�����BL<br>
 * ��CommodityBL��ֱ��������ϵ
 * @author �Ҷ��*/
public class CategoryBL implements CategoryBLService, GetCategoryInterface {
    
    private CategoryDataService categoryDs = Rmi.flag ? Rmi.getRemote(CategoryDataService.class) : new CategoryDs_stub();
    private AddLogInterface addLog;

    /**
     * ��ʹ��GetCategoryInterfaceʱ�Ĺ��췽��
     */
    public CategoryBL() {}
    
    public CategoryBL(UserVO user) {
        //categoryDs = RemoteHelper.getInstance().getCategoryDataService();
        addLog = new LogBL(user);
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
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new CategoryVO("","000000","���з���"));
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
            if (categoryDs.add(category.toPO())) {
            	addLog.add("������Ʒ����", "������Ʒ�����ţ�"+ category.getId() + "  ���ƣ�" + category.getName());
            	return true;
            }
            return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try{
            if (categoryDs.delete(id)) {
            	addLog.add("ɾ����Ʒ����", "ɾ���ķ���Id:"+id);
            	return true;
            }
            return false;
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean change(CategoryVO category) {
        try{
            if (categoryDs.update(category.toPO())) {
            	addLog.add("�޸���Ʒ����", "���޸ĵ���Ʒ���ࣺ"+category.getId());
            	return true;
            }
            return false;
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

	@Override
	public CategoryVO getCategory(String id) {
		try {
			CategoryPO category = categoryDs.findById(id);
			if (category == null) return null;
            return new CategoryVO(category.getFatherId(),category.getId(),category.getName());
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

}