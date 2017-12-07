package presentation.dataui.accountui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.tools.InputCheck;
import vo.AccountVO;

public class InputAccountPanel extends JPanel{
	
	private JTextField accountIdTextField, accountNameTextField, accountMoneyTextField; 
	
	/**
	 * �����������ݳ�ʼ������
	 * @param �����ȡ����һ������
	 */
	protected InputAccountPanel(String[] account, boolean isAdd) {
		super();
		double[] rows = new double[7];
        rows[0] = TableLayout.FILL;
        for(int i = 0; i < 3; i++){
            rows[2 * i + 1] = TableLayout.PREFERRED;
            rows[2 * i + 2] = 10.0;
        }
        rows[rows.length - 1] = TableLayout.FILL;
        double[][] size = {{TableLayout.FILL, TableLayout.PREFERRED, 10.0, 0.5, TableLayout.FILL}, rows};
        this.setLayout(new TableLayout(size));
		
		String[] texts = {"�����˺�", "�˻�����", "���"};
        JLabel[] labels = new JLabel[texts.length];
        for(int i = 0; i < labels.length; i++){
            labels[i] = new JLabel(texts[i]);
            this.add(labels[i], "1 " + (2 * i + 1));
        }
		
        accountIdTextField = new JTextField(account[0]);
		accountNameTextField = new JTextField(account[1]);
		accountMoneyTextField = new JTextField(account[2]);

		accountIdTextField.setEditable(isAdd);
		accountMoneyTextField.setEditable(isAdd);
        
		add(accountIdTextField, "3 1");
		add(accountNameTextField, "3 3");
		add(accountMoneyTextField, "3 5");
	}
	
	/**
	 * �õ���������AccountVO����
	 * @return
	 */
	public AccountVO getAccountVO() {
		if (! InputCheck.isAllNumber(accountIdTextField.getText(), 0)) new InfoWindow("�������ʽ��ȷ�Ŀ���");
		else if (! InputCheck.isLegal(accountIdTextField.getText())) new InfoWindow("�������ʽ��ȷ���˻�����");
		else if (! InputCheck.isDouble(accountMoneyTextField.getText())) new InfoWindow("��������ȷ�����");
		else {
			String id = accountIdTextField.getText(),
		    		name = accountNameTextField.getText();
		    double money = Double.parseDouble(accountMoneyTextField.getText());
		    return new AccountVO(name, id, money);
		}
	    return null;
	}
}		
