package vo;

/**
 * 促销策略VO的抽象父类
 * 
 * @author 恽叶霄
 */
public abstract class PromotionVO {
    
    private String id;
    private String from, to;

    public PromotionVO(String id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }
    
    @Override
    abstract public String toString();

    public String getId() {
        return id;
    }

    public String getFromDate() {
        return from;
    }

    public String getToDate() {
        return to;
    }

}
