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
 * 查看库存快照的面板<br>
 * @author 192 恽叶霄
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
    private String[] columnNames = {"行号", "商品名称", "商品型号", "库存数量", "库存均价", "批次批号", "出厂日期"};
    // TODO Just for test purposes. It needs to be initialized in another way.
    private Object[][] rowData = {{1, "装A神灯", "纯天然", 200, 100, "A-001", "2017-10-22"}
        , {2, "装B神灯", "纯天然", 300, 400, "B-01", "2017-10-22"}};

  
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
        outputButton = UiHelper.initButton("导出", false);
        closeButton = UiHelper.initButton("关闭", false);
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
