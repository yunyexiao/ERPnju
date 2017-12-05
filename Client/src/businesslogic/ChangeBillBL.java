package businesslogic;

import java.util.ArrayList;

import bl_stub.CommodityBL_stub;
import blservice.billblservice.ChangeBillBLService;
import blservice.infoservice.GetCategoryInterface;
import blservice.infoservice.GetCommodityInterface;
import dataservice.ChangeBillDataService;
import ds_stub.ChangeBillDs_stub;
import po.billpo.ChangeBillPO;
import po.billpo.ChangeItem;
import presentation.component.MyTableModel;
import vo.billvo.ChangeBillVO;

public class ChangeBillBL implements ChangeBillBLService {

	private ChangeBillDataService changeBillDS;
	private GetCommodityInterface commodityInfo = new CommodityBL_stub();
	private GetCategoryInterface categoryInfo = new CategoryBL();
	private String[] headers = {"商品id", "商品名称", "库存数量", "实际数量"};
	private static boolean isOver = true;
	
	public ChangeBillBL() {
		changeBillDS = new ChangeBillDs_stub();//Rmi.getRemote(ChangeBillDataService.class);
	}
	
	@Override
	public String getNewId() {
		return changeBillDS.getNewId(isOver);
	}

	@Override
	public boolean deleteBill(String id) {
		return changeBillDS.deleteBill(id);
	}

	@Override
	public boolean saveBill(ChangeBillVO bill) {
		MyTableModel model = bill.getTableModel();
		ArrayList<ChangeItem> commodityList = new ArrayList<ChangeItem>();
		for (int i = 0; i < model.getRowCount(); i++) {
			String[] s = model.getValueAtRow(i);
			ChangeItem item = new ChangeItem(s[0], Integer.parseInt(s[2]), Integer.parseInt(s[3]));
			commodityList.add(item);
		}
		return changeBillDS.saveBill(new ChangeBillPO(bill, bill.getFlag(), commodityList));
	}

	@Override
	public ChangeBillVO getBill(String id) {
		ChangeBillPO bill = changeBillDS.getBillById(id);
		int size = bill.getCommodityList().size();
		String[][] data = new String[size][4];
		for (int i = 0; i < size; i++) {
			ChangeItem item = bill.getCommodityList().get(i);
			data[i] = new String[]{item.getCommodityId(), "", ""+item.getOriginalValue(), ""+item.getChangedValue()};
			data[i][1] = categoryInfo.getCatrgory(commodityInfo.getCommodity(item.getCommodityId()).getCategoryId()).getName();
			if (bill.getState() != ChangeBillPO.PASS) data[i][2]= ""+commodityInfo.getCommodity(item.getCommodityId()).getAmount();
		}
		return new ChangeBillVO(bill.getDate(), bill.getTime(), bill.getId(), bill.getOperator(), bill.getState(), bill.getFlag(), new MyTableModel(data, headers));
	}

	public static void setOver(boolean isOver) {
		ChangeBillBL.isOver = isOver;
	}
}
