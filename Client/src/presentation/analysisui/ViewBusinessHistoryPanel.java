package presentation.analysisui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.billblservice.BillBLService;
import blservice.billblservice.BillSearchBLService;
import blservice.infoservice.GetUserInterface;
import businesslogic.BillBL;
import businesslogic.BillSearchBL;
import businesslogic.UserBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.billui.BillPanel;
import presentation.billui.CashCostBillPanel;
import presentation.billui.ChangeBillPanel;
import presentation.billui.PurchaseBillPanel;
import presentation.billui.PurchaseReturnBillPanel;
import presentation.billui.ReceiptOrPaymentBillPanel;
import presentation.billui.SalesBillPanel;
import presentation.billui.SalesReturnBillPanel;
import presentation.component.DateChoosePanel;
import presentation.component.IdNamePanel;
import presentation.component.InfoWindow;
import presentation.component.LFPanel;
import presentation.component.MyTableModel;
import presentation.component.choosewindow.AccountChooseWin;
import presentation.component.choosewindow.CustomerChooseWin;
import presentation.component.choosewindow.UserChooseWin;
import presentation.tools.ExcelExporter;
import presentation.tools.Timetools;
import vo.AccountVO;
import vo.CustomerVO;
import vo.UserType;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.CashCostBillVO;
import vo.billvo.ChangeBillVO;
import vo.billvo.PaymentBillVO;
import vo.billvo.PurchaseBillVO;
import vo.billvo.PurchaseReturnBillVO;
import vo.billvo.ReceiptBillVO;
import vo.billvo.SalesBillVO;
import vo.billvo.SalesReturnBillVO;

/**
 * �鿴��Ӫ���̱����壬����ʵ�ֲ鿴���ܣ���֧�ֺ��Ȳ���
 * @author �Ҷ��
 */
public class ViewBusinessHistoryPanel implements PanelInterface {
    
