package vo.billvo;

import presentation.component.MyTableModel;

public abstract class MarketBillVO extends BillVO {
    
    private String customerId, customerName;
    private MyTableModel model;
    private String remark;
    private double sum;

    public MarketBillVO(String date, String time, String id
        , String operator, int state, String customerId
        , String customerName, MyTableModel model
        , String remark, double sum) {
        super(date, time, id, operator, state);
        this.customerId = customerId;
        this.customerName = customerName;
        this.model = model;
        this.remark = remark;
        this.sum = sum;
    }

    @Override
    public String getAllId() {
        return getPrefix() + "-" + getDate() + "-" + getId(); 
    }
    
    abstract protected String getPrefix();

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public MyTableModel getModel() {
        return model;
    }

    public String getRemark() {
        return remark;
    }
    
    public double getSum(){
        return sum;
    }

}