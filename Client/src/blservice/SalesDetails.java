package blservice;

import presentation.component.MyTableModel;

public interface SalesDetails {
    
    MyTableModel filter(String from, String to, String commodityId, String store
        , String customerId, String operatorId);

}
