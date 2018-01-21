package presentation.analysisui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blservice.billblservice.BillOperationService;
import businesslogic.BillOperationBL;
import presentation.billui.BillPanelHelper;
import presentation.billui.BillPanelInterface;
import presentation.billui.ChangeBillPanel;
import vo.billvo.BillVO;

class CopyBillViewer {
    
    private JDialog frame;
    private JPanel panel;
    
    public CopyBillViewer(BillVO bill, boolean isCopy) {
    	frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("�鿴���ݾ�������");
        BillPanelInterface billPanelImp = BillPanelHelper.createInner(bill);
        billPanelImp.setEditable(isCopy);
        panel = (JPanel) billPanelImp;
        frame.setContentPane(panel);
        frame.setLocation(300, 100);
        frame.setSize(800, 600);
        frame.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent event) {
        		int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ��岢���ƴ˵���", "����", JOptionPane.YES_NO_OPTION);
        		if (isCopy && response == 0) {
        			try{
        	            BillVO bill;
        	            if(panel instanceof ChangeBillPanel){
        	                bill = ((ChangeBillPanel) panel).getBill();
        	                bill.setState(BillVO.PASS);
        	            } else {
        	                bill = (BillVO)panel.getClass().getMethod("getBill", int.class).invoke(panel, BillVO.PASS);
        	            }
        	            if(bill == null) return;
        	            BillOperationService billOperationBl = new BillOperationBL();
        	            if(billOperationBl.offsetBill(bill.getAllId()) && billOperationBl.copyBill(bill)){
        	                JOptionPane.showMessageDialog(null, "��岢���Ƴɹ�");
        	                frame.dispose();
        	            } else {
        	                JOptionPane.showMessageDialog(null, "��岢����ʧ�ܣ�������");
        	            }
        	        }catch(Exception e){
        	            e.printStackTrace();
        	        }
        		}
        	}
        });
        frame.setVisible(true);
    }
}
