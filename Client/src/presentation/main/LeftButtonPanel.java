package presentation.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bl_stub.UserBL_stub;
import businesslogic.AccountBL;
import businesslogic.CategoryBL;
import businesslogic.CommodityBL;
import businesslogic.CustomerBL;
import layout.TableLayout;
import presentation.bill.BillExaminePanel;
import presentation.bill.BillPanel;
import presentation.billui.CashCostBillPanel;
import presentation.billui.ChangeBillPanel;
import presentation.billui.ReceiptOrPaymentBillPanel;
import presentation.billui.SaleBillPanel;
import presentation.component.InfoAdapter;
import presentation.component.Listener_stub;
import presentation.dataui.accountui.AccountDataPanel;
import presentation.dataui.categoryui.CategoryDataPanel;
import presentation.dataui.commodityui.CommodityDataPanel;
import presentation.dataui.customerui.CustomerDataPanel;
import presentation.dataui.userui.UserDataPanel;
import presentation.logui.LogPanel;
import vo.BillType;
import vo.UserType;
import vo.UserVO;

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
		button.setFont(new Font("����",Font.BOLD,18));
		button.addActionListener(listener);
		if ("�˳�".equals(text)) button.addMouseListener(new InfoAdapter("�˳�ϵͳ"));
		else button.addMouseListener(new InfoAdapter("����<" + text + ">����"));
		innerPanel.add(button);
    }
    /**
     * ���췽��������MainWindow��UserVO��ʼ����ť��
     * @param mw �����MainWindow����
     */
	public LeftButtonPanel(MainWindow mw) {
		this.mainWindow = mw;
		UserVO user = mainWindow.getUser();
		UserType type = user.getType();
		innerPanel.setOpaque(false);
		
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
			addButton("��Ʒ�������", e -> mw.changePanel(new CategoryDataPanel(new CategoryBL(), closeListener)));
			addButton("��Ʒ����", e -> mw.changePanel(new CommodityDataPanel(new CommodityBL(), closeListener)));
			addButton("���鿴", new Listener_stub());
			addButton("����̵�", new Listener_stub());
			addButton("����/����", e -> mw.changePanel(new ChangeBillPanel(user, closeListener)));
			addButton("�˳�", new CloseListener());
			
		}
		else if (type == UserType.SALESMAN) {
			addButton("�ͻ�����", e -> mw.changePanel(new CustomerDataPanel(user, new CustomerBL(), closeListener)));
			addButton("�ƶ�������", e -> mw.changePanel(new BillPanel(mw, BillType.PURCHASE, true)));
			addButton("�ƶ������˻���", new Listener_stub());
			addButton("�ƶ����۵�", e -> mw.changePanel(new SaleBillPanel(user, closeListener)));
			addButton("�ƶ������˻���", new Listener_stub());
			addButton("�˳�", new CloseListener());
		}
		else if (type == UserType.ACCOUNTANT) {
			addButton("�˻�����", e -> mw.changePanel(new AccountDataPanel(user, new AccountBL(user), closeListener)));
			addButton("�ƶ��ո��", e -> mw.changePanel(new ReceiptOrPaymentBillPanel(user, closeListener)));
			addButton("�ƶ��ֽ���õ�", e -> mw.changePanel(new CashCostBillPanel(user, closeListener)));
			addButton("�鿴������ϸ��", new Listener_stub());
			addButton("�鿴��Ӫ״����", new Listener_stub());
			addButton("�鿴��Ӫ���̱�", new Listener_stub());
			addButton("�ڳ�����", new Listener_stub());
			addButton("�鿴��־", e -> mw.changePanel(new LogPanel(mw)));		
			addButton("�˳�", new CloseListener());
		}
		else if (type == UserType.GM) {
			addButton("��������", e -> mw.changePanel(new BillExaminePanel(mw)));
			addButton("�ƶ���������", new Listener_stub());
			addButton("�鿴������ϸ��", new Listener_stub());
			addButton("�鿴��Ӫ״����", new Listener_stub());
			addButton("�鿴��Ӫ���̱�", new Listener_stub());
			addButton("�鿴��־", e -> mw.changePanel(new LogPanel(mw)));		
			addButton("�˳�", new CloseListener());
		}
		else if (type == UserType.ADMIN) {
			addButton("�û�����", e -> mw.changePanel(new UserDataPanel(new UserBL_stub(), closeListener)));
			addButton("�鿴��־", e -> mw.changePanel(new LogPanel(mw)));		
			addButton("�˳�", new CloseListener());
		}
		//-----------------------------------------
		
		double[][] size = {{TableLayout.FILL, 0.8, TableLayout.FILL},{TableLayout.FILL}};
		this.setLayout(new TableLayout(size));
		this.add(innerPanel, "1,0");
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void setBackground() {
		ImageIcon image = new ImageIcon("resource/LeftButtonPanel.png");   
        Image img = image.getImage();  
        img = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);  
        image.setImage(img);
		this.add(new JLabel(image), "0, 0, 2, 0");
	}
}