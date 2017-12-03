package presentation.dataui.accountui;

import java.awt.BorderLayout;

import blservice.AccountBLService;
import presentation.dataui.FatherWindow;
import presentation.dataui.accountui.InputAccountPanel;
import vo.AccountVO;

public class UpdateAccountWindow extends FatherWindow{
	
	private InputAccountPanel centerPanel;
    private AccountBLService accountBl;

	public UpdateAccountWindow(AccountBLService accountBl, String[] data) {
		super();
		this.accountBl = accountBl;
        frame.setTitle("�޸��˻�");
        centerPanel = new InputAccountPanel(data);
        frame.add(centerPanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
	}

    @Override
    protected boolean taskFinished() {
        AccountVO account = centerPanel.getAccountVO();
        return account != null && accountBl.change(account);
    }

    @Override
    protected String getSuccessMsg() {
        return "�˻���Ϣ�Ѹ���";
    }
}
