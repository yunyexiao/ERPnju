package bl_stub;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import blservice.CategoryBLService;
import vo.CategoryVO;

public class CategoryBL_stub implements CategoryBLService {

	@Override
	public DefaultTreeModel getModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("��Ʒ���");
        root.add(new DefaultMutableTreeNode("����"));
        root.add(new DefaultMutableTreeNode("��ͨ�źŵ�"));
        root.add(new DefaultMutableTreeNode("���������"));
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
		return treeModel;
	}

	@Override
	public boolean add(CategoryVO category) {
		return true;
	}

	@Override
	public boolean delete(CategoryVO category) {
		return true;
	}

	@Override
	public boolean change(CategoryVO category) {
		return true;
	}

}
