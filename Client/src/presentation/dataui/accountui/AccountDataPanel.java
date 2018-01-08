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
	            	} else new InfoWindow("您的权限不足");
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
                    else new InfoWindow("请选择需要修改的银行账户");
            	} else new InfoWindow("您的权限不足");
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
            	} else new InfoWindow("您的权限不足");
            }
            
        };
	}

	protected ActionListener getDeleteListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (user.getRank() != 0) {
					int index = table.getSelectedRow();
				    if(index < 0) {new InfoWindow("请选择需要删除的银行账户");return;}
				    //暂且不管余额多少
					int response = JOptionPane.showConfirmDialog(null, "确认要删除此条信息？", "提示", JOptionPane.YES_NO_OPTION);
					if (response == 0) {
					    String id = (String)((MyTableModel)table.getModel()).getValueAt(index, 0);
						if (accountBL.delete(id)) new InfoWindow("信息已成功删除");
						table.setModel(accountBL.update());
					}
            	} else new InfoWindow("您的权限不足");
			    
			}			
		};
	}
}
