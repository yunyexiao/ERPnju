package ds_stub;

import dataservice.PurchaseBillDataService;
import po.billpo.PurchaseBillPO;


public class PurchaseBillDs_stub implements PurchaseBillDataService {

    public PurchaseBillDs_stub() {}

    @Override
    public boolean saveBill(PurchaseBillPO purchaseBill) {
        System.out.println("purchase bill saved in database: " + purchaseBill.getId());
        return true;
    }

    @Override
    public boolean deleteBill(String id) {
        System.out.println("purchase bill deleted in database: " + id);
        return true;
    }

    @Override
    public String getNewId() {
        return "00123";
    }

    @Override
    public PurchaseBillPO getBillById(String id) {
        return null;
    }

}
