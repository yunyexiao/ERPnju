package presentation.billui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import blservice.billblservice.ChangeBillBLService;
import businesslogic.ChangeBillBL;
import layout.TableLayout;
import presentation.component.InfoAdapter;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.choosewindow.CommodityChooseWin;
import presentation.tools.Timetools;
import vo.CommodityVO;
import vo.UserVO;
import vo.billvo.ChangeBillVO;

public class ChangeBillPanel extends JPanel implements BillPanelInterface {

	private UserVO user;
	private JTable table;
	private JButton addButton;
	private JButton deleteButton;
	private JTextField billIdField, operaterField;
	private JRadioButton overButton, lostButton;
	private ChangeBillBLService changeBillBL = new ChangeBillBL(true);
	
	/**
	 * �½����ݽ���
	 * @param user
	 * @param closeListener
	 */
	public ChangeBillPanel(UserVO user) {
		this.user = user;
		initBillPanel();
		operaterField.setText(user.getName());
	}

	/**
	 * �������е�һ�ŵ��ݳ�ʼ�����棬������/�������Ͳ����޸�
	 * @param user
	 * @param bill
	 * @param closeListener
	 */
	public ChangeBillPanel(UserVO user, ChangeBillVO bill) {
		this.user = user;
		initBillPanel();
		changeBillBL = new ChangeBillBL(bill.getFlag());
		billIdField.setText(bill.getAllId());
		overButton.setEnabled(false);
		lostButton.setEnabled(false);
		if (bill.getFlag()) overButton.setSelected(true);
		else lostButton.setSelected(true);
		operaterField.setText(bill.getOperator());
		MyTableModel tableModel = bill.getTableModel();
		tableModel.setEditable(new int[]{3});
		table.setModel(tableModel);
	}
	
