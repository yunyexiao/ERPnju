package presentation.billui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.CustomerBL;
import businesslogic.UserBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.IdNamePanel;
import presentation.component.LFPanel;
import vo.billvo.GiftBillVO;

/**
 * 查看赠品单的面板，只提供查看功能
 * 
 * @author 恽叶霄
 */
public class ViewGiftBillPanel implements PanelInterface {
    
    private JPanel panel;
    
    public ViewGiftBillPanel(GiftBillVO bill){
        if(bill == null) return;
        LFPanel idPanel = new LFPanel("编号", 15),
                timePanel = new LFPanel("时间", 10),
                salesBillPanel = new LFPanel("原销售单", 10);
        IdNamePanel operatorPanel = new IdNamePanel("操作员", 7, 7),
                    customerPanel = new IdNamePanel("顾客", 7, 7);
        JTable gifts = new JTable(bill.getGifts());
        idPanel.getTextField().setEditable(false);
        idPanel.setFieldText(bill.getAllId());
        timePanel.getTextField().setEditable(false);
        timePanel.setFieldText(bill.getTime());
        salesBillPanel.getTextField().setEditable(false);
        salesBillPanel.setFieldText(bill.getSalesBillId());
        operatorPanel.setId(bill.getOperator());
        String operatorName = new UserBL().getUser(bill.getOperator()).getName();
        operatorPanel.setItsName(operatorName);
        customerPanel.setId(bill.getCustomerId());
        String customerName = new CustomerBL().getCustomer(bill.getCustomerId()).getName();
        customerPanel.setItsName(customerName);
        gifts.getTableHeader().setReorderingAllowed(false);

        double[][] size = {
                {-1.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0},
                {-1.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0}
        };
        panel = new JPanel(new TableLayout(size));
        panel.add(idPanel, "1 1");
        panel.add(timePanel, "3 1");
        panel.add(operatorPanel, "5 1");
        panel.add(customerPanel, "1 3");
        panel.add(salesBillPanel, "3 3");
        panel.add(new JLabel("赠品"), "1 5");
        panel.add(new JScrollPane(gifts), "1 7 5 7");
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

}
