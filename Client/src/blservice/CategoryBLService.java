package blservice;

import javax.swing.tree.DefaultTreeModel;

import vo.CategoryVO;
/**
 * 商品分类管理业务逻辑接口
 * @author 钱美缘
 *
 */
public interface CategoryBLService {

	/**
	 * 根据数据层的PO对象生成树供展示层显示
	 * @return 用于显示的TreeModel
	 */
	public DefaultTreeModel getModel();
	/**
	 * 增加一个节点</br>
	 * 只有叶节点的分类才能添加商品</br>
	 * 一旦分类下有商品就不能在该分类下再添加子分类
	 * @param category 界面层传递的VO对象
	 * @return 是否添加成功
	 */
	public boolean add(CategoryVO category);
	/**
	 * 删除一个节点</br>
	 * （那么如果已有商品能不能删除？？？）
	 * @param category 界面层传递的VO对象
	 * @return 是否删除成功
	 */
	public boolean delete(CategoryVO category);
	/**
	 * 修改一个商品分类的信息（只能改名称。。。）
	 * @param category 界面层传递的VO对象
	 * @return 是否修改成功
	 */
	public boolean change(CategoryVO category);
}
