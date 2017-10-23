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


public class InventoryViewPanel extends JPanel {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = -5954360393221671682L;
    
    private static InventoryViewPanel instance;
    
    /* From the main panel, in order to send prompt info to users. */
    private JLabel prompt;
    
    // TODO It needs a param of a bussiness service and a prompt.
    private InventoryViewPanel(){
        super(new BorderLayout());
        initNorth();
        initCenter();
        initSouth();
    }
    
    // TODO It should be modified like the constructor.
    public static InventoryViewPanel getInstance(){
        instance = new InventoryViewPanel();
        return instance;
    }
    
    private JTextField keyTextField;
    private JButton findButton, closeButton;
    private JToolBar findToolBar;
    private JPanel northPanel;

    private void initNorth(){
        keyTextField = new JTextField(10);
        findButton = UiHelper.initButton("查询", false);
        findToolBar = UiHelper.initToolBar(new JComponent[]{keyTextField, findButton});
        closeButton = UiHelper.initButton("关闭", false);
        northPanel = UiHelper.initBarlikePanel(10, 20, new JComponent[]{findToolBar}, new JComponent[]{closeButton});
       
        this.add(northPanel, BorderLayout.NORTH);
    }
    
    private JScrollPane scrollPane;
    private JTable table;
    private String[] columnNames = {"商品编号", "商品名称", "出入库方向", "库存数量", "进货/销售", "进货/销售数量"};
    
    // It needs to be initialized by the serviceImpl in the method initCenter().
    private Object[][] rowData = {{"0001", "名称1", "出库", 100, "销售", 100}
        , {"0002", "名称2", "入库", 100, "进货", 100}};

    private void initCenter(){
        scrollPane = new JScrollPane(table = new JTable(rowData, columnNames));
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    private JLabel nameLabel, totalInLabel, totalOutLabel;
    private JPanel southPanel;

    private void initSouth(){
        nameLabel = new JLabel("商品名称：×××");
        totalInLabel = new JLabel("合计入库：×××");
        totalOutLabel = new JLabel("合计出库：×××");
        southPanel = UiHelper.initBarlikePanel(90, 20, new JComponent[]{nameLabel}
            , new JComponent[]{totalInLabel, totalOutLabel});
       
        this.add(southPanel, BorderLayout.SOUTH);
    }

}
