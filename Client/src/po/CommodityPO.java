package po;

import java.io.Serializable;

/**
 * Create time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>��Ʒ��ص�PO��
 * @author �Ҷ��
 */
public class CommodityPO implements Serializable {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = 6222451958770566420L;
    
    private String id, name, type, store, categoryId, categoryName;
    private long amount, alarmNum;
    private double inPrice, salePrice, recentInPrice, recentSalePrice;

	private boolean isExist;

    public CommodityPO() {}
    
    public CommodityPO(String id, String name, String type, String store, String categoryId
        , String categoryName, long amount, long alarmNum, double inPrice, double salePrice
        , double recentInPrice, double recentSalePrice, boolean isExist) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.store = store;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.amount = amount;
        this.alarmNum = alarmNum;
        this.inPrice = inPrice;
        this.salePrice = salePrice;
        this.recentInPrice = recentInPrice;
        this.recentSalePrice = recentSalePrice;
        this.isExist = isExist;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(long alarmNum) {
        this.alarmNum = alarmNum;
    }

    public double getInPrice() {
        return inPrice;
    }

    public void setInPrice(double inPrice) {
        this.inPrice = inPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getRecentInPrice() {
        return recentInPrice;
    }

    public void setRecentInPrice(double recentInPrice) {
        this.recentInPrice = recentInPrice;
    }

    public double getRecentSalePrice() {
        return recentSalePrice;
    }

    public void setRecentSalePrice(double recentSalePrice) {
        this.recentSalePrice = recentSalePrice;
    }
    
    public void setExistFlag(boolean flag) {
    	this.isExist = flag;
    }
    
    public boolean getExistFlag() {
    	return this.isExist;
    }

}