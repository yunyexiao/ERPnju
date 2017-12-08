package presentation.component.choosewindow;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;

import blservice.billblservice.SalesBillBLService;
import businesslogic.SalesBillBL;
import presentation.component.DateChooser;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.tools.TableTools;
import vo.billvo.SalesBillVO;

public class SalesBillChooseWin extends ChooseWindow {
    
    private SalesBillBLService salesBillBl;
    private Container searchPanel;
    private DateChooser dateFromChooser, dateToChooser;
    private JTextField dateFromField, dateToField;
    private SalesBillVO bill;
    
    public SalesBillChooseWin(String customerId){
        super();
        MyTableModel model = salesBillBl.getFinishedBills(customerId);
        table.setModel(model);
        table.setDragEnabled(false);
        TableTools.autoFit(table);
        frame.setTitle("ѡ��Դ���۵�");
        frame.setVisible(true);
    }

    @Override
    public void init() {
        searchPanel = searchTypeBox.getParent();
        initDateLabels();

        salesBillBl = new SalesBillBL();
        setTypes(new String[]{"���������", "��ʱ������"});
        searchTypeBox.addItemListener(e -> handleItemChanged());
    }

    public SalesBillVO getSalesBill(){
        return bill;
    }

    @Override
    protected void yesAction() {
        int index = table.getSelectedRow();
        if(index >= 0){
            String id = (String)table.getValueAt(index, 1);
            bill = salesBillBl.getBill(id);
            frame.dispose();
        }
    }
    
	@Override
	protected void searchAction() {
		String type = searchTypeBox.getSelectedItem().toString();
		if(type.equals("���������")){
		    String key = keyField.getText();
		    if(key.length() == 0){
		        table.setModel(salesBillBl.getFinishedBills());
		    } else {
                table.setModel(salesBillBl.search(type, key));
		    }
		} else {
		    String from = dateFromField.getText(), to = dateToField.getText();
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    try {
                Date fromDate = dateFormat.parse(from), toDate = dateFormat.parse(to);
                if(fromDate.after(toDate)){
                    new InfoWindow("��ѡ����ȷ������@_@");
                    return;
                }
                table.setModel(salesBillBl.getBillsByDate(from, to));
            } catch (ParseException e) {
                e.printStackTrace();
            }
		}
		TableTools.autoFit(table);
	}

	private void initDateLabels(){
        dateFromField = new JTextField("��ʼ����");
        dateToField = new JTextField("��������");
        dateFromField.setEditable(false);
        dateToField.setEditable(false);
        dateFromChooser = DateChooser.getInstance();
        dateToChooser = DateChooser.getInstance();
        dateFromChooser.register(dateFromField);
        dateToChooser.register(dateToField);
	}

	private void handleItemChanged(){
	    String selected = searchTypeBox.getItemAt(searchTypeBox.getSelectedIndex());
        Component[] components = searchPanel.getComponents();
	    if(selected.equals("���������")){
	        int index = indexOf(components, dateFromField);
	        if(index < 0) return;
	        keyField.setText("");
	        searchPanel.remove(dateFromField);
	        searchPanel.remove(dateToField);
	        searchPanel.add(keyField, index);
	    } else {
	        int index = indexOf(components, keyField);
	        if(index < 0) return;
            int x = keyField.getX(),
                   y = keyField.getY(),
                   width = keyField.getWidth() / 2,
                   height = keyField.getHeight();
            dateFromField.setLocation(x, y);
            dateFromField.setSize(width, height);
            dateToField.setLocation(x + width, y);
            dateToField.setSize(width, height);

	        searchPanel.remove(keyField);
	        searchPanel.add(dateFromField, index);
	        searchPanel.add(dateToField, index + 1);
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
