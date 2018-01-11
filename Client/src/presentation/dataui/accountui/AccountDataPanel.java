package presentation.dataui.accountui;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.AccountBLService;
import presentation.component.InfoWindow;
import presentation.dataui.DataPanelInterface;
import presentation.main.MainWindow;
import vo.MyTableModel;
import vo.UserVO;

public class AccountDataPanel extends JPanel implements DataPanelInterface{
	private JTable table = new JTable();
	private AccountBLService accountBL;
	private UserVO user = MainWindow.getUser();

	public AccountDataPanel(AccountBLService accountBL) {
		this.accountBL = accountBL;
		table.setModel(accountBL.update());
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcollpane = new JScrollPane(table);
		this.add(srcollpane);
	}
	@Override
	public void addAction() {
		if (user.getRank() != 0) {
    		new AddAccountWindow(accountBL);
    		table.setModel(accountBL.update());
    	} else new InfoWindow("����Ȩ�޲���");
	}
	@Override
	public void updateAction() {
		if (user.getRank() != 0) {
            MyTableModel tableModel = (MyTableModel)table.getModel();
            if (table.getSelectedRow() != -1) {
            	new UpdateAccountWindow(accountBL, tableModel.getValueAtRow(table.getSelectedRow()));
        		table.setModel(accountBL.update());
            }
            else new InfoWindow("��ѡ����Ҫ�޸ĵ������˻�");
    	} else new InfoWindow("����Ȩ�޲���");
	}
	@Override
	public void searchAction() {
		if (user.getRank() != 0) {
            MyTableModel model = new SearchAccountWindow(accountBL).getModel();
            if(model != null) table.setModel(model);
    	} else new InfoWindow("����Ȩ�޲���");
	}
	@Override
	public void deleteAction(){
		if (user.getRank() != 0) {
			int index = table.getSelectedRow();
		    if(index < 0) {new InfoWindow("��ѡ����Ҫɾ���������˻�");return;}
		    //���Ҳ���������
			int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������Ϣ��", "��ʾ", JOptionPane.YES_NO_OPTION);
			if (response == 0) {
			    String id = (String)((MyTableModel)table.getModel()).getValueAt(index, 0);
				if (accountBL.delete(id)) new InfoWindow("��Ϣ�ѳɹ�ɾ��");
				table.setModel(accountBL.update());
			}
    	} else new InfoWindow("����Ȩ�޲���");
	}
}
