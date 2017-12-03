package presentation.billui;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import layout.TableLayout;
import presentation.billui.choosewindow.CommodityChooseWin;
import vo.CommodityVO;

/**
 * 输入商品的面板
 * @author 恽叶霄
 */
class InputCommodityInfoWin {
    
    private JTextField[] fields; 
    private JDialog frame;
    private String[] rowData;
    private String[] labelTexts = {"编号", "名称", "型号", "库存"
                , "单价", "数量", "总价", "备注"};

    public InputCommodityInfoWin() {
        frame = new JDialog();
        frame.setTitle("填写交易信息");
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
        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> handleOk());
        JButton cancelButton = new JButton("取消");
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
            fields[i] = new JTextField(10);
            fields[i].setEditable(false);
            centerPanel.add(labels[i], "1 " + (2 * i + 1));
            centerPanel.add(fields[i], "3 " + (2 * i + 1));
        }
        fields[5].setEditable(true);
        fields[7].setEditable(true);
        fields[5].addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                // No needs to complete it.
            }

            @Override
            public void focusLost(FocusEvent e) {
                sumUp();
            }
            
        });
        
        JButton chooseButton = new JButton("选择商品");
        chooseButton.addActionListener(e -> handleChoose());
        centerPanel.add(chooseButton, "5 1");
        
        frame.add(centerPanel, BorderLayout.CENTER);
    }
    
    private void handleOk(){
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
        fields[4].setText(c.getSalePrice() + "");
    }

    private void sumUp(){
        try{
            int num = Integer.parseInt(fields[5].getText());
            double price = Double.parseDouble(fields[4].getText());
            fields[6].setText(num * price + "");
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

}
