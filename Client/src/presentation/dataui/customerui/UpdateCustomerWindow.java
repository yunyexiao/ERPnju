package presentation.dataui.customerui;

import java.awt.BorderLayout;

import blservice.CustomerBLService;
import presentation.dataui.FatherWindow;
import presentation.dataui.customerui.InputCustomerPanel;
import vo.CustomerVO;

public class UpdateCustomerWindow extends FatherWindow{
	
	private InputCustomerPanel centerPanel;
    private CustomerBLService customerBl;

	public UpdateCustomerWindow(CustomerBLService customerBl, String[] data) {
		super();
		this.customerBl = customerBl;
        frame.setTitle("修改客户");
        String[] customerData = {data[0], data[1], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11]};
        centerPanel = new InputCustomerPanel(customerData);
        frame.add(centerPanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
	}

    @Override
    protected boolean taskFinished() {
        CustomerVO customer = centerPanel.getCustomerVO();
        return customer != null && customerBl.change(customer);
    }

    @Override
    protected String getSuccessMsg() {
        return "用户信息已更新";
    }
}
