package bl_stub;

import blservice.AccountBLService;
import presentation.component.MyTableModel;
import vo.AccountVO;

public class AccountBL_stub implements AccountBLService {

	@Override
	public boolean delete(String id) {
		System.out.println("�˻���Ϣ�ѳɹ�ɾ��");
		return true;
	}

	@Override
	public MyTableModel search(String type, String key) {
		String[] attributes={"�����˺�", "�˻�����", "���"};
		String[][] info={{"111111111", "����", "9999999999999"}};
		System.out.println("��ʾ�������˻���Ϣ");
		return new MyTableModel(info, attributes);
	}

	@Override
	public MyTableModel update() {
		String[] attributes={"�����˺�", "�˻�����", "���"};
		String[][] info={{"111111111", "����", "9999999999999"}};
		System.out.println("�˻���Ϣ�ѳɹ�����");
		return new MyTableModel(info, attributes);
	}

	@Override
	public boolean add(AccountVO account) {
		System.out.println("�˻���Ϣ�ѳɹ����");
		return true;
	}

	@Override
	public boolean change(AccountVO account) {
		System.out.println("�˻���Ϣ�Ѹ���");
		return true;
	}

	@Override
	public String getNewId() {
		return "0003";
	}

}
