package presentation.dataui;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import layout.TableLayout;
import vo.UserType;
import vo.UserVO;

/**
 * 用户信息的增加/修改面板
 * @author 恽叶霄
 *
 */
@SuppressWarnings("serial")
class InputUserPanel extends JPanel {

    private JTextField userNameTextField, userIdTextField, userAgeTextField, userTelTextField;
    private ButtonGroup sexButtonGroup, typeButtonGroup;

	/**
	 * 根据已有数据初始化界面
	 * @param 表格中取出的一行数据
	 */
	protected InputUserPanel(String[] user) {
		super();
        double[][] size = {{40.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 40.0}
		, {40.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED
			, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 40.0}};
		this.setLayout(new TableLayout(size));
	
		JLabel userNameLabel = new JLabel("用户名"),
               userTypeLabel = new JLabel("用户种类"),
			   userIdLabel = new JLabel("用户编号"),
			   userSexLabel = new JLabel("用户性别"),
			   userAgeLabel = new JLabel("用户年龄"),
			   userTelLabel = new JLabel("用户电话号码");

		userNameTextField = new JTextField();
        userIdTextField = new JTextField();
        userAgeTextField = new JTextField();
        userTelTextField = new JTextField();
        
        userIdTextField.setEditable(false);

		JRadioButton manRadioButton = new JRadioButton("男"),
			    	 womanRadioButton = new JRadioButton("女");
		manRadioButton.setSelected(true);
		JPanel sexPanel = new JPanel();
		sexPanel.add(manRadioButton);
		sexPanel.add(womanRadioButton);
		sexButtonGroup = new ButtonGroup();
		sexButtonGroup.add(manRadioButton);
		sexButtonGroup.add(womanRadioButton);

		JRadioButton typeAdminRadioButton = new JRadioButton(UserType.ADMIN.getName()),
					 typeKeeperRadioButton = new JRadioButton(UserType.KEEPER.getName()),
					 typeSalesmanRadioButton = new JRadioButton(UserType.SALESMAN.getName()),
					 typeAccountantRadioButton = new JRadioButton(UserType.ACCOUNTANT.getName()),
					 typeGmRadioButton = new JRadioButton(UserType.GM.getName());
		
		typeKeeperRadioButton.setSelected(true);
		JPanel typePanel = new JPanel();
		typePanel.add(typeAdminRadioButton);
		typePanel.add(typeKeeperRadioButton);
		typePanel.add(typeSalesmanRadioButton);
		typePanel.add(typeAccountantRadioButton);
		typePanel.add(typeGmRadioButton);
		typeButtonGroup = new ButtonGroup();
		typeButtonGroup.add(typeAdminRadioButton);
		typeButtonGroup.add(typeKeeperRadioButton);
		typeButtonGroup.add(typeSalesmanRadioButton);
		typeButtonGroup.add(typeAccountantRadioButton);
		typeButtonGroup.add(typeGmRadioButton);
		
		int length = user.length;
		if(length > 0 && user[0] != null) userIdTextField.setText(user[0]);
		if(length > 1 && user[1] != null) userNameTextField.setText(user[1]);
		if(length > 2 && user[2] != null){
		    Enumeration<AbstractButton> eb = typeButtonGroup.getElements();
		    while(eb.hasMoreElements()){
		        AbstractButton b = eb.nextElement(); 
		        if(b.getText().equals(user[2])){
		            b.setSelected(true);
		            break;
		        }
		    }
		}
		if(length > 3 && user[3] != null){
		    if(user[3].equals(manRadioButton.getText()))
		        manRadioButton.setSelected(true);
		    else womanRadioButton.setSelected(true);
		}
		if(length > 4 && user[4] != null) userAgeTextField.setText(user[4]);
        if(length > 5 && user[5] != null) userTelTextField.setText(user[5]);
	
		add(userIdLabel, "1 1");
		add(userNameLabel, "1 3");
		add(userTypeLabel, "1 5");
		add(userSexLabel, "1 7");
		add(userAgeLabel, "1 9");
		add(userTelLabel, "1 11");
		add(userIdTextField, "3 1");
		add(userNameTextField, "3 3");
		add(typePanel, "3 5");
		add(sexPanel, "3 7");
		add(userAgeTextField, "3 9");
		add(userTelTextField, "3 11");
	}

	/**
	 * 得到面板输入的UserVO对象
	 * @return
	 */
	public UserVO getUserVO() {
	    String name = userNameTextField.getText()
	        , id = userIdTextField.getText()
	        , sex = getSelectedText(sexButtonGroup)
	        , telNumber = userTelTextField.getText();
	    UserType type = getSelectedType();
        int age = Integer.parseInt(userAgeTextField.getText());
	    return new UserVO(name, type, id, sex, telNumber, age);
	}
	
	private String getSelectedText(ButtonGroup group){
	    Enumeration<AbstractButton> eb = group.getElements();
	    while(eb.hasMoreElements()){
	        AbstractButton b = eb.nextElement();
	        if(b.isSelected())
	            return b.getText();
	    }
	    return null;
	}
	
	private UserType getSelectedType(){
	    String type = getSelectedText(typeButtonGroup);
	    if(type.equals(UserType.ADMIN.getName()))
	        return UserType.ADMIN;
	    if(type.equals(UserType.KEEPER.getName()))
	        return UserType.KEEPER;
	    if(type.equals(UserType.SALESMAN.getName()))
	        return UserType.SALESMAN;
	    if(type.equals(UserType.ACCOUNTANT.getName()))
	        return UserType.ACCOUNTANT;
	    if(type.equals(UserType.GM.getName()))
	        return UserType.GM;
	    return null;
	}

}
