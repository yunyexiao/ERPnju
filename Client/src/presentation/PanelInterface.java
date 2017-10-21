package presentation;

import vo.UserVO;

public interface PanelInterface {
	
	public boolean close();
	
	public void init(UserVO user);
	
}
