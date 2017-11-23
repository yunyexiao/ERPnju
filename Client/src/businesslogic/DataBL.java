package businesslogic;

import blservice.AccountBLService;
import blservice.CommodityBLService;
import blservice.CustomerBLService;
import blservice.DataBLService;
import blservice.UserBLService;
import presentation.component.MyTableModel;
import vo.AccountVO;
import vo.CommodityVO;
import vo.CustomerVO;
import vo.UserVO;

public class DataBL implements DataBLService, UserBLService, CustomerBLService, AccountBLService, CommodityBLService {

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
		return ((CustomerBLService) dataBL).change(customer);
	}

	@Override
	public boolean add(UserVO user) {
		return ((UserBLService) dataBL).add(user);
	}

	@Override
	public boolean change(UserVO user) {
		return ((UserBLService) dataBL).change(user);
	}

	@Override
	public boolean add(CommodityVO commodity) {
		return ((CommodityBLService) dataBL).add(commodity);
	}

	@Override
	public boolean change(CommodityVO commodity) {
		return ((CommodityBLService) dataBL).change(commodity);
	}

	@Override
	public boolean add(AccountVO account) {
		return ((AccountBLService) dataBL).add(account);
	}

	@Override
	public boolean change(AccountVO account) {
		return ((AccountBLService) dataBL).change(account);
	}

	@Override
	public String getNewId() {
		return "0003";
	}
}
