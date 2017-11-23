package presentation.dataui;

import java.awt.BorderLayout;

import blservice.UserBLService;
import vo.UserVO;

public class UpdateUserWindow extends FatherWindow {
    
    private InputUserPanel centerPanel;
    private UserBLService userBl;

	public UpdateUserWindow(UserBLService userBl, String[] data) {
		super();
		this.userBl = userBl;
        frame.setTitle("修改用户");
        centerPanel = new InputUserPanel(data);
        frame.add(centerPanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
	}

    @Override
    boolean taskFinished() {
        UserVO user = centerPanel.getUserVO();
        return user != null && userBl.change(user);
    }

    @Override
    String getSuccessMsg() {
        return "用户信息已更新";
    }

}
