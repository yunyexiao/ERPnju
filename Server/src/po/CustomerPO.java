package po;

import java.io.Serializable;

/**
 * Create time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>顾客的PO类
 * @author 恽叶霄
 */
public class CustomerPO implements Serializable {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = -6850102650969420098L;
    
    private String id, name, telNumber, address, mail,code;
    /** 默认业务员 */
    private String salesman;
    /** 客户的级别，共5级 */
    private int rank;
    /** 客户的类别 */
    private int type;
    /** 应收额度 */
    private double recRange;
    /** 应收 */
    private double receivable;
    /** 应付 */
    private double payment;

	private boolean isExist;

    public CustomerPO() {}
    
    public CustomerPO(String id, String name, String telNumber, String address
        , String mail, String code,String salesman, int rank, int type, double recRange
        , double receivable, double payment) {
        this.setId(id);
        this.setName(name);
        this.setTelNumber(telNumber);
        this.setAddress(address);
        this.setMail(mail);
   
        this.setSalesman(salesman);
        this.setRank(rank);
        this.setType(type);
        this.setRecRange(recRange);
        this.setReceivable(receivable);
        this.setPayment(payment);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getRecRange() {
        return recRange;
    }

    public void setRecRange(double recRange) {
        this.recRange = recRange;
    }

    public double getReceivable() {
        return receivable;
    }

    public void setReceivable(double receivable) {
        this.receivable = receivable;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public void setExistFlag(boolean flag) {
    	this.isExist = flag;
    }
    
    public boolean getExistFlag() {
    	return this.isExist;
    }
    
    public class CustomerType{
        public static final int SUPPLIER = 1;
        public static final int VENDER = 2;
    }
    
}