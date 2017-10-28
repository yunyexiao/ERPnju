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
import vo.UserType;

/**
 * MainWindow���İ�ť�����˴��̳���JPanel
 * @author Ǯ��Ե
 */
class LeftButtonPanel extends JPanel{
	private MainWindow mainWindow;
    private JPanel innerPanel = new JPanel(new GridLayout(11, 1, 5, 5));
    /**
     * ��Panel���Ӱ�ť
     * @param text ��ť��ʾ������
     * @param listener ��ť�󶨵ļ���������Listener�����ɣ�
     */
    private void addButton(String text, ActionListener listener) {
    	JButton button = new JButton(text);
		button.setFont(new Font("����",Font.BOLD,14));
		button.addActionListener(listener);
		innerPanel.add(button);
    }
    /**
     * ���췽��������MainWindow��UserVO��ʼ����ť��
     * @param mw �����MainWindow����
     */
	public LeftButtonPanel(MainWindow mw) {
		this.mainWindow = mw;
		
		UserType type = mainWindow.getUser().getType();
		//TODO �޸İ�ť��------------------------------
		if (type == UserType.KEEPER) {
			addButton("��Ʒ�������", new Listener(mw, new BillPanel(mw, mainWindow.getUser(), CentrePanel.PURCHASE, true)));
		}
		else if (type == UserType.SALESMAN) {
			addButton("�ƶ�������", new Listener(mw, new BillPanel(mw, mainWindow.getUser(), CentrePanel.PURCHASE, true)));
		}
		//-----------------------------------------
		double[][] size = {{TableLayout.FILL, 0.8, TableLayout.FILL},{TableLayout.FILL}};
		this.setLayout(new TableLayout(size));
		this.add(innerPanel, "1,0");
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
