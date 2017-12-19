package blservice.billblservice;

import presentation.component.MyTableModel;
import vo.billvo.CashCostBillVO;
import vo.billvo.ChangeBillVO;
import vo.billvo.PaymentBillVO;
import vo.billvo.PurchaseBillVO;
import vo.billvo.PurchaseReturnBillVO;
import vo.billvo.ReceiptBillVO;
import vo.billvo.SalesBillVO;
import vo.billvo.SalesReturnBillVO;

public interface BillSearchBLService {
    
    MyTableModel filterInventoryBills(String from, String to, String store, String operatorId, boolean isOver);
    
    MyTableModel filterPurchaseBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterPurchaseReturnBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterSalesBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterSalesReturnBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterCashCostBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterPaymentBills(String from, String to, String customerId, String operatorId);

    MyTableModel filterReceiptBills(String from, String to, String customerId, String operatorId);
    
    MyTableModel filterInventoryBills(String from, String to, String store, String operatorId, boolean isOver, int state);
    
    MyTableModel filterPurchaseBills(String from, String to, String customerId, String operatorId, int state);

    MyTableModel filterPurchaseReturnBills(String from, String to, String customerId, String operatorId, int state);

    MyTableModel filterSalesBills(String from, String to, String customerId, String operatorId, int state);

    MyTableModel filterSalesReturnBills(String from, String to, String customerId, String operatorId, int state);

    MyTableModel filterCashCostBills(String from, String to, String customerId, String operatorId, int state);

    MyTableModel filterPaymentBills(String from, String to, String customerId, String operatorId, int state);

    MyTableModel filterReceiptBills(String from, String to, String customerId, String operatorId, int state);
    
    ChangeBillVO findInventoryBillById(String id);
    
    PurchaseBillVO findPurchaseBillById(String id);
    
    PurchaseReturnBillVO findPurchaseReturnBillById(String id);
    
    SalesBillVO findSalesBillById(String id);
    
    SalesReturnBillVO findSalesReturnBillById(String id);
    
    CashCostBillVO findCashCostBillById(String id);
    
    PaymentBillVO findPaymentBillById(String id);
    
    ReceiptBillVO findReceiptBillById(String id);

}
