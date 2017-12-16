package presentation.analysisui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import presentation.billui.BillPanel;
import vo.UserVO;
import vo.billvo.BillVO;

class BillViewer {
    
    private JDialog frame;
    private JPanel panel;
    private boolean editable;

    public BillViewer(Class<? extends BillPanel> panelType, 
        Class<? extends BillVO> billType, UserVO user, BillVO bill) {
        editable = bill.getState() == BillVO.SAVED;
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("查看单据具体内容");
        ActionListener closeListener = e -> frame.dispose();
        try{
            panel = panelType.getConstructor(UserVO.class, ActionListener.class, billType)
                        .newInstance(user, closeListener, billType.cast(bill)).getPanel();
            adjustTopPanel();
            frame.setContentPane(panel);
            frame.setLocation(300, 100);
            frame.setSize(800, 600);
            frame.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
            frame.dispose();
        }
    }

    private void adjustTopPanel(){
        JPanel topPanel = (JPanel)panel.getComponents()[0];
        topPanel.remove(0);
        topPanel.remove(0);
        if(editable){
            JButton okButton = (JButton)topPanel.getComponent(0);
            okButton.setText("完成并生效");
            okButton.addActionListener(e -> copyBill());
        } else {
            topPanel.remove(0);
        }
    }
    
    private void copyBill(){
        // TODO
    }

}
