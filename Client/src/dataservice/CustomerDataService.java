package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CustomerPO;

public interface CustomerDataService {

	/**
	 * 新增客户时，获取客户应当持有的唯一id
	 * @return 下一个客户应当持有的id
	 * @throws RemoteException
	 */
	public String getNewId() throws RemoteException;
	/**
	 * 根据客户的ID返回一个CustomerPO对象<br/>
	 * 找不到就返回一个null...
	 * @param id 客户的id
	 * @return 查找到的CustomerPO对象
	 * @throws RemoteException
	 */
	public CustomerPO findById(String id) throws RemoteException;
	/**
	 * 向数据库中增加一个CustomerPO对象
	 * @param customer 打包好的CustomerPO对象
	 * @return 是否增加成功(数据库读写中出错返回false)(id已经存在返回false)
	 * @throws RemoteException
	 */
	public boolean add(CustomerPO customer) throws RemoteException;
	/**
	 * 向数据库中删除一个CustomerPO对象
	 * @param id 需要删除的客户的唯一id
	 * @return 是否删除成功(数据库操作中出错返回false)(id不存在返回false)
	 * @throws RemoteException
	 */
	public boolean delete(String id) throws RemoteException;
	/**
	 * 更新数据库中一个已经存在的CustomerPO对象
	 * @param customer 需要更新的CustomerPO对象
	 * @return 是否更新成功(数据库操作中出错返回false)(id不存在返回false)
	 * @throws RemoteException
	 */
	public boolean update(CustomerPO customer) throws RemoteException;
	/**
	 * 获取数据库中所有未被删除的CustomerPO对象列表
	 * @return 数据库中所有未被删除的CustomerPO对象
	 * @throws RemoteException
	 */
	public ArrayList<CustomerPO> getAllCustomer() throws RemoteException;
}
