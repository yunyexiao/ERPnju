package presentation.dataui;

import java.awt.BorderLayout;

import blservice.UserBLService;
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
        centerPanel = new InputUserPanel(new String[]{userBl.getNewId(), null, null, null, null, null});
        frame.add(centerPanel, BorderLayout.CENTER);
        
		frame.setVisible(true);
	}

    @Override
    boolean taskFinished() {
        UserVO user = centerPanel.getUserVO();
        return user != null && userBL.add(user);
    }

    @Override
    String getSuccessMsg() {
        return "����û��ɹ�";
    }

}

