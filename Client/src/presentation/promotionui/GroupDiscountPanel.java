package presentation.promotionui;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import blservice.PromotionBLService;


@SuppressWarnings("serial")
public class GroupDiscountPanel extends PromotionDetailPanelBase {

    public GroupDiscountPanel(PromotionBLService promotionAdder, ActionListener closeListener) {
        super(promotionAdder, closeListener);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected JPanel getCenterPanel() {
        JPanel panel = new JPanel();
        return panel;
    }

    @Override
    protected boolean addPromotionImpl() {
        return true;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
