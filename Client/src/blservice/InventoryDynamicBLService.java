package blservice;

import presentation.component.MyTableModel;

public interface InventoryDynamicBLService {
    
    MyTableModel getDynamic(String from, String to);
    
    MyTableModel getDefault();

}
