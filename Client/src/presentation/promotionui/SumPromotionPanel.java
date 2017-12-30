package presentation.promotionui;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import blservice.PromotionBLService;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import vo.SumPromotionVO;


@SuppressWarnings("serial")
public class SumPromotionPanel extends PromotionDetailPanel {
    
    private DetailSumPromotionPanel panel;
    private GiftTable giftTable;

    public SumPromotionPanel(PromotionBLService promotionAdder, ActionListener closeListener) {
        super(promotionAdder, closeListener);
    }

    @Override
    protected JPanel getCenterPanel() {
        JScrollPane sp = new JScrollPane();
        giftTable = new GiftTable(sp);
        return panel = new DetailSumPromotionPanel(sp);
    }

    @Override
    protected boolean addPromotionImpl() {
        String id = getId(),
               from = getFromDate(),
               to = getToDate();
        double startPrice = panel.getStartPriceField().getValue();
        String endPriceStr = panel.getEndPriceField().getText();
        double endPrice = endPriceStr.length() == 0 ? Double.POSITIVE_INFINITY : Double.parseDouble(endPriceStr);
        double coupon = panel.getCouponField().getValue();
        MyTableModel model = (MyTableModel)giftTable.getModel();
        SumPromotionVO promotion = new SumPromotionVO(id, from, to, startPrice, endPrice, coupon, model);
        return promotionAdder.add(promotion);
    }

    @Override
    protected boolean isFinished() {
        // check date
        boolean dateValid = super.isFinished();
        if(!dateValid)return false;
        // check start and end price
        double startPrice = panel.getStartPriceField().getValue();
        String endPriceStr = panel.getEndPriceField().getText();
        if(endPriceStr.length() > 0){
            try{
                double endPrice = Double.parseDouble(endPriceStr);
                if(startPrice >= endPrice){
                    new InfoWindow("消费区间错误。");
                    return false;
                }
            }catch(NumberFormatException e){
                new InfoWindow("消费区间请输入小数（不输入表示无穷）");
                return false;
            }
        }
        // check others
        if(panel.getCouponField().getValue() == 0.0 && giftTable.getRowCount() == 0){
            new InfoWindow("请在代金券和赠品中至少选择一种优惠策略。");
            return false;
        }
        return true;
    }

}
