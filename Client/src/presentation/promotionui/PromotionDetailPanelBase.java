package presentation.promotionui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import blservice.PromotionBLService;
import layout.TableLayout;
import presentation.component.DateChooser;
import presentation.component.InfoWindow;
import presentation.tools.Timetools;


@SuppressWarnings("serial")
public abstract class PromotionDetailPanelBase extends CenterPanel{
    
    protected PromotionBLService promotionAdder;
    private boolean closable = false;
    protected JTextField idField, fromField, toField;

    public PromotionDetailPanelBase(PromotionBLService promotionAdder, ActionListener closeListener) {
        super();
        this.promotionAdder = promotionAdder;
        double[][] size = {{-2.0, -1.0}, {-1.0, 50.0}};
        super.setLayout(new TableLayout(size));
        super.add(getLeftPanel(), "0 0");
        super.add(getCenterPanel(), "1 0");
        super.add(getBottomPanel(closeListener), "0 1 1 1");
    }
    
    @Override
    public boolean close(){
        if(!closable){
            int option = JOptionPane.showConfirmDialog(this, "Ҫȡ����ǰ������", "ϵͳ��ʾ", JOptionPane.YES_NO_OPTION);
            if(option == 0) return true;
            else return false;
        }
        return true;
    }
    
    protected boolean isFinished(){
        String from = fromField.getText();
        String to = toField.getText();
        if(Timetools.checkDate(from, to)){
            return true;
        } else {
            new InfoWindow("����ȷ��д�������ڡ�");
            return false;
        }
    }
    
    abstract protected JPanel getCenterPanel();
    
    abstract protected boolean addPromotionImpl();
    
    private JPanel getLeftPanel(){
        idField = new JTextField(10);
        idField.setEditable(false);
        idField.setText(promotionAdder.getNewId());
        fromField = new JTextField(10);
        DateChooser.getInstance().register(fromField);
        toField = new JTextField(10);
        DateChooser.getInstance().register(toField);
        
        double[][] size = {
                {80.0, -2.0, 80.0},
                {-1.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0}
        };
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(new JLabel("���"), "1 1");
        panel.add(idField, "1 3");
        panel.add(new JLabel("��ʼ����"), "1 5");
        panel.add(fromField, "1 7");
        panel.add(new JLabel("��������"), "1 9");
        panel.add(toField, "1 11");
        return panel;
    }

    private JPanel getBottomPanel(ActionListener closeListener){
        double[][] size = {{-1.0, -2.0, 10.0, -2.0, 20.0}, {10.0, -2.0, 10.0}};
        JPanel panel = new JPanel(new TableLayout(size));
        JButton okButton = new JButton("ȷ��");
        okButton.addActionListener(e->addPromotion(closeListener));
        panel.add(okButton, "1 1");
        JButton cancelButton = new JButton("ȡ��");
        cancelButton.addActionListener(e->cancel(closeListener));
        panel.add(cancelButton, "3 1");
        return panel;
    }
    
    private void addPromotion(ActionListener closeListener){
        if(!isFinished()){
            return;
        }
        if(addPromotionImpl()){
            JOptionPane.showMessageDialog(this, "��ӳɹ���");
            closable = true;
            closeListener.actionPerformed(null);
        } else {
            JOptionPane.showMessageDialog(this, "���ʧ�ܣ������ԡ�");
        }
    }
    
    private void cancel(ActionListener closeListener){
        closable = true;
        closeListener.actionPerformed(null);
    }

}
