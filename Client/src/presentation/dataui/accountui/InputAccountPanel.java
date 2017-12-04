package presentation.dataui.accountui;

import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import layout.TableLayout;
import presentation.dataui.util.Tools;
import vo.AccountVO;

public class InputAccountPanel extends JPanel{
	private JTextField accountIdTextField, accountNameTextField, accountMoneyTextField; 
	
	
	/**
	 * 根据已有数据初始化界面
	 * @param 表格中取出的一行数据
	 */
	protected InputAccountPanel(String[] account) {
		super();
		double[] rows = new double[7];
        rows[0] = TableLayout.FILL;
        for(int i = 0; i < 3; i++){
            rows[2 * i + 1] = TableLayout.PREFERRED;
            rows[2 * i + 2] = 10.0;
        }
        rows[rows.length - 1] = TableLayout.FILL;
        double[][] size = {{0.37, TableLayout.FILL, 10.0, TableLayout.FILL, 0.37}, rows};
        this.setLayout(new TableLayout(size));
		
		String[] texts = {"银行账号", "账户姓名", "余额"};
        JLabel[] labels = new JLabel[texts.length];
        for(int i = 0; i < labels.length; i++){
            labels[i] = new JLabel(texts[i]);
            this.add(labels[i], "1 " + (2 * i + 1));
        }
		
        accountIdTextField = new JTextField(account[0]);
		accountNameTextField = new JTextField(account[1]);
		accountMoneyTextField = new JTextField(account[2]);

        accountIdTextField.setEditable(false);
        
		add(accountIdTextField, "3 1");
		add(accountNameTextField, "3 3");
		add(accountMoneyTextField, "3 5");
	}
	
	/**
	 * 得到面板输入的AccountVO对象
	 * @return
	 */
	public AccountVO getAccountVO() {
	    String id = accountIdTextField.getText(),
	    		name = accountNameTextField.getText();
	    double money = Double.parseDouble(accountMoneyTextField.getText());
	    return new AccountVO(id, name, money);
	}
}		
