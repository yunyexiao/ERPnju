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
	private String[] tableHeader = {"�ͻ����", "�ͻ�����", "����", "����", "�绰", "��ַ", 
			"�ʱ�", "��������", "Ӧ�ն��", "Ӧ��", "Ӧ��", "Ĭ��ҵ��Ա"};
	
	public CustomerBL() {}
	
	public CustomerBL(UserVO user) {
		addLog = new LogBL(user);
	}
	
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
			if (customerDataService.delete(id)) {
            	addLog.add("ɾ���ͻ�", "ɾ������ƷID��"+id);
            	return true;
            }
            return false;
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
			if (addLog != null) addLog.add("�����ͻ�", "������ʽ��"+type+"  �����ؼ��ʣ�"+key);
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
            	addLog.add("�����ͻ�", "�����ͻ���ID��"+customer.getId()+"�ͻ����ƣ�"+customer.getName());
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
            	addLog.add("�޸Ŀͻ�", "���޸ĵĿͻ���ţ�"+customer.getId());
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
