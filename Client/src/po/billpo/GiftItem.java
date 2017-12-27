package po.billpo;

import java.io.Serializable;

/**
 * ��ʾһ����Ʒ�������Ϣ
 * 
 * @author �Ҷ��
 */
@SuppressWarnings("serial")
public class GiftItem implements Serializable{
    
    private String comId;
    private int num;
    private double price;

    public GiftItem(String comId, int num, double price) {
        this.comId = comId;
        this.num = num;
        this.price = price;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
