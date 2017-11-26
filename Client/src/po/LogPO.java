package po;

import java.io.Serializable;

/**
 * Create time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>系统日志相关PO类
 * @author 恽叶霄
 */
public class LogPO implements Serializable {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = -5033948963240199526L;
    
    private String id, name, record;

    public LogPO() {}
    
    public LogPO(String id, String name, String record) {
        this.id = id;
        this.name = name;
        this.record = record;
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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

}