package presentation.dataui.accountui;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import blservice.AccountBLService;
import presentation.dataui.SearchWindow;

public class SearchAccountWindow extends SearchWindow{

	public SearchAccountWindow(AccountBLService accountBl) {
		super(accountBl);
        frame.setTitle("��ѯ�˻�");
        frame.setSize(300, 200);
        frame.setVisible(true);
	}

	@Override
	protected ButtonGroup initTypeGroup() {
        JRadioButton idRadioButton = new JRadioButton("���˺�����");
        JRadioButton keyRadioButton = new JRadioButton("����������");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(idRadioButton);
        typeGroup.add(keyRadioButton);
        return typeGroup;
	}

}
