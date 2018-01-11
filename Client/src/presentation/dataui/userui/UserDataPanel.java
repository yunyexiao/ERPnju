package presentation.dataui.userui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import blservice.UserBLService;
import presentation.component.InfoWindow;
import presentation.dataui.DataPanelInterface;
import vo.MyTableModel;

public class UserDataPanel extends JPanel implements DataPanelInterface{
	private JTable table = new JTable();
	private UserBLService userBL;

    public UserDataPanel(UserBLService userBL) {
        this.userBL = userBL;
    }

    @Override
    public void addAction() {
    	new AddUserWindow(userBL);
    	table.setModel(userBL.update());
    }

    @Override
    public void updateAction() {
    	MyTableModel tableModel = (MyTableModel)table.getModel();
        if (table.getSelectedRow() != -1) {
        	new UpdateUserWindow(userBL, tableModel.getValueAtRow(table.getSelectedRow()),false);
        	table.setModel(userBL.update());
        } else new InfoWindow("��ѡ����Ҫ�޸ĵ��û�");
    }
    
    @Override
    public void searchAction() {
    	MyTableModel model = new SearchUserWindow(userBL).getModel();
    	if(model != null)table.setModel(model);
    }

	@Override
	public void deleteAction() {
		int index = table.getSelectedRow();
	    if(index < 0) {new InfoWindow("��ѡ����Ҫɾ������Ʒ��Ϣ");return;}
		int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������Ʒ��Ϣ��", "��ʾ", JOptionPane.YES_NO_OPTION);
		if (response == 0) {
		    String id = (String)((MyTableModel)table.getModel()).getValueAt(index, 0);
			if (userBL.delete(id)) new InfoWindow("ѡ�е���Ʒ��Ϣ�ѳɹ�ɾ��");
			table.setModel(userBL.update());
		}
	}
}
