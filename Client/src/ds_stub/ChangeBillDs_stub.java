package ds_stub;

import dataservice.ChangeBillDataService;
import po.billpo.ChangeBillPO;

public class ChangeBillDs_stub implements ChangeBillDataService {

	private static int count = 0; 
	@Override
	public ChangeBillPO getBillById(String id) {
		return null;
	}

	@Override
	public boolean saveBill(ChangeBillPO bill) {
		return true;
	}

	@Override
	public boolean deleteBill(String id) {
		return true;
	}

	@Override
	public String getNewId(boolean isOver) {
		count++;
		return String.format("%05d", count);
	}

}
