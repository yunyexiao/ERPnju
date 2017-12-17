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
 * ������Աר�þ�Ӫ���̱�<br>
 * ����ʵ�ֻ����ĵ��ݲ鿴����ʵ���˺���Լ���岢���ƵĹ��ܣ���塢��岢���ƾ���ֱ����Ч����һ����BillOperationService����
 * @author �Ҷ��
 */
public class BusinessHistoryPanel extends ViewBusinessHistoryPanel {
    
    private BillOperationService billOperationBl;

    public BusinessHistoryPanel(ActionListener closeListener) {
        super(closeListener);
        billOperationBl = new BillOperationBL();
    }
    
    @Override
    protected JPanel getEastPanel(){
        JButton offsetButton = new JButton("���");
        offsetButton.addActionListener(e -> offsetBill());
        JButton copyButton = new JButton("���Ʋ����");
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
            new InfoWindow("��ѡ��һ�ŵ��ݺ��@_@");
            return;
        }
        int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ���õ�����", "��ʾ", JOptionPane.YES_NO_OPTION);
        if(response == 1) return;
        String id = table.getValueAt(index, 0).toString();
        if(billOperationBl.offsetBill(id)){
            JOptionPane.showMessageDialog(null, "��嵥�ݳɹ�^_^");
            search();
        } else {
            JOptionPane.showMessageDialog(null, "��嵥��ʧ�ܣ�������@_@");
        }
    }
    
    private void copyBill(){
        int index = table.getSelectedRow();
        if(index < 0){
            new InfoWindow("��ѡ��һ�ŵ��ݽ��в���@_@");
            return;
        }
        String id = table.getValueAt(index, 0).toString();
        generateBillViewer(id, true);
    }

}
