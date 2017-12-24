package po;

import java.io.Serializable;

/**
 * Create time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>�й���ҵ�˻���PO��
 * @author �Ҷ��
 */
public class AccountPO implements Serializable {

    private static final long serialVersionUID = -1081165244939370285L;
    private String id, name;
    private double money;
	private boolean isExist;
    
    public AccountPO(String id, String name, double money, boolean isExist) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.isExist = isExist;
    }

    public String getId() {return id;}

    public String getName() {return name;}

    public double getMoney() {return money;}
    
    public boolean getExistFlag() {return isExist;}
}