package dataservice;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.AccountPO;

public interface AccountDataService {

	/**
	 * 新增账户时，获取账户应当持有的唯一id
	 * @return 下一个账户应当持有的id [id格式：四位数字字符串，如0001]
	 * @throws RemoteException
	 */
	public String getNewId() throws RemoteException;
	/**
	 * 根据账户的ID返回一个AccountPO对象<br/>
	 * 找不到就返回一个null...
	 * @param id 账户的id [id格式：四位数字字符串，如0001]
	 * @return 查找到的AccountPO对象
	 * @throws RemoteException
	 */
	public AccountPO findById(String id) throws RemoteException;
	/**
	 * 向数据库中增加一个AccountPO对象
	 * @param account 打包好的AccountPO对象
	 * @return 是否增加成功(数据库读写中出错返回false)(id已经存在返回false)
	 * @throws RemoteException
	 */
	public boolean add(AccountPO account) throws RemoteException;
	/**
	 * 向数据库中删除一个AccountPO对象
	 * @param id 需要删除的账户的唯一id [id格式：四位数字字符串，如0001]
	 * @return 是否删除成功(数据库操作中出错返回false)(id不存在返回false)
	 * @throws RemoteException
	 */
	public boolean delete(String id) throws RemoteException;
	/**
	 * 更新数据库中一个已经存在的AccountPO对象
	 * @param account 需要更新的AccountPO对象
	 * @return 是否更新成功(数据库操作中出错返回false)(id不存在返回false)
	 * @throws RemoteException
	 */
	public boolean update(AccountPO account) throws RemoteException;
	/**
	 * 获取数据库中所有未被删除的AccountPO对象列表
	 * @return 数据库中所有未被删除的AccountPO对象
	 * @throws RemoteException
	 */
	public ArrayList<AccountPO> getAllAccount() throws RemoteException;
}
