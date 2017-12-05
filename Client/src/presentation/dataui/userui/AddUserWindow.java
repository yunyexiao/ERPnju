package presentation.dataui.userui;

import java.awt.BorderLayout;

import blservice.UserBLService;
import presentation.dataui.FatherWindow;
import vo.UserVO;

/**
 * ��ͨ������ģ���Ӧ��ͨ��������Ӵ���</br>
 * ͬ������UserType�б����ͣ��������޸�
 * @author Ǯ��Ե
 */
public class AddUserWindow extends FatherWindow{
    
    private InputUserPanel centerPanel;
    private UserBLService userBL;
	
	public AddUserWindow(UserBLService userBl) {
		super();
		this.userBL = userBl;

        frame.setTitle("�����û�");
        centerPanel = new InputUserPanel(new String[]{userBl.getNewId(), null, null, null, null, null, null, null});
        frame.add(centerPanel, BorderLayout.CENTER);
        
		frame.setVisible(true);
	}

    @Override
    protected boolean taskFinished() {
        UserVO user = centerPanel.getUserVO();
        return user != null && userBL.add(user);
    }

    @Override
    protected String getSuccessMsg() {
        return "����û��ɹ�";
    }

}