package presentation;

import javax.swing.JPanel;

import vo.UserVO;

public interface PanelInterface {
	
	/**
	 * Panel关闭自身时调用close确认退出并保存等
	 * @return 确认退出返回true,否则false(界面阶段默认为true)
	 * @author 钱美缘
	 */
	public boolean close();
	
	//根据主窗口传递的VO对象初始化面板上的数据，注意与构造函数区别开
	public void init(UserVO user);
	
	//返回所持有的JPanel对象的引用
	public JPanel getPanel();
	
}
