package presentation.billui.choosewindow;

import blservice.CommodityBLService;
import blservice.DataBLService;

public class CommodityChooseWin extends ChooseWindow {

	public CommodityChooseWin(CommodityBLService commodityBL) {
		super(commodityBL);
	}

	@Override
	public void init(DataBLService dataBL) {
		setTypes(new String[]{"���������", "����������"});
		setTableModel(((CommodityBLService) dataBL).update());
		setTitle("ѡ����Ʒ");
	}

	@Override
	protected void yesAction() {
		// TODO Auto-generated method stub

	}

}
