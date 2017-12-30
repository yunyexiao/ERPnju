package presentation.promotionui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.PromotionBLService;
import presentation.component.MyTableModel;
import vo.PromotionVO;

@SuppressWarnings("serial")
public class SearchResultPanel extends CenterPanel {
    
    private JTable table;
    private MouseListener viewDetailHandler;
    private PromotionBLService promotionBl;
    
    public SearchResultPanel(MyTableModel model, PromotionBLService promotionBl){
        super();
        this.promotionBl = promotionBl;
        JScrollPane sp = new JScrollPane(table = new JTable(model));
        this.initViewDetailHandler();
        table.addMouseListener(viewDetailHandler);
        super.setLayout(new BorderLayout());
        super.add(sp, BorderLayout.CENTER);
    }
    
    private void initViewDetailHandler(){
        viewDetailHandler = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() >= 2){
                    int selected = table.getSelectedRow();
                    if(selected < 0) return;
                    String id = table.getValueAt(selected, 0).toString();
                    PromotionVO promotion = promotionBl.findById(id);
                    new ViewPromotionDetailWin(promotion);
                }
            }
        };
    }

}
