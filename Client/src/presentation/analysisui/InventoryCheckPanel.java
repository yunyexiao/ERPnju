package presentation.analysisui;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.CommodityBL;
import presentation.PanelInterface;
import presentation.component.MyTableModel;
import presentation.component.TopButtonPanel;
import presentation.tools.ExcelExporter;


public class InventoryCheckPanel implements PanelInterface {
    
    private JPanel panel;
    private JTable table;

    public InventoryCheckPanel() {
        panel = new JPanel(new BorderLayout());
        initNorth();
        initCenter();
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
    
    private void initNorth(){
        TopButtonPanel northPanel = new TopButtonPanel();
        // TODO add an imageicon here
        northPanel.addButton("导出为Excel表", null, e -> export());
        panel.add(northPanel.getPanel(), BorderLayout.NORTH);
    }
    
    private void initCenter(){
        table = new JTable(new CommodityBL().update());
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane sp = new JScrollPane(table);
        panel.add(sp, BorderLayout.CENTER);
    }
    
    private void export(){
        String path = ExcelExporter.openFileChooser();
        if(path == null) return;
        if(ExcelExporter.export((MyTableModel)table.getModel(), path)){
            JOptionPane.showMessageDialog(null, "导出成功^_^");
        } else {
            JOptionPane.showMessageDialog(null, "导出失败，请重试@_@");
        }
    }

}
