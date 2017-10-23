package presentation.inventory;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 * չʾ��Ʒ�����<br>
 * @author 192 �Ҷ��
 */
public class CommodityPanel extends JPanel {

    /**
     * auto-generated UID 
     */
    private static final long serialVersionUID = -5046683195580706233L;
    private static CommodityPanel instance;
    
    /* From the main panel, in order to send prompt info to users. */
    private JLabel prompt;
    
    // TODO This constructor needs a parameter of a business logic service and a prompt.
    private CommodityPanel(){
        super(new BorderLayout());
        initNorth();
        initCenter();
    }
    
    // TODO It should be modified like the constructor.
    public static CommodityPanel getInstance(){
        instance = new CommodityPanel();
        return instance;
    }
    
    private JButton newButton, editButton, deleteButton, findButton, sortButton, closeButton;
    private JTextField keyTextField;
    private JToolBar editToolBar, findToolBar, sortToolBar;
    private JPanel toolPanel;

    private void initNorth(){
        newButton = UiHelper.initButton("�½���Ʒ", false);
        editButton = UiHelper.initButton("�޸���Ʒ", false);
        deleteButton = UiHelper.initButton("ɾ����Ʒ", false);
        editToolBar = UiHelper.initToolBar(new JComponent[]{newButton, editButton, deleteButton});
        keyTextField = new JTextField(10);
        findButton = UiHelper.initButton("������Ʒ", false);
        findToolBar = UiHelper.initToolBar(new JComponent[]{keyTextField, findButton});
        sortButton = UiHelper.initButton("����", false);
        sortToolBar = UiHelper.initToolBar(new JComponent[]{sortButton});
        closeButton = UiHelper.initButton("�ر�", false);
        toolPanel = UiHelper.initBarlikePanel(10, 20
            , new JComponent[]{editToolBar, findToolBar, sortToolBar}, new JComponent[]{closeButton});

        this.add(toolPanel, BorderLayout.NORTH);
    }
    
    private JScrollPane scrollPane;
    private JTable table;
    private String[] columnNames = {"��Ʒ���", "��Ʒ����", "��Ʒ����", "�������", "����", "���ۼ�", "����ֵ"};

    // TODO Just for test purpose. It needs a service to get relevant data in the method initCenter.
    private Object[][] rowData = {{"0001", "װA���", "����ƾ�", 1000, 100, 200, 200}
        , {"0002", "װB���", "����ƾ�", 2000, 200, 400, 400}};
    
    private void initCenter(){
        table = new JTable(rowData, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    

}
