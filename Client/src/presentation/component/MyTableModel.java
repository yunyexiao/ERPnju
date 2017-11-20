package presentation.component;

import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	//private int[] rows = {};
	private int[] columns = {};
	
	public MyTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	public void setEditable(int[] columns) {
		this.columns = columns;
	}
	
	public boolean isCellEditable(int row, int column) {
		if (Arrays.binarySearch(columns, column) >= 0) {
			return true;
		}
		else return false;
	}
}
