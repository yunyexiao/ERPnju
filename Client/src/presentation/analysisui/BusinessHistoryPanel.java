package presentation.analysisui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blservice.billblservice.BillOperationService;
import businesslogic.BillOperationBL;
import layout.TableLayout;
import presentation.component.InfoWindow;

/**
 * 财务人员专用经营历程表<br>
 * 除了实现基本的单据查看，还实现了红冲以及红冲并复制的功能，红冲、红冲并复制均是直接生效，这一点由BillOperationService决定
 * @author 恽叶霄
 */
public class BusinessHistoryPanel extends ViewBusinessHistoryPanel {
    
    private BillOperationService billOperationBl;

    public BusinessHistoryPanel(ActionListener closeListener) {
        super(closeListener);
        billOperationBl = new BillOperationBL();
    }
    
    @Override
    protected JPanel getEastPanel(){
        JButton offsetButton = new JButton("红冲");
        offsetButton.addActionListener(e -> offsetBill());
        JButton copyButton = new JButton("复制并红冲");
        copyButton.addActionListener(e -> copyBill());

        JPanel panel = super.getEastPanel();
        JButton viewButton = (JButton)panel.getComponents()[0];
        double[][] size = {
                {10.0, -2.0, -1.0},
                {10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0}
        };
        panel.setLayout(new TableLayout(size));
        panel.add(viewButton, "1 1");
        panel.add(offsetButton, "1 3");
        panel.add(copyButton, "1 5");
        return panel;
    }
    
    private void offsetBill(){
        int index = table.getSelectedRow();
        if(index < 0){
            new InfoWindow("请选择一张单据红冲@_@");
            return;
        }
        int response = JOptionPane.showConfirmDialog(null, "确认要红冲该单据吗？", "提示", JOptionPane.YES_NO_OPTION);
        if(response == 1) return;
        String id = table.getValueAt(index, 0).toString();
        if(billOperationBl.offsetBill(id)){
            JOptionPane.showMessageDialog(null, "红冲单据成功^_^");
            search();
        } else {
            JOptionPane.showMessageDialog(null, "红冲单据失败，请重试@_@");
        }
    }
    
    private void copyBill(){
        int index = table.getSelectedRow();
        if(index < 0){
            new InfoWindow("请选择一张单据进行操作@_@");
            return;
        }
        String id = table.getValueAt(index, 0).toString();
        generateBillViewer(id, true);
    }

}
