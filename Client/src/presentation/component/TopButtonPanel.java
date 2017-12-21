package presentation.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import layout.TableLayout;

/**
 * JPanel顶部放置JButton并统一图片和文字格式的JPanel
 * @author 钱美缘
 *
 */
public class TopButtonPanel {
	private JPanel panel = new JPanel();
	private ArrayList<DateChooser> dateChooserArray = new ArrayList<DateChooser>();
	
	public TopButtonPanel() {
		double p = TableLayout.PREFERRED;
		double b = 5;
		panel.setLayout(new TableLayout(new double[][]{
			{b,p,b,p,b,p,b,p,b,p,b,p,b,p,b,p}, 
			{5,TableLayout.FILL,5}}));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	/**
	 * 务必先使用addButton方法添加按钮再获取JPanel
	 * @return 返回JPanel
	 */
	public JPanel getPanel() {
		return panel;
	}
	/**
	 * 按顺序添加按钮到面板
	 * @param text 按钮的名称
	 * @param icon 按钮的图标（资源文件在resource下）
	 * @param listener 绑定的监听器（先声明匿名内部类，再new）
	 */
	public void addButton(String text, Icon icon, ActionListener listener) {
		JButton button = new JButton(text, icon);
		button.setFont(new Font("宋体",Font.BOLD,14));
		button.addActionListener(listener);
		panel.add(button, (panel.getComponentCount() * 2 + 1) +" 1");
	}
	/**
	 * 向Panel内增加一个选择日期的空间，使用html标签分两行显示<br/>
	 * 没有图标。。。。。
	 * @param text 日历按钮第一行显示的文本（不能过长）
	 */
	public void addCalendar(String text) {
		DateChooser dateChooser = DateChooser.getInstance("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		String date = sdf.format(dt);
		JButton button = new JButton("<html><center>" + text + "</center></p><p style=\"line-height:0px\">" + date + "</p></html>");
		button.setFont(new Font("宋体",Font.BOLD,14));
		dateChooser.register(button);
		dateChooserArray.add(dateChooser);
		panel.add(button);
	}
	/**
	 * 得到日期选择控件选择的日期数组
	 * @return 选择的日期字符串（默认yyyy-MM-dd），按添加顺序排列
	 */
	public String[] getCalender() {
		String[] result = new String[dateChooserArray.size()];
		for (int i = 0; i < dateChooserArray.size(); i++) {
			result[i] = dateChooserArray.get(i).getDate();
		}
		return result;
	}
	/**
	 * 将按钮栏设置为不可用，最后一个按钮除外（一般是关闭按钮）
	 * @param b
	 */
	public void setEnable(Boolean b) {
		int n = panel.getComponentCount();
		for(int i = 0; i < n; i++) {
			panel.getComponent(i).setEnabled(b);
		}
		panel.getComponent(n-1).setEnabled(true);
		panel.updateUI();
		panel.repaint();
	}
}
