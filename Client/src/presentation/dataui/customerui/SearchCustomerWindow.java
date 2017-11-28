package presentation.dataui.customerui;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import blservice.CustomerBLService;
import presentation.dataui.SearchWindow;

public class SearchCustomerWindow extends SearchWindow{

	public SearchCustomerWindow(CustomerBLService customerBl) {
		super(customerBl);
	}

	@Override
	protected ButtonGroup initTypeGroup() {
        JRadioButton idRadioButton = new JRadioButton("°´±àºÅËÑË÷");
        JRadioButton keyRadioButton = new JRadioButton("°´¹Ø¼ü×ÖËÑË÷");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(idRadioButton);
        typeGroup.add(keyRadioButton);
        return typeGroup;
	}

}
