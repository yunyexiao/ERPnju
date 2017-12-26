package blservice;

import presentation.component.MyTableModel;

public interface ViewBusinessSituationBLService {

	/**
	 * 计算一段时间内折让前的销售总额
	 * @param from
	 * @param to
	 * @return
	 */
	public double getSalesIncome(String from, String to);
	
	public double getCommodityOverflowIncome(String from, String to);
	
	//成本调价收入，略迷，不知道为什么成本调价会带来收入
	
	public double getPurchaseAndReturnIncome(String from, String to);
	
	/**
	 * 计算一段时间内使用的代金券总额
	 * @param from
	 * @param to
	 * @return
	 */
	public double getCashCouPonIncome(String from, String to);
	
	public double getSalesExpense(String from, String to);
	
	public double getCommodityBrokenExpense(String from, String to);
	
	public double getCommoditySentExpense(String from, String to);
	
	public MyTableModel fillIncomeTable(String from, String to);
	
	public MyTableModel fillExpenseTable(String from, String to);
	
	public double getProfit(String from, String to);

}
