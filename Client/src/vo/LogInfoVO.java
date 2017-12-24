package vo;

public class LogInfoVO {

	private String time;
	private String operatorId;
	private String operation;
	private String detail;
	
	/**
	 * ������¼�Ĺ��캯��
	 * @param time ����ʱ��
	 * @param operatorId ����Աid
	 * @param operation ��������
	 * @param detail ����
	 */
	public LogInfoVO(String time, String operatorId, String operation, String detail) {
		this.time = time;
		this.operatorId = operatorId;
		this.operation = operation;
		this.detail = detail;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getOperatorId() {
		return operatorId;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public String getDetail() {
		return detail;
	}
}
