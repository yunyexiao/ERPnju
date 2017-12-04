package presentation.dataui.customerui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blservice.CustomerBLService;
import blservice.DataBLService;
import presentation.component.MyTableModel;
import presentation.dataui.DataPanel;
import presentation.dataui.commodityui.AddCommodityWindow;
import presentation.dataui.commodityui.SearchCommodityWindow;
import presentation.dataui.userui.UpdateUserWindow;

public class CustomerDataPanel extends DataPanel{
	
    private CustomerBLService customerBL;

	public CustomerDataPanel(CustomerBLService customerBL, ActionListener closeListener) {
		super(customerBL, closeListener);
		this.customerBL = customerBL;
	}

	@Override
	protected ActionListener getAddListener() {
		 return new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                new AddCustomerWindow(customerBL);
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
                new UpdateCustomerWindow(customerBL, tableModel.getValueAtRow(table.getSelectedRow()));
                updateTable();
            }
            
        };
	}

	@Override
	protected ActionListener getSearchListener() {
		return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel model = new SearchCustomerWindow(customerBL).getModel();
                if(model != null) table.setModel(model);
            }
            
        };
	}

}
