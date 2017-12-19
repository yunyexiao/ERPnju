package blservice.billblservice;

import presentation.component.MyTableModel;

public interface BillSearchBLService {
    
    MyTableModel filterInventoryBills(String from, String to, String store, String operatorId, boolean isOver);
    
    MyTableModel filterPurchaseBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterPurchaseReturnBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterSalesBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterSalesReturnBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterCashCostBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterPaymentBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterReceiptBills(String from, String to, String customerId, String operatorId);
}
