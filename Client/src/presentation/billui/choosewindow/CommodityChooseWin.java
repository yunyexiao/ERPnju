package presentation.billui.choosewindow;

import blservice.CommodityBLService;
import blservice.DataBLService;

public class CommodityChooseWin extends ChooseWindow {

	public CommodityChooseWin(CommodityBLService commodityBL) {
		super(commodityBL);
	}

	@Override
	public void init(DataBLService dataBL) {
		setTypes(new String[]{"按编号搜索", "按名称搜索"});
		setTableModel(((CommodityBLService) dataBL).update());
		setTitle("选择商品");
	}

	@Override
	protected void yesAction() {
		// TODO Auto-generated method stub

	}

}
