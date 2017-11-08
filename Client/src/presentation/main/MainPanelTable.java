package presentation.main;

import javax.swing.table.DefaultTableModel;

class MainPanelTable extends DefaultTableModel {

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
