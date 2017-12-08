package presentation.billui;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import layout.TableLayout;
import presentation.component.choosewindow.CommodityChooseWin;
import presentation.tools.DoubleField;
import presentation.tools.IntField;
import vo.CommodityVO;

/**
 * ������Ʒ�����
 * @author �Ҷ��
 */
class InputCommodityInfoWin {
    
    private JTextField[] fields; 
    private JDialog frame;
    private String[] rowData;
    private String[] labelTexts = {"���", "����", "�ͺ�", "���"
                , "����", "����", "�ܼ�", "��ע"};

    public InputCommodityInfoWin() {
        frame = new JDialog();
        frame.setTitle("��д������Ϣ");
        frame.setModal(true);
        frame.setSize(400, 430);
        frame.setLocation(400, 200);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        initCenter();
        initSouth();
        frame.setVisible(true);
    }

    public String[] getRowData(){
        return rowData;
    }

    private void initSouth() {
        JButton okButton = new JButton("ȷ��");
        okButton.addActionListener(e -> handleOk());
        JButton cancelButton = new JButton("ȡ��");
        cancelButton.addActionListener(e -> frame.dispose());

        double[][] size = {
                {-1.0, -2.0, 10.0, -2.0, 20.0},
                {10.0, -2.0, 10.0}
        };
        JPanel southPanel = new JPanel(new TableLayout(size));
        southPanel.add(okButton, "1 1");
        southPanel.add(cancelButton, "3 1");
        
        frame.add(southPanel, BorderLayout.SOUTH);
    }

    private void initCenter() {
        double[] columnSize = {-1.0, -2.0, 10.0, -2.0, 10.0, -2.0, -1.0};
        double[] rowSize = new double[labelTexts.length * 2 + 1];
        rowSize[0] = 20.0;
        for(int i = 0; i < labelTexts.length; i++){
            rowSize[2 * i + 1] = -2.0;
            rowSize[2 * i + 2] = 10.0;
        }
        JPanel centerPanel = new JPanel(new TableLayout(
            new double[][]{columnSize, rowSize}));

        JLabel[] labels = new JLabel[labelTexts.length];
        fields = new JTextField[labelTexts.length];
        for(int i = 0; i < labelTexts.length; i++){
            labels[i] = new JLabel(labelTexts[i]);
            if(i == 5){
                fields[i] = new IntField(10);
            } else if(i == 4){
                fields[i] = new DoubleField(10);
            } else {
                fields[i] = new JTextField(10);
            }
            fields[i].setEditable(false);
            centerPanel.add(labels[i], "1 " + (2 * i + 1));
            centerPanel.add(fields[i], "3 " + (2 * i + 1));
        }
        FocusListener l = new FocusAdapter(){
            @Override
            public void focusLost(FocusEvent e) {
                sumUp();
            }
        };
        fields[4].setEditable(true);
        fields[4].addFocusListener(l);
        fields[5].setEditable(true);
        fields[7].setEditable(true);
        fields[5].addFocusListener(l);
        
        JButton chooseButton = new JButton("ѡ����Ʒ");
        chooseButton.addActionListener(e -> handleChoose());
        centerPanel.add(chooseButton, "5 1");
        
        frame.add(centerPanel, BorderLayout.CENTER);
    }
    
    private void handleOk(){
        if(fields[0].getText().length() == 0) return;
        if(fields[6].getText().length() == 0){
            if(!sumUp())return;
        }
        rowData = new String[fields.length];
        for(int i = 0; i < fields.length; i++){
            rowData[i] = fields[i].getText();
        }
        frame.dispose();
    }

    private void handleChoose(){
        CommodityVO c = new CommodityChooseWin().getCommodity();
        if(c == null) return;
        fields[0].setText(c.getId());
        fields[1].setText(c.getName());
        fields[2].setText(c.getType());
        fields[3].setText(c.getStore());
        // set default value for priceField(fields[4])
        if(fields[5].getText().length() > 0) sumUp();
    }

    private boolean sumUp(){
        try{
            int num = ((IntField)fields[5]).getValue();
            double price = Double.parseDouble(fields[4].getText());
            fields[6].setText(num * price + "");
            return true;
        }catch(NumberFormatException e){
            e.printStackTrace();
            return false;
        }
    }

}
