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
 * JPanel��������JButton��ͳһͼƬ�����ָ�ʽ��JPanel
 * @author Ǯ��Ե
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
	 * �����ʹ��addButton������Ӱ�ť�ٻ�ȡJPanel
	 * @return ����JPanel
	 */
	public JPanel getPanel() {
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
		panel.add(button, (panel.getComponentCount() * 2 + 1) +" 1");
	}
	/**
	 * �õ�����ѡ��ؼ�ѡ�����������
	 * @return ѡ��������ַ�����Ĭ��yyyy-MM-dd���������˳������
	 */
	public String[] getCalender() {
		String[] result = new String[dateChooserArray.size()];
		for (int i = 0; i < dateChooserArray.size(); i++) {
			result[i] = dateChooserArray.get(i).getDate();
		}
		return result;
	}
	/**
	 * ����ť������Ϊ�����ã����һ����ť���⣨һ���ǹرհ�ť��
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
