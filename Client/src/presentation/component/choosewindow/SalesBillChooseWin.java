package presentation.component.choosewindow;

import java.awt.Component;
import java.awt.Container;

import blservice.billblservice.SalesBillBLService;
import businesslogic.SalesBillBL;
import presentation.component.DateChooser;
import presentation.component.MyTableModel;
import presentation.tools.TableTools;
import vo.billvo.SalesBillVO;

public class SalesBillChooseWin extends ChooseWindow {
    
    private String[] data;
    private SalesBillBLService salesBillBl;
    private Container searchPanel;
    private DateChooser dateChooser;
    
    public SalesBillChooseWin(){
        super();
    }

    @Override
    public void init() {
        searchPanel = searchTypeBox.getParent();
        dateChooser = DateChooser.getInstance();

        salesBillBl = new SalesBillBL();
        setTypes(new String[]{"按编号搜索", "按时间搜索"});
        searchTypeBox.addItemListener(e -> handleItemChanged());
        MyTableModel model = salesBillBl.getFinishedBills();
        table.setModel(model);
        table.setDragEnabled(false);
        TableTools.autoFit(table);
        frame.setTitle("选择源销售单");
        frame.setVisible(true);
    }

    @Override
    protected void yesAction() {
        int index = table.getSelectedRow();
        if(index >= 0){
            String id = (String)table.getValueAt(index, 1);
            SalesBillVO bill = salesBillBl.getBill(id);
            System.out.println(bill.getBeforeDiscount());
            System.out.println(bill.getSum());
            double discountRate = bill.getSum() / bill.getBeforeDiscount();
            data = new String[]{id, String.format("%.4f", discountRate)};
            frame.dispose();
        }
    }
    
    public String[] getSalesBillInfo(){
        return data;
    }

	@Override
	protected void searchAction() {
		String type = searchTypeBox.getSelectedItem().toString()
		     , key = keyField.getText();
		if(key.length() == 0){
		    table.setModel(salesBillBl.getFinishedBills());
		} else {
		    table.setModel(salesBillBl.search(type, key));
		}
	}

	private void handleItemChanged(){
	    String selected = searchTypeBox.getItemAt(searchTypeBox.getSelectedIndex());
        Component[] components = searchPanel.getComponents();
	    if(selected.equals("按编号搜索")){
	        int index = indexOf(components, dateChooser);
	        if(index < 0) return;
	        keyField.setText("");
	        searchPanel.remove(dateChooser);
	        searchPanel.add(keyField, index);
	    } else {
	        int index = indexOf(components, keyField);
	        if(index < 0) return;
	        dateChooser = DateChooser.getInstance();
	        searchPanel.remove(keyField);
	        searchPanel.add(dateChooser, index);
	    }
	    searchPanel.repaint();
	}
	
	private int indexOf(Component[] components, Component c){
	    for(int i = 0; i < components.length; i++){
	        if(components[i].equals(c)){
	            return i;
	        }
	    }
	    return -1;
	}

}
