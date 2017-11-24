package presentation.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import bl_stub.AccountBL_stub;
import bl_stub.CustomerBL_stub;
import bl_stub.UserBL_stub;
import layout.TableLayout;
import presentation.bill.BillExaminePanel;
import presentation.bill.BillPanel;
import presentation.categoryui.CategoryPanel;
import presentation.component.Listener_stub;
import presentation.dataui.MockDataPanel;
import presentation.dataui.UserDataPanel;
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
		button.setFont(new Font("宋体",Font.BOLD,14));
		button.addActionListener(listener);
		innerPanel.add(button);
    }
    /**
     * 构造方法：根据MainWindow的UserVO初始化按钮栏
     * @param mw 传入的MainWindow引用
     */
	public LeftButtonPanel(MainWindow mw) {
		this.mainWindow = mw;
		UserType type = mainWindow.getUser().getType();
		
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
			addButton("商品分类管理", new Listener(mw, new CategoryPanel(mw)));
			addButton("商品管理", new Listener_stub());
			addButton("库存查看", new Listener_stub());
			addButton("库存盘点", new Listener_stub());
			addButton("报溢/报损", new Listener_stub());
			addButton("退出", new CloseListener());
			
		}
		else if (type == UserType.SALESMAN) {
			addButton("客户管理", new Listener(mw, new MockDataPanel(new CustomerBL_stub(), closeListener)));
			addButton("制定进货单", new Listener(mw, new BillPanel(mw, BillType.PURCHASE, true)));
			addButton("制定进货退货单", new Listener_stub());
			addButton("制定销售单", new Listener_stub());
			addButton("制定销售退货单", new Listener_stub());
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.ACCOUNTANT) {
			addButton("账户管理", new Listener(mw, new MockDataPanel(new AccountBL_stub(), closeListener)));
			addButton("制定收付款单", new Listener_stub());
			addButton("制定现金费用单", new Listener_stub());
			addButton("查看销售明细表", new Listener_stub());
			addButton("查看经营状况表", new Listener_stub());
			addButton("查看经营历程表", new Listener_stub());
			addButton("期初建账", new Listener_stub());
			addButton("查看日志", new Listener(mw, new LogPanel(mw)));		
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.GM) {
			addButton("审批单据", new Listener(mw, new BillExaminePanel(mw)));
			addButton("制定促销策略", new Listener_stub());
			addButton("查看销售明细表", new Listener_stub());
			addButton("查看经营状况表", new Listener_stub());
			addButton("查看经营历程表", new Listener_stub());
			addButton("查看日志", new Listener(mw, new LogPanel(mw)));		
			addButton("退出", new CloseListener());
		}
		else if (type == UserType.ADMIN) {
			addButton("用户管理", new Listener(mw, new UserDataPanel(new UserBL_stub(), closeListener)));
			addButton("查看日志", new Listener(mw, new LogPanel(mw)));		
			addButton("退出", new CloseListener());
		}
		//-----------------------------------------
		
		double[][] size = {{TableLayout.FILL, 0.8, TableLayout.FILL},{TableLayout.FILL}};
		this.setLayout(new TableLayout(size));
		this.add(innerPanel, "1,0");
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
