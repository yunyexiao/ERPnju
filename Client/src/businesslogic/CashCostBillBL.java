package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.billblservice.CashCostBillBLService;
import dataservice.CashCostBillDataService;
import ds_stub.CashCostBillDs_stub;
import po.billpo.CashCostBillPO;
import po.billpo.CashCostItem;
import presentation.component.MyTableModel;
import vo.billvo.CashCostBillVO;

public class CashCostBillBL implements CashCostBillBLService {

	private CashCostBillDataService cashCostBillDataService;
	
	public CashCostBillBL() {
		cashCostBillDataService = new CashCostBillDs_stub();//Rmi.getRemote(CashCostBillDataService.class);
	}
	
	@Override
	public String getNewId() {
        try {
    		return cashCostBillDataService.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public boolean deleteBill(String id) {
		try{
			return cashCostBillDataService.deleteBill(id);
		}catch(RemoteException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveBill(CashCostBillVO bill) {
		try{
            return cashCostBillDataService.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }	}

	@Override
    public boolean updateBill(CashCostBillVO bill) {
        try{
            return cashCostBillDataService.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
	public CashCostBillVO getBill(String id) {
		try{
            CashCostBillPO bill = cashCostBillDataService.getBillById(id);
            return toVO(bill);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
	}
	
    private CashCostBillPO toPO(CashCostBillVO bill){
        ArrayList<CashCostItem> items = new ArrayList<>();
        for(int i = 0; i < bill.getTableModel().getRowCount(); i++){
            String[] row = bill.getTableModel().getValueAtRow(i);
            double price = Double.parseDouble(row[1]);
            items.add(new CashCostItem(row[0], price, row[2]));
        }

        return new CashCostBillPO(bill.getDate(), bill.getTime()
            , bill.getId(), bill.getOperator(), bill.getState()
            , bill.getAccountId(), items);
    }

    private CashCostBillVO toVO(CashCostBillPO bill){
        String[] columnNames = {"条目名", "金额", "备注"};
        ArrayList<CashCostItem> items = bill.getCashcostList();
        String[][] data = new String[columnNames.length][items.size()];
        for(int i = 0; i < data.length; i++){
            data[i] = toArray(items.get(i));
        }
        MyTableModel model = new MyTableModel(data, columnNames);
        CashCostBillVO cashCostBillVO = new CashCostBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator()
                , bill.getState(), bill.getAccountId());
        cashCostBillVO.setTableModel(model);
        return cashCostBillVO;
    }
    
    private String[] toArray(CashCostItem item){
        // TODO the type and store to complete
        return new String[]{item.getName(), Double.toString(item.getMoney()), item.getRemark()};
    }
}
