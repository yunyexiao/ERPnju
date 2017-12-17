package po;

import java.io.Serializable;

/**
 * Create time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>�˿͵�PO��
 * @author �Ҷ��
 */
public class CustomerPO implements Serializable {

    private static final long serialVersionUID = -6850102650969420098L;
    private String id, name, telNumber, address, mail, code;
    private String salesman;// Ĭ��ҵ��Ա
    private int rank;//�ͻ��ļ��𣬹�5��
    private int type;//�ͻ������
    private double recRange;//Ӧ�ն��
    private double receivable;//Ӧ��
    private double payment;//Ӧ��
	private boolean isExist;
    
    public CustomerPO(String id, String name, String telNumber, String address
            , String mail, String code,String salesman, int rank, int type, double recRange
            , double receivable, double payment, boolean isExist) {
		super();
		this.id = id;
		this.name = name;
		this.telNumber = telNumber;
		this.address = address;
		this.mail = mail;
		this.code = code;
		this.salesman = salesman;
		this.rank = rank;
		this.type = type;
		this.recRange = recRange;
		this.receivable = receivable;
		this.payment = payment;
		this.isExist = isExist;
	}

    public String getId() {return id;}

    public String getName() {return name;}

    public String getTelNumber() {return telNumber;}

    public String getAddress() {return address;}

    public String getMail() {return mail;}
    
    public String getCode() {return code;}

    public String getSalesman() {return salesman;}

    public int getRank() {return rank;}

    public int getType() {return type;}

    public double getRecRange() {return recRange;}

    public double getReceivable() {return receivable;}

    public double getPayment() {return payment;}
    
    public boolean getExistFlag() {return this.isExist;}
    
    public class CustomerType{
        public static final int SUPPLIER = 1;
        public static final int VENDER = 2;
    }
}