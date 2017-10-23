package presentation.inventory;

import java.awt.BorderLayout;

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

import presentation.PanelInterface;
import vo.UserVO;

/**
 * ��Ʒ�����չʾ��塣<br>
 * @author 192 �Ҷ��
 */
public class CategoryPanel extends JPanel implements PanelInterface{

    /**
     * auto generated UID 
     */
    private static final long serialVersionUID = -2281972334688467228L;
    private static CategoryPanel instance;

    /* The prompt label should be got from the main panel to send prompt info to users */
    private static JLabel prompt;

    // TODO It needs a param of a bussiness service and a prompt.
    private CategoryPanel() {
        super(new BorderLayout());
        initNorth();
        initCenter();
    }
    
    // TODO It should be modified like the constructor.
    public static CategoryPanel getInstance(){
        instance = new CategoryPanel();
        return instance;
    }
 
    @Override
    public boolean close() {
        // TODO Auto-generated method stub
        return false;
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
        newButton = UiHelper.initButton("�½�����", false);
        editButton = UiHelper.initButton("�޸ķ���", false);
        deleteButton = UiHelper.initButton("ɾ������", false);
        editToolBar = UiHelper.initToolBar(new JComponent[]{newButton, editButton, deleteButton});
        keyTextField = new JTextField(10);
        findButton = UiHelper.initButton("���ҷ���", false);
        findToolBar = UiHelper.initToolBar(new JComponent[]{keyTextField, findButton});
        closeButton = UiHelper.initButton("�ر�", false);
        
        toolsPanel = UiHelper.initBarlikePanel(10, 20
            , new JComponent[]{editToolBar, findToolBar}, new JComponent[]{closeButton});

        this.add(toolsPanel, BorderLayout.PAGE_START);
    }
    
    private JScrollPane contentScrollPane;
    
    // TODO The tree needs initialization in the method initCenter().
    private JTree categoryTree;
    private DefaultTreeModel treeModel;

    private void initCenter(){
        contentScrollPane = new JScrollPane();
        categoryTree = new JTree();
        treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("��Ʒ���"));
        categoryTree.setModel(treeModel);
        contentScrollPane.setViewportView(categoryTree);

        this.add(contentScrollPane, BorderLayout.CENTER);
    }


}
