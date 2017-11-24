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
 * MainWindow���İ�ť�����˴��̳���JPanel
 * @author Ǯ��Ե
 */
@SuppressWarnings("serial")
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
		
		// �޸İ�ť��------------------------------
		if (type == UserType.KEEPER) {
			addButton("��Ʒ�������", new Listener(mw, new CategoryPanel(mw)));
			addButton("��Ʒ����", new Listener_stub());
			addButton("���鿴", new Listener_stub());
			addButton("����̵�", new Listener_stub());
			addButton("����/����", new Listener_stub());
			addButton("�˳�", new CloseListener());
			
		}
		else if (type == UserType.SALESMAN) {
			addButton("�ͻ�����", new Listener(mw, new MockDataPanel(new CustomerBL_stub(), closeListener)));
			addButton("�ƶ�������", new Listener(mw, new BillPanel(mw, BillType.PURCHASE, true)));
			addButton("�ƶ������˻���", new Listener_stub());
			addButton("�ƶ����۵�", new Listener_stub());
			addButton("�ƶ������˻���", new Listener_stub());
			addButton("�˳�", new CloseListener());
		}
		else if (type == UserType.ACCOUNTANT) {
			addButton("�˻�����", new Listener(mw, new MockDataPanel(new AccountBL_stub(), closeListener)));
			addButton("�ƶ��ո��", new Listener_stub());
			addButton("�ƶ��ֽ���õ�", new Listener_stub());
			addButton("�鿴������ϸ��", new Listener_stub());
			addButton("�鿴��Ӫ״����", new Listener_stub());
			addButton("�鿴��Ӫ���̱�", new Listener_stub());
			addButton("�ڳ�����", new Listener_stub());
			addButton("�鿴��־", new Listener(mw, new LogPanel(mw)));		
			addButton("�˳�", new CloseListener());
		}
		else if (type == UserType.GM) {
			addButton("��������", new Listener(mw, new BillExaminePanel(mw)));
			addButton("�ƶ���������", new Listener_stub());
			addButton("�鿴������ϸ��", new Listener_stub());
			addButton("�鿴��Ӫ״����", new Listener_stub());
			addButton("�鿴��Ӫ���̱�", new Listener_stub());
			addButton("�鿴��־", new Listener(mw, new LogPanel(mw)));		
			addButton("�˳�", new CloseListener());
		}
		else if (type == UserType.ADMIN) {
			addButton("�û�����", new Listener(mw, new UserDataPanel(new UserBL_stub(), closeListener)));
			addButton("�鿴��־", new Listener(mw, new LogPanel(mw)));		
			addButton("�˳�", new CloseListener());
		}
		//-----------------------------------------
		
		double[][] size = {{TableLayout.FILL, 0.8, TableLayout.FILL},{TableLayout.FILL}};
		this.setLayout(new TableLayout(size));
		this.add(innerPanel, "1,0");
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
