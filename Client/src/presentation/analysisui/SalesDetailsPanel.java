package presentation.analysisui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.SalesDetailsBLService;
import businesslogic.SalesDetailsBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.DateChoosePanel;
import presentation.component.IdNamePanel;
import presentation.component.InfoWindow;
import presentation.component.LFPanel;
import presentation.component.choosewindow.CommodityChooseWin;
import presentation.component.choosewindow.CustomerChooseWin;
import presentation.component.choosewindow.UserChooseWin;
import presentation.tools.ExcelExporter;
import presentation.tools.Timetools;
import vo.CommodityVO;
import vo.CustomerVO;
import vo.MyTableModel;
import vo.UserType;
import vo.UserVO;

/**
 * @author �Ҷ��
 */
public class SalesDetailsPanel implements PanelInterface {
    
    private SalesDetailsBLService salesDetailsBl;
    private DateChoosePanel fromDatePanel, toDatePanel;
    private LFPanel storePanel;
    private IdNamePanel commodityPanel, customerPanel, operatorPanel;
    private JTable table;
    private JPanel panel;

    public SalesDetailsPanel() {
        salesDetailsBl = new SalesDetailsBL();
        panel = new JPanel(new BorderLayout());
        initNorth();
        initCenter();
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
    
    private void initNorth(){
        JButton searchButton = new JButton("����");
        searchButton.addActionListener(e -> search());
        JButton exportButton = new JButton("����ΪExcel");
        exportButton.addActionListener(e -> ExcelExporter.export((MyTableModel)table.getModel()));

        double[][] size = {
                {20.0, -2.0, 10.0, -2.0, 10.0, -2.0, 20.0, -2.0, -1.0},
                {10.0, -2.0, 10.0, -2.0, -1.0}
        };
        JPanel northPanel = new JPanel(new TableLayout(size));

        northPanel.add(fromDatePanel = new DateChoosePanel("��ʼ����"), "1 1");
        northPanel.add(toDatePanel = new DateChoosePanel("��������"), "1 3");

        northPanel.add(commodityPanel = new IdNamePanel("��Ʒ", 7, 7), "3 1");
        commodityPanel.addMouseListener(chooseCommodity());
        northPanel.add(storePanel = new LFPanel("���", 10), "3 3");
        northPanel.add(customerPanel = new IdNamePanel("������", 7, 7), "5 1");
        customerPanel.addMouseListener(chooseCustomer());
        northPanel.add(operatorPanel = new IdNamePanel("����Ա", 7, 7), "5 3");
        operatorPanel.addMouseListener(chooseOperator());

        northPanel.add(searchButton, "7 1");
        northPanel.add(exportButton, "7 3");
        
        panel.add(northPanel, BorderLayout.NORTH);
    }
    
    private void initCenter(){
        table = new JTable();
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp = new JScrollPane(table);
        panel.add(sp, BorderLayout.CENTER);
    }
    
    private void search(){
        String from = fromDatePanel.getText(), to = toDatePanel.getText();
        if(!Timetools.checkDate(from, to)){
            new InfoWindow("��������ȷ�����ڶ�@_@");
            return;
        }
        String commodityId = commodityPanel.getId(), 
               store = storePanel.getText(),
               customerId = customerPanel.getId(),
               operatorId = operatorPanel.getId();
        commodityId = commodityId.length() == 0 ? null : commodityId;
        store = store.length() == 0 ? null : store;
        customerId = customerId.length() == 0 ? null : customerId;
        operatorId = operatorId.length() == 0 ? null : operatorId;

        table.setModel(salesDetailsBl.filter(from, to, commodityId, store, customerId, operatorId));
    }
    
    private MouseListener chooseCommodity(){
        return new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                CommodityVO commodity = new CommodityChooseWin().getCommodity();
                if(commodity == null) return;
                commodityPanel.setId(commodity.getId());
                commodityPanel.setItsName(commodity.getName());
            }
        };
    }
    
    private MouseListener chooseCustomer(){
        return new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                CustomerVO customer = new CustomerChooseWin().getCustomer();
                if(customer == null)return;
                if(customer.getType() == 0){
                    new InfoWindow("��ѡ��������@_@");
                    return;
                }
                customerPanel.setId(customer.getId());
                customerPanel.setItsName(customer.getName());
            }
        };
    }
    
    private MouseListener chooseOperator(){
        return new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                UserVO user = new UserChooseWin().getUser();
                if(user == null) return;
                if(user.getType() != UserType.SALESMAN){
                    new InfoWindow("��ѡ��һ��������Ա@_@");
                    return;
                }
                operatorPanel.setId(user.getId());
                operatorPanel.setItsName(user.getName());
            }
        };
    }
    
}
