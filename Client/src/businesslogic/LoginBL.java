package businesslogic;

import blservice.LoginBLService;
import businesslogic.inter.AddLogInterface;
import dataservice.UserDataService;
import ds_stub.UserDs_stub;
import po.UserPO;
import rmi.Rmi;
import vo.UserType;
import vo.UserVO;

public class LoginBL implements LoginBLService {

	@Override
	public UserVO getUser(String id, String password){
		System.out.println(id + "	" + password);
		UserDataService userDataService = new UserDs_stub();//Rmi.getRemote(UserDataService.class);
		AddLogInterface addLog;
		try {
			UserPO user = userDataService.findById(id);
			if (password.equals(user.getUserPwd())) {
				UserVO userVO =  new UserVO(
						user.getUserName(), 
						user.getUserPwd(),
						UserType.getType(user.getUsertype()),
						user.getUserRank(),
						user.getUserId(),
						user.getUserSex(),
						user.getUserTelNumber(),
						user.getUserAge());
				addLog = new LogBL(userVO);
				addLog.add("ÓÃ»§µÇÂ¼", "µÇÂ¼IP£º"+Rmi.getIPAddress());
				return userVO;
			}
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
