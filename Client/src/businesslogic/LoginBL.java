package businesslogic;

import blservice.LoginBLService;
import dataservice.UserDataService;
import po.UserPO;
import rmi.Rmi;
import vo.UserType;
import vo.UserVO;

public class LoginBL implements LoginBLService {

	@Override
	public UserVO getUser(String id, String password){
		System.out.println(id + "	" + password);
		UserDataService userDataService = Rmi.getRemote(UserDataService.class);
		try {
			UserPO user = userDataService.findById(id);
			if (password.equals(user.getUserPwd())) {
				return new UserVO(
						user.getUserName(), 
						user.getUserPwd(),
						UserType.getType(user.getUsertype()),
						user.getUserId(),
						user.getUserSex(),
						user.getUserTelNumber(),
						user.getUserAge());
			}
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
