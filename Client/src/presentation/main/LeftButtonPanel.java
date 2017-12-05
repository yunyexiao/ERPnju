package presentation.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bl_stub.AccountBL_stub;
import bl_stub.CategoryBL_stub;
import bl_stub.CommodityBL_stub;
import bl_stub.CustomerBL_stub;
import bl_stub.UserBL_stub;
import layout.TableLayout;
import presentation.bill.BillExaminePanel;
import presentation.bill.BillPanel;
import presentation.billui.CashCostBillPanel;
import presentation.billui.ChangeBillPanel;
import presentation.billui.PurchaseBillPanel;
import presentation.billui.ReceiptOrPaymentBillPanel;
import presentation.billui.SaleBillPanel;
import presentation.component.Listener_stub;
import presentation.dataui.MockDataPanel;
import presentation.dataui.accountui.AccountDataPanel;
import presentation.dataui.categoryui.CategoryDataPanel;
import presentation.dataui.commodityui.CommodityDataPanel;
import presentation.dataui.customerui.CustomerDataPanel;
import presentation.dataui.userui.UserDataPanel;
import presentation.logui.LogPanel;
import vo.BillType;
import vo.UserType;

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
		button.addMouseListener(new MouseAdapter() {
			@Override  
		    public void mouseEntered(MouseEvent e) {
		        if ("退出".equals(text)) MainWindow.setInfo("退出系统");
		        else MainWindow.setInfo("进入<" + text + ">界面");  
		    }  
		  
		    @Override  
		    public void mouseExited(MouseEvent e) {
		    	MainWindow.setInfo();  
		    }  
		});
		innerPanel.add(button);
    }
    /**
     * 构造方法：根据MainWindow的UserVO初始化按钮栏
     * @param mw 传入的MainWindow引用
     */
	public LeftButtonPanel(MainWindow mw) {
		this.mainWindow = mw;
		UserType type = mainWindow.getUser().getType();
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
		
		// 修改按钮处------------------------------
		if (type == UserType.KEEPER) {
			addButton("商品分类管理", e -> mw.changePanel(new CategoryDataPanel(new CategoryBL_stub(), closeListener)));
			addButton("商品管理", e -> mw.changePanel(new CommodityDataPanel(new CommodityBL_stub(), closeListener)));
			addButton("库存查看", new Listener_stub());
			addButton("库存盘点", new Listener_stub());
			addButton("报溢/报损", e -> mw.changePanel(new ChangeBillPanel(mainWindow.getUser(), closeListener)));
			addButton("退出", new CloseListener());
			
		}
		else if (type == UserType.SALESMAN) {
			addButton("客户管理", e -> mw.changePanel(new CustomerDataPanel(new CustomerBL_stub(), closeListener)));
			addButton("制定进货单", e -> mw.changePanel(new PurchaseBillPanel(mainWindow.getUser(), closeListener)));
			addButton("制定进货退货单", new Listener_stub());
			addButton("制定销售单", e -> mw.changePanel(new SaleBillPanel(mainWindow.getUser(), closeListener)));
			addButton("制定销售退货单", new Listener_stub());
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.ACCOUNTANT) {
			addButton("账户管理", e -> mw.changePanel(new AccountDataPanel(new AccountBL_stub(), closeListener)));
			addButton("制定收付款单", e -> mw.changePanel(new ReceiptOrPaymentBillPanel(mainWindow.getUser(), closeListener)));
			addButton("制定现金费用单", e -> mw.changePanel(new CashCostBillPanel(mainWindow.getUser(), closeListener)));
			addButton("查看销售明细表", new Listener_stub());
			addButton("查看经营状况表", new Listener_stub());
			addButton("查看经营历程表", new Listener_stub());
			addButton("期初建账", new Listener_stub());
			addButton("查看日志", e -> mw.changePanel(new LogPanel(mw)));		
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.GM) {
			addButton("审批单据", e -> mw.changePanel(new BillExaminePanel(mw)));
			addButton("制定促销策略", new Listener_stub());
			addButton("查看销售明细表", new Listener_stub());
			addButton("查看经营状况表", new Listener_stub());
			addButton("查看经营历程表", new Listener_stub());
			addButton("查看日志", e -> mw.changePanel(new LogPanel(mw)));		
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.ADMIN) {
			addButton("用户管理", e -> mw.changePanel(new UserDataPanel(new UserBL_stub(), closeListener)));
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