package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import blservice.CustomerBLService;
import dataservice.CustomerDataService;
import dataservice.UserDataService;
import po.CustomerPO;
import po.UserPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.CustomerVO;

public class CustomerBL implements CustomerBLService{
	
	private CustomerDataService customerDataService = Rmi.getRemote(CustomerDataService.class);

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
			return customerDataService.delete(id);
		} catch (Exception e) {
			return false;
		}
	}

	@Override//暂时只实现按ID查找功能
	public MyTableModel search(String type, String key) {
		try {
			String[] columnNames = {"客户编号", "客户姓名", "分类", "级别", "电话", "地址", 
					"邮编", "电子邮箱", "应收额度", "应收", "应付", "默认业务员"};
			CustomerPO customerPO = customerDataService.findById(key);
			String[][] data = {{customerPO.getId(), customerPO.getName(), Integer.toString(customerPO.getType()),
				Integer.toString(customerPO.getRank()), customerPO.getTelNumber(), customerPO.getAddress(), 
				customerPO.getCode(), customerPO.getMail(), Double.toString(customerPO.getRecRange()), 
				Double.toString(customerPO.getReceivable()), Double.toString(customerPO.getPayment()), customerPO.getSalesman()}};
			MyTableModel searchTable = new MyTableModel (data, columnNames);
			return searchTable;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public MyTableModel update() {
		try {
			ArrayList<CustomerPO> list = customerDataService.getAllCustomer();
			String[] columnNames = {"客户编号", "客户姓名", "分类", "级别", "电话", "地址", 
					"邮编", "电子邮箱", "应收额度", "应收", "应付", "默认业务员"};
			String[][] data = new String [list.size()][12];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getId();
				data[i][1] = list.get(i).getName();
				data[i][2] = Integer.toString(list.get(i).getType());
				data[i][3] = Integer.toString(list.get(i).getRank());
				data[i][4] = list.get(i).getTelNumber();
				data[i][5] = list.get(i).getAddress();
				data[i][6] = list.get(i).getCode();
				data[i][7] = list.get(i).getMail();
				data[i][8] = Double.toString(list.get(i).getRecRange());
				data[i][9] = Double.toString(list.get(i).getReceivable());
				data[i][10] = Double.toString(list.get(i).getPayment());
				data[i][11] = list.get(i).getSalesman();
			}
			MyTableModel updateTable = new MyTableModel (data, columnNames);
			return updateTable;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean add(CustomerVO customer) {
		try {
			CustomerPO customerPO = customer.toPO(); 
			return customerDataService.add(customerPO);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean change(CustomerVO customer) {
		try {
			CustomerPO customerPO = customer.toPO();
			return customerDataService.update(customerPO);
		} catch (Exception e) {
			return false;
		}
	}

}
