package vo.billvo;

import presentation.component.MyTableModel;


public class PurchaseReturnBillVO extends MarketBillVO {

    public PurchaseReturnBillVO(String date, String time, String id, String operator, int state, String customerId,
        String customerName, MyTableModel model, String remark, double sum) {
        super(date, time, id, operator, state, customerId, customerName, model, remark, sum);
    }

    @Override
    protected String getPrefix() {
        return "JHTHD";
    }

}
