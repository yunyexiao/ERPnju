package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.TopButtonPanel;
import vo.UserVO;

/**
 * 两个构造方法分别对应新建单据和修改单据，其中也包含了两个不同的抽象方法初始化BillPanel(使用getBillPanel得到)<br/>
 * 三个获取监听器方法在子类中表现为不同的业务逻辑处理的监听器<br/>
 * 子类中将对应单据的BL作为私有成员变量，如果需要测试请自己写桩stub<br/>
 * 子类可能需要将单据的VO作为私有成员变量，尤其是带有表格的单据<br/>
 * 子类的构造方法中使用相应的BillVO的子类
 * @author 钱美缘
 *
 */
public abstract class BillPanel implements PanelInterface {

	protected UserVO user;
	private JPanel panel;
	protected JPanel billPanel;
	/**
	 * 新建一张单据时的构造方法
	 * @param closeListener MainWindow的关闭Panel监听器
	 */
	public BillPanel(UserVO user, ActionListener closeListener) {
		this.user = user;
		initPanel(closeListener);
		initBillPanel();
	}
	
	private void initPanel(ActionListener closeListener) {
		TopButtonPanel buttonPanel = new TopButtonPanel();
		
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel = new JPanel(new TableLayout(size));
		billPanel = new JPanel();
		
		buttonPanel.addButton("新建", new ImageIcon("resource/New.png"), getNewActionListener());
		buttonPanel.addButton("保存", new ImageIcon("resource/Save.png"), getSaveActionListener());
		buttonPanel.addButton("提交", new ImageIcon("resource/Commit.png"), getCommitActionListener());
		buttonPanel.addButton("关闭", new ImageIcon("resource/Close.png"), closeListener);
		
		panel.add(buttonPanel.getPanel(), "0 0");
		panel.add(billPanel, "0 1");
	}
	/**
	 * 初始化BillPanel的显示部分（除了单据id其他皆为空白）<br/>
	 * 组长惨痛教训：千万别再new一个billPaenl，布局直接set
	 */
	abstract protected void initBillPanel();
	/**
	 * 
	 * @return 获得新建按钮需要绑定的监听器
	 */
	abstract protected ActionListener getNewActionListener();
	/**
	 * 
	 * @return 获得保存按钮需要绑定的监听器
	 */
	abstract protected ActionListener getSaveActionListener();
	/**
	 * 
	 * @return 获得提交按钮需要绑定的监听器
	 */
	abstract protected ActionListener getCommitActionListener();
	/**
	 * 
	 * @return 判断单据填写内容是否正确
	 */
	abstract protected boolean isCorrectable();
	
	@Override
	public boolean close() {
		return true;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
