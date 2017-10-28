package vo;

import javax.swing.table.DefaultTableModel;

public class BillVO {

	private String id;//µ¥¾Ý±àºÅ
	private String operator;
	private String chooseA;
	private String chooseB;
	private DefaultTableModel model;
	private String sum;
	private String remark = "";
	
	public BillVO(String id, String operator, String chooseA, String chooseB, DefaultTableModel model, String sum, String remark) {
		this.id = id;
		this.operator = operator;
		this.chooseA = chooseA;
		this.chooseB = chooseB;
		this.model = model;
		this.sum = sum;
		this.remark = remark;
	}
	
	public String getId() {
		return id;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public DefaultTableModel getModel() {
		return model;
	}
}
