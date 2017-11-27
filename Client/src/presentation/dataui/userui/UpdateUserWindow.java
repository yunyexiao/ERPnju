package presentation.dataui.userui;

import java.awt.BorderLayout;

import blservice.UserBLService;
import presentation.dataui.FatherWindow;
import vo.UserVO;

public class UpdateUserWindow extends FatherWindow {
    
    private InputUserPanel centerPanel;
    private UserBLService userBl;

	public UpdateUserWindow(UserBLService userBl, String[] data) {
		super();
		this.userBl = userBl;
        frame.setTitle("�޸��û�");
        centerPanel = new InputUserPanel(data);
        frame.add(centerPanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
	}

    @Override
    protected boolean taskFinished() {
        UserVO user = centerPanel.getUserVO();
        return user != null && userBl.change(user);
    }

    @Override
    protected String getSuccessMsg() {
        return "�û���Ϣ�Ѹ���";
    }

}
