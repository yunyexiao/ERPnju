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
 * 展示商品的面板<br>
 * @author 192 恽叶霄
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
        newButton = UiHelper.initButton("新建商品", false);
        editButton = UiHelper.initButton("修改商品", false);
        deleteButton = UiHelper.initButton("删除商品", false);
        editToolBar = UiHelper.initToolBar(new JComponent[]{newButton, editButton, deleteButton});
        keyTextField = new JTextField(10);
        findButton = UiHelper.initButton("查找商品", false);
        findToolBar = UiHelper.initToolBar(new JComponent[]{keyTextField, findButton});
        sortButton = UiHelper.initButton("排序", false);
        sortToolBar = UiHelper.initToolBar(new JComponent[]{sortButton});
        closeButton = UiHelper.initButton("关闭", false);
        toolPanel = UiHelper.initBarlikePanel(10, 20
            , new JComponent[]{editToolBar, findToolBar, sortToolBar}, new JComponent[]{closeButton});

        this.add(toolPanel, BorderLayout.NORTH);
    }
    
    private JScrollPane scrollPane;
    private JTable table;
    private String[] columnNames = {"商品编号", "商品名称", "商品分类", "库存数量", "进价", "销售价", "警戒值"};

    // TODO Just for test purpose. It needs a service to get relevant data in the method initCenter.
    private Object[][] rowData = {{"0001", "装A神灯", "特殊灯具", 1000, 100, 200, 200}
        , {"0002", "装B神灯", "特殊灯具", 2000, 200, 400, 400}};
    
    private void initCenter(){
        table = new JTable(rowData, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    

}
