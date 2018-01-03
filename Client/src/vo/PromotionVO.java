package vo;

import po.PromotionPO;
import presentation.component.MyTableModel;

/**
 * ��������VO�ĳ�����
 * 
 * @author �Ҷ��
 */
public abstract class PromotionVO {
    
    private String id;
    private String from, to;
    /** һ��������������������Ʒ */
    private MyTableModel gifts;
    /** һ�����������������Ľ����ܶ� */
    protected double reduction;

    public PromotionVO(String id, String from, String to, MyTableModel gifts) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.gifts = gifts;
    }
    
    @Override
    public String toString(){
        return "��ţ�" + id;
    }
    
    abstract public PromotionPO toPO();
    
    public String getId() {
        return id;
    }

    public String getFromDate() {
        return from;
    }

    public String getToDate() {
        return to;
    }

    public MyTableModel getGifts() {
        return gifts;
    }

    public double getReduction() {
        return reduction;
    }

    /**
     * @param num ��Ͻ���ר�õĲ�������ʾ�ͻ��������ϵ��������ò�������������������Ч��
     */
    public void setReduction(int num) {}

}
