package presentation.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import layout.TableLayout;
import presentation.bill.BillPanel;
import presentation.bill.CentrePanel;
import presentation.data.DataPanel;
import vo.UserType;

/**
 * MainWindow左侧的按钮栏，此处继承了JPanel
 * @author 钱美缘
 */
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
		//TODO 修改按钮处------------------------------
		if (type == UserType.KEEPER) {
			addButton("商品分类管理", new Listener(mw, new BillPanel(mw, CentrePanel.PURCHASE, true)));
		}
		else if (type == UserType.SALESMAN) {
			addButton("客户管理", new Listener(mw, new DataPanel(mw)));
			addButton("制定进货单", new Listener(mw, new BillPanel(mw, CentrePanel.PURCHASE, true)));
		}
		//-----------------------------------------
		double[][] size = {{TableLayout.FILL, 0.8, TableLayout.FILL},{TableLayout.FILL}};
		this.setLayout(new TableLayout(size));
		this.add(innerPanel, "1,0");
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
