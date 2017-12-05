package blservice;

import vo.CommodityVO;

public interface CommodityBLService extends DataBLService {

	/**
	 * 增加一条商品信息的记录
	 * @param commodity 商品VO
	 * @return
	 */
	public boolean add(CommodityVO commodity);
	/**
	 * 更改一条商品信息
	 * @param commodity 商品VO
	 * @return
	 */
	public boolean change(CommodityVO commodity);
	/**
	 * 返回某一分类下是否有商品
	 * @param categoryId 商品分类id
	 * @return
	 */
	public boolean hasCommodity(String categoryId);
}
