package presentation.component.choosewindow;

import bl_stub.CommodityBL_stub;
import blservice.CommodityBLService;
import blservice.infoservice.GetCommodityInterface;
import vo.CommodityVO;

public class CommodityChooseWin extends ChooseWindow {
	
	private CommodityVO data;

	public CommodityChooseWin() {
		super();
	}

	@Override
	public void init() {
		CommodityBLService commodityBL = new CommodityBL_stub();
		setTypes(new String[]{"���������", "����������"});
		table.setModel(commodityBL.update());
		FitTableColumns();
		frame.setTitle("ѡ����Ʒ");
		frame.setVisible(true);
	}

	@Override
	protected void yesAction() {
		GetCommodityInterface commodityInfo = new CommodityBL_stub();
		if (table.getSelectedRow() != -1) {
			data = commodityInfo.getCommodity((String) table.getValueAt(table.getSelectedRow(), 0));
			frame.dispose();
		}
	}

	public CommodityVO getCommodity() {
		return data;
	}
}
