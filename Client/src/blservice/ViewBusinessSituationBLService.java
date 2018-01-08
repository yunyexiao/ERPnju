package blservice;

import presentation.component.MyTableModel;

public interface ViewBusinessSituationBLService {

	/**
	 * ����һ��ʱ��������ǰ�������ܶ�
	 * @param from
	 * @param to
	 * @return
	 */
	public double getSalesIncome(String from, String to);
	
	public double getCommodityOverflowIncome(String from, String to);
	
	public double getPurchaseAndReturnIncome(String from, String to);
	
	/**
	 * ����һ��ʱ����ʹ�õĴ���ȯ�ܶ�
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
