package bl_stub;

import blservice.LoginBLService;
import blservice.UserBLService;
import presentation.component.MyTableModel;
import vo.UserType;
import vo.UserVO;

public class UserBL_stub implements UserBLService, LoginBLService {

	@Override
	public boolean delete(String id) {
		System.out.println("�û���Ϣ�ѳɹ�ɾ��");
		return true;
	}

	@Override
	public MyTableModel search(String type, String key) {
		String[] attributes={"�û�ID", "�û���", "�û����", "�û�Ȩ��", "�û�����", "�Ա�", "����", "�绰����"};
		String[][] info={{"0001","Van","��������Ա", "Ĭ��", "123", "��","34","��"}};
		System.out.println("��ʾ�������û���Ϣ");
		return new MyTableModel(info, attributes);
	}

	@Override
	public MyTableModel update() {
		String[] attributes={"�û�ID", "�û���", "�û����", "�û�Ȩ��", "�û�����", "�Ա�", "����", "�绰����"};
		String[][] info={{"0001","Van","��������Ա", "Ĭ��", "123", "��","34","��"},
				{"0002","Bili","�ܾ���", "Ĭ��", "321", "��","50","��"}};
		System.out.println("�û���Ϣ�ѳɹ�����");
		return new MyTableModel(info, attributes);
	}

	/**
	 * ��1��5�ֱ�˳�򷵻�5�����
	 */
	@Override
	public UserVO getUser(String id, String password) {
		if ("1".equals(id)) return new UserVO("��", "", UserType.KEEPER, 0, id, "��", "", 91);
		else if ("2".equals(id)) return new UserVO("��", "", UserType.SALESMAN, 0, id, "��", "", 91);
		else if ("3".equals(id)) return new UserVO("����", "", UserType.ACCOUNTANT, 0, id, "��", "", 91);
		else if ("4".equals(id)) return new UserVO("��ۼ���", "", UserType.GM, 0, id, "��", "", 91);
		else if ("5".equals(id)) return new UserVO("�û�������", "", UserType.ADMIN, 0, id, "��", "", 91);
		else return null;
	}

	@Override
	public boolean add(UserVO user) {
		System.out.println("�û���Ϣ�ѳɹ����");
		return true;
	}

	@Override
	public boolean change(UserVO user) {
		System.out.println("�û���Ϣ�Ѹ���");
		return true;
	}

	@Override
	public String getNewId() {
		return "0002";
	}

}
