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
 * JPanel��������JButton��ͳһͼƬ�����ָ�ʽ��JPanel
 * @author Ǯ��Ե
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
	 * �����ʹ��addButton������Ӱ�ť�ٻ�ȡJPanel
	 * @return ����JPanel
	 */
	public JPanel getPanel() {
		if (buttonArray.size() != panel.getComponentCount()) {
			for (JButton button: buttonArray) panel.add(button);
		}
		return panel;
	}
	/**
	 * ��˳����Ӱ�ť�����
	 * @param text ��ť������
	 * @param icon ��ť��ͼ�꣨��Դ�ļ���resource�£�
	 * @param listener �󶨵ļ������������������ڲ��࣬��new��
	 */
	public void addButton(String text, Icon icon, ActionListener listener) {
		JButton button = new JButton(text, icon);
		button.setFont(new Font("����",Font.BOLD,14));
		button.addActionListener(listener);
		buttonArray.add(button);
	}
}
