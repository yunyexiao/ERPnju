package presentation.dataui.customerui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blservice.CustomerBLService;
import presentation.component.InfoWindow;
import presentation.dataui.DataPanel;
import presentation.tools.TableTools;
import vo.MyTableModel;
import vo.UserVO;

public class CustomerDataPanel extends DataPanel{
	
    private CustomerBLService customerBL;
    private UserVO user;

	public CustomerDataPanel(UserVO user, CustomerBLService customerBL, ActionListener closeListener) {
		super(customerBL, closeListener);
		this.user = user;
		this.customerBL = customerBL;
		TableTools.autoFit(table);
	}

	@Override
	protected ActionListener getAddListener() {
		 return new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new AddCustomerWindow(user.getRank(), customerBL);
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
	            if (table.getSelectedRow() != -1) {
	                new UpdateCustomerWindow(customerBL, tableModel.getValueAtRow(table.getSelectedRow()), user.getRank());
	                updateTable();
                }
	            else new InfoWindow("请选择需要修改的客户");
            }
            
        };
	}

	@Override
	protected ActionListener getSearchListener() {
		return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel model = new SearchCustomerWindow(customerBL).getModel();
                if(model != null) {
                	table.setModel(model);
                }
            }
        };
	}

}
