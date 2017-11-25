package businesslogic.inter;

public interface AddLogInterface {

	/**
	 * 向数据库中添加一条操作记录
	 * @param time 操作时间
	 * @param operatorId 操作员id
	 * @param operation 操作名称
	 * @param detail 详情
	 * @return 是否添加成功
	 */
	public boolean add(String time, String operatorId, String operation, String detail);
	
}
