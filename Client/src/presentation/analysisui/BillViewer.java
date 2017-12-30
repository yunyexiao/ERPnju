package presentation.analysisui;

import javax.swing.JDialog;
import javax.swing.JPanel;

import presentation.billui.BillPanelHelper;
import presentation.billui.BillPanelInterface;
import vo.billvo.BillVO;

class BillViewer {
    
    private JDialog frame;
    private JPanel panel;
    
    public BillViewer(BillVO bill, boolean isCopy) {
    	frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("查看单据具体内容");
        BillPanelInterface billPanelImp = BillPanelHelper.createInner(bill);
        billPanelImp.setEditable(true);
        panel = (JPanel) billPanelImp;
        frame.setContentPane(panel);
        frame.setLocation(300, 100);
        frame.setSize(800, 600);
       
        frame.setVisible(true);
    }
}
