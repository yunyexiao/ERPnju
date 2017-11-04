package presentation.main;

import javax.swing.table.DefaultTableModel;

class MainPanelTable extends DefaultTableModel {

	public MainPanelTable(String[][] billInfo, String[] strings) {
		super(billInfo, strings);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
