package businesslogic;

import java.util.HashMap;
import java.util.Map;

import blservice.billblservice.BillOperationService;
import vo.billvo.BillVO;


public class BillOperationBL implements BillOperationService {
    
    private Map<String, BillOperationService> services;

    public BillOperationBL() {
        services = new HashMap<>();
        services.put("XJFYD", new CashCostBillBL());
        // TODO to synchronize with develop
        services.put("BYD", new ChangeBillBL());
        services.put("BSD", new ChangeBillBL());
        services.put("FKD", new PaymentBillBL());
        services.put("JHD", new PurchaseBillBL());
        services.put("JHTHD", new PurchaseReturnBillBL());
        services.put("XSD", new SalesBillBL());
        services.put("XSTHD", new SalesReturnBillBL());
        services.put("SKD", new ReceiptBillBL());
    }

    @Override
    public boolean offsetBill(String billId) {
        String key = billId.split("-")[0];
        return services.get(key).offsetBill(billId);
    }

    @Override
    public boolean copyBill(BillVO bill) {
        String key = bill.getAllId().split("-")[0];
        return services.get(key).copyBill(bill);
    }

}
