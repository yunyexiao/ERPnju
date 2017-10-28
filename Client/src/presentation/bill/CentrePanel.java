package presentation.bill;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import layout.TableLayout;
import vo.BillVO;

/**
 * BillPanel�����岿��
 * @author Ǯ��Ե
 */
public class CentrePanel {
	public static int PURCHASE = 0;
	public static int SALE = 1;
	
	private JPanel panel = new JPanel();
	private JPanel firstPanel, secondPanel, thirdPanel,centerPanel,scrollPanel,forthPanel,fifthPanel;
	private JScrollPane goodsListPane;
	private JLabel chooseLabel, listNameLabel, sumLabel, remarkLabel;
	private JButton chooseButton = new JButton();
	private JButton tableAddButton = new JButton(new ImageIcon("resource/AddButton.png"));
	private JButton tableDeleteButton = new JButton(new ImageIcon("resource/DeleteButton.png"));
	private JButton tableSumButton = new JButton(new ImageIcon("resource/FreshButton.png"));
	private JTextField billIdField = new JTextField(15);
	private JTextField operaterField = new JTextField(15);
	private JTextField chooseFieldA = new JTextField(10); 
	private JTextField chooseFieldB = new JTextField(10); 
	private JTextField sumField = new JTextField(5);
	private JTextField remarkField = new JTextField(30);
	private JTable listTable;
	private String[] goodsListAttributes={"��Ʒ���","����","�ͺ�","����","�ֿ�","����","���","��ע"};
	private String[][] goodsInfo={{"001","xx","yy","20","3","3","40","xx"}};
	
	private JLabel createLabel(String s) {
		JLabel label = new JLabel(s);
		label.setFont(new Font("����",Font.PLAIN,14));
		return label;
	}
	
	private void setPanel(int type, boolean editable) {
		if (type == PURCHASE) {
			chooseLabel = createLabel("��Ӧ��");
			chooseFieldA.setText("���");
			chooseFieldB.setText("����");
			chooseButton.setText("ѡ��Ӧ��");
			listNameLabel.setText("�����Ʒ�б�");
			tableAddButton.setText("ѡ����Ʒ");
			tableDeleteButton.setText("ɾ����Ʒ");
			tableSumButton.setText("�ܶ�ϼ�");
			sumLabel.setText("�����ܶ�");
			remarkLabel.setText("��ע");
		} else if (type == SALE) {
			chooseLabel = createLabel("�ͻ�");
			chooseFieldA.setText("���");
			chooseFieldB.setText("����");
			chooseButton.setText("ѡ��ͻ�");
			listNameLabel.setText("������Ʒ�б�");
			tableAddButton.setText("ѡ����Ʒ");
			tableDeleteButton.setText("ɾ����Ʒ");
			tableSumButton.setText("�ܶ�ϼ�");
			sumLabel.setText("����ǰ�ܶ�");
			remarkLabel.setText("��ע");
		}
		//setEnable()
		chooseButton.setEnabled(editable);
		tableAddButton.setEnabled(editable);
		tableDeleteButton.setEnabled(editable);
		tableSumButton.setEnabled(editable);
	}
	/**
	 * ��ʼ��CentrePanel
	 * @param type ��ʾ�ĵ������ͱ�ʶ
	 * @param editable �Ƿ���޸ģ�trueΪ�����޸ģ�
	 */
	public CentrePanel(int type, boolean editable) {
		//first panel
		JLabel billIdLabel = createLabel("���ݱ��");
		JLabel operatorLabel = createLabel("������");
		billIdField.setEditable(false);
		operaterField.setEditable(false);
		//second panel
		chooseFieldA.setEditable(false);
		chooseFieldB.setEditable(false);
		//third panel
		listNameLabel = createLabel("");
		//forth panel
		sumLabel = createLabel("");
		sumField.setEditable(false);
		//fifth panel
		remarkLabel = createLabel("");
		
		setPanel(type, editable);
		
		firstPanel=new JPanel();
		double firstPanelSize[][]={
				{20,0.08,5,150,20,0.08,5,150,TableLayout.FILL},
				{TableLayout.FILL,0.65,TableLayout.FILL}
		};
		firstPanel.setLayout(new TableLayout(firstPanelSize));
		firstPanel.add(billIdLabel,"1,1");
		firstPanel.add(billIdField,"3,1");
		firstPanel.add(operatorLabel,"5,1");
		firstPanel.add(operaterField,"7,1");
		
		
		secondPanel=new JPanel();
		double secondPanelSize[][]={
				{20,0.08,5,150,5,150,5,0.12,15,TableLayout.FILL},
				{TableLayout.FILL,0.65,TableLayout.FILL}
		};
		secondPanel.setLayout(new TableLayout(secondPanelSize));
		secondPanel.add(chooseLabel,"1,1");
		secondPanel.add(chooseFieldA,"3,1");
		secondPanel.add(chooseFieldB,"5,1");
		secondPanel.add(chooseButton,"7,1");
		
		thirdPanel=new JPanel();
		double thirdPanelSize[][]={
				{20,110,TableLayout.FILL},
				{TableLayout.FILL,0.65,TableLayout.FILL}
		};
		thirdPanel.setLayout(new TableLayout(thirdPanelSize));
		thirdPanel.add(listNameLabel,"1,1");
		
		scrollPanel=new JPanel();
		double scrollPanelSize[][]={
				{20,TableLayout.FILL},
				{TableLayout.FILL},
		};
		scrollPanel.setLayout(new TableLayout(scrollPanelSize));
		listTable=new JTable(goodsInfo,goodsListAttributes);
		goodsListPane=new JScrollPane(listTable);
		scrollPanel.add(goodsListPane, "1,0");
		
		forthPanel=new JPanel();
		double forthPanelSize[][]={
				{10,0.5,TableLayout.FILL},
				{30,10,30,10,30,10,30,30,TableLayout.FILL},
		};
		forthPanel.setLayout(new TableLayout(forthPanelSize));
		forthPanel.add(tableAddButton, "1,0");
		forthPanel.add(tableDeleteButton, "1,2");
		forthPanel.add(tableSumButton, "1,4");
		forthPanel.add(sumLabel, "1,6");
		forthPanel.add(sumField, "1,7");
		
		centerPanel=new JPanel();
		double centerPanelSize[][]={
				{0.8,0.2},
				{TableLayout.FILL}
		};
		centerPanel.setLayout(new TableLayout(centerPanelSize));
		centerPanel.add(scrollPanel, "0,0");
		centerPanel.add(forthPanel, "1,0");
		
		fifthPanel=new JPanel();
		double fifthPanelSize[][]={
				{20,55,5,400,TableLayout.FILL},
				{TableLayout.FILL,0.65,TableLayout.FILL}
		};
		fifthPanel.setLayout(new TableLayout(fifthPanelSize));
		fifthPanel.add(remarkLabel,"1,1");
		fifthPanel.add(remarkField,"3,1");

		double size[][] = 
			{{TableLayout.FILL}
			,{0.1,0.1,0.1,0.45,0.1,TableLayout.FILL}};
		panel.setLayout(new TableLayout(size));
		panel.add(firstPanel, "0,0");
		panel.add(secondPanel, "0,1");
		panel.add(thirdPanel, "0,2");
		panel.add(centerPanel,"0,3");
		panel.add(fifthPanel, "0,4");
	}
	
	public void setPanel(BillVO bill) {
		billIdField.setText(bill.getId());
		operaterField.setText(bill.getOperator());
		listTable.setModel(bill.getModel());
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
