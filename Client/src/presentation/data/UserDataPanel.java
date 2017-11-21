package presentation.data;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import layout.TableLayout;
import vo.UserVO;

/**
 * 用户信息的增加/修改面板
 * @author 恽叶霄
 *
 */
class UserDataPanel extends JPanel {
	private final static double[][] size = {{40.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 40.0}
		, {40.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED
			, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 40.0}};
	/** 
	 * 默认构造方法，即生成一个空白的输入界面
	 */
	protected UserDataPanel() {
		super(new TableLayout(size));
	
		JLabel userNameLabel = new JLabel("用户名"),
               userTypeLabel = new JLabel("用户种类"),
			   userIdLabel = new JLabel("用户编号"),
			   userSexLabel = new JLabel("用户性别"),
			   userAgeLabel = new JLabel("用户年龄"),
			   userTelLabel = new JLabel("用户电话号码");

		JTextField userNameTextField = new JTextField(),
			       userIdTextField = new JTextField(),
			       userAgeTextField = new JTextField(),
			       userTelTextField = new JTextField();

		JRadioButton manRadioButton = new JRadioButton("男"),
			    	 womanRadioButton = new JRadioButton("女");
		JPanel sexPanel = new JPanel();
		sexPanel.add(manRadioButton);
		sexPanel.add(womanRadioButton);
		ButtonGroup sexButtonGroup = new ButtonGroup();
		sexButtonGroup.add(manRadioButton);
		sexButtonGroup.add(womanRadioButton);

		JRadioButton typeAdminRadioButton = new JRadioButton("用户管理员"),
					 typeKeeperRadioButton = new JRadioButton("库存管理员"),
					 typeSalesmanRadioButton = new JRadioButton("销售人员"),
					 typeAccountantRadioButton = new JRadioButton("财务人员"),
					 typeGmRadioButton = new JRadioButton("总经理");
		
		JPanel typePanel = new JPanel();
		typePanel.add(typeAdminRadioButton);
		typePanel.add(typeKeeperRadioButton);
		typePanel.add(typeSalesmanRadioButton);
		typePanel.add(typeAccountantRadioButton);
		typePanel.add(typeGmRadioButton);
		ButtonGroup typeButtonGroup = new ButtonGroup();
		typeButtonGroup.add(typeAdminRadioButton);
		typeButtonGroup.add(typeKeeperRadioButton);
		typeButtonGroup.add(typeSalesmanRadioButton);
		typeButtonGroup.add(typeAccountantRadioButton);
		typeButtonGroup.add(typeGmRadioButton);
		
		add(userNameLabel, "1 1");
		add(userTypeLabel, "1 3");
		add(userIdLabel, "1 5");
		add(userSexLabel, "1 7");
		add(userAgeLabel, "1 9");
		add(userTelLabel, "1 11");
		add(userNameTextField, "3 1");
		add(typePanel, "3 3");
		add(userIdTextField, "3 5");
		add(sexPanel, "3 7");
		add(userAgeTextField, "3 9");
		add(userTelTextField, "3 11");
	}
	/**
	 * 根据已有数据初始化界面
	 * @param 表格中取出的一行数据
	 */
	protected UserDataPanel(String[] user) {
		//TODO
	}
	/**
	 * 得到面板输入的UserVO对象
	 * @return
	 */
	public UserVO getUserVO() {
		//TODO 根据输入生成新的UserVO对象，输入合法的判断在此进行
		return null;
	}
}
