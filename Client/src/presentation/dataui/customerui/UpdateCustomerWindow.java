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
        centerPanel = new InputCustomerPanel(data);
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
