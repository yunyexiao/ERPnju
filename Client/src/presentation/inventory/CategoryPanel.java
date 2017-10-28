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
 * ��Ʒ�����չʾ��塣<br>
 * @author 192 �Ҷ��
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
        newButton = UiHelper.initButton("�½�����", false);
        editButton = UiHelper.initButton("�޸ķ���", false);
        deleteButton = UiHelper.initButton("ɾ������", false);
        editToolBar = UiHelper.initToolBar(new JComponent[]{newButton, editButton, deleteButton});
        keyTextField = new JTextField(10);
        findButton = UiHelper.initButton("���ҷ���", false);
        findToolBar = UiHelper.initToolBar(new JComponent[]{keyTextField, findButton});
        closeButton = UiHelper.initButton("�ر�", false);
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
        treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("��Ʒ���"));
        categoryTree.setModel(treeModel);
        contentScrollPane.setViewportView(categoryTree);

        panel.add(contentScrollPane, BorderLayout.CENTER);
    }

	@Override
	public JPanel getPanel() {
		return panel;
	}


}
