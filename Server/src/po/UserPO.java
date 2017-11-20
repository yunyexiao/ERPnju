package po;

import java.io.Serializable;

/**
 * First edit time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>用户的PO类
 * @author 恽叶霄
 */
public class UserPO implements Serializable{
    
    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = 6831754628172855841L;
    private String userId, userName, userPwd, userSex, userTelNumber;
    private int userAge, usertype;
    
    public UserPO(){}

    public UserPO(String userId, String userName, String userPwd, String userSex, 
        String userTelNumber, int usertype, int userAge){
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userSex = userSex;
        this.userTelNumber = userTelNumber;
        this.usertype = usertype;
        this.userAge = userAge;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserTelNumber() {
        return userTelNumber;
    }

    public void setUserTelNumber(String userTelNumber) {
        this.userTelNumber = userTelNumber;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
    
    public class UserType{
        public static final int STORE_KEEPER = 0;
        public static final int SALESMAN = 1;
        public static final int ACCOUNTANT = 2;
        public static final int GM = 3;
        public static final int ADMIN = 4;
    }

}
