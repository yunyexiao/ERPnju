package presentation.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import businesslogic.AccountBL;
import businesslogic.CategoryBL;
import businesslogic.CommodityBL;
import businesslogic.CustomerBL;
import businesslogic.UserBL;
import layout.TableLayout;
import presentation.analysisui.ViewBusinessHistoryPanel;
import presentation.bill.BillExaminePanel;
import presentation.billui.BillPanelHelper;
import presentation.component.InfoAdapter;
import presentation.component.Listener_stub;
import presentation.dataui.accountui.AccountDataPanel;
import presentation.dataui.categoryui.CategoryDataPanel;
import presentation.dataui.commodityui.CommodityDataPanel;
import presentation.dataui.customerui.CustomerDataPanel;
import presentation.dataui.userui.UserDataPanel;
import presentation.initui.InitPanel;
import presentation.logui.LogPanel;
import vo.UserType;
import vo.UserVO;

/**
 * MainWindow左侧的按钮栏，此处继承了JPanel
 * @author 钱美缘
 */
@SuppressWarnings("serial")
class LeftButtonPanel extends JPanel{
	private MainWindow mainWindow;
    private JPanel innerPanel = new JPanel(new GridLayout(11, 1, 5, 5));
    /**
     * 向Panel增加按钮
     * @param text 按钮显示的文字
     * @param listener 按钮绑定的监听器（由Listener类生成）
     */
    private void addButton(String text, ActionListener listener) {
    	JButton button = new JButton(text);
		button.setFont(new Font("等线",Font.BOLD,18));
		button.addActionListener(listener);
		if ("退出".equals(text)) button.addMouseListener(new InfoAdapter("退出系统"));
		else button.addMouseListener(new InfoAdapter("进入<" + text + ">界面"));
		innerPanel.add(button);
    }
    /**
     * 构造方法：根据MainWindow的UserVO初始化按钮栏
     * @param mw 传入的MainWindow引用
     */
	public LeftButtonPanel(MainWindow mw) {
		this.mainWindow = mw;
		UserVO user = mainWindow.getUser();
		UserType type = user.getType();
		innerPanel.setOpaque(false);
		
		class CloseListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.close();
			}	
		}
		
		ActionListener closeListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.changePanel();
            }
		    
		};
		
		BillPanelHelper.user = user;
		BillPanelHelper.closeListener = closeListener;
		// 修改按钮处------------------------------
		if (type == UserType.KEEPER) {
			addButton("商品分类管理", e -> mw.changePanel(new CategoryDataPanel(new CategoryBL(user), closeListener)));
			addButton("商品管理", e -> mw.changePanel(new CommodityDataPanel(new CommodityBL(user), closeListener)));
			addButton("库存查看", new Listener_stub());
			addButton("库存盘点", new Listener_stub());
			addButton("报溢/报损", e -> mw.changePanel(BillPanelHelper.create("ChangeBill")));
			addButton("退出", new CloseListener());
			
		}
		else if (type == UserType.SALESMAN) {
			addButton("客户管理", e -> mw.changePanel(new CustomerDataPanel(user, new CustomerBL(user), closeListener)));
			addButton("制定进货单", e -> mw.changePanel(BillPanelHelper.create("PurchaseBill")));
			addButton("制定进货退货单", e -> mw.changePanel(BillPanelHelper.create("PurchaseReturnBill")));
			addButton("制定销售单", e -> mw.changePanel(BillPanelHelper.create("SalesBill")));
			addButton("制定销售退货单", e -> mw.changePanel(BillPanelHelper.create("SalesReturnBill")));
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.ACCOUNTANT) {
			addButton("账户管理", e -> mw.changePanel(new AccountDataPanel(user, new AccountBL(user), closeListener)));
			addButton("制定收付款单", e -> mw.changePanel(BillPanelHelper.create("ReceiptOrPaymentBill")));
			addButton("制定现金费用单", e -> mw.changePanel(BillPanelHelper.create("CashCostBill")));
			addButton("查看销售明细表", new Listener_stub());
			addButton("查看经营状况表", new Listener_stub());
			addButton("查看经营历程表", e -> mw.changePanel(new ViewBusinessHistoryPanel(user, closeListener)));
			addButton("期初建账", e -> mw.changePanel(new InitPanel(mw)));
			addButton("查看日志", e -> mw.changePanel(new LogPanel(mw)));		
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.GM) {
			addButton("审批单据", e -> mw.changePanel(new BillExaminePanel(mw,closeListener)));
			addButton("制定促销策略", new Listener_stub());
			addButton("查看销售明细表", new Listener_stub());
			addButton("查看经营状况表", new Listener_stub());
			addButton("查看经营历程表", e -> mw.changePanel(new ViewBusinessHistoryPanel(user, closeListener)));
			addButton("查看日志", e -> mw.changePanel(new LogPanel(mw)));		
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.ADMIN) {
			addButton("用户管理", e -> mw.changePanel(new UserDataPanel(new UserBL(user), closeListener)));
			addButton("查看日志", e -> mw.changePanel(new LogPanel(mw)));		
			addButton("退出", new CloseListener());
		}
		//-----------------------------------------
		
		double[][] size = {{TableLayout.FILL, 0.8, TableLayout.FILL},{TableLayout.FILL}};
		this.setLayout(new TableLayout(size));
		this.add(innerPanel, "1,0");
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void setBackground() {
		ImageIcon image = new ImageIcon("resource/LeftButtonPanel.png");   
        Image img = image.getImage();  
        img = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);  
        image.setImage(img);
		this.add(new JLabel(image), "0, 0, 2, 0");
	}
}