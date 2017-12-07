package vo.billvo;

import presentation.component.MyTableModel;


public class SalesReturnBillVO extends MarketBillVO {
    
    private String originalSBId;
    private double discountRate, originalSum;

    public SalesReturnBillVO(String date, String time, String id
        , String operator, int state, String customerId,
        String customerName, MyTableModel model, String remark
        , String originalSBId, double discountRate, double originalSum, double sum) {
        super(date, time, id, operator, state, customerId
            , customerName, model, remark, sum);
        this.originalSBId = originalSBId;
        this.discountRate = discountRate;
        this.originalSum = originalSum;
    }

    @Override
    protected String getPrefix() {
        return "XSTHD";
    }
    
    public String getOriginalSBId(){
        return this.originalSBId;
    }
    
    public double getOriginalSum(){
        return this.originalSum;
    }
    
    public double getDiscountRate(){
        return this.discountRate;
    }

}
