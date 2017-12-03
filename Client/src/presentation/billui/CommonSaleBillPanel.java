package presentation.billui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import layout.TableLayout;
import presentation.billui.choosewindow.CustomerChooseWin;
import presentation.component.MyTableModel;
import vo.CustomerVO;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.MarketBillVO;

/**
 * 用于销售类单据的基类<br>
 * 其中销售单由于存在优惠和折扣，还需要额外重写某些方法外，其他只需要实现这里的抽象方法即可。
 * @author 恽叶霄
 */
public abstract class CommonSaleBillPanel extends BillPanel {

    protected JTextField billIdField, operatorField, customerIdField, customerNameField, remarkField, sumField;
	protected JTable goodsListTable;
	protected boolean editable;

    public CommonSaleBillPanel(UserVO user, ActionListener closeListener) {
        super(user, closeListener);
        this.editable = true;
    }
    
    public CommonSaleBillPanel(UserVO user, ActionListener closeListener, MarketBillVO bill){
        super(user, closeListener);
        billIdField.setText(bill.getAllId());
        operatorField.setText(bill.getOperator());
        customerIdField.setText(bill.getCustomerId());
        customerNameField.setText(bill.getCustomerName());
        remarkField.setText(bill.getRemark());
        sumField.setText(bill.getSum() + "");
        goodsListTable.setModel(bill.getModel());
        if(bill.getState() == BillVO.COMMITED || bill.getState() == BillVO.PASS){
            editable = false;
            remarkField.setEditable(false);
        } else {
            editable = true;
        }
    }

    @Override
    protected void initBillPanel() {
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus风格，jdk6 update10版本以后的才会出现
		}catch(Exception e){
			e.printStackTrace();
		}
		
