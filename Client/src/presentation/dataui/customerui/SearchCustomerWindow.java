package presentation.dataui.customerui;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import blservice.CustomerBLService;
import presentation.dataui.SearchWindow;

public class SearchCustomerWindow extends SearchWindow{

	public SearchCustomerWindow(CustomerBLService customerBl) {
		super(customerBl);
        frame.setTitle("��ѯ�ͻ�");
        frame.setSize(300, 200);
        frame.setVisible(true);
	}

	@Override
	protected ButtonGroup initTypeGroup() {
        JRadioButton idRadioButton = new JRadioButton("���������");
        JRadioButton keyRadioButton = new JRadioButton("���ؼ�������");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(idRadioButton);
        typeGroup.add(keyRadioButton);
        return typeGroup;
	}

}
