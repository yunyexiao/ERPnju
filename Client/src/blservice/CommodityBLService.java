package blservice;

import vo.CommodityVO;

public interface CommodityBLService extends DataBLService {

	/**
	 * ����һ����Ʒ��Ϣ�ļ�¼
	 * @param commodity ��ƷVO
	 * @return
	 */
	public boolean add(CommodityVO commodity);
	/**
	 * ����һ����Ʒ��Ϣ
	 * @param commodity ��ƷVO
	 * @return
	 */
	public boolean change(CommodityVO commodity);
}
