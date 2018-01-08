package presentation.dataui.customerui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.CustomerBLService;
import presentation.component.InfoWindow;
import presentation.dataui.DataPanelInterface;
import presentation.main.MainWindow;
import presentation.tools.TableTools;
import vo.MyTableModel;
import vo.UserVO;

public class CustomerDataPanel extends JPanel implements DataPanelInterface{
	private JTable table = new JTable();
    private CustomerBLService customerBL;
    private UserVO user = MainWindow.getUser();

	public CustomerDataPanel(CustomerBLService customerBL) {
		this.customerBL = customerBL;
		table.setModel(customerBL.update());
		table.getTableHeader().setReorderingAllowed(false);
		TableTools.autoFit(table);
		JScrollPane srcollpane = new JScrollPane(table);
		this.add(srcollpane);
	}

	@Override
	public void addAction() {
		new AddCustomerWindow(user.getRank(), customerBL);
		table.setModel(customerBL.update());
		TableTools.autoFit(table);
	}

	@Override
	public void updateAction() {
		MyTableModel tableModel = (MyTableModel)table.getModel();
        if (table.getSelectedRow() != -1) {
            new UpdateCustomerWindow(customerBL, tableModel.getValueAtRow(table.getSelectedRow()), user.getRank());
            table.setModel(customerBL.update());
    		TableTools.autoFit(table);
        }
        else new InfoWindow("��ѡ����Ҫ�޸ĵĿͻ�");
	}

	@Override
	public void searchAction() {
		MyTableModel model = new SearchCustomerWindow(customerBL).getModel();
        if(model != null) table.setModel(model);
 		TableTools.autoFit(table);
	}

	@Override
	public void deleteAction() {
		int index = table.getSelectedRow();
	    if(index < 0) {new InfoWindow("��ѡ����Ҫɾ���Ŀͻ���Ϣ");return;}
		int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ�������ͻ���Ϣ��", "��ʾ", JOptionPane.YES_NO_OPTION);
		if (response == 0) {
		    String id = (String)((MyTableModel)table.getModel()).getValueAt(index, 0);
			if (customerBL.delete(id)) new InfoWindow("ѡ�еĿͻ���Ϣ�ѳɹ�ɾ��");
			table.setModel(customerBL.update());
			TableTools.autoFit(table);
		}
	}
}
