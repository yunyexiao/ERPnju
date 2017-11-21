package businesslogic;

import blservice.CustomerBLService;
import blservice.DataBLService;
import blservice.UserBLService;
import presentation.component.MyTableModel;
import vo.CustomerVO;
import vo.UserVO;

public class DataBL implements DataBLService, UserBLService, CustomerBLService {

	private DataBLService dataBL;
	
	public DataBL(DataBLService dataBL) {
		this.dataBL = dataBL;
	}
	
	public  Class<? extends DataBLService> getSubClass() {
		return dataBL.getClass();
	}
	@Override
	public boolean delete(String id) {
		return dataBL.delete(id);
	}

	@Override
	public MyTableModel search(String type, String key) {
		return dataBL.search(type, key);
	}

	@Override
	public MyTableModel update() {
		return dataBL.update();
	}

	@Override
	public boolean add(CustomerVO customer) {
		return ((CustomerBLService) dataBL).add(customer);
	}

	@Override
	public boolean change(CustomerVO customer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(UserVO user) {
		return ((UserBLService) dataBL).add(user);
	}

	@Override
	public boolean change(UserVO user) {
		return ((UserBLService) dataBL).change(user);
	}

}
