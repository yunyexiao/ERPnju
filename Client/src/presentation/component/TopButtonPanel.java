package presentation.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	private ArrayList<DateChooser> dateChooserArray = new ArrayList<DateChooser>();
	//private ArrayList<JButton> buttonArray = new ArrayList<JButton>();
	
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
		//if (buttonArray.size() != panel.getComponentCount()) {
		//	for (JButton button: buttonArray) panel.add(button);
		//}
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
		panel.add(button);
		//buttonArray.add(button);
	}
	/**
	 * ��Panel������һ��ѡ�����ڵĿռ䣬ʹ��html��ǩ��������ʾ<br/>
	 * û��ͼ�ꡣ��������
	 * @param text ������ť��һ����ʾ���ı������ܹ�����
	 */
	public void addCalendar(String text) {
		DateChooser dateChooser = DateChooser.getInstance("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		String date = sdf.format(dt);
		JButton button = new JButton("<html><center>" + text + "</center></p><p style=\"line-height:0px\">" + date + "</p></html>");
		button.setFont(new Font("����",Font.BOLD,14));
		dateChooser.register(button);
		dateChooserArray.add(dateChooser);
		panel.add(button);
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
}
