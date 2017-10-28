package presentation;

import javax.swing.*;
import layout.TableLayout;
import presentation.main.MainWindow;
import vo.UserVO;

import java.awt.*;
import javax.swing.UIManager;

public class PurchaseBillPanel implements PanelInterface {
	private MainWindow mainWindow;
	private JPanel purchaseBillPanel;
	private JPanel firstPanel, secondPanel, thirdPanel,centerPanel,scrollPanel,forthPanel,fifthPanel,sixthPanel;
	private JScrollPane goodsListPane;
	private JLabel billIdLabel, operatorLabel, supplierLabel, goodsListLabel, summaryLabel, remarkLabel;
	private JButton supplierChooseButton, goodsChooseButton, goodsDeleteButton, chooseFinishButton, submitButton, saveButton, clearButton;
	private JTextField billIdField, operaterField, supplierIdField, supplierNameField, summaryField;
	private JTextArea remarkArea;
	private JTable goodsListTable;
	private String[] goodsListAttributes={"商品编号","名称","型号","数量","仓库","单价","金额","备注"};
	private String[][] goodsInfo={{"001","xx","yy","20","3","3","40","xx"}};
	
	public PurchaseBillPanel(MainWindow mw) {
		this.mainWindow = mw;
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus风格，jdk6 update10版本以后的才会出现
		}catch(Exception e){
			e.printStackTrace();
		}
	
		billIdLabel=new JLabel("单据编号");
		operatorLabel=new JLabel("操作人");
		supplierLabel=new JLabel("供应商");
		goodsListLabel=new JLabel("入库商品列表");
		summaryLabel=new JLabel("单据总额");
		remarkLabel=new JLabel("备注");
		
		supplierChooseButton=new JButton("选择");
		goodsChooseButton=new JButton("选择商品");
		goodsDeleteButton=new JButton("删除商品");
		chooseFinishButton=new JButton("总额合计");
		submitButton=new JButton("提交");
		saveButton=new JButton("保存");
		clearButton=new JButton("清空");
		
		billIdField=new JTextField(10);
		operaterField=new JTextField(10);
		supplierIdField=new JTextField(10);
		supplierIdField.setText("编号");
		supplierNameField=new JTextField(10);
		supplierNameField.setText("名称");
		summaryField=new JTextField(10);
		
		remarkArea=new JTextArea(2,30);
		
		billIdField.setEditable(false);
		operaterField.setEditable(false);
		supplierIdField.setEditable(false);
		supplierNameField.setEditable(false);
		summaryField.setEditable(false);
		
		
		
		firstPanel=new JPanel();
		double firstPanelSize[][]={
				{20,55,5,150,20,40,5,150,TableLayout.FILL},
				{TableLayout.FILL,25,TableLayout.FILL}
		};
		firstPanel.setLayout(new TableLayout(firstPanelSize));
		firstPanel.add(billIdLabel,"1,1");
		firstPanel.add(billIdField,"3,1");
		firstPanel.add(operatorLabel,"5,1");
		firstPanel.add(operaterField,"7,1");
		
		
		secondPanel=new JPanel();
		double secondPanelSize[][]={
				{20,45,5,100,5,100,5,60,TableLayout.FILL},
				{TableLayout.FILL,25,TableLayout.FILL}
		};
		secondPanel.setLayout(new TableLayout(secondPanelSize));
		secondPanel.add(supplierLabel,"1,1");
		secondPanel.add(supplierIdField,"3,1");
		secondPanel.add(supplierNameField,"5,1");
		secondPanel.add(supplierChooseButton,"7,1");
		
		thirdPanel=new JPanel();
		double thirdPanelSize[][]={
				{20,80,TableLayout.FILL},
				{TableLayout.FILL,25,TableLayout.FILL}
		};
		thirdPanel.setLayout(new TableLayout(thirdPanelSize));
		thirdPanel.add(goodsListLabel,"1,1");
		
		scrollPanel=new JPanel();
		double scrollPanelSize[][]={
				{20,TableLayout.FILL},
				{TableLayout.FILL},
		};
		scrollPanel.setLayout(new TableLayout(scrollPanelSize));
		goodsListTable=new JTable(goodsInfo,goodsListAttributes);
		goodsListPane=new JScrollPane(goodsListTable);
		scrollPanel.add(goodsListPane, "1,0");
		
		forthPanel=new JPanel();
		double forthPanelSize[][]={
				{10,85,TableLayout.FILL},
				{25,10,25,10,25,10,TableLayout.FILL},
		};
		forthPanel.setLayout(new TableLayout(forthPanelSize));
		forthPanel.add(goodsChooseButton, "1,0");
		forthPanel.add(goodsDeleteButton, "1,2");
		forthPanel.add(chooseFinishButton, "1,4");
		
		
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
				{20,55,5,150,30,30,5,200,TableLayout.FILL},
				{TableLayout.FILL,25,TableLayout.FILL}
		};
		fifthPanel.setLayout(new TableLayout(fifthPanelSize));
		fifthPanel.add(summaryLabel,"1,1");
		fifthPanel.add(summaryField,"3,1");
		fifthPanel.add(remarkLabel,"5,1");
		fifthPanel.add(remarkArea,"7,1");
		
		sixthPanel=new JPanel();
		double sixthPanelSize[][]={
				{20,60,10,60,10,60,TableLayout.FILL,},
				{TableLayout.FILL,25,TableLayout.FILL}
		};
		sixthPanel.setLayout(new TableLayout(sixthPanelSize));
		sixthPanel.add(submitButton, "1,1");
		sixthPanel.add(saveButton, "3,1");
		sixthPanel.add(clearButton, "5,1");
		
		
		purchaseBillPanel=new JPanel();
		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.1,0.1,0.1,0.45,0.15,0.1}	
		};
		purchaseBillPanel.setLayout(new TableLayout(mainPanelSize));
		purchaseBillPanel.add(firstPanel, "0,0");
		purchaseBillPanel.add(secondPanel, "0,1");
		purchaseBillPanel.add(thirdPanel, "0,2");
		purchaseBillPanel.add(centerPanel,"0,3");
		purchaseBillPanel.add(fifthPanel, "0,4");
		purchaseBillPanel.add(sixthPanel, "0,5");
		
					
	}
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void init(UserVO user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return purchaseBillPanel;
	}
	
}
