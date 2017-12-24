package presentation.categoryui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import bl_stub.CategoryBL_stub;
import blservice.CategoryBLService;
import presentation.PanelInterface;
import presentation.component.CloseListener;
import presentation.component.Listener_stub;
import presentation.component.TopButtonPanel;
import presentation.main.MainWindow;

/**
 * 商品分类的展示面板。<br>
 * @author 192 恽叶霄
 */
public class CategoryPanel implements PanelInterface{

    private MainWindow mainWindow;
    private CategoryBLService categoryBL = new CategoryBL_stub();
    private JPanel panel = new JPanel(new BorderLayout());
    private TopButtonPanel toolsPanel = new TopButtonPanel();
    /* Components in the center. */
    private JScrollPane contentScrollPane;
    // The tree needs initialization in the method initCenter().
    private JTree categoryTree;


    public CategoryPanel(MainWindow mw) {
        this.mainWindow = mw;
        initNorth();
        initCenter();
    }
    
    private void initNorth(){
    	//TODO 监听器未完全添加
    	toolsPanel.addButton("新建分类", new ImageIcon("resource/AddData.png"), new Listener_stub());
    	toolsPanel.addButton("修改分类", new ImageIcon("resource/ChangeData.png"), new Listener_stub());
    	toolsPanel.addButton("删除分类", new ImageIcon("resource/DeleteData.png"), new Listener_stub());
    	toolsPanel.addButton("查找分类", new ImageIcon("resource/SearchData.png"), new Listener_stub());
    	toolsPanel.addButton("退出", new ImageIcon("resource/Close.png"), new CloseListener(mainWindow));
        //editToolBar = UiHelper.initToolBar(new JComponent[]{newButton, editButton, deleteButton});
        //findToolBar = UiHelper.initToolBar(new JComponent[]{keyTextField, findButton});

        panel.add(toolsPanel.getPanel(), BorderLayout.NORTH);
    }
    
    private void initCenter(){
        contentScrollPane = new JScrollPane();
		categoryTree = new JTree();
        categoryTree.setModel(categoryBL.getModel());
        contentScrollPane.setViewportView(categoryTree);

        panel.add(contentScrollPane, BorderLayout.CENTER);
    }
    
    @Override
    public boolean close() {
        return true;
    }
 
	@Override
	public JPanel getPanel() {
		return panel;
	}


}