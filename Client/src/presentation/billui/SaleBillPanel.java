package presentation.billui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import bl_stub.SaleBillBL_stub;
import blservice.billblservice.SaleBillBLService;
import layout.TableLayout;
import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.SaleBillVO;
/**
 * 没有添加内部几个按钮监听器的SaleBill
 * @author user
 *
 */
public class SaleBillPanel extends BillPanel {
	
	private SaleBillBLService saleBillBL = new SaleBillBL_stub();
	
	private JTextField billIdField, operaterField, supplierIdField, supplierNameField, 
	beforeDiscountSummaryField,discountField,afterDiscountSummaryField,couponField;
	private JTextArea promotionInfoArea,remarkArea;
	private JTable goodsListTable = new JTable();
	
	public SaleBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);

		billIdField.setText(saleBillBL.getNewId());
		operaterField.setText(this.getUser().getName());
	}

	public SaleBillPanel(UserVO user, SaleBillVO bill, ActionListener closeListener) {
		super(user, closeListener);
		
		//TODO 根据bill初始化，还没写！
	}
	
	@Override
	protected void initBillPanel() {
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus风格，jdk6 update10版本以后的才会出现
		}catch(Exception e){
			e.printStackTrace();
		}
	
		JPanel headPanel, supplierInfoPanel,centerPanel,goodsButtonPanel,tailPanel,promotionInfoPanel;
		JScrollPane goodsListPane;
		JButton supplierChooseButton, goodsChooseButton, goodsDeleteButton, 
		chooseFinishButton,summaryButton;

		supplierChooseButton=new JButton("选择");
		goodsChooseButton=new JButton("选择商品");
		goodsDeleteButton=new JButton("删除商品");
		chooseFinishButton=new JButton("选择完成");
		summaryButton=new JButton("合计");
		
		
		billIdField=new JTextField(10);
		operaterField=new JTextField(10);
		supplierIdField=new JTextField(10);
		supplierIdField.setText("编号");
		supplierNameField=new JTextField(10);
		supplierNameField.setText("名称");
		beforeDiscountSummaryField=new JTextField(10);
		discountField=new JTextField(10);
		afterDiscountSummaryField=new JTextField(10);
		couponField=new JTextField(10);
		
		remarkArea=new JTextArea(3,40);
		promotionInfoArea=new JTextArea(3,40);
		
		billIdField.setEditable(false);
		operaterField.setEditable(false);
		supplierIdField.setEditable(false);
		supplierNameField.setEditable(false);
		beforeDiscountSummaryField.setEditable(false);
		afterDiscountSummaryField.setEditable(false);
		promotionInfoArea.setEditable(false);
		
		
		
		headPanel=new JPanel();
		double firstPanelSize[][]={
				{20,55,5,150,20,40,5,150,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
		headPanel.setLayout(new TableLayout(firstPanelSize));
		headPanel.add(new JLabel("单据编号"),"1,1");
		headPanel.add(billIdField,"3,1");
		headPanel.add(new JLabel("操作人"),"5,1");
		headPanel.add(operaterField,"7,1");
		
		
		supplierInfoPanel=new JPanel();
		double secondPanelSize[][]={
				{20,45,5,70,12,100,5,60,TableLayout.FILL},
				{8,25,TableLayout.FILL}
		};
		supplierInfoPanel.setLayout(new TableLayout(secondPanelSize));
		supplierInfoPanel.add(new JLabel("客户"),"1,1");
		supplierInfoPanel.add(supplierIdField,"3,1");
		supplierInfoPanel.add(new JLabel("--"),"4,1");
		supplierInfoPanel.add(supplierNameField,"5,1");
		supplierInfoPanel.add(supplierChooseButton,"7,1");
		
		String[] goodsListAttributes={"商品编号","名称","型号","数量","仓库","单价","金额","备注"};
		String[][] goodsInfo={{"001","xx","yy","20","3","3","40","xx"}};
		goodsListTable = new JTable(new MyTableModel(goodsInfo,goodsListAttributes));
		goodsListPane = new JScrollPane(goodsListTable);
		
		goodsButtonPanel=new JPanel();
		double forthPanelSize[][]={
				{85,TableLayout.FILL},
				{25,10,25,10,25,10,TableLayout.FILL},
		};
		goodsButtonPanel.setLayout(new TableLayout(forthPanelSize));
		goodsButtonPanel.add(goodsChooseButton, "0,0");
		goodsButtonPanel.add(goodsDeleteButton, "0,2");
		goodsButtonPanel.add(chooseFinishButton, "0,4");
		
		
		centerPanel=new JPanel();
		double centerPanelSize[][]={
				{20,0.8,20,0.2},
				{10,25,5,TableLayout.FILL}
		};
		centerPanel.setLayout(new TableLayout(centerPanelSize));
		centerPanel.add(new JLabel("出库商品列表"),"1,1");
		centerPanel.add(goodsListPane, "1,3");
		centerPanel.add(goodsButtonPanel, "3,3");
		
		promotionInfoPanel=new JPanel();
		double promotionInfoPanelSize[][]={
				{20,0.8,20,0.2},
				{10,25,5,TableLayout.FILL}
		};
		promotionInfoPanel.setLayout(new TableLayout(promotionInfoPanelSize));
		promotionInfoPanel.add(new JLabel("促销信息"), "1,1");
		promotionInfoPanel.add(promotionInfoArea, "1,3");
		
		tailPanel=new JPanel();
		double fifthPanelSize[][]={
				{20,60,5,80,15,40,5,80,15,60,5,0.7,TableLayout.FILL},
				{15,25,10,25,TableLayout.FILL}
		};
		tailPanel.setLayout(new TableLayout(fifthPanelSize));
		tailPanel.add(new JLabel("    折让"),"5,1");
		tailPanel.add(discountField,"7,1");
		tailPanel.add(new JLabel("折让前总额"),"1,1");
		tailPanel.add(beforeDiscountSummaryField,"3,1");
		tailPanel.add(summaryButton,"9,1");
		tailPanel.add(new JLabel("折让后总额"),"1,3");
		tailPanel.add(afterDiscountSummaryField,"3,3");
		tailPanel.add(new JLabel("代金券"),"5,3");
		tailPanel.add(couponField,"7,3");
		tailPanel.add(new JLabel("      备注"),"9,3");
		tailPanel.add(remarkArea,"11,3");
		
		double mainPanelSize[][]={
				{TableLayout.FILL},
				{0.08,0.07,0.5,0.175,0.175}	
		};
		billPanel.setLayout(new TableLayout(mainPanelSize));
		billPanel.add(headPanel, "0,0");
		billPanel.add(supplierInfoPanel, "0,1");
		billPanel.add(centerPanel,"0,2");
		billPanel.add(promotionInfoPanel,"0,3");
		billPanel.add(tailPanel, "0,4");
		
	}

	@Override
	protected ActionListener getNewActionListener() {
		// TODO 弹出对话框询问，确认后用initBillPanel()清空，再获取新id和用户名称
		return null;
	}

	@Override
	protected ActionListener getSaveActionListener() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO 此处应当根据结果弹出对话框，保存前将状态设置为SAVED，若失败则恢复DRAFT
				saleBillBL.saveBill(getBill());
			}
		};
	}

	@Override
	protected ActionListener getCommitActionListener() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得单据VO
	 * @return
	 */
	public SaleBillVO getBill() {
		if (isCorrectable()) {
			//TODO 返回一张单据的VO，
		}
		return null;
	}

	@Override
	protected boolean isCorrectable() {
		// TODO Auto-generated method stub
		return true;
	}

}
