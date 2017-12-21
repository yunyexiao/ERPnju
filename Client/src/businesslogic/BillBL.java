package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import blservice.billblservice.BillBLService;
import dataservice.BillDataService;
import ds_stub.BillDs_stub;
import po.billpo.BillPO;
import presentation.component.MyTableModel;
import rmi.Rmi;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.CashCostBillVO;
import vo.billvo.ChangeBillVO;
import vo.billvo.PaymentBillVO;
import vo.billvo.PurchaseBillVO;
import vo.billvo.PurchaseReturnBillVO;
import vo.billvo.ReceiptBillVO;
import vo.billvo.SalesBillVO;
import vo.billvo.SalesReturnBillVO;

public class BillBL implements BillBLService {

	private BillDataService billDs = Rmi.flag ? Rmi.getRemote(BillDataService.class) : new BillDs_stub();
	
	@Override
	public MyTableModel getBillTable(UserVO user) {
		try {
			ArrayList<BillPO> list = billDs.getBillList(user.toPO());
			String[] attributes = new String[]{"制定时间","单据编号","单据类型","状态"};
			String[][] info = new String[list.size()][attributes.length];
			
			Collections.sort(list, new Comparator<BillPO>() {  
	            @Override  
	            public int compare(BillPO o1, BillPO o2) {
	            	int[] states = {BillPO.SAVED, BillPO.NOTPASS, BillPO.COMMITED, BillPO.PASS};
	            	if (Arrays.binarySearch(states, o1.getState()) == Arrays.binarySearch(states, o2.getState())) {
	            		if (o1.getDate().equals(o2.getDate())) {
	                		return Integer.parseInt(o1.getTime()) >= Integer.parseInt(o2.getTime()) ? 1 : -1;
	                	} else return Integer.parseInt(o1.getDate()) > Integer.parseInt(o2.getDate()) ? 1 : -1;
	            	} else return Arrays.binarySearch(states, o1.getState()) > Arrays.binarySearch(states, o2.getState()) ? 1 : -1 ;
	            }  
	        });
			
			for (int i = 0; i < list.size(); i++) {
				BillPO bill = list.get(i);
				info[i][0] = bill.getDate() + "  " + bill.getTime();
				info[i][1] = bill.getAllId();
				info[i][2] = BillTools.getBillName(bill);
				info[i][3] = bill.getStateName();
			}
			
			return new MyTableModel(info, attributes);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ChangeBillVO getChangeBill(String id) {
		try {
			return BillTools.toChangeBillVO(billDs.getChangeBill(id));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public PurchaseBillVO getPurchaseBill(String id) {
		try {
			return BillTools.toPurchaseBillVO(billDs.getPurchaseBill(id));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public PurchaseReturnBillVO getPurchaseReturnBill(String id) {
		try {
			return BillTools.toPurchaseReturnBillVO(billDs.getPurchaseReturnBill(id));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SalesBillVO getSalesBill(String id) {
		try {
			return BillTools.toSalesBillVO(billDs.getSalesBill(id));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public SalesReturnBillVO getSalesReturnBill(String id) {
		try {
			return BillTools.toSalesReturnBillVO(billDs.getSalesReturnBill(id), this);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public CashCostBillVO getCashCostBill(String id) {
		try {
			return BillTools.toCashCostBillVO(billDs.getCashCostBill(id));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public PaymentBillVO getPaymentBill(String id) {
		try {
			return BillTools.toPaymentBillVO(billDs.getPaymentBill(id));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ReceiptBillVO getReceiptBill(String id) {
		try {
			return BillTools.toReceiptBillVO(billDs.getReceiptBill(id));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BillVO getBill(String id) {
		String type = id.split("-")[0];
		switch (type) {
		case "BYD" : return getChangeBill(id);
		case "BSD" : return getChangeBill(id);
		case "JHD" : return getPurchaseBill(id);
		case "JHTHD" : return getPurchaseReturnBill(id);
		case "XSD" : return getSalesBill(id);
		case "XSTHD" : return getSalesReturnBill(id);
		case "XJFYD" : return getCashCostBill(id);
		case "FKD" : return getPaymentBill(id);
		case "SKD" : return getReceiptBill(id);
		default : return null;
		}
	}
}
