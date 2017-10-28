package presentation.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * JPanel顶部放置JButton并统一图片和文字格式的JPanel
 * @author 钱美缘
 *
 */
public class TopButtonPanel {
	private JPanel panel = new JPanel();
	private ArrayList<JButton> buttonArray = new ArrayList<JButton>();
	
	public TopButtonPanel() {
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		panel.setLayout(f);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	/**
	 * 务必先使用addButton方法添加按钮再获取JPanel
	 * @return 返回JPanel
	 */
	public JPanel getPanel() {
		if (buttonArray.size() != panel.getComponentCount()) {
			for (JButton button: buttonArray) panel.add(button);
		}
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
		buttonArray.add(button);
	}
}
