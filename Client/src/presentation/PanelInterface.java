package presentation;

import vo.UserVO;

public interface PanelInterface {
	
	//主窗口切换关闭Panel时，如果成功关闭，返回true,否则返回false
	public boolean close();
	
	//根据主窗口传递的VO对象初始化面板上的数据，注意与构造函数区别开
	public void init(UserVO user);
	
}
