package po;

/**
 * ��������PO�ĳ�����
 * @author �Ҷ��
 */
public abstract class PromotionPO {
    
    private String id;
    /** �ô������Ե��������� */
    private String fromDate, toDate;
    private boolean isExist;
    
    protected PromotionPO(){
        this(null, null, null);
    }
    
    protected PromotionPO(String id, String fromDate, String toDate){
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.isExist = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean isExist) {
        this.isExist = isExist;
    }

}
