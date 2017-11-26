package po;

import java.io.Serializable;

/**
 * Create time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>有关企业账户的PO类
 * @author 恽叶霄
 */
public class AccountPO implements Serializable {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = -1081165244939370285L;
    
    private String id, name;
    double money;

    public AccountPO() {}
    
    public AccountPO(String id, String name, double money) {
        this.id = id;
        this.name = name;
        this.money = money;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

}