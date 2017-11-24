package vo;

public class LogInfoVO {

	private String time;
	private String operatorId;
	private String operation;
	private String detail;
	
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
