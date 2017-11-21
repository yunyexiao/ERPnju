package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.UserPO;

public interface UserDataService {

	/**
	 * 新增用户时，获取用户应当持有的唯一id
	 * @return 下一个用户应当持有的id [id格式：四位数字字符串，如0001]
	 * @throws RemoteException
	 */
	public String getNewId() throws RemoteException;
	/**
	 * 根据用户的ID返回一个UserPO对象<br/>
	 * 找不到就返回一个null...
	 * @param id 用户的id [id格式：四位数字字符串，如0001]
	 * @return 查找到的UserPO对象
	 * @throws RemoteException
	 */
	public UserPO findById(String id) throws RemoteException;
	/**
	 * 向数据库中增加一个UserPO对象
	 * @param user 打包好的UserPO对象
	 * @return 是否增加成功(数据库读写中出错返回false)(id已经存在返回false)
	 * @throws RemoteException
	 */
	public boolean add(UserPO user) throws RemoteException;
	/**
	 * 向数据库中删除一个UserPO对象
	 * @param id 需要删除的用户的唯一id [id格式：四位数字字符串，如0001]
	 * @return 是否删除成功(数据库操作中出错返回false)(id不存在返回false)
	 * @throws RemoteException
	 */
	public boolean delete(String id) throws RemoteException;
	/**
	 * 更新数据库中一个已经存在的UserPO对象
	 * @param user 需要更新的UserPO对象
	 * @return 是否更新成功(数据库操作中出错返回false)(id不存在返回false)
	 * @throws RemoteException
	 */
	public boolean update(UserPO user) throws RemoteException;
	/**
	 * 获取数据库中所有未被删除的UserPO对象列表
	 * @return 数据库中所有未被删除的UserPO对象
	 * @throws RemoteException
	 */
	public ArrayList<UserPO> getAllUser() throws RemoteException;
}
