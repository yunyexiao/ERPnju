package presentation.analysisui;

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

import blservice.billblservice.BillOperationService;
import blservice.billblservice.BillSearchBLService;
import businesslogic.BillOperationBL;
import businesslogic.BillSearchBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.DateChoosePanel;
import presentation.component.IdNamePanel;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.TopButtonPanel;
import presentation.component.choosewindow.UserChooseWin;
import presentation.tools.ExcelExporter;
import presentation.tools.Timetools;
import vo.UserType;
import vo.UserVO;

/**
 * �鿴��Ӫ���̱�����,����ʵ�ֻ����ĵ��ݲ鿴����ʵ���˺���Լ���岢���ƵĹ��ܣ���塢��岢���ƾ���ֱ����Ч����һ����BillOperationService����
 * @author �Ҷ��
 */
public class ViewBusinessHistoryPanel implements PanelInterface {
    
    private BillSearchBLService billSearchBl;
    private BillOperationService billOperationBl;
    
    private JPanel panel;
    private DateChoosePanel fromPanel, toPanel;
    private IdNamePanel operatorPanel, choosePanel;
    private JComboBox<String> choiceBox;
    private JTable table;
    
    private UserType type = UserType.KEEPER;

    public ViewBusinessHistoryPanel(UserVO user, ActionListener closeListener) {
        billSearchBl = new BillSearchBL();
        billOperationBl = new BillOperationBL();
        
        double[][] size = new double[][]{{TableLayout.FILL},{0.1,0.15,TableLayout.FILL}};
        TopButtonPanel buttonPanel = new TopButtonPanel();
        buttonPanel.addButton("����", new ImageIcon("resource/SearchData.png"), e->search());
		buttonPanel.addButton("����", new ImageIcon("resource/Export.png"), e->ExcelExporter.export((MyTableModel)table.getModel()));
		if (user.getType() != UserType.GM) buttonPanel.addButton("���", new ImageIcon("resource/Offset.png"), e->offsetBill());
		if (user.getType() != UserType.GM) buttonPanel.addButton("��岢����", new ImageIcon("resource/OffsetCopy.png"), e->copyBill());
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), closeListener);
		
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
        panel.add(getNorthPanel(), "0,1");
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
    
    private JPanel getNorthPanel() {
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
    private void search(){
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
        case "���絥": table.setModel(billSearchBl.filterInventoryBills(from, to, chooseId, operatorId, true));break;
        case "����": table.setModel(billSearchBl.filterInventoryBills(from, to, chooseId, operatorId, false));break;
        case "������": table.setModel(billSearchBl.filterPurchaseBills(from, to, chooseId, operatorId));break;
        case "�����˻���" : table.setModel(billSearchBl.filterPurchaseReturnBills(from, to, chooseId, operatorId));break;
        case "���۵�" : table.setModel(billSearchBl.filterSalesBills(from, to, chooseId, operatorId));break;
        case "�����˻���" : table.setModel(billSearchBl.filterSalesReturnBills(from, to, chooseId, operatorId));break;
        case "�տ" : table.setModel(billSearchBl.filterReceiptBills(from, to, chooseId, operatorId));break;
        case "���" : table.setModel(billSearchBl.filterPaymentBills(from, to, chooseId, operatorId));break;
        case "�ֽ���õ�" : table.setModel(billSearchBl.filterCashCostBills(from, to, chooseId, operatorId));break;
        }
    }
    private void offsetBill(){
        int index = table.getSelectedRow();
        if(index < 0){
            new InfoWindow("��ѡ��һ�ŵ��ݺ��@_@");
            return;
        }
        int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ���õ�����", "��ʾ", JOptionPane.YES_NO_OPTION);
        if(response == 1) return;
        String id = table.getValueAt(index, 0).toString();
        if(billOperationBl.offsetBill(id)){
            JOptionPane.showMessageDialog(null, "��嵥�ݳɹ�^_^");
            search();
        } else {
            JOptionPane.showMessageDialog(null, "��嵥��ʧ�ܣ�������@_@");
        }
    }
    private void copyBill(){
        int index = table.getSelectedRow();
        if(index < 0){
            new InfoWindow("��ѡ��һ�ŵ��ݽ��в���@_@");
            return;
        }
        new BillViewer(billOperationBl.getBillById(table.getValueAt(index, 0).toString()), true);
    }
}
