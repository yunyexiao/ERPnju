package blservice.infoservice;

import presentation.component.MyTableModel;
import vo.CommodityVO;

public interface GetCommodityInterface {

	public CommodityVO getCommodity(String id);

	/**
	 * ����ĳһ�������Ƿ�����Ʒ
	 * @param categoryId ��Ʒ����id
	 * @return
	 */
	public boolean hasCommodity(String categoryId);
	
	public MyTableModel getCategoryCommodities(String categoryId);
}
