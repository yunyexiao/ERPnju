package presentation.inventory;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * �鿴�����յ����<br>
 * @author 192 �Ҷ��
 */
public class InventoryCheckPanel extends JPanel {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = -9034981774205750335L;
    private static InventoryCheckPanel instance;
    
    /* From the main panel, in order to send prompt info to users. */
    private JLabel prompt;
    
    /* Components in the north. */
    private JButton outputButton, closeButton;
    private JPanel toolPanel;

    /* Components in the center. */
    private JScrollPane scrollPane;
    private JTable table;
    private String[] columnNames = {"�к�", "��Ʒ����", "��Ʒ�ͺ�", "�������", "������", "��������", "��������"};
    // TODO Just for test purposes. It needs to be initialized in another way.
    private Object[][] rowData = {{1, "װA���", "����Ȼ", 200, 100, "A-001", "2017-10-22"}
        , {2, "װB���", "����Ȼ", 300, 400, "B-01", "2017-10-22"}};

  
    // TODO It needs a param of a bussiness service and a prompt.
    private InventoryCheckPanel(){
        super(new BorderLayout());
        initNorth();
        initCenter();
    }
    
    // TODO It should be modified like the constructor.
    public static InventoryCheckPanel getInstance(){
        instance = new InventoryCheckPanel();
        return instance;
    }
    
    private void initNorth(){
        outputButton = UiHelper.initButton("����", false);
        closeButton = UiHelper.initButton("�ر�", false);
        toolPanel = UiHelper.initBarlikePanel(10, 20, new JComponent[]{outputButton}, new JComponent[]{closeButton});
       
        this.add(toolPanel, BorderLayout.NORTH);
    }
    
    private void initCenter(){
        scrollPane = new JScrollPane(table = new JTable(rowData, columnNames));
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        table.setFillsViewportHeight(true);
        
        this.add(scrollPane, BorderLayout.CENTER);
    }
    

}
