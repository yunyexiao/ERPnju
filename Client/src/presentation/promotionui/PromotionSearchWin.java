package presentation.promotionui;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import blservice.PromotionBLService;
import layout.TableLayout;
import presentation.component.DateChooser;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.tools.IntField;
import presentation.tools.StyleTools;
import presentation.tools.Timetools;
import vo.PromotionVO;

public class PromotionSearchWin {
    
    private JDialog frame;
    private JComboBox<String> typeBox;
    private JTextField idField, fromField, toField, dateField;
    private IntField rankField;
    private int mode;
    private PromotionBLService promotionBl;
    private MyTableModel result;
    
    public PromotionSearchWin(PromotionBLService promotionBl){
        super();
        this.promotionBl = promotionBl;
        frame = new JDialog();
        StyleTools.setNimbusLookAndFeel();
        frame.setModal(true);
        frame.setSize(600, 400);
        frame.setLocation(300, 200);
        frame.setLayout(new BorderLayout());
        frame.add(getCenterPanel(), BorderLayout.CENTER);
        frame.add(getSouthPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    public MyTableModel getResult(){
        return result;
    }

    private JPanel getCenterPanel(){
        typeBox = new JComboBox<>();
        typeBox.addItem("�ȼ���������");
        typeBox.addItem("��Ʒ��Ͻ���");
        typeBox.addItem("�����ܶ����");
        typeBox.addItemListener(e->handleTypeChosen());
        idField = new JTextField(10);
        fromField = new JTextField(10);
        DateChooser.getInstance().register(fromField);
        toField = new JTextField(10);
        DateChooser.getInstance().register(toField);
        dateField = new JTextField(10);
        DateChooser.getInstance().register(dateField);
        rankField = new IntField(10);
        
        double[][] size = {
                {-1.0, -2.0, 10.0, -2.0, -1.0},
                {-1.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0}
        };
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(new JLabel("������������"), "1 1");
        panel.add(typeBox, "3 1");
        panel.add(new JLabel("����ģʽ"), "1 3");
        panel.add(getButtonGroupPanel(), "3 3");
        panel.add(new JLabel("���"), "1 5");
        panel.add(idField, "3 5");
        panel.add(new JLabel("��������"), "1 7");
        panel.add(fromField, "3 7");
        panel.add(new JLabel("��������"), "1 9");
        panel.add(toField, "3 9");
        panel.add(new JLabel("����"), "1 11");
        panel.add(dateField, "3 11");
        panel.add(new JLabel("�˿͵ȼ�"), "1 13");
        panel.add(rankField, "3 13");
        return panel;
    }
    
    private JPanel getButtonGroupPanel(){
        JRadioButton idRB = new JRadioButton("���������");
        idRB.addActionListener(e->toIdMode());
        idRB.setSelected(true);
        toIdMode();
        JRadioButton periodRB = new JRadioButton("����һ�������ڵĴ�������");
        periodRB.addActionListener(e->toPeriodMode());
        JRadioButton dateRB = new JRadioButton("����ĳ�յ���Ч��������");
        dateRB.addActionListener(e->toDateMode());
        ButtonGroup group = new ButtonGroup();
        group.add(idRB);
        group.add(periodRB);
        group.add(dateRB);
        
        double[][] size = {{-2.0, 20.0, -2.0, 20.0, -2.0}, {-2.0}};
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(idRB, "0 0");
        panel.add(periodRB, "2 0");
        panel.add(dateRB, "4 0");
        return panel;
    }
    
    private JPanel getSouthPanel(){
        JButton okButton = new JButton("����");
        okButton.addActionListener(e->ok());
        JButton cancelButton = new JButton("ȡ��");
        cancelButton.addActionListener(e->cancel());
        
        double[][] size = {{-1.0, -2.0, 10.0, -2.0, 50.0}, {10.0, -2.0, 10.0}};
        JPanel panel = new JPanel(new TableLayout(size));
        panel.add(okButton, "1 1");
        panel.add(cancelButton, "3 1");
        return panel;
    }
    
    private void ok(){
        int selected = typeBox.getSelectedIndex();
        switch(mode){
            case 0: 
                searchById();
                break;
            case 1: 
                searchByPeriod(selected);
                break;
            case 2: 
                searchByDate(selected);
                break;
        }
    }
    
    private void searchById(){
        String id = idField.getText();
        PromotionVO promotion = promotionBl.findById(id);
        String[] columnNames = {"���", "��Ч����", "ʧЧ����"};
        String[][] data = {{promotion.getId(), promotion.getFromDate(), promotion.getToDate()}};
        result = new MyTableModel(data, columnNames);
        frame.dispose();
    }
    
    private void searchByPeriod(int selected){
        String from = fromField.getText(), to = toField.getText();
        if(!Timetools.checkDate(from, to)){
            new InfoWindow("���ڶθ�ʽ����ȷ��");
            return;
        }
        switch(selected){
            case 0:
                int rank = rankField.getValue();
                result = promotionBl.searchRankPromotion(from, to, rank);
                break;
            case 1:
                result = promotionBl.searchGroupDiscount(from, to);
                break;
            case 2:
                result = promotionBl.searchSumPromotion(from, to);
                break;
        }
        frame.dispose();
    }
    
    private void searchByDate(int selected){
        String date = dateField.getText();
        try{
            new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }catch(Exception e){
            new InfoWindow("��������ȷ�����ڡ�");
            return;
        }
        switch(selected){
            case 0:
                int rank = rankField.getValue();
                result = promotionBl.searchRankPromotion(date, rank);
                break;
            case 1:
                result = promotionBl.searchGroupDiscount(date);
                break;
            case 2:
                result = promotionBl.searchSumPromotion(date);
                break;
        }
        frame.dispose();
    }
    
    private void cancel(){
        frame.dispose();
    }
    
    private void toIdMode(){
        mode = 0;
        typeBox.setEnabled(false);
        fromField.setEnabled(false);
        toField.setEnabled(false);
        dateField.setEnabled(false);
        idField.setEnabled(true);
    }

    private void toPeriodMode(){
        mode = 1;
        dateField.setEnabled(false);
        idField.setEnabled(false);
        typeBox.setEnabled(true);
        fromField.setEnabled(true);
        toField.setEnabled(true);
    }
    
    private void toDateMode(){
        mode = 2;
        idField.setEnabled(false);
        fromField.setEnabled(false);
        toField.setEnabled(false);
        typeBox.setEnabled(true);
        dateField.setEnabled(true);
    }

    private void handleTypeChosen(){
        int index = typeBox.getSelectedIndex();
        if(index == 0){
            rankField.setEnabled(true);
        } else {
            rankField.setEnabled(false);
        }
    }

}
