package presentation.data;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import layout.TableLayout;
import vo.UserVO;

/**
 * �û���Ϣ������/�޸����
 * @author �Ҷ��
 *
 */
class UserDataPanel extends JPanel {
	private final static double[][] size = {{40.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 40.0}
		, {40.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED
			, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 40.0}};
	/** 
	 * Ĭ�Ϲ��췽����������һ���հ׵��������
	 */
	protected UserDataPanel() {
		super(new TableLayout(size));
	
		JLabel userNameLabel = new JLabel("�û���"),
               userTypeLabel = new JLabel("�û�����"),
			   userIdLabel = new JLabel("�û����"),
			   userSexLabel = new JLabel("�û��Ա�"),
			   userAgeLabel = new JLabel("�û�����"),
			   userTelLabel = new JLabel("�û��绰����");

		JTextField userNameTextField = new JTextField(),
			       userIdTextField = new JTextField(),
			       userAgeTextField = new JTextField(),
			       userTelTextField = new JTextField();

		JRadioButton manRadioButton = new JRadioButton("��"),
			    	 womanRadioButton = new JRadioButton("Ů");
		JPanel sexPanel = new JPanel();
		sexPanel.add(manRadioButton);
		sexPanel.add(womanRadioButton);
		ButtonGroup sexButtonGroup = new ButtonGroup();
		sexButtonGroup.add(manRadioButton);
		sexButtonGroup.add(womanRadioButton);

		JRadioButton typeAdminRadioButton = new JRadioButton("�û�����Ա"),
					 typeKeeperRadioButton = new JRadioButton("������Ա"),
					 typeSalesmanRadioButton = new JRadioButton("������Ա"),
					 typeAccountantRadioButton = new JRadioButton("������Ա"),
					 typeGmRadioButton = new JRadioButton("�ܾ���");
		
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
	 * �����������ݳ�ʼ������
	 * @param �����ȡ����һ������
	 */
	protected UserDataPanel(String[] user) {
		//TODO
	}
	/**
	 * �õ���������UserVO����
	 * @return
	 */
	public UserVO getUserVO() {
		//TODO �������������µ�UserVO��������Ϸ����ж��ڴ˽���
		return null;
	}
}
