package presentation.bill;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.billblservice.BillExamineService;
import blservice.billblservice.BillOperationService;
import blservice.billblservice.BillSearchBLService;
import businesslogic.BillExamineBL;
import businesslogic.BillOperationBL;
import businesslogic.BillSearchBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.CloseListener;
import presentation.component.DateChoosePanel;
import presentation.component.IdNamePanel;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.TopButtonPanel;
import presentation.component.choosewindow.UserChooseWin;
import presentation.main.MainWindow;
import presentation.tools.ExcelExporter;
import presentation.tools.Timetools;
import vo.UserType;
import vo.UserVO;
import vo.billvo.BillVO;

/**
 * �����������
 * @author ���
 */
public class BillExaminePanel implements PanelInterface {
    
    private BillSearchBLService billSearchBl;
    private BillOperationService billOperationBl;
    private BillExamineService billExamineBl;

	private JPanel panel;
    private DateChoosePanel fromPanel, toPanel;
    private IdNamePanel operatorPanel, choosePanel;
    private JComboBox<String> choiceBox;
    protected JTable table;
    
    private UserType type = UserType.KEEPER;

    public BillExaminePanel(MainWindow mainWindow, ActionListener closeListener) {
        billSearchBl = new BillSearchBL();
        billOperationBl = new BillOperationBL();

        double[][] size = new double[][]{{TableLayout.FILL},{0.1,0.15,TableLayout.FILL}};
    	TopButtonPanel buttonPanel = new TopButtonPanel();
        buttonPanel.addButton("����", new ImageIcon("resource/SearchData.png"), e->search());
    	buttonPanel.addButton("ͨ��", new ImageIcon("resource/Examine.png"), e->pass());
    	buttonPanel.addButton("��ͨ��", new ImageIcon("resource/NotPass.png"), e->notPass());
		buttonPanel.addButton("����ΪExcel", new ImageIcon("resource/Save.png"), e -> ExcelExporter.export((MyTableModel)table.getModel()));
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), new CloseListener(mainWindow));
		
		table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if(e.getClickCount() == 2) {
        			int index = table.getSelectedRow();
        	        new BillViewer(billOperationBl.getBillById(table.getValueAt(index, 0).toString()), false);
        		}
        	}
        });
        
        panel = new JPanel(new TableLayout(size));
		panel.add(buttonPanel.getPanel(), "0,0");
        panel.add(getNorthPanel(closeListener), "0,1");
        panel.add(sp, "0,2");
 
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
        choiceBox = new JComboBox<String>(new String[]{"���絥","����","������","�����˻���","���۵�","�����˻���","�տ","���","�ֽ���õ�"});
        choiceBox.addItemListener(e -> itemChanged(e));
        operatorPanel = new IdNamePanel("����Ա", 7, 7);
        operatorPanel.addMouseListener(chooseOperator());
        choosePanel = new IdNamePanel("��Ʒ", 7, 7);


        
        double[][] size = {
                {20.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0},
                {10.0, -2.0, 10.0, -2.0, -1.0}
        };
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(fromPanel, "1 1");
        panel.add(toPanel, "1 3");
        panel.add(choiceBox, "3 1");
        panel.add(operatorPanel, "3 3");
        panel.add(choosePanel, "5 1");
        return panel;
    }

    private void itemChanged(ItemEvent e){
        if(e.getStateChange() == ItemEvent.DESELECTED) return;
        switch ((String)choiceBox.getSelectedItem()) {
        case "���絥": choosePanel.setLabelText("��Ʒ");type = UserType.KEEPER;break;
        case "����": choosePanel.setLabelText("��Ʒ");type = UserType.KEEPER;break;
        case "������": choosePanel.setLabelText("�ͻ�");type = UserType.SALESMAN;break;
        case "�����˻���" : choosePanel.setLabelText("�ͻ�");type = UserType.SALESMAN;break;
        case "���۵�" : choosePanel.setLabelText("�ͻ�");type = UserType.SALESMAN;break;
        case "�����˻���" : choosePanel.setLabelText("�ͻ�");type = UserType.SALESMAN;break;
        case "�տ" : choosePanel.setLabelText("�˻�");type = UserType.ACCOUNTANT;break;
        case "���" : choosePanel.setLabelText("�˻�");type = UserType.ACCOUNTANT;break;
        case "�ֽ���õ�" : choosePanel.setLabelText("�˻�");type = UserType.ACCOUNTANT;break;
        }
        operatorPanel.setId(""); 
        operatorPanel.setItsName("");
        choosePanel.setId("");
        choosePanel.setItsName("");
    }

    private MouseListener chooseOperator(){
    	 return new MouseAdapter(){
             @Override
             public void mouseClicked(MouseEvent e){
                 UserVO operator = new UserChooseWin().getUser();
                 if(operator == null) return;
                 if(type == operator.getType()){
                     operatorPanel.setId(operator.getId());
                     operatorPanel.setItsName(operator.getName());
                 } else {
                     new InfoWindow("��ѡ����ȷ�Ĳ���Ա");
                 }
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
        String chooseId = choosePanel.getId();
        chooseId = chooseId.length() == 0 ? null : chooseId;
        switch ((String)choiceBox.getSelectedItem()) {
        case "���絥": table.setModel(billSearchBl.filterInventoryBills(from, to, chooseId, operatorId, true, BillVO.COMMITED));break;
        case "����": table.setModel(billSearchBl.filterInventoryBills(from, to, chooseId, operatorId, false, BillVO.COMMITED));break;
        case "������": table.setModel(billSearchBl.filterPurchaseBills(from, to, chooseId, operatorId, BillVO.COMMITED));break;
        case "�����˻���" : table.setModel(billSearchBl.filterPurchaseReturnBills(from, to, chooseId, operatorId, BillVO.COMMITED));break;
        case "���۵�" : table.setModel(billSearchBl.filterSalesBills(from, to, chooseId, operatorId, BillVO.COMMITED));break;
        case "�����˻���" : table.setModel(billSearchBl.filterSalesReturnBills(from, to, chooseId, operatorId, BillVO.COMMITED));break;
        case "�տ" : table.setModel(billSearchBl.filterReceiptBills(from, to, chooseId, operatorId, BillVO.COMMITED));break;
        case "���" : table.setModel(billSearchBl.filterPaymentBills(from, to, chooseId, operatorId, BillVO.COMMITED));break;
        case "�ֽ���õ�" : table.setModel(billSearchBl.filterCashCostBills(from, to, chooseId, operatorId, BillVO.COMMITED));break;
        }
    }
    
    private void pass() {
    	billExamineBl = new BillExamineBL();
        int index = table.getSelectedRow();
        if(index < 0){
            new InfoWindow("��ѡ��һ�ŵ�������@_@");
            return;
        }
        String id = table.getValueAt(index, 0).toString();
        if(billExamineBl.examineBill(id)){
            JOptionPane.showMessageDialog(null, "ͨ����������^_^");
        }else {
            JOptionPane.showMessageDialog(null, "����ʧ�ܣ�������@_@");
        }
    }
    
    private void notPass() {
    	billExamineBl = new BillExamineBL();
        int index = table.getSelectedRow();
        if(index < 0){
            new InfoWindow("��ѡ��һ�ŵ�������@_@");
            return;
        }
        String id = table.getValueAt(index, 0).toString();
        if (billExamineBl.notPassBill(id)) {
        	JOptionPane.showMessageDialog(null, "��ͨ����������^_^");
        }else {
            JOptionPane.showMessageDialog(null, "����ʧ�ܣ�������@_@");
        }
    }
}
