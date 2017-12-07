package presentation.dataui.userui;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.dataui.util.Tools;
import presentation.tools.InputCheck;
import vo.UserType;
import vo.UserVO;

/**
 * �û���Ϣ������/�޸����
 * @author �Ҷ��
 *
 */
@SuppressWarnings("serial")
class InputUserPanel extends JPanel {

    private JTextField userIdTextField, userNameTextField, userKeyTextField, userAgeTextField, userTelTextField;
    private ButtonGroup sexButtonGroup, typeButtonGroup;
    private JComboBox<String> rankComboBox;

	/**
	 * �����������ݳ�ʼ������
	 * @param �����ȡ����һ������
	 */
	protected InputUserPanel(String[] user) {
		super();
        double[][] size = {{40.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 40.0}
            , {20.0, TableLayout.FILL, 10.0, TableLayout.FILL, 10.0, TableLayout.FILL
                , 10.0, TableLayout.FILL, 10.0, TableLayout.FILL, 10.0, TableLayout.FILL, 10.0
                , TableLayout.FILL, 10.0, TableLayout.FILL, 20.0,}};
		this.setLayout(new TableLayout(size));
		
		String[] texts = {"�û����", "�û���", "�û�����", "�û�����", "�û��Ա�", "�û�����", "�û��绰����", "�û�����"};
		JLabel[] labels = new JLabel[texts.length];
		for(int i = 0; i < labels.length; i++){
		    labels[i] = new JLabel(texts[i]);
		    this.add(labels[i], "1 " + (2 * i + 1));
		}
	
        userIdTextField = new JTextField(user[0]);
		userNameTextField = new JTextField(user[1]);
		userKeyTextField = new JTextField(user[4]);
        userAgeTextField = new JTextField(user[6]);
        userTelTextField = new JTextField(user[7]);
        
        userIdTextField.setEditable(false);
        
        rankComboBox = new JComboBox<>();
        rankComboBox.addItem("Ĭ��");

		JRadioButton manRadioButton = new JRadioButton("��"),
			    	 womanRadioButton = new JRadioButton("Ů");
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
		typeKeeperRadioButton.addActionListener(e -> defaultType());
		typeSalesmanRadioButton.addActionListener(e -> salesType());
		typeAccountantRadioButton.addActionListener(e -> accountantType());
		typeGmRadioButton.addActionListener(e -> defaultType());
		typeAdminRadioButton.addActionListener(e -> defaultType());
		
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
		if(length > 2 && user[2] != null) {
        	if ("������Ա".equals(user[2])) accountantType();
        	if ("����������Ա".equals(user[2])) accountantType();
		    Enumeration<AbstractButton> eb = typeButtonGroup.getElements();
		    while(eb.hasMoreElements()){
		        AbstractButton b = eb.nextElement(); 
		        if(b.getText().equals(user[2])) {
		            b.setSelected(true);
		            break;
		        }
		    }
		}
		
		if(length > 5 && user[5] != null) {
		    if(user[5].equals(manRadioButton.getText()))
		        manRadioButton.setSelected(true);
		    else womanRadioButton.setSelected(true);
		}
		
		if(length > 3 && user[3] != null) {
			if (rankComboBox.getItemAt(0).equals(user[3])) rankComboBox.setSelectedIndex(0);
			else rankComboBox.setSelectedIndex(1);
		}
	
		add(userIdTextField, "3 1");
		add(userNameTextField, "3 3");
		add(userKeyTextField, "3 5");
		add(rankComboBox, "3 7");
		add(sexPanel, "3 9");
		add(userAgeTextField, "3 11");
		add(userTelTextField, "3 13");
		add(typePanel, "3 15");
	}

	/**
	 * �õ���������UserVO����
	 * @return
	 */
	public UserVO getUserVO() {
		if (! InputCheck.isLegal(userNameTextField.getText())) new InfoWindow("�û�������ʽ�Ƿ�");
		else if (! InputCheck.isAlnum(userKeyTextField.getText(), 0)) new InfoWindow("�û������ʽ�Ƿ�");
		else if (! InputCheck.isAllNumber(userAgeTextField.getText(), 2)) new InfoWindow("��������ʵ���û�����");
		else if (! InputCheck.isAllNumber(userTelTextField.getText(), 11)) new InfoWindow("������11λ�ֻ�����");
		else {
			String id = userIdTextField.getText()
			        , name = userNameTextField.getText()
			        , key = userKeyTextField.getText()
			        , sex = Tools.getSelectedText(sexButtonGroup)
			        , telNumber = userTelTextField.getText();
		    UserType type = getSelectedType();
		    int rank = rankComboBox.getSelectedIndex();
	        int age = Integer.parseInt(userAgeTextField.getText());
		    return new UserVO(name, key, type, rank, id, sex, telNumber, age);
		}
		return null;
	    
	}
	
	private UserType getSelectedType(){
	    String type = Tools.getSelectedText(typeButtonGroup);
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
	
	private void defaultType(){
	    rankComboBox.removeAllItems();
	    rankComboBox.addItem("Ĭ��");
	}
	
	private void salesType(){
	    rankComboBox.removeAllItems();
	    rankComboBox.addItem("��ͨ����Ա");
	    rankComboBox.addItem("���۾���");
	}

	private void accountantType(){
	    rankComboBox.removeAllItems();
	    rankComboBox.addItem("��ͨ������Ա");
	    rankComboBox.addItem("���Ȩ�޲�����Ա");
	}
}
