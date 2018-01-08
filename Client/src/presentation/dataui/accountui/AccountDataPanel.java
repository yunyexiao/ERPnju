package presentation.dataui.accountui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import blservice.AccountBLService;
import presentation.component.InfoWindow;
import presentation.dataui.DataPanel;
import presentation.main.MainWindow;
import vo.MyTableModel;
import vo.UserVO;

public class AccountDataPanel extends DataPanel{

	private AccountBLService accountBL;
	private UserVO user = MainWindow.getUser();

	public AccountDataPanel(AccountBLService accountBL, ActionListener closeListener) {
		super(accountBL, closeListener);
		this.accountBL = accountBL;
	}

	@Override
	protected ActionListener getAddListener() {
		 return new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	if (user.getRank() != 0) {
	            		new AddAccountWindow(accountBL);
		                updateTable();
	            	} else new InfoWindow("����Ȩ�޲���");
	            }
	        };
	}

	@Override
	protected ActionListener getUpdateListener() {
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (user.getRank() != 0) {
                    MyTableModel tableModel = (MyTableModel)table.getModel();
                    if (table.getSelectedRow() != -1) {
                    	new UpdateAccountWindow(accountBL, tableModel.getValueAtRow(table.getSelectedRow()));
                        updateTable();
                    }
                    else new InfoWindow("��ѡ����Ҫ�޸ĵ������˻�");
            	} else new InfoWindow("����Ȩ�޲���");
            }
            
        };
	}

	@Override
	protected ActionListener getSearchListener() {
		return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (user.getRank() != 0) {
                    MyTableModel model = new SearchAccountWindow(accountBL).getModel();
                    if(model != null) table.setModel(model);
            	} else new InfoWindow("����Ȩ�޲���");
            }
            
        };
	}

	protected ActionListener getDeleteListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
		};
	}
}
