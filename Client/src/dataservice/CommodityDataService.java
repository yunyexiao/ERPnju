package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CommodityPO;

public interface CommodityDataService {
	/**
	 * 新增商品时，获取商品应当持有的唯一id
	 * @return 下一个商品应当持有的id [id格式：]
	 * @throws RemoteException
	 */
	public String getNewId() throws RemoteException;
	/**
	 * 根据商品的ID返回一个CommodityPO对象<br/>
	 * 找不到就返回一个null...
	 * @param id 商品的id [id格式：]
	 * @return 查找到的CommodityPO对象
	 * @throws RemoteException
	 */
	public CommodityPO findById(String id) throws RemoteException;
	/**
	 * 向数据库中增加一个CommodityPO对象
	 * @param commodity 打包好的CommodityPO对象
	 * @return 是否增加成功(数据库读写中出错返回false)(id已经存在返回false)
	 * @throws RemoteException
	 */
	public boolean add(CommodityPO commodity) throws RemoteException;
	/**
	 * 向数据库中删除一个CommodityPO对象
	 * @param id 需要删除的商品的唯一id [id格式：]
	 * @return 是否删除成功(数据库操作中出错返回false)(id不存在返回false)
	 * @throws RemoteException
	 */
	public boolean delete(String id) throws RemoteException;
	/**
	 * 更新数据库中一个已经存在的CommodityPO对象
	 * @param commodity 需要更新的CommodityPO对象
	 * @return 是否更新成功(数据库操作中出错返回false)(id不存在返回false)
	 * @throws RemoteException
	 */
	public boolean update(CommodityPO commodity) throws RemoteException;
	/**
	 * 获取数据库中所有未被删除的CommodityPO对象列表
	 * @return 数据库中所有未被删除的CommodityPO对象
	 * @throws RemoteException
	 */
	public ArrayList<CommodityPO> getAllCommodity() throws RemoteException;
}
