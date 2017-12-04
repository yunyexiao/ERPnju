package presentation.dataui.accountui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blservice.AccountBLService;
import blservice.DataBLService;
import presentation.component.MyTableModel;
import presentation.dataui.DataPanel;
import presentation.dataui.userui.UpdateUserWindow;

public class AccountDataPanel extends DataPanel{

	private AccountBLService accountBL;

	public AccountDataPanel(AccountBLService accountBL, ActionListener closeListener) {
		super(accountBL, closeListener);
		this.accountBL = accountBL;
	}

	@Override
	protected ActionListener getAddListener() {
		 return new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new AddAccountWindow(accountBL);
	                updateTable();
	            }
	        };
	}

	@Override
	protected ActionListener getUpdateListener() {
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel tableModel = (MyTableModel)table.getModel();
                new UpdateAccountWindow(accountBL, tableModel.getValueAtRow(table.getSelectedRow()));
                updateTable();
            }
            
        };
	}

	@Override
	protected ActionListener getSearchListener() {
		return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel model = new SearchAccountWindow(accountBL).getModel();
                if(model != null) table.setModel(model);
            }
            
        };
	}

}
