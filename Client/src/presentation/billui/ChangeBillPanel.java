package presentation.billui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.choosewindow.CommodityChooseWin;
import presentation.main.MainWindow;
import presentation.tools.Timetools;
import vo.CommodityVO;
import vo.UserVO;
import vo.billvo.ChangeBillVO;

public class ChangeBillPanel extends BillPanel {

	private JTable table;
	private JButton addButton;
	private JButton deleteButton;
	private JTextField billIdField, operaterField;
	private JRadioButton overButton, lostButton;
	private ChangeBillBLService changeBillBL;
	
	public ChangeBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);
	}

	@Override
	protected void initBillPanel() {
		changeBillBL = new ChangeBillBL();
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception e){
			e.printStackTrace();
		}

		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.08,0.07,0.5,0.175,0.175}	
		};
		billPanel.setLayout(new TableLayout(mainPanelSize));
		
		JPanel headPanel = new JPanel();
		double firstPanelSize[][]={
				{20,55,5,150,20,40,5,150,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		billIdField = new JTextField("");
		setBillId(true);
		billIdField.setEditable(false);
		operaterField = new JTextField(getUser().getName());
		operaterField.setEditable(false);
		headPanel.setLayout(new TableLayout(firstPanelSize));
		headPanel.add(new JLabel("单据编号"), "1,1");
		headPanel.add(billIdField, "3,1");
		headPanel.add(new JLabel("操作人"), "5,1");
		headPanel.add(operaterField, "7,1");
		
		JPanel choosePanel = new JPanel();
		double choosePanelSize[][] = {
				{20,55,5,55,20,55,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		choosePanel.setLayout(new TableLayout(choosePanelSize));
		overButton = new JRadioButton("报溢");
		lostButton = new JRadioButton("报损");
		overButton.setSelected(true);
		overButton.addActionListener(e -> setBillId(true));
		lostButton.addActionListener(e -> setBillId(false));
		choosePanel.add(new JLabel("单据类型"), "1,1");
		choosePanel.add(overButton, "3,1");
		choosePanel.add(lostButton, "5,1");
		ButtonGroup chooseGroup = new ButtonGroup();
		chooseGroup.add(overButton);
		chooseGroup.add(lostButton);
		
		String[] headers = {"商品id", "商品名称", "库存数量", "实际数量"};
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
		addButton = new JButton("增加一项商品", new ImageIcon("resource/AddButton.png"));
		deleteButton = new JButton("删除一项商品", new ImageIcon("resource/DeleteButton.png"));
		buttonPanel.setLayout(new TableLayout(buttonPanelSize));
		buttonPanel.add(addButton, "0,0");
		buttonPanel.add(deleteButton, "0,2");
		
		JPanel tablePanel = new JPanel();
		double centerPanelSize[][]={
				{20,0.8,20,0.2},
				{10,25,5,TableLayout.FILL}
		};
		tablePanel.setLayout(new TableLayout(centerPanelSize));
		tablePanel.add(new JLabel("出库商品列表"),"1,1");
		tablePanel.add(tablePane, "1,3");
		tablePanel.add(buttonPanel, "3,3");
		
		billPanel.add(headPanel, "0,0");
		billPanel.add(choosePanel, "0,1");
		billPanel.add(tablePanel, "0,2");

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CommodityVO commodity = new CommodityChooseWin().getCommodity();
				if (commodity != null) {
					for(int i = 0; i < table.getRowCount(); i++) {
						if (commodity.getId().equals(table.getValueAt(i, 0))) {
							new InfoWindow("表格内已有此商品");
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
				} else new InfoWindow("请选择一行记录");
			}
		});
		
		addButton.addMouseListener(new MouseAdapter() {
			@Override  
		    public void mouseEntered(MouseEvent e) {
				MainWindow.setInfo("选择需要修改的商品");  
		    }
		    @Override  
		    public void mouseExited(MouseEvent e) {
		    	MainWindow.setInfo();  
		    }
		});
		
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override  
		    public void mouseEntered(MouseEvent e) {
				MainWindow.setInfo("删除选中的一行");  
		    }
		    @Override  
		    public void mouseExited(MouseEvent e) {
		    	MainWindow.setInfo();  
		    }
		});
	}

	@Override
	protected ActionListener getNewActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setBillId(true);
				overButton.setSelected(true);
				String[] headers = {"商品id", "商品名称", "库存数量", "实际数量"};
				MyTableModel tableModel = new MyTableModel(null, headers);
				tableModel.setEditable(new int[]{3});
				table.setModel(tableModel);
			}
		};
	}

	@Override
	protected ActionListener getSaveActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChangeBillVO bill = getBill();
				if (bill != null) {
					bill.setState(ChangeBillVO.SAVED);
					if (changeBillBL.saveBill(bill)) new InfoWindow("单据保存成功");
					else new InfoWindow("单据保存失败");
				}
			}
		};
	}

	@Override
	protected ActionListener getCommitActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChangeBillVO bill = getBill();
				if (bill != null) {
					bill.setState(ChangeBillVO.COMMITED);
					if (changeBillBL.saveBill(bill)) new InfoWindow("单据提交成功");
					else new InfoWindow("单据提交失败");
				}
			}
		};
	}

	@Override
	protected boolean isCorrectable() {
		if (table.getRowCount() == 0) {new InfoWindow("没有选择商品");return false;}
		for(int i = 0; i < table.getRowCount(); i++) {
			int a = Integer.parseInt((String)table.getValueAt(i, 2));
			int b = Integer.parseInt((String)table.getValueAt(i, 3));
			if (overButton.isSelected() && a >= b) {
				new InfoWindow("与单据类型不符合");
				return false;
			} else if (lostButton.isSelected() && a <= b) {
				new InfoWindow("与单据类型不符合");
				return false;
			}
		}
		return true;
	}

	private ChangeBillVO getBill() {
		if (! isCorrectable()) {
			return null;
		}
		Timetools.check();
		return new ChangeBillVO(Timetools.getDate(), Timetools.getTime(), changeBillBL.getNewId(), operaterField.getText(), ChangeBillVO.DRAFT, overButton.isSelected(), (MyTableModel) table.getModel());
	}
	
	private void setBillId(boolean isOver) {
		ChangeBillBL.setOver(isOver);
		billIdField.setText((isOver?"BYD-":"BSD-")+Timetools.getDate()+"-"+changeBillBL.getNewId());
	}
}
