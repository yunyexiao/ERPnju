package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.CustomerBLService;
import blservice.infoservice.GetCustomerInterface;
import dataservice.CustomerDataService;
import ds_stub.CustomerDs_stub;
import po.CustomerPO;
import presentation.component.MyTableModel;
import vo.CustomerVO;

public class CustomerBL implements CustomerBLService, GetCustomerInterface{
	
	private CustomerDataService customerDataService = new CustomerDs_stub();//Rmi.getRemote(CustomerDataService.class);
	private String[] tableHeader = {"�ͻ����", "�ͻ�����", "����", "����", "�绰", "��ַ", 
			"�ʱ�", "��������", "Ӧ�ն��", "Ӧ��", "Ӧ��", "Ĭ��ҵ��Ա"};
	
	private String[] getLine(CustomerPO customer) {
		return new String[] {
				customer.getId(),
				customer.getName(),
				customer.getType()==0?"������":"������",
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
			return customerDataService.delete(id);
		} catch (Exception e) {
			return false;
		}
	}

	@Override//��ʱֻʵ�ְ�ID���ҹ���
	public MyTableModel search(String type, String key) {
		try {
			ArrayList<CustomerPO> list = null;
			CustomerPO customerPO = customerDataService.findById(key);
			String[][] data = new String [list.size()][tableHeader.length];
			for (int i = 0; i < list.size(); i++) {
				data[i] = getLine(list.get(i));
			}
			MyTableModel searchTable = new MyTableModel (data, tableHeader);
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
