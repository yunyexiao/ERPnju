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
 * ��Ʒ�����չʾ��塣<br>
 * @author 192 �Ҷ��
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
    	//TODO ������δ��ȫ���
    	toolsPanel.addButton("�½�����", new ImageIcon("resource/AddData.png"), new Listener_stub());
    	toolsPanel.addButton("�޸ķ���", new ImageIcon("resource/ChangeData.png"), new Listener_stub());
    	toolsPanel.addButton("ɾ������", new ImageIcon("resource/DeleteData.png"), new Listener_stub());
    	toolsPanel.addButton("���ҷ���", new ImageIcon("resource/SearchData.png"), new Listener_stub());
    	toolsPanel.addButton("�˳�", new ImageIcon("resource/Close.png"), new CloseListener(mainWindow));
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