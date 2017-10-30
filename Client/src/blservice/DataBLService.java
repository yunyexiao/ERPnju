package blservice;

import javax.swing.table.DefaultTableModel;

public interface DataBLService {

	public boolean delete(String id);
	
	public DefaultTableModel search(String type, String key);
	
	public DefaultTableModel update();
	
}
