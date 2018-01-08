package vo;

import java.util.Arrays;

import javax.swing.table.DefaultTableModel;
/**
 * �̳�DefaultTableModel���Զ����࣬����ʵ�ֲ����޸ĵĹ���
 * @author user
 *
 */
public class MyTableModel extends DefaultTableModel {

	private int[] columns = {};
	
	public MyTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	/**
	 * ���ÿ��Ա༭����
	 * @param columns �к�����
	 */
	public void setEditable(int[] columns) {
		this.columns = columns;
	}
	
	public boolean isCellEditable(int row, int column) {
		if (Arrays.binarySearch(columns, column) >= 0) {
			return true;
		}
		else return false;
	}
	/**
	 * ���ĳһ�е�����(String������ʽ)
	 * @param n �к�
	 * @return ĳһ�е�����
	 */
	public String[] getValueAtRow(int n) {
		String[] data = new String[this.getColumnCount()];
		for (int i = 0; i < data.length; i++) {
			data[i] = (String) this.getValueAt(n, i);
		}
		return data;
	}
}
