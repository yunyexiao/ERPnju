package blservice;

import presentation.component.MyTableModel;

public interface SalesDetailsBLService {
    
    MyTableModel filter(String from, String to, String commodityId, String store
        , String customerId, String operatorId);

}
