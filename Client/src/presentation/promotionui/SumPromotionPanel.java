package presentation.promotionui;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import blservice.PromotionBLService;
import presentation.component.InfoWindow;
import vo.MyTableModel;
import vo.PromotionVO;
import vo.SumPromotionVO;


@SuppressWarnings("serial")
public class SumPromotionPanel extends PromotionDetailPanel {
    
    private DetailSumPromotionPanel panel;
    private GiftTable giftTable;

    public SumPromotionPanel(PromotionBLService promotionAdder, ActionListener closeListener) {
        this(promotionAdder, closeListener, null);
    }
    
    public SumPromotionPanel(PromotionBLService promotionAdder, ActionListener closeListener, SumPromotionVO promotion){
        super(promotionAdder, closeListener, promotion);
    }

    @Override
    protected JPanel getCenterPanel(PromotionVO promotion) {
        JScrollPane sp = new JScrollPane();
        giftTable = new GiftTable(sp);
        panel = new DetailSumPromotionPanel(sp);
        if(promotion != null){
            SumPromotionVO spv = (SumPromotionVO)promotion;
            panel.getStartPriceField().setValue(spv.getStartPrice());
            if(spv.getEndPrice() != Double.POSITIVE_INFINITY){
                panel.getEndPriceField().setText(spv.getEndPrice() + "");
            }
            panel.getCouponField().setValue(spv.getCoupon());
            giftTable.setModel(spv.getGifts());
        }
        return panel;
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
                    new InfoWindow("�����������");
                    return false;
                }
            }catch(NumberFormatException e){
                new InfoWindow("��������������С�����������ʾ���");
                return false;
            }
        }
        // check others
        if(panel.getCouponField().getValue() == 0.0 && giftTable.getRowCount() == 0){
            new InfoWindow("���ڴ���ȯ����Ʒ������ѡ��һ���Żݲ��ԡ�");
            return false;
        }
        return true;
    }

}
