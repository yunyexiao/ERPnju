package presentation.inventory;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import presentation.main.MainWindow;
import presentation.PanelInterface;
import vo.UserVO;

/**
 * 商品分类的展示面板。<br>
 * @author 192 恽叶霄
 */
public class CategoryPanel implements PanelInterface{

    /**
     * auto generated UID 
     */
    private static final long serialVersionUID = -2281972334688467228L;
    private static CategoryPanel instance;

    /* The prompt label should be got from the main panel to send prompt info to users */
    private static JLabel prompt;
    private static JPanel panel = new JPanel(new BorderLayout());
    private static MainWindow mw;

    // TODO It needs a param of a bussiness service and a prompt.
    public CategoryPanel(MainWindow mw) {
    	this.mw = mw;
        initNorth();
        initCenter();
    }
    
    @Override
    public boolean close() {
        return true;
    }

    @Override
    public void init(UserVO user) {
        // TODO Auto-generated method stub
        
    }

    private JButton newButton, editButton, deleteButton, findButton, closeButton;
    private JTextField keyTextField;
    private JToolBar editToolBar, findToolBar;
    private JPanel toolsPanel;
  
    private void initNorth(){
        newButton = UiHelper.initButton("新建分类", false);
        editButton = UiHelper.initButton("修改分类", false);
        deleteButton = UiHelper.initButton("删除分类", false);
        editToolBar = UiHelper.initToolBar(new JComponent[]{newButton, editButton, deleteButton});
        keyTextField = new JTextField(10);
        findButton = UiHelper.initButton("查找分类", false);
        findToolBar = UiHelper.initToolBar(new JComponent[]{keyTextField, findButton});
        closeButton = UiHelper.initButton("关闭", false);
        closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (close()) mw.changePanel();
			}  	
        });
        
        toolsPanel = UiHelper.initBarlikePanel(10, 20
            , new JComponent[]{editToolBar, findToolBar}, new JComponent[]{closeButton});

        panel.add(toolsPanel, BorderLayout.PAGE_START);
    }
    
    private JScrollPane contentScrollPane;
    
    // TODO The tree needs initialization in the method initCenter().
    private JTree categoryTree;
    private DefaultTreeModel treeModel;

    private void initCenter(){
        contentScrollPane = new JScrollPane();
        categoryTree = new JTree();
        treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("商品类别"));
        categoryTree.setModel(treeModel);
        contentScrollPane.setViewportView(categoryTree);

        panel.add(contentScrollPane, BorderLayout.CENTER);
    }

	@Override
	public JPanel getPanel() {
		return panel;
	}


}
