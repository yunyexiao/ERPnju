package presentation.dataui.customerui;

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
import presentation.tools.InputCheck;
import vo.CustomerVO;

public class InputCustomerPanel extends JPanel{
	private JTextField customerIdTextField, customerNameTextField, customerTelNumberTextField,
	customerAddressTextField, customerCodeTextField, customerMailTextField, customerSalesmanTextField,
	customerRecRangeTextField; 
	
	private String[] customer;
	private JComboBox<String> comboBox;
	private JRadioButton purchaseRadioButton;
	
	/**
	 * �����������ݳ�ʼ������
	 * @param �����ȡ����һ������
	 */
	protected InputCustomerPanel(String[] customer, boolean isSaleGM) {
		super();
		this.customer = customer;
		double[] rows = new double[37];
        rows[0] = TableLayout.FILL;
        for(int i = 0; i < 12; i++){
            rows[3 * i + 1] = TableLayout.PREFERRED;
            rows[3 * i + 2] = 10.0;
        }
        rows[rows.length - 1] = TableLayout.FILL;
        double[][] size = {{TableLayout.FILL, TableLayout.PREFERRED, 10.0, 0.5, TableLayout.FILL}, rows};
        this.setLayout(new TableLayout(size));
		
		String[] texts = {"�ͻ����", "�ͻ�����", "����", "����", "�绰", "��ַ", 
				"�ʱ�", "��������", "Ӧ�ն��", "Ĭ��ҵ��Ա"};
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
        customerSalesmanTextField = new JTextField(customer[11]);


        customerIdTextField.setEditable(false);
        customerRecRangeTextField.setEditable(isSaleGM);

		purchaseRadioButton = new JRadioButton("������");
	    JRadioButton saleRadioButton = new JRadioButton("������");
		purchaseRadioButton.setSelected(true);
		JPanel typePanel = new JPanel();
		typePanel.add(purchaseRadioButton);
		typePanel.add(saleRadioButton);
		ButtonGroup typeButtonGroup = new ButtonGroup();
		typeButtonGroup.add(purchaseRadioButton);
		typeButtonGroup.add(saleRadioButton);
		
		comboBox = new JComboBox<String>(new String[]{"LV1","LV2","LV3","LV4","LV5"});

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
		add(comboBox, "3 10");
		add(customerTelNumberTextField, "3 13");
		add(customerAddressTextField, "3 16");
		add(customerCodeTextField, "3 19");
		add(customerMailTextField, "3 22");
		add(customerRecRangeTextField, "3 25");
		add(customerSalesmanTextField, "3 28");
	}
	
	/**
	 * �õ���������CustomerVO����
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
		if (! InputCheck.isLegal(customerNameTextField.getText())) new InfoWindow("������Ϸ�������");
		else if (! InputCheck.isAllNumber(telNumber, 11)) new InfoWindow("��������ȷ���ֻ���");
		else if (! InputCheck.isLegal(address)) new InfoWindow("������Ϸ��ĵ�ַ");
		else if (! InputCheck.isAllNumber(code, 6)) new InfoWindow("��������ȷ���ʱ�");
		else if (! InputCheck.isLegal(salesman)) new InfoWindow("������Ϸ���ҵ��Ա����");
		else if (! InputCheck.isDouble(customerRecRangeTextField.getText())) new InfoWindow("��������ȷ��Ӧ�ն��");
		else {
		    int type = purchaseRadioButton.isSelected()?0:1;
		    int rank = comboBox.getSelectedIndex()+1;
	        double recRange = Double.parseDouble(customerRecRangeTextField.getText());
	        double receivable = Double.parseDouble(customer[9]);
	        double payment = Double.parseDouble(customer[10]);
	        
		    return new CustomerVO(id, name, type, rank, telNumber, address, code, mail, recRange, receivable, payment, salesman);
		}
	    return null;
	}
}		
