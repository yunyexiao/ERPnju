package presentation.component.choosewindow;

import bl_stub.AccountBL_stub;
import blservice.AccountBLService;
import blservice.infoservice.GetAccountInterface;
import vo.AccountVO;

public class AccountChooseWin extends ChooseWindow {

	private AccountVO account;
	
	public AccountChooseWin() {
		super();
	}
	
	@Override
	public void init() {
		AccountBLService accountBl = new AccountBL_stub();
        setTypes(new String[]{"���˺�id����", "���˻���������"});
        table.setModel(accountBl.update());
        frame.setTitle("ѡ���˻�");
        frame.setVisible(true);		
	}

	@Override
	protected void yesAction() {
		 GetAccountInterface accountInfo = new AccountBL_stub();
	        int index = table.getSelectedRow();
	        if(index < 0) return;
	        String id = (String) table.getValueAt(index, 0);
	        account = accountInfo.getAccount(id);
	        frame.dispose();		
	}

	public AccountVO getAccount(){
        return account;
    }

	@Override
	protected void searchAction() {
		// TODO Auto-generated method stub
		
	}
}
