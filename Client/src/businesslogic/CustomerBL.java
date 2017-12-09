package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.CustomerBLService;
import blservice.infoservice.GetCustomerInterface;
import businesslogic.inter.AddLogInterface;
import dataservice.CustomerDataService;
import ds_stub.CustomerDs_stub;
import po.CustomerPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.CustomerVO;
import vo.UserVO;

public class CustomerBL implements CustomerBLService, GetCustomerInterface{
	
	private AddLogInterface addLog;
	private CustomerDataService customerDataService = Rmi.flag ? Rmi.getRemote(CustomerDataService.class) : new CustomerDs_stub();
	private String[] tableHeader = {"客户编号", "客户姓名", "分类", "级别", "电话", "地址", 
			"邮编", "电子邮箱", "应收额度", "应收", "应付", "默认业务员"};
	
	public CustomerBL() {}
	
	public CustomerBL(UserVO user) {
		addLog = new LogBL(user);
	}
	
	private String[] getLine(CustomerPO customer) {
		return new String[] {
				customer.getId(),
				customer.getName(),
				customer.getType()==0?"进货商":"销售商",
				"LV"+customer.getRank(),
				customer.getTelNumber(),
				customer.getAddress(),
				customer.getCode(),
				customer.getMail(),
				Double.toString(customer.getRecRange()),
				Double.toString(customer.getReceivable()),
				Double.toString(customer.getPayment()), 
				customer.getSalesman()
		};
	}
	
	@Override
	public String getNewId() {
		try {
			return customerDataService.getNewId();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(String id) {
		try {
			if (customerDataService.delete(id)) {
            	addLog.add("删除客户", "删除的商品ID："+id);
            	return true;
            }
            return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override//暂时只实现按ID查找功能
	public MyTableModel search(String type, String key) {
		try {
			ArrayList<CustomerPO> list = null;
			CustomerPO customerPO = customerDataService.findById(key);
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			MyTableModel searchTable = new MyTableModel (data, tableHeader);
			if (addLog != null) addLog.add("搜索客户", "搜索方式："+type+"  搜索关键词："+key);
			return searchTable;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public MyTableModel update() {
		try {
			ArrayList<CustomerPO> list = customerDataService.getAllCustomer();
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			MyTableModel updateTable = new MyTableModel (data, tableHeader);
			return updateTable;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean add(CustomerVO customer) {
		try {
			if (customerDataService.add(customer.toPO())) {
            	addLog.add("新增客户", "新增客户的ID："+customer.getId()+"客户名称："+customer.getName());
            	return true;
            }
            return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean change(CustomerVO customer) {
		try {
			if (customerDataService.update(customer.toPO())) {
            	addLog.add("修改客户", "被修改的客户编号："+customer.getId());
            	return true;
            }
            return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public CustomerVO getCustomer(String id) {
		CustomerPO customer = null;
		try {
			customer = customerDataService.findById(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (customer != null) {
			return new CustomerVO(
					customer.getId(),
					customer.getName(),
					customer.getType(),
					customer.getRank(),
					customer.getTelNumber(),
					customer.getAddress(),
					customer.getCode(),
					customer.getMail(),
					customer.getRecRange(),
					customer.getReceivable(),
					customer.getPayment(),
					customer.getSalesman()); 
		}
		return null;
	}

}
