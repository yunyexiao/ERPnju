package presentation.promotionui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import blservice.PromotionBLService;
import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.choosewindow.CommodityChooseWin;
import vo.CommodityVO;
import vo.GroupDiscountVO;


@SuppressWarnings("serial")
public class GroupDiscountPanel extends PromotionDetailPanel {
    
    private GroupTable groupTable;
    private DetailGroupDiscountPanel panel;

    public GroupDiscountPanel(PromotionBLService promotionAdder, ActionListener closeListener) {
        super(promotionAdder, closeListener);
    }

    @Override
    protected JPanel getCenterPanel() {
        JScrollPane sp = new JScrollPane(groupTable = new GroupTable());
        groupTable.register(sp);
        
        return panel = new DetailGroupDiscountPanel(sp);
    }

    @Override
    protected boolean addPromotionImpl() {
        String id = getId(),
               from = getFromDate(),
               to = getToDate();
        ArrayList<String> comIds = getComIds();
        double discount = panel.getDiscountField().getValue();
        GroupDiscountVO promotion = new GroupDiscountVO(id, from, to, discount, comIds);
        return promotionAdder.add(promotion);
    }

    @Override
    protected boolean isFinished() {
        boolean dateValid = super.isFinished();
        if(dateValid){
            double discount = panel.getDiscountField().getValue();
            if(groupTable.getRowCount() == 0){
                new InfoWindow("������ѡ��һ����Ʒ��");
                return false;
            }
            if(discount == 0){
                new InfoWindow("����д����һ����");
                return false;
            }
            return true;
        }
        return false;
    }
    
    private ArrayList<String> getComIds(){
        MyTableModel model = (MyTableModel)groupTable.getModel();
        int size = model.getRowCount();
        ArrayList<String> comIds = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            comIds.add(model.getValueAt(i, 0).toString());
        }
        return comIds;
    }

}

@SuppressWarnings("serial")
class GroupTable extends JTable{
    
    private MouseListener chooseHandler;
    private JPopupMenu popup;
    
    public GroupTable(){
        super();
        super.getTableHeader().setReorderingAllowed(false);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        super.setModel(initModel());
        initChooseHandler();
        initPopup();
        super.addMouseListener(chooseHandler);
        super.setComponentPopupMenu(popup);
    }
    
    public void register(JScrollPane sp){
        sp.addMouseListener(chooseHandler);
        sp.setComponentPopupMenu(popup);
    }

    private void initChooseHandler(){
        chooseHandler = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() >= 2){
                    int index = GroupTable.this.getSelectedRow();
                    if(index < 0){
                        addCommodity();
                    }
                }
            }
        };
    }
    
    private void initPopup(){
        popup = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("�����Ʒ");
        addItem.addActionListener(e->addCommodity());
        JMenuItem delItem = new JMenuItem("ɾ����Ʒ");
        delItem.addActionListener(e->delCommodity(this.getSelectedRow()));
        popup.add(addItem);
        popup.add(delItem);
    }
    
    private void delCommodity(int row){
        if(row < 0)return;
        MyTableModel model = (MyTableModel)this.getModel();
        model.removeRow(row);
    }
    
    private void addCommodity(){
        String[] data = new CommodityInputWin().getData();
        if(data == null) return;
        int index = findId(data[0]);
        MyTableModel model = (MyTableModel)this.getModel();
        if(index < 0) model.addRow(data);
        else {
            new InfoWindow("��������Ѵ��ڸ���Ʒ��");
        }
    }
    
    private int findId(String id){
        int rowCount = this.getRowCount();
        for(int i = 0; i < rowCount; i++){
            if(this.getValueAt(i, 0).equals(id)){
                return i;
            }
        }
        return -1;
    }
    
    private MyTableModel initModel(){
        String[] columnNames = {"��Ʒ���", "����", "�ͺ�"};
        String[][] data = new String[0][columnNames.length];
        return new MyTableModel(data, columnNames);
    }

}

class CommodityInputWin{
    
    private JDialog frame;
    private String[] data;
    private JTextField[] fields;
    
    public CommodityInputWin(){
        super();
        frame = new JDialog();
        frame.setModal(true);
        frame.setSize(400, 300);
        frame.setLocation(300, 200);
        frame.setLayout(new BorderLayout());
        frame.add(initCenter(), BorderLayout.CENTER);
        frame.add(initSouth(), BorderLayout.SOUTH);
        frame.setTitle("��Ʒ��Ϣ");
        frame.setVisible(true);
    }
    
    public String[] getData(){
        return data;
    }
    
    private JPanel initCenter(){
        String[] labelTexts = {"��Ʒ���", "����", "�ͺ�"};

        double[][] size = {
                {-1.0, -2.0, 10.0, -1.0, -1.0},
                {-1.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0}
        };
        JPanel panel = new JPanel(new TableLayout(size));
        fields = new JTextField[labelTexts.length];
        for(int i = 0; i < fields.length; i++){
            panel.add(new JLabel(labelTexts[i]), "1 " + (i * 2 + 1));
            panel.add(fields[i] = new JTextField(10), "3 " + (i * 2 + 1));
            fields[i].addMouseListener(chooseCommodity());
            fields[i].setEditable(false);
        }
        return panel;
    }
    
    private JPanel initSouth(){
        JButton okButton = new JButton("ȷ��");
        okButton.addActionListener(e->ok());
        JButton cancelButton = new JButton("ȡ��");
        cancelButton.addActionListener(e->cancel());

        double[][] size = {
                {-1.0, -2.0, 10.0, -2.0, 20.0}, 
                {10.0, -2.0, 10.0}
        };
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(okButton, "1 1");
        panel.add(cancelButton, "3 1");
        return panel;
    }
    
    private void ok(){
        data = new String[fields.length];
        for(int i = 0; i < fields.length; i++){
            data[i] = fields[i].getText();
        }
        if(data[0].length() == 0){
            data = null;
            new InfoWindow("��ѡ����Ʒ��");
            return;
        }
        frame.dispose();
    }
    
    private void cancel(){
        frame.dispose();
    }
    
    private MouseListener chooseCommodity(){
        return new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                CommodityVO commodity = new CommodityChooseWin().getCommodity();
                if(commodity == null) return;
                fields[0].setText(commodity.getId());
                fields[1].setText(commodity.getName());
                fields[2].setText(commodity.getType());
            }
        };
    }

}
