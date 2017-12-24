package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.CashCostBillDataService;
import po.billpo.BillPO;
import po.billpo.CashCostBillPO;
import po.billpo.CashCostItem;

public class CashCostBillDs_stub implements CashCostBillDataService {

    private static final ArrayList<CashCostBillPO> BILLS
    = new ArrayList<>();
	
	static{
        ArrayList<CashCostItem> items1 = new ArrayList<>();
		items1.add(new CashCostItem("�㽶��", 10000, "���㽶"));
		items1.add(new CashCostItem("ħ��", 12345, "ȥ�������"));
        BILLS.add(new CashCostBillPO(
				"2017-12-07", "11:03:23", "00123", "0002", BillPO.COMMITED,
				"6209111100001111",  items1, 22345
				));

        ArrayList<CashCostItem> items2 = new ArrayList<>();
		items2.add(new CashCostItem("�����ħ", 1000, "����"));
		items2.add(new CashCostItem("�Ƿ���", 1234567, "���˽������������ׯ�ڣ�"));
        BILLS.add(new CashCostBillPO(
				"2017-12-07", "20:06:37", "23333", "0002", BillPO.COMMITED,
				"6209111100002222",  items2, 1235567
				));
        
        ArrayList<CashCostItem> items3 = new ArrayList<>();
		items3.add(new CashCostItem("�㽶��", 100, "������С��"));
		items3.add(new CashCostItem("ħ��", 12345, "Ϊ�˱�ǿ"));
        BILLS.add(new CashCostBillPO(
				"2017-12-07", "23:25:28", "00123", "0002", BillPO.COMMITED,
				"6209111100003333",  items3, 12445
				));
    }
	
    @Override
    public boolean deleteBill(String id) throws RemoteException {
        for(int i = 0; i < BILLS.size(); i++){
            if(BILLS.get(i).getId().equals(id)){
                BILLS.remove(i);
                break;
            }
        }
        System.out.println("Purchase Return Bill deleted in database: " + id);
        return true;
    }

    @Override
    public String getNewId() throws RemoteException {
        return "98756";
    }

    @Override
    public boolean saveBill(CashCostBillPO cashCostBill) throws RemoteException {
        BILLS.add(cashCostBill);
        System.out.println("Cash Cost Bill added in database: " + cashCostBill.getId());
        return true;
    }

    @Override
    public CashCostBillPO getBillById(String id) throws RemoteException {
        System.out.println("Cash Cost Bill found in database: " + id);
        return BILLS.get(1);
    }

    @Override
    public ArrayList<CashCostBillPO> getBillsByDate(String from, String to) throws RemoteException {
        ArrayList<CashCostBillPO> result = new ArrayList<>();
        result.add(BILLS.get(1));
        result.add(BILLS.get(2));
        System.out.println("Cash Cost Bill found by date in database");
        return result;
    }

}
