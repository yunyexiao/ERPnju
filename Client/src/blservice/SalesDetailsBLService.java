package blservice;

import vo.MyTableModel;

public interface SalesDetailsBLService {
    /**��ȡ������ϸ��*/
    MyTableModel filter(String from, String to, String commodityId, String store
        , String customerId, String operatorId);
}
