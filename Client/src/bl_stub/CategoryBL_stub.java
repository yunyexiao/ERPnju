package bl_stub;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import blservice.CategoryBLService;
import vo.CategoryVO;

public class CategoryBL_stub implements CategoryBLService {

	@Override
	public DefaultTreeModel getModel() {
		CategoryVO rootCategory = new CategoryVO("", "000001", "��Ʒ����"),
	               category1 = new CategoryVO("000001", "000002", "����"),
	               category2 = new CategoryVO("000001", "000003", "��ͨ�źŵ�"),
	               category3 = new CategoryVO("000001", "000004", "���������");

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootCategory);
		root.add(new DefaultMutableTreeNode(category1));
		root.add(new DefaultMutableTreeNode(category2));
		root.add(new DefaultMutableTreeNode(category3));
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		return treeModel;
	}

	@Override
	public boolean add(CategoryVO category) {
		System.out.println("category added: " + category.getId());
		return true;
	}

	@Override
	public boolean delete(String id) {
		System.out.println("category deleted: " + id);
		return true;
	}

	@Override
	public boolean change(CategoryVO category) {
		System.out.println("category changed: " + category.getId());
		return true;
	}

	@Override
    public String getNewId() {
        return "001-20171127-002";
    }

	@Override
	public boolean hasContent(String id) {
		return false;
	}

	@Override
	public CategoryVO findById(String id) {
		return null;
	}
	
}