	private void initBillPanel() {
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception e){
			e.printStackTrace();
		}

		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.08,0.07,0.5,0.175,0.175}	
		};
		this.setLayout(new TableLayout(mainPanelSize));
		
		JPanel headPanel = new JPanel();
		double firstPanelSize[][]={
				{20,55,5,150,20,40,5,150,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		billIdField = new JTextField("");
		setBillId(true);
		billIdField.setEditable(false);
		operaterField = new JTextField();
		operaterField.setEditable(false);
		headPanel.setLayout(new TableLayout(firstPanelSize));
		headPanel.add(new JLabel("���ݱ��"), "1,1");
		headPanel.add(billIdField, "3,1");
		headPanel.add(new JLabel("������"), "5,1");
		headPanel.add(operaterField, "7,1");
		
		JPanel choosePanel = new JPanel();
		double choosePanelSize[][] = {
				{20,55,5,55,20,55,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		choosePanel.setLayout(new TableLayout(choosePanelSize));
		overButton = new JRadioButton("����");
		lostButton = new JRadioButton("����");
		overButton.setSelected(true);
		overButton.addActionListener(e -> setBillId(true));
		lostButton.addActionListener(e -> setBillId(false));
		choosePanel.add(new JLabel("��������"), "1,1");
		choosePanel.add(overButton, "3,1");
		choosePanel.add(lostButton, "5,1");
		ButtonGroup chooseGroup = new ButtonGroup();
		chooseGroup.add(overButton);
		chooseGroup.add(lostButton);
		
		String[] headers = {"��Ʒid", "��Ʒ����", "�������", "ʵ������"};
		MyTableModel tableModel = new MyTableModel(null, headers);
		tableModel.setEditable(new int[]{3});
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane tablePane = new JScrollPane(table);
		
		JPanel buttonPanel = new JPanel();
		double buttonPanelSize[][]={
				{TableLayout.PREFERRED,TableLayout.FILL},
				{25,10,25,10,25,10,TableLayout.FILL},
		};
		addButton = new JButton("����һ����Ʒ", new ImageIcon("resource/AddButton.png"));
		deleteButton = new JButton("ɾ��һ����Ʒ", new ImageIcon("resource/DeleteButton.png"));
		buttonPanel.setLayout(new TableLayout(buttonPanelSize));
		buttonPanel.add(addButton, "0,0");
		buttonPanel.add(deleteButton, "0,2");
		
		JPanel tablePanel = new JPanel();
		double centerPanelSize[][]={
				{20,0.8,20,0.2},
				{10,25,5,TableLayout.FILL}
		};
		tablePanel.setLayout(new TableLayout(centerPanelSize));
		tablePanel.add(new JLabel("������Ʒ�б�"),"1,1");
		tablePanel.add(tablePane, "1,3");
		tablePanel.add(buttonPanel, "3,3");
		
		this.add(headPanel, "0,0");
		this.add(choosePanel, "0,1");
		this.add(tablePanel, "0,2");

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CommodityVO commodity = new CommodityChooseWin().getCommodity();
				if (commodity != null) {
					for(int i = 0; i < table.getRowCount(); i++) {
						if (commodity.getId().equals(table.getValueAt(i, 0))) {
							new InfoWindow("��������д���Ʒ");
							return;
						}
					}
					((MyTableModel) table.getModel()).addRow(new String[]{commodity.getId(),commodity.getName(),""+commodity.getAmount(),""+commodity.getAmount()});
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					((MyTableModel) table.getModel()).removeRow(table.getSelectedRow());
				} else new InfoWindow("��ѡ��һ�м�¼");
			}
		});
		
		addButton.addMouseListener(new InfoAdapter("ѡ����Ҫ�޸ĵ���Ʒ"));
		
		deleteButton.addMouseListener(new InfoAdapter("ɾ��ѡ�е�һ��"));
	}

	private boolean isCorrectable() {
		if (table.getRowCount() == 0) {new InfoWindow("û��ѡ����Ʒ");return false;}
		for(int i = 0; i < table.getRowCount(); i++) {
			int a = Integer.parseInt((String)table.getValueAt(i, 2));
			int b = Integer.parseInt((String)table.getValueAt(i, 3));
			if (overButton.isSelected() && a >= b) {
				new InfoWindow("�뵥�����Ͳ�����");
				return false;
			} else if (lostButton.isSelected() && a <= b) {
				new InfoWindow("�뵥�����Ͳ�����");
				return false;
			}
		}
		return true;
	}

	public ChangeBillVO getBill() {
		if (! isCorrectable()) {
			return null;
		}
		Timetools.check();
		return new ChangeBillVO(Timetools.getDate(), Timetools.getTime(), changeBillBL.getNewId(), user.getId(), ChangeBillVO.DRAFT, overButton.isSelected(), (MyTableModel) table.getModel());
	}
	
	private void setBillId(boolean isOver) {
		changeBillBL = new ChangeBillBL(isOver);
		billIdField.setText((isOver?"BYD-":"BSD-")+Timetools.getDate()+"-"+changeBillBL.getNewId());
	}

	@Override
	public void newAction() {
		setBillId(true);
		overButton.setSelected(true);
		String[] headers = {"��Ʒid", "��Ʒ����", "�������", "ʵ������"};
		MyTableModel tableModel = new MyTableModel(null, headers);
		tableModel.setEditable(new int[]{3});
		table.setModel(tableModel);
	}

	@Override
	public void saveAction() {
		ChangeBillVO bill = getBill();
		if (bill != null) {
			bill.setState(ChangeBillVO.SAVED);
			if (changeBillBL.saveBill(bill)) new InfoWindow("���ݱ���ɹ�");
			else new InfoWindow("���ݱ���ʧ��");
		}
	}

	@Override
	public void commitAction() {
		ChangeBillVO bill = getBill();
		if (bill != null) {
			bill.setState(ChangeBillVO.COMMITED);
			if (changeBillBL.saveBill(bill)) new InfoWindow("�����ύ�ɹ�");
			else new InfoWindow("�����ύʧ��");
		}
	}

	@Override
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
