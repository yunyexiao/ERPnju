package presentation.analysisui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blservice.billblservice.BillOperationService;
import businesslogic.BillOperationBL;
import presentation.PanelInterface;
import presentation.billui.BillPanel;
import presentation.billui.ChangeBillPanel;
import vo.UserVO;
import vo.billvo.BillVO;

class BillViewer {
    
    private JDialog frame;
    private PanelInterface panel;
    private boolean editable;
    
    public BillViewer(Class<? extends BillPanel> panelType, 
        Class<? extends BillVO> billType, UserVO user, BillVO bill) {
        editable = bill.getState() == BillVO.SAVED;
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("�鿴���ݾ�������");
        ActionListener closeListener = e -> frame.dispose();
        try{
            panel = panelType.getConstructor(UserVO.class, ActionListener.class, billType)
                        .newInstance(user, closeListener, billType.cast(bill));
            adjustTopPanel();
            frame.setContentPane(panel.getPanel());
            frame.setLocation(300, 100);
            frame.setSize(800, 600);
            frame.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
            frame.dispose();
        }
    }

    private void adjustTopPanel(){
        JPanel topPanel = (JPanel)panel.getPanel().getComponents()[0];
        topPanel.remove(0);
        topPanel.remove(0);
        if(editable){
            JButton okButton = (JButton)topPanel.getComponent(0);
            okButton.removeActionListener(okButton.getActionListeners()[0]);
            okButton.setText("��ɲ���Ч");
            okButton.addActionListener(e -> copyBill());
        } else {
            topPanel.remove(0);
        }
    }
    
    /**
     * ��岢���ƣ�ֱ����Ч��ע��ֱ����Ч��һ������{@code BillOperationService}������
     */
    private void copyBill(){
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
            if(billOperationBl.copyBill(bill)){
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
