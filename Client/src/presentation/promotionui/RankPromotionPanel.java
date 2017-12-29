package presentation.promotionui;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import blservice.PromotionBLService;
import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.tools.DoubleField;
import presentation.tools.IntField;
import vo.RankPromotionVO;


@SuppressWarnings("serial")
public class RankPromotionPanel extends PromotionDetailPanelBase {
    
    private IntField rankField;
    private DoubleField discountField, couponField;
    private GiftTable gifts;

    public RankPromotionPanel(PromotionBLService promotionAdder, ActionListener closeListener) {
        super(promotionAdder, closeListener);
    }

    @Override
    protected JPanel getCenterPanel() {
        JScrollPane sp = new JScrollPane(gifts = new GiftTable());

        double[][] size = {
                {-1.0, -2.0, 10.0, -2.0, -1.0},
                {-1.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, 200.0, -1.0}
        };
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(new JLabel("顾客等级"), "1 1");
        panel.add(rankField = new IntField(10), "3 1");
        panel.add(new JLabel("价格折让（元）"), "1 3");
        panel.add(discountField = new DoubleField(10), "3 3");
        panel.add(new JLabel("赠送代金券（元）"), "1 5");
        panel.add(couponField = new DoubleField(10), "3 5");
        panel.add(new JLabel("赠品（最多三种）"), "1 7 3 7");
        panel.add(sp, "1 9 3 9");
        return panel;
    }

    @Override
    protected boolean isFinished() {
        boolean dateValid = super.isFinished();
        if(dateValid){
            double discount = discountField.getValue(), 
                   coupon = couponField.getValue();
            if(discount == 0.0 && coupon == 0.0 && gifts.getRowCount() == 0){
                new InfoWindow("请在价格折让、代金券、赠品中至少选择一种优惠策略。");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean addPromotionImpl() {
        String id = idField.getText(),
               from = fromField.getText(),
               to = toField.getText();
        int rank = rankField.getValue();
        double discount = discountField.getValue(),
               coupon = couponField.getValue();
        MyTableModel gifts = (MyTableModel)this.gifts.getModel();
        RankPromotionVO promotion = new RankPromotionVO(id, from, to, discount, coupon, rank, gifts);
        return promotionAdder.add(promotion);
    }
    
}
