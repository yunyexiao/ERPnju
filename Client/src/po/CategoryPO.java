package po;

import java.io.Serializable;

/**
 * Create time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>����Ʒ������ص�PO��
 * @author �Ҷ��
 */
public class CategoryPO implements Serializable {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = 802795139476003045L;
    
    private String id, name, fatherId;

	private boolean isExist;

    public CategoryPO() {}
    
    public CategoryPO(String id, String name, String fatherId, boolean isExist) {
        this.id = id;
        this.name = name;
        this.fatherId = fatherId;
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

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }
    
    public void setExistFlag(boolean flag) {
    	this.isExist = flag;
    }
    
    public boolean getExistFlag() {
    	return this.isExist;
    }

}