    private BillSearchBLService billSearchBl;
    private BillBLService billBL;
    private JPanel panel;
    private DateChoosePanel fromPanel, toPanel;
    private LFPanel storePanel;
    private IdNamePanel operatorPanel, customerPanel, accountPanel;
    private JComboBox<ChoiceItem> choiceBox;
    protected JTable table;
    /** 
     * ���û��л���������ʱ���Ÿı䣬
     * �����: storePanel�� �����༰�տ�: customerPanel�� �ֽ���õ�: accountPanel
     */
    private JPanel typePanel;
    /**
     * ���������ѡ�ÿһ��ѡ�����Ӧ�ĸ��ֲ���
     * @see ChoiceItem
     */
    private ChoiceItem[] choices = {
            new ChoiceItem("���絥"){
                public boolean validOperator(UserType type) {return type == UserType.KEEPER;}
                public boolean validCustomer(int type) {return false;}
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterInventoryBills(from, to, getStore(), operatorId, true);
                }
                public JPanel getTypePanel() { return storePanel; }
                public BillVO getBill(String id) { return billBL.getChangeBill(id); }
                public Class<? extends BillPanel> getPanelClass() { return ChangeBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return ChangeBillVO.class; }
            },
            new ChoiceItem("����"){
                public boolean validOperator(UserType type) { return type == UserType.KEEPER; }
                public boolean validCustomer(int type) { return false; }
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterInventoryBills(from, to, getStore(), operatorId, false);
                }
                public JPanel getTypePanel() { return storePanel; }
                public BillVO getBill(String id) { return billBL.getChangeBill(id);}
                public Class<? extends BillPanel> getPanelClass() { return ChangeBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return ChangeBillVO.class; }
            },
            new ChoiceItem("�ֽ���õ�"){
                public boolean validOperator(UserType type) { return type == UserType.ACCOUNTANT; }
                public boolean validCustomer(int type) { return false; }
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterCashCostBills(from, to, getAccountId(), operatorId);
                }
                public JPanel getTypePanel() { return accountPanel; }
                public BillVO getBill(String id) { return billBL.getCashCostBill(id); }
                public Class<? extends BillPanel> getPanelClass() {return CashCostBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return CashCostBillVO.class; }
            },
            new ChoiceItem("���"){
                public boolean validOperator(UserType type) { return type == UserType.ACCOUNTANT; }
                public boolean validCustomer(int type) { return true; }
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterPaymentBills(from, to, getCustomerId(), operatorId);
                }
                public JPanel getTypePanel() { return customerPanel; }
                public BillVO getBill(String id) { return billBL.getPaymentBill(id); }
                public Class<? extends BillPanel> getPanelClass() { return ReceiptOrPaymentBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return PaymentBillVO.class; }
            },
            new ChoiceItem("�տ"){
                public boolean validOperator(UserType type) { return type == UserType.ACCOUNTANT; }
                public boolean validCustomer(int type) { return true; }
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterReceiptBills(from, to, getCustomerId(), operatorId);
                }
                public JPanel getTypePanel() { return customerPanel; }
                public BillVO getBill(String id) { return billBL.getReceiptBill(id); }
                public Class<? extends BillPanel> getPanelClass() { return ReceiptOrPaymentBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return ReceiptBillVO.class; }
            },
            new ChoiceItem("������"){
                public boolean validOperator(UserType type) { return type == UserType.SALESMAN; }
                public boolean validCustomer(int type) { return type == 0; }
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterPurchaseBills(from, to, getCustomerId(), operatorId);
                }
                public JPanel getTypePanel() { return customerPanel; }
                public BillVO getBill(String id) { return billBL.getPurchaseBill(id); }
                public Class<? extends BillPanel> getPanelClass() { return PurchaseBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return PurchaseBillVO.class; }
            },
            new ChoiceItem("�����˻���"){
                public boolean validOperator(UserType type) { return type == UserType.SALESMAN; }
                public boolean validCustomer(int type) { return type == 0; }
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterPurchaseReturnBills(from, to, getCustomerId(), operatorId);
                }
                public JPanel getTypePanel() { return customerPanel; }
                public BillVO getBill(String id) { return billBL.getPurchaseReturnBill(id); }
                public Class<? extends BillPanel> getPanelClass() { return PurchaseReturnBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return PurchaseReturnBillVO.class; }
            },
            new ChoiceItem("���۵�"){
                public boolean validOperator(UserType type) { return type == UserType.SALESMAN; }
                public boolean validCustomer(int type) { return type == 1; }
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterSalesBills(from, to, getCustomerId(), operatorId);
                }
                public JPanel getTypePanel() { return customerPanel; }
                public BillVO getBill(String id) { return billBL.getSalesBill(id); }
                public Class<? extends BillPanel> getPanelClass() { return SalesBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return SalesBillVO.class; }
            },
            new ChoiceItem("�����˻���"){
                public boolean validOperator(UserType type) { return type == UserType.SALESMAN; }
                public boolean validCustomer(int type) { return type == 1; }
                public MyTableModel search(String from, String to, String operatorId) {
                    return billSearchBl.filterSalesReturnBills(from, to, getCustomerId(), operatorId);
                }
                public JPanel getTypePanel() { return customerPanel; }
                public BillVO getBill(String id) { return billBL.getSalesReturnBill(id); }
                public Class<? extends BillPanel> getPanelClass() { return SalesReturnBillPanel.class; }
                public Class<? extends BillVO> getBillClass() { return SalesReturnBillVO.class; }
            }
    };

    public ViewBusinessHistoryPanel(ActionListener closeListener) {
        billSearchBl = new BillSearchBL();
        billBL = new BillBL();
        panel = new JPanel(new BorderLayout());
        panel.add(getNorthPanel(closeListener), BorderLayout.NORTH);
        panel.add(getCenterPanel(), BorderLayout.CENTER);
        panel.add(getEastPanel(), BorderLayout.EAST);
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
    
    private JPanel getNorthPanel(ActionListener closeListener) {
        fromPanel = new DateChoosePanel("��ʼʱ��");
        toPanel = new DateChoosePanel("����ʱ��");
        choiceBox = new JComboBox<>(choices);
        choiceBox.addItemListener(e -> itemChanged(e));
        operatorPanel = new IdNamePanel("����Ա", 7, 7);
        operatorPanel.addMouseListener(chooseOperator());
        customerPanel = new IdNamePanel("�ͻ�", 7, 7);
        customerPanel.addMouseListener(chooseCustomer());
        storePanel = new LFPanel("���", 14);
        typePanel = storePanel;
        accountPanel = new IdNamePanel("�˻�", 7, 7);
        accountPanel.addMouseListener(chooseAccount());
        // TODO icons not added
        JButton searchButton = new JButton("����");
        searchButton.addActionListener(e -> search());
        JButton exportButton = new JButton("����ΪExcel");
        exportButton.addActionListener(e -> ExcelExporter.export((MyTableModel)table.getModel()));
        JButton closeButton = new JButton("�ر�");
        closeButton.addActionListener(closeListener);
        
        double[][] size = {
                {20.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0},
                {10.0, -2.0, 10.0, -2.0, -1.0}
        };
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(fromPanel, "1 1");
        panel.add(toPanel, "1 3");
        panel.add(choiceBox, "3 1");
        panel.add(operatorPanel, "3 3");
        panel.add(storePanel, "5 1");
        panel.add(searchButton, "7 1");
        panel.add(exportButton, "9 1");
        panel.add(closeButton, "11 1");
        return panel;
    }

    private Component getCenterPanel() {
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        return sp;
    }

    protected JPanel getEastPanel(){
        JButton viewButton = new JButton("�鿴��������");
        viewButton.addActionListener(e -> viewDetails());
        double[][] size = {{10.0, -2.0, -1.0}, {10.0, -2.0, -1.0}};
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(viewButton, "1 1");
        return panel;
    }

    private void itemChanged(ItemEvent e){
        if(e.getStateChange() == ItemEvent.DESELECTED) return;
        JPanel newTypePanel = choiceBox.getItemAt(choiceBox.getSelectedIndex()).getTypePanel();
        if(!newTypePanel.equals(typePanel)){
            Container container = typePanel.getParent();
            container.setVisible(false);
            container.setLayout(container.getLayout());
            container.remove(typePanel);
            container.add(newTypePanel, "5 1");
            typePanel = newTypePanel;
            container.setVisible(true);
        }
        operatorPanel.setId(""); 
        operatorPanel.setItsName("");
        customerPanel.setId("");
        customerPanel.setItsName("");
    }
    
    private String getStore(){
        String store = storePanel.getText();
        return store.length() == 0 ? null : store;
    }
    
    private String getAccountId(){
        String accountId = accountPanel.getId();
        return accountId.length() == 0 ? null : accountId;
    }
    
    private String getCustomerId(){
        String customerId = customerPanel.getId();
        return customerId.length() == 0 ? null : customerId;
    }

    private MouseListener chooseOperator(){
        return new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                UserVO operator = new UserChooseWin().getUser();
                if(operator == null) return;
                ChoiceItem selectedItem = choiceBox.getItemAt(choiceBox.getSelectedIndex());
                if(selectedItem.validOperator(operator.getType())){
                    operatorPanel.setId(operator.getId());
                    operatorPanel.setItsName(operator.getName());
                } else {
                    new InfoWindow("��ѡ����ȷ�Ĳ���Ա@_@");
                    return;
                }
            }
        };
    }
    
    private MouseListener chooseCustomer(){
        return new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                CustomerVO customer = new CustomerChooseWin().getCustomer();
                if(customer == null) return;
                ChoiceItem selectedItem = choiceBox.getItemAt(choiceBox.getSelectedIndex());
                if(selectedItem.validCustomer(customer.getType())){
                    customerPanel.setId(customer.getId());
                    customerPanel.setItsName(customer.getName());
                } else {
                    new InfoWindow("��ѡ����ȷ�Ŀͻ�@_@");
                    return;
                }
            }
        };
    }
    
    private MouseListener chooseAccount(){
        return new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                AccountVO account = new AccountChooseWin().getAccount();
                if(account == null) return;
                accountPanel.setId(account.getNumber());
                accountPanel.setItsName(account.getName());
            }
        };
    }

    protected void search(){
        String from = fromPanel.getText(), to = toPanel.getText();
        if(!Timetools.checkDate(from, to)){
            new InfoWindow("��������ȷ�����ڶ�@_@");
            return;
        }
        String operatorId = operatorPanel.getId();
        operatorId = operatorId.length() == 0 ? null : operatorId;
        ChoiceItem selectedItem = choiceBox.getItemAt(choiceBox.getSelectedIndex());
        table.setModel(selectedItem.search(from, to, operatorId));
    }
    
    private void viewDetails(){
        int index = table.getSelectedRow();
        if(index < 0){
            new InfoWindow("��ѡ��һ�ŵ��ݲ鿴@_@");
            return;
        }
        String id = table.getValueAt(index, 0).toString();
        generateBillViewer(id, false);
    }
    
    /**
     * �������ɵ��ݾ������ݵĴ���<br>
     * ���������{@code BusinessHistoryPanel}��ʹ���˸÷������ں�岢����
     * @param editable �õ����Ƿ�ɱ༭�����ɱ༭��Ϊ��岢���ƹ��ܣ������ɱ༭��Ϊ�鿴����
     */
    protected void generateBillViewer(String id, boolean editable){
        int index = choiceBox.getSelectedIndex();
        GetUserInterface userInfo = new UserBL();
        ChoiceItem selectedItem = choiceBox.getItemAt(index);
        BillVO bill = selectedItem.getBill(id);
        Class<? extends BillPanel> panelType = selectedItem.getPanelClass();
        Class<? extends BillVO> billType = selectedItem.getBillClass();
        if(editable) bill.setState(BillVO.SAVED);
        new BillViewer(panelType, billType, userInfo.getUser(bill.getOperator()), bill);
    }
    
    /**
     * ���������ѡ�<br>
     * �Դ�һЩ�ж��߼��������߼����Լ���ȡ�����Ϣ���߼�������ڲ�����ĳ��󷽷�
     */
    private abstract class ChoiceItem{

        private String info;
        
        private ChoiceItem(String info){
            this.info = info;
        }
        
        public abstract boolean validOperator(UserType type);
        
        public abstract boolean validCustomer(int type);
        
        public abstract MyTableModel search(String from, String to, String operatorId);
        
        public abstract JPanel getTypePanel();
        
        public abstract BillVO getBill(String id);
        
        public abstract Class<? extends BillPanel> getPanelClass();
        
        public abstract Class<? extends BillVO> getBillClass();
        
        @Override
        public String toString(){
            return info;
        }

    }
    
}
