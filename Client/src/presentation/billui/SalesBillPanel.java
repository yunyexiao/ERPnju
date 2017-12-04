package presentation.billui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bl_stub.SaleBillBL_stub;
import blservice.billblservice.SaleBillBLService;
import layout.TableLayout;
import presentation.component.MyTableModel;
import vo.UserVO;
import vo.billvo.BillVO;
import vo.billvo.SalesBillVO;

/**
 * 销售单的面板<br>
 * 上一级抽象父类中的sumField为这里的折扣前总额的TextField
 * @author 恽叶霄
 */
public class SalesBillPanel extends CommonSaleBillPanel {
	
	private SaleBillBLService saleBillBL = new SaleBillBL_stub();
	
	private JTextField discountField,afterDiscountField,couponField;
	private JTextArea promotionInfoArea;
	
	public SalesBillPanel(UserVO user, ActionListener closeListener) {
		super(user, closeListener);

		billIdField.setText(saleBillBL.getNewId());
		operatorField.setText(this.getUser().getId());
	}

	public SalesBillPanel(UserVO user, SalesBillVO bill, ActionListener closeListener) {
		super(user, closeListener, bill);
		if(!editable){
		    discountField.setEditable(false);
		    couponField.setEditable(false);
		}
	}
	
	@Override
	protected ActionListener getNewActionListener() {
		return e -> {
            int response = JOptionPane.showConfirmDialog(null, "确认要新建一张销售单吗？", "提示", JOptionPane.YES_NO_OPTION);
            if(response == 1)return;
            clear();
            billIdField.setText(saleBillBL.getNewId());
            operatorField.setText(this.getUser().getName());
		};
	}

	@Override
	protected ActionListener getSaveActionListener() {
		return e -> {
            SalesBillVO bill = getBill(BillVO.SAVED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
                return;
            }
            saleBillBL.saveBill(bill);
		};
	}

	@Override
	protected ActionListener getCommitActionListener() {
		return e -> {
            SalesBillVO bill = getBill(BillVO.COMMITED);
            if(bill == null){
                JOptionPane.showMessageDialog(null, "信息有错，请重新编辑。");
                return;
            }
            saleBillBL.saveBill(bill);
		};
	}

	@Override
	protected boolean isCorrectable() {
	    if(!super.isCorrectable()) return false;
	    try{
	        int discount = Integer.parseInt(discountField.getText());
	        if(getUser().getRank() == 0 && discount > 1000)
	            return false;
	        if(getUser().getRank() == 1 && discount > 5000)
	            return false;
	        Integer.parseInt(couponField.getText());
	        return true;
	    }catch(NumberFormatException e){
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	protected void clear(){
	    super.clear();
	    discountField.setText("");
	    discountField.setEditable(true);
	    afterDiscountField.setText("");
	    couponField.setText("");
	    couponField.setEditable(true);
	    promotionInfoArea.setText("");
	}

	/**
	 * 获得单据VO
	 * @return
	 */
	private SalesBillVO getBill(int state) {
		if (isCorrectable()) {
		    Calendar c = Calendar.getInstance();
		    String date = c.get(Calendar.YEAR) + "" 
		        + c.get(Calendar.MONTH) + "" + c.get(Calendar.DATE);
		    String time = c.get(Calendar.HOUR_OF_DAY) + "" 
		        + c.get(Calendar.MINUTE) + "" + c.get(Calendar.SECOND);
		    String id = billIdField.getText()
		         , operater = operatorField.getText()
		         , customerId = customerIdField.getText()
		         , customerName = customerNameField.getText()
		         , remark = remarkField.getText();
		    MyTableModel model = (MyTableModel)goodsListTable.getModel();
		    double beforeDiscount = Double.parseDouble(sumField.getText())
		         , sum = Double.parseDouble(afterDiscountField.getText())
		         , discount = Double.parseDouble(discountField.getText())
		         , coupon = Double.parseDouble(couponField.getText());
		    return new SalesBillVO(date, time, id, operater, state
		        , customerId, customerName, model, remark, beforeDiscount
		        , discount, coupon, sum);
		}
		return null;
	}

	@Override
	protected void initSouth(){
	    double[][] size = {{-1.0}, {-1.0, -1.0}};
	    JPanel southPanel = new JPanel(new TableLayout(size));
	    southPanel.add(getPromotionPanel(), "0 0");
	    southPanel.add(getTailPanel(), "0 1");
	    billPanel.add(southPanel, BorderLayout.SOUTH);
	}
	
	private JPanel getPromotionPanel(){
	    double[][] size = {{20.0, -1.0, -1.0}, {20.0, 15.0, 10.0, -1.0}};
	    JPanel promotionPanel = new JPanel(new TableLayout(size));
	    promotionPanel.add(new JLabel("促销信息"), "1 1");
	    promotionInfoArea = new JTextArea(2, 20);
	    promotionInfoArea.setEditable(false);
	    promotionPanel.add(promotionInfoArea, "1 3");
	    return promotionPanel;
	}
	
	private JPanel getTailPanel(){
	    JButton summaryButton = new JButton("合计");
		summaryButton.addActionListener(e -> sumUp());
		
		sumField = new JTextField(10);
		afterDiscountField = new JTextField(10);
		sumField.setEditable(false);
		afterDiscountField.setEditable(false);
		discountField = new JTextField(10);
		couponField = new JTextField(10);
		remarkField = new JTextField(10);
		
		double size[][] = {
				{20,60,5,80,15,40,5,80,15,60,5,0.7,TableLayout.FILL},
				{15,25,10,25,TableLayout.FILL}
		};
		JPanel tailPanel = new JPanel(new TableLayout(size));
		tailPanel.add(new JLabel("    折让"),"5,1");
		tailPanel.add(discountField,"7,1");
		tailPanel.add(new JLabel("折让前总额"),"1,1");
		tailPanel.add(sumField,"3,1");
		tailPanel.add(summaryButton,"9,1");
		tailPanel.add(new JLabel("折让后总额"),"1,3");
		tailPanel.add(afterDiscountField,"3,3");
		tailPanel.add(new JLabel("代金券"),"5,3");
		tailPanel.add(couponField,"7,3");
		tailPanel.add(new JLabel("      备注"),"9,3");
		tailPanel.add(remarkField, "11 3");
		return tailPanel;
	}

	@Override
	protected void sumUp(){
	    // TODO promotions haven't been considered
	    if(!isCorrectable()) return;
	    int before = 0, after = 0;
	    super.sumUp();
	    before = Integer.parseInt(sumField.getText());
	    after = before - Integer.parseInt(discountField.getText()) - Integer.parseInt(couponField.getText());
	    afterDiscountField.setText(after + "");
	}

    @Override
    protected String getObjectType() {
        return "销售商";
    }

    @Override
    protected String getTableTitle() {
        return "销售列表";
    }

}
