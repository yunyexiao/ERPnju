package businesslogic;

import javax.swing.table.DefaultTableModel;

import blservice.CustomerBLService;
import vo.CustomerVO;

public class CustomerBL_stub implements CustomerBLService {

	@Override
	public boolean delete(String id) {
		System.out.println("�ͻ���Ϣ�ѳɹ�ɾ��");
		return true;
	}

	@Override
	public DefaultTableModel search(String type, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTableModel update() {
		String[] attributes={"�ͻ����","����","����","����","Ӧ�ն��","Ӧ��","Ӧ��","�绰","�ʱ�","��������","Ĭ��ҵ��Ա"};
		String[][] info={{"JHS-001","����","������","LV5","4000","0","0","","","","Van"},
				{"XSS-001","����","������","LV1","2000","100","400","","","","Van"}};
		return new DefaultTableModel(info, attributes);
	}

	@Override
	public boolean add(CustomerVO customer) {
		System.out.println("�ͻ���Ϣ�ѳɹ����");
		return true;
	}

}
