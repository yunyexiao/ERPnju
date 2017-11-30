package po.billpo;

public abstract class BillPO {

	public static int DRAFT = 0;
	public static int SAVED = 1;
	public static int COMMITED = 2;
	public static int PASS = 3;
	public static int NOTPASS = 4;
	
	private String date;
	private String time;
	private String id;
	private String operator;
	private int state;
	
	/**
	 * BillVO的构造方法
	 * @param date 制定日期
	 * @param time 制定时间
	 * @param id 每一天之内的编号
	 * @param operator 操作人员的id
	 */
	public BillPO(String date, String time, String id, String operator, int state) {
		this.date = date;
		this.time = time;
		this.id = id;
		this.operator = operator;
		this.state = state;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getId() {
		return id;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public int getState() {
		return state;
	}
}
