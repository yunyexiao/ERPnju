package bl_stub;

import blservice.CustomerBLService;
import blservice.infoservice.GetCustomerInterface;
import presentation.component.MyTableModel;
import vo.CustomerVO;

public class CustomerBL_stub implements CustomerBLService, GetCustomerInterface {

	@Override
	public boolean delete(String id) {
		System.out.println("�ͻ���Ϣ�ѳɹ�ɾ��");
		return true;
	}

	@Override
	public MyTableModel search(String type, String key) {
		String[] attributes={"�ͻ����","����","����","����","�绰","��ַ","�ʱ�","��������","Ӧ�ն��","Ӧ��","Ӧ��","Ĭ��ҵ��Ա"};
		String[][] info={{"JHS-001","����","������","LV5","","deep dark �� fantasy","","","4000","0","0","Van"},
				{"XSS-001","����","������","LV1","","�������","","","2000","100","400","Van"}};
		System.out.println("��ʾ�����Ŀͻ���Ϣ");
		return new MyTableModel(info, attributes);
	}

	@Override
	public MyTableModel update() {
		String[] attributes={"�ͻ����","����","����","����","�绰","��ַ","�ʱ�","��������","Ӧ�ն��","Ӧ��","Ӧ��","Ĭ��ҵ��Ա"};
		String[][] info={{"JHS-001","����","������","LV5","","deep dark �� fantasy","","","4000","0","0","Van"},
				{"XSS-001","����","������","LV1","","�������","","","2000","100","400","Van"}};
		System.out.println("�ͻ���Ϣ�ѳɹ�����");
		return new MyTableModel(info, attributes);
	}

	@Override
	public boolean add(CustomerVO customer) {
		System.out.println("�ͻ���Ϣ�ѳɹ����");
		return true;
	}

	@Override
	public boolean change(CustomerVO customer) {
		System.out.println("�ͻ���Ϣ�Ѹ���");
		return true;
	}

	@Override
	public String getNewId() {
		return "0003";
	}

    @Override
    public CustomerVO getCustomer(String id) {
        return new CustomerVO("JHS-001","����", 1, 5,""
            ,"deep dark �� fantasy","","",4000,0,0,"Van");
    }
	
}
