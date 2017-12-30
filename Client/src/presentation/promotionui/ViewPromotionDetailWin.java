package presentation.promotionui;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.infoservice.GetCommodityInterface;
import businesslogic.CommodityBL;
import layout.TableLayout;
import presentation.component.MyTableModel;
import presentation.tools.StyleTools;
import vo.CommodityVO;
import vo.GroupDiscountVO;
import vo.PromotionVO;
import vo.RankPromotionVO;
import vo.SumPromotionVO;

public class ViewPromotionDetailWin {
    
    private JDialog frame;
    private PromotionVO promotion;
    
    public ViewPromotionDetailWin(PromotionVO promotion){
        super();
        StyleTools.setNimbusLookAndFeel();
        this.promotion = promotion;
        frame = new JDialog();
        frame.setModal(true);
        frame.setSize(600, 400);
        frame.setLocation(300, 200);
        frame.setTitle("�鿴��������");
        double[][] size = {{200.0, -1.0}, {-1.0}};
        frame.setLayout(new TableLayout(size));
        frame.add(getBasicInfoPanel(), "0 0");
        frame.add(getDetailPanel(), "1 0");
        frame.setVisible(true);
    }
    
    private JPanel getBasicInfoPanel(){
        BasicPromotionInfoPanel panel = new BasicPromotionInfoPanel();
        panel.getIdField().setEditable(false);
        panel.getIdField().setText(promotion.getId());
        panel.getFromField().setEnabled(false);
        panel.getFromField().setEditable(false);
        panel.getFromField().setText(promotion.getFromDate());
        panel.getToField().setEnabled(false);
        panel.getToField().setEditable(false);
        panel.getToField().setText(promotion.getToDate());
        return panel;
    }
    
    private JPanel getDetailPanel(){
        if(promotion instanceof RankPromotionVO){
            return rankPromotionPanel((RankPromotionVO) promotion);
        } else if(promotion instanceof GroupDiscountVO){
            return groupDiscountPanel((GroupDiscountVO) promotion);
        } else {
            return sumPromotionPanel((SumPromotionVO) promotion);
        }
    }
    
    private JPanel rankPromotionPanel(RankPromotionVO promotion){
        JTable table = new JTable(promotion.getGifts());
        looseTable(table);
        JScrollPane sp = new JScrollPane(table);
        DetailRankPromotionPanel panel = new DetailRankPromotionPanel(sp);
        panel.getRankField().setEditable(false);
        panel.getRankField().setValue(promotion.getRank());
        panel.getCouponField().setEditable(false);
        panel.getCouponField().setValue(promotion.getCoupon());
        panel.getDiscountField().setEditable(false);
        panel.getDiscountField().setValue(promotion.getReduction());
        return panel;
    }
    
    private JPanel groupDiscountPanel(GroupDiscountVO promotion){
        JTable table = new JTable(getComModel(promotion.getGroup()));
        looseTable(table);
        JScrollPane sp = new JScrollPane(table);
        DetailGroupDiscountPanel panel = new DetailGroupDiscountPanel(sp);
        panel.getDiscountField().setEditable(false);
        panel.getDiscountField().setValue(promotion.getReduction());
        return panel;
    }
    
    private MyTableModel getComModel(ArrayList<String> comIds){
        GetCommodityInterface comInfo = new CommodityBL();
        String[] columnNames = {"��Ʒ���", "����", "�ͺ�"};
        int size = comIds.size();
        String[][] data = new String[size][];
        for(int i = 0; i < size; i++){
            String id = comIds.get(i);
            CommodityVO com = comInfo.getCommodity(id);
            String name = com.getName();
            String type = com.getType();
            data[i] = new String[]{id, name, type};
        }
        return new MyTableModel(data, columnNames);
    }
    
    private JPanel sumPromotionPanel(SumPromotionVO promotion){
        JTable table = new JTable(promotion.getGifts());
        looseTable(table);
        JScrollPane sp = new JScrollPane(table);
        DetailSumPromotionPanel panel = new DetailSumPromotionPanel(sp);
        panel.getStartPriceField().setEditable(false);
        panel.getStartPriceField().setValue(promotion.getStartPrice());
        panel.getEndPriceField().setEditable(false);
        if(promotion.getEndPrice() != Double.POSITIVE_INFINITY){
            panel.getEndPriceField().setText(promotion.getEndPrice() + "");
        }
        panel.getCouponField().setEditable(false);
        panel.getCouponField().setValue(promotion.getCoupon());
        return panel;
    }
    
    private static void looseTable(JTable table){
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

}
