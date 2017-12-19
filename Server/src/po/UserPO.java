package po;

import java.io.Serializable;

/**
 * First edit time: 2017/10/26<br>
 * Last update time: 2017/10/26<br>
 * <br>�û���PO��
 * @author �Ҷ��
 */
public class UserPO implements Serializable{
    private static final long serialVersionUID = 6831754628172855841L;
    private String userId, userName, userPwd ,userSex, userTelNumber;
    private int userAge, userRank, usertype;
    private boolean isExist;
    
    public UserPO(String userId, String userName, String userPwd, String userSex, String userTelNumber, int userAge,
			int userRank, int usertype, boolean isExist) {
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.userSex = userSex;
		this.userTelNumber = userTelNumber;
		this.userAge = userAge;
		this.userRank = userRank;
		this.usertype = usertype;
		this.isExist = isExist;
	}

    public String getUserId() {return userId;}

    public String getUserName() {return userName;}

    public String getUserPwd() {return userPwd;}
    
    public int getUserRank() {return userRank;}
    
    public String getUserSex() {return userSex;}

    public String getUserTelNumber() {return userTelNumber;}
    
    public int getUsertype() { return usertype;}

    public int getUserAge() {return userAge;}
    
    public boolean getExistFlag() {return isExist;}
    
    public class UserType{
        public static final int STORE_KEEPER = 0;
        public static final int SALESMAN = 1;
        public static final int ACCOUNTANT = 2;
        public static final int GM = 3;
        public static final int ADMIN = 4;
    }

    public String getRankName() {
    	if (usertype == UserType.SALESMAN) {
    		if (userRank == 0) return "��ͨ����Ա";
    		if (userRank == 1) return "���۾���";
    	}
    	else if (usertype == UserType.ACCOUNTANT) {
    		if (userRank == 0) return "��ͨ������Ա";
    		if (userRank == 1) return "���Ȩ�޲�����Ա";
    	}
    	return "Ĭ��";
    }
}