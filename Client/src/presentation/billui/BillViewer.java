package presentation.billui;

import javax.swing.JDialog;
import javax.swing.JPanel;

import vo.billvo.BillVO;

class BillViewer {
    
    private JDialog frame;
    private JPanel panel;
    
    public BillViewer(BillVO bill) {
    	frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("�鿴���ݾ�������");
        BillPanelInterface billPanelImp = BillPanelHelper.createInner(bill);
        billPanelImp.setEditable(true);
        panel = (JPanel) billPanelImp;
        frame.setContentPane(panel);
        frame.setLocation(300, 100);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
