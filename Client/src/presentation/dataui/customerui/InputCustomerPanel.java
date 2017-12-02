package presentation.dataui.customerui;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import layout.TableLayout;
import presentation.dataui.util.Tools;
import vo.CustomerVO;
import vo.UserType;
import vo.UserVO;

public class InputCustomerPanel extends JPanel{
	private JTextField customerIdTextField, customerNameTextField, customerTelNumberTextField,
	customerAddressTextField, customerCodeTextField, customerMailTextField, customerSalesmanTextField,
	customerRecRangeTextField, customerReceivableTextField, customerPaymentTextField; 
	
	private ButtonGroup rankButtonGroup, typeButtonGroup;
	
	/**
	 * 根据已有数据初始化界面
	 * @param 表格中取出的一行数据
	 */
	protected InputCustomerPanel(String[] customer) {
		super();
		double[] rows = new double[37];
        rows[0] = TableLayout.FILL;
        for(int i = 0; i < 12; i++){
            rows[3 * i + 1] = TableLayout.PREFERRED;
            rows[3 * i + 2] = 10.0;
        }
        rows[rows.length - 1] = TableLayout.FILL;
        double[][] size = {{0.37, TableLayout.FILL, 10.0, TableLayout.FILL, 0.37}, rows};
        this.setLayout(new TableLayout(size));
		
		String[] texts = {"客户编号", "客户姓名", "分类", "级别", "电话", "地址", 
				"邮编", "电子邮箱", "应收额度", "应收", "应付", "默认业务员"};
        JLabel[] labels = new JLabel[texts.length];
        for(int i = 0; i < labels.length; i++){
            labels[i] = new JLabel(texts[i]);
            this.add(labels[i], "1 " + (3 * i + 1));
        }
		
        customerIdTextField = new JTextField(customer[0]);
		customerNameTextField = new JTextField(customer[1]);
		customerTelNumberTextField = new JTextField(customer[4]);
        customerAddressTextField = new JTextField(customer[5]);
        customerCodeTextField = new JTextField(customer[6]);
        customerMailTextField = new JTextField(customer[7]);
        customerRecRangeTextField = new JTextField(customer[8]);
        customerReceivableTextField = new JTextField(customer[9]);
        customerPaymentTextField = new JTextField(customer[10]);
        customerSalesmanTextField = new JTextField(customer[11]);


        customerIdTextField.setEditable(false);

		JRadioButton purchaseRadioButton = new JRadioButton("进货商");
	    JRadioButton saleRadioButton = new JRadioButton("销售商");
		purchaseRadioButton.setSelected(true);
		JPanel typePanel = new JPanel();
		typePanel.add(purchaseRadioButton);
		typePanel.add(saleRadioButton);
		typeButtonGroup = new ButtonGroup();
		typeButtonGroup.add(purchaseRadioButton);
		typeButtonGroup.add(saleRadioButton);
		
		JRadioButton rank1RadioButton = new JRadioButton("1");
		JRadioButton rank2RadioButton = new JRadioButton("2");
		JRadioButton rank3RadioButton = new JRadioButton("3");
		JRadioButton rank4RadioButton = new JRadioButton("4");
		JRadioButton rank5RadioButton = new JRadioButton("5");
		rank1RadioButton.setSelected(true);
		JPanel rankPanel = new JPanel();
		rankPanel.add(rank1RadioButton);
		rankPanel.add(rank2RadioButton);
		rankPanel.add(rank3RadioButton);
		rankPanel.add(rank4RadioButton);
		rankPanel.add(rank5RadioButton);
		rankButtonGroup = new ButtonGroup();
		rankButtonGroup.add(rank1RadioButton);
		rankButtonGroup.add(rank2RadioButton);
		rankButtonGroup.add(rank3RadioButton);
		rankButtonGroup.add(rank4RadioButton);
		rankButtonGroup.add(rank5RadioButton);

		int length = customer.length;
		
		if(length > 2 && customer[2] != null){
			if(customer[2].equals(purchaseRadioButton.getText()))
		        purchaseRadioButton.setSelected(true);
		    else saleRadioButton.setSelected(true);
		}

		if(length > 3 && customer[3] != null){
		    Enumeration<AbstractButton> eb = typeButtonGroup.getElements();
		    while(eb.hasMoreElements()){
		        AbstractButton b = eb.nextElement(); 
		        if(b.getText().equals(customer[3])){
		            b.setSelected(true);
		            break;
		        }
		    }
		}
		
		add(customerIdTextField, "3 1");
		add(customerNameTextField, "3 4");
		add(typePanel, "3 7");
		add(rankPanel, "3 10");
		add(customerTelNumberTextField, "3 13");
		add(customerAddressTextField, "3 16");
		add(customerCodeTextField, "3 19");
		add(customerMailTextField, "3 22");
		add(customerRecRangeTextField, "3 25");
		add(customerReceivableTextField, "3 28");
		add(customerPaymentTextField, "3 31");
		add(customerSalesmanTextField, "3 34");
	}
	
	/**
	 * 得到面板输入的CustomerVO对象
	 * @return
	 */
	public CustomerVO getCustomerVO() {
	    String id = customerIdTextField.getText(),
	    		name = customerNameTextField.getText(),
	    		telNumber = customerTelNumberTextField.getText(), 
	    		address = customerAddressTextField.getText(), 
	    		code = customerCodeTextField.getText(),
	    		mail = customerMailTextField.getText(),
	    		salesman = customerSalesmanTextField.getText();
	    int type = Integer.parseInt(Tools.getSelectedText(typeButtonGroup));//可能有问题
	    int rank = Integer.parseInt(Tools.getSelectedText(rankButtonGroup));
        double recRange = Double.parseDouble(customerRecRangeTextField.getText());
        double receivable = Double.parseDouble(customerReceivableTextField.getText());
        double payment = Double.parseDouble(customerPaymentTextField.getText());

	    return new CustomerVO(id, name, type, rank, telNumber, address, code, mail, recRange, receivable, payment, salesman);
	}
}		
