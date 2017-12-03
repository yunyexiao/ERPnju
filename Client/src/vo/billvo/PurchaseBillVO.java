package vo.billvo;

import presentation.component.MyTableModel;


public class PurchaseBillVO extends MarketBillVO {
    
    public PurchaseBillVO(String date, String time, String id
        , String operator, int state, String customerId,
        String customerName, MyTableModel model
        , String remark, double sum) {
        super(date, time, id, operator, state, customerId
            , customerName, model, remark, sum);
    }
    
    @Override
    protected String getPrefix() {
        return "JHD";
    }

}
