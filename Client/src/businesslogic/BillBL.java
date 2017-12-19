package businesslogic;

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
	}

	@Override
	public ChangeBillVO getChangeBill(String id) {
		return BillTools.toChangeBillVO(billDs.getChangeBill(id));
	}
	
	public PurchaseBillVO getPurchaseBill(String id) {
		return BillTools.toPurchaseBillVO(billDs.getPurchaseBill(id));
	}

	public PurchaseReturnBillVO getPurchaseReturnBill(String id) {
		return BillTools.toPurchaseReturnBillVO(billDs.getPurchaseReturnBill(id));
	}
	
	public SalesBillVO getSalesBill(String id) {
		return BillTools.toSalesBillVO(billDs.getSalesBill(id));
	}

	public SalesReturnBillVO getSalesReturnBill(String id) {
		return BillTools.toSalesReturnBillVO(billDs.getSalesReturnBill(id), this);
	}
	
	public CashCostBillVO getCashCostBill(String id) {
		return BillTools.toCashCostBillVO(billDs.getCashCostBillPO(id));
	}
	
	public PaymentBillVO getPaymentBill(String id) {
		return BillTools.toPaymentBillVO(billDs.getPaymentBillPO(id));
	}
	
	public ReceiptBillVO getReceiptBill(String id) {
		return BillTools.toReceiptBillVO(billDs.getReceiptBillPO(id));
	}
}