		billPanel.setLayout(new BorderLayout());
		initNorth();
		initCenter();
		initEast();
		initSouth();
    }
    
    @Override
    protected boolean isCorrectable(){
        if(billIdField.getText().length() == 0) return false;
        if(operatorField.getText().length() == 0) return false;
        if(customerIdField.getText().length() == 0) return false;
        if(customerNameField.getText().length() == 0) return false;
        if(sumField.getText().length() == 0) return false;
        if(goodsListTable.getModel().getRowCount() == 0) return false;
        return true;
    }

    abstract protected String getObjectType();
    
    abstract protected String getTableTitle();
    
    private void initNorth(){
        double[][] size = {{-1.0}, {-1.0, -1.0}};
        JPanel northPanel = new JPanel(new TableLayout(size));
        northPanel.add(getHeader(), "0 0");
        northPanel.add(getCustomerPanel(), "0 1");
        
        billPanel.add(northPanel, BorderLayout.NORTH);
    }
    
    private JPanel getHeader(){
        billIdField = new JTextField(10);
        billIdField.setEditable(false);
        operatorField = new JTextField(10);
        operatorField.setEditable(false);
		double size[][]={
				{20,55,5,150,20,40,5,150,TableLayout.FILL},
				{12,25,TableLayout.FILL}
		};
	    JPanel headPanel = new JPanel(new TableLayout(size));
		headPanel.add(new JLabel("单据编号"),"1,1");
		headPanel.add(billIdField,"3,1");
		headPanel.add(new JLabel("操作人编号"),"5,1");
		headPanel.add(operatorField,"7,1");
		return headPanel;
    }
    
    private JPanel getCustomerPanel(){
        customerIdField = new JTextField(10);
        customerIdField.setEditable(false);
        customerNameField = new JTextField(10);
		customerNameField.setEditable(false);
	    double size[][]={
				{20,45,5,70,12,100,5,60,TableLayout.FILL},
				{8,25,TableLayout.FILL}
		};
		JPanel customerInfoPanel=new JPanel(new TableLayout(size));
		JButton customerChooseButton = new JButton("选择");
		customerChooseButton.addActionListener(e -> handleChooseCustomer());
		customerInfoPanel.add(new JLabel(getObjectType()),"1,1");
		customerInfoPanel.add(customerIdField,"3,1");
		customerInfoPanel.add(new JLabel("--"),"4,1");
		customerInfoPanel.add(customerNameField,"5,1");
		customerInfoPanel.add(customerChooseButton,"7,1");
		return customerInfoPanel;
    }
    
    private void initCenter(){
        String[] goodsListAttributes={"商品编号","名称","型号","数量","仓库","单价","金额","备注"};
		String[][] goodsInfo={{"001","xx","yy","20","3","3","40","xx"}};
		goodsListTable = new JTable(new MyTableModel(goodsInfo,goodsListAttributes));
		JScrollPane goodsListPane = new JScrollPane(goodsListTable);

		double size[][]={
				{20,-1.0},
				{10,25,5,TableLayout.FILL}
		};
		JPanel centerPanel = new JPanel(new TableLayout(size));
		centerPanel.add(new JLabel(getTableTitle()),"1,1");
		centerPanel.add(goodsListPane, "1,3");
		
		billPanel.add(centerPanel, BorderLayout.CENTER);
    }
   
    private void initEast(){
        double size[][]={
                {-1.0,TableLayout.FILL},
                {40.0,25,10,25,10,25,10,TableLayout.FILL},
        };
        JPanel goodsButtonPanel = new JPanel(new TableLayout(size));
        JButton goodsChooseButton=new JButton("选择商品");
        goodsChooseButton.addActionListener(e -> addItem());
        JButton goodsDeleteButton=new JButton("删除商品");
        goodsDeleteButton.addActionListener(e -> deleteItem());
        // TODO seemingly useless button here
        JButton chooseFinishButton=new JButton("选择完成");

        goodsButtonPanel.add(goodsChooseButton, "0,1");
        goodsButtonPanel.add(goodsDeleteButton, "0,3");
        goodsButtonPanel.add(chooseFinishButton, "0,5");

        billPanel.add(goodsButtonPanel, BorderLayout.EAST);
    }
    
    protected void initSouth(){
        remarkField = new JTextField(10);
        sumField = new JTextField(5);
        sumField.setEditable(false);
        double[][] size = {
                {20.0, -2.0, 10.0, 100.0, -1.0, -1.0},
                {10.0, -1.0, 10.0, -1.0, -1.0}
        };
        JPanel southPanel = new JPanel(new TableLayout(size));
        southPanel.add(new JLabel("             备注"), "1 1");
        southPanel.add(remarkField, "3 1 4 1");
        JButton sumButton = new JButton("金额合计");
        sumButton.addActionListener(e -> sumUp());
        southPanel.add(sumButton, "1 3");
        southPanel.add(sumField, "3 3");
        
        billPanel.add(southPanel, BorderLayout.SOUTH);
    }
    
    private void addItem(){
        if(!editable) return;
        String[] newRow = new InputCommodityInfoWin().getRowData();
        if(newRow == null) return;
        MyTableModel model = (MyTableModel) goodsListTable.getModel();
        model.addRow(newRow);
    }
    
    private void deleteItem(){
        if(!editable) return;
	    int row = goodsListTable.getSelectedRow();
	    if(row < 0) return;
        ((MyTableModel)goodsListTable.getModel()).removeRow(goodsListTable.getSelectedRow());
    }

    private void handleChooseCustomer(){
        if(!editable) return;
        CustomerVO c = new CustomerChooseWin().getCustomer();
        if(c == null) return;
        customerIdField.setText(c.getId());
        customerNameField.setText(c.getName());
    }

    protected void sumUp(){
        if(!editable) return;
	    MyTableModel model = (MyTableModel)this.goodsListTable.getModel();
        double total = 0;
	    for(int i = 0; i < model.getRowCount(); i++){
	        total += Double.parseDouble((String)model.getValueAt(i, 6));
	    }
	    sumField.setText(total + "");
    }

    protected void clear(){
        billIdField.setText("");
        operatorField.setText("");
        customerIdField.setText("");
        customerNameField.setText("");
        remarkField.setText("");
        remarkField.setEditable(true);
        sumField.setText("");
        MyTableModel model = (MyTableModel) goodsListTable.getModel();
        while(model.getRowCount() > 0){
            model.removeRow(0);
        }
    }

}
