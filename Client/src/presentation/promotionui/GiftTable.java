package presentation.promotionui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import presentation.component.MyTableModel;


@SuppressWarnings("serial")
public class GiftTable extends JTable {

    public GiftTable() {
        super();
        super.getTableHeader().setReorderingAllowed(false);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        super.setModel(initModel());
        super.addMouseListener(handleGiftEvent());
    }

    private MouseListener handleGiftEvent(){
        return new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.println(e.getClickCount());
                if(e.getClickCount() == 2){
                    int index = GiftTable.this.getSelectedRow();
                    if(index >= 0){
                        editGift(index);
                    } else {
                        addGift();
                    }
                }
            }
        };
    }
    
    private void editGift(int row){
        MyTableModel model = (MyTableModel)this.getModel();
        String[] data = new GiftInputWin(model.getValueAtRow(row)).getData();
        if(data == null) return;
        for(int i = 0; i < data.length; i++){
            model.setValueAt(data[i], row, i);
        }
    }
    
    private void addGift(){
        String[] data = new GiftInputWin().getData();
        if(data == null) return;
        int index = findId(data[0]);
        MyTableModel model = (MyTableModel)this.getModel();
        if(index < 0) model.addRow(data);
        else {
            int oldNum = Integer.parseInt(model.getValueAt(index, 4).toString());
            int newNum = oldNum + Integer.parseInt(data[4]);
            model.setValueAt(newNum, index, 4);
        }
    }
    
    private int findId(String id){
        int rowCount = this.getRowCount();
        for(int i = 0; i < rowCount; i++){
            if(this.getValueAt(i, 0).equals(id))
                return i;
        }
        return -1;
    }

    private MyTableModel initModel(){
        String[] columnNames = {"商品编号", "数量", "单价"};
        String[][] data = new String[0][3];
        return new MyTableModel(data, columnNames);
    }
    
}
