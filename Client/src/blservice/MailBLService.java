package blservice;

import java.util.ArrayList;

import vo.MailVO;
import vo.UserVO;

public interface MailBLService {

	public boolean saveMail(String fromId, String toId, String content);
	
	public boolean saveMail(String fromId, int type, String content);
	
	public boolean readMail(MailVO mail);
	
	public ArrayList<MailVO> getMailList(UserVO user);
}
