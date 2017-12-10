package bl_stub;

import blservice.billblservice.PaymentBillBLService;
import vo.billvo.PaymentBillVO;

public class PaymentBillBL_stub implements PaymentBillBLService {

    public PaymentBillBL_stub() {}

    @Override
    public String getNewId() {
        return "FKD-20171205-00001";
    }

    @Override
    public boolean deleteBill(String id) {
        System.out.println(id + " has been deleted");
        return true;
    }

    @Override
    public boolean saveBill(PaymentBillVO bill) {
        System.out.println(bill.getId() + " has been saved");
        return true;
    }

    @Override
    public boolean updateBill(PaymentBillVO bill) {
        System.out.println(bill.getId() + " has been updated");
        return true;
    }

    @Override
    public PaymentBillVO getBill(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}