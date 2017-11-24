package dataservice;

import java.util.ArrayList;

import po.LogInfoPO;

public interface LogDataService {

	/**
	 * 新增一条操作记录
	 * @param logInfo 增加的操作记录
	 * @return 是否增加成功
	 */
	public boolean add(LogInfoPO logInfo);
	/**
	 * 获得所有的操作日志记录
	 * @return 全部的操作信息
	 */
	public ArrayList<LogInfoPO> getAllInfo();
	/**
	 * 获得一段时间之内的所有操作记录
	 * @param startTime 起始日期
	 * @param EndTime 结束日期
	 * @return 一段时间之内的所有操作记录
	 */
	public ArrayList<LogInfoPO> getAllInfo(String startTime, String EndTime);
}
