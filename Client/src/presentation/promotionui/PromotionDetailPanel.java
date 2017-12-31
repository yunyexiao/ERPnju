package presentation.promotionui;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blservice.PromotionBLService;
import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.tools.Timetools;
import vo.PromotionVO;


@SuppressWarnings("serial")
public abstract class PromotionDetailPanel extends CenterPanel{
    
    protected PromotionBLService promotionAdder;
    private boolean closable = false;
    private BasicPromotionInfoPanel basicInfoPanel;

    public PromotionDetailPanel(PromotionBLService promotionAdder, ActionListener closeListener) {
        this(promotionAdder, closeListener, null);
    }
    
    public PromotionDetailPanel(PromotionBLService promotionAdder, ActionListener closeListener, PromotionVO promotion){
        super();
        this.promotionAdder = promotionAdder;
        initLeftPanel(promotion);
        double[][] size = {{-2.0, -1.0}, {-1.0, 50.0}};
        super.setLayout(new TableLayout(size));
        super.add(basicInfoPanel, "0 0");
        super.add(getCenterPanel(promotion), "1 0");
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
        String from = basicInfoPanel.getFromField().getText();
        String to = basicInfoPanel.getToField().getText();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if(Timetools.checkDate(from, to) && Timetools.checkDate(today, from)){
            return true;
        } else {
            new InfoWindow("����ȷ��д�������ڡ�");
            return false;
        }
    }
    
    abstract protected JPanel getCenterPanel(PromotionVO promotion);
    
    abstract protected boolean addPromotionImpl();
    
    protected String getId(){
        return basicInfoPanel.getIdField().getText();
    }
    
    protected String getFromDate(){
        return basicInfoPanel.getFromField().getText();
    }
    
    protected String getToDate(){
        return basicInfoPanel.getToField().getText();
    }

    private void initLeftPanel(PromotionVO promotion){
        basicInfoPanel = new BasicPromotionInfoPanel();
        if(promotion == null)
            basicInfoPanel.getIdField().setText(promotionAdder.getNewId());
        else {
            basicInfoPanel.getIdField().setText(promotion.getId());
            basicInfoPanel.getFromField().setText(promotion.getFromDate());
            basicInfoPanel.getToField().setText(promotion.getToDate());
        }
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
