package presentation.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import businesslogic.AccountBL;
import businesslogic.CategoryBL;
import businesslogic.CommodityBL;
import businesslogic.CustomerBL;
import businesslogic.UserBL;
import layout.TableLayout;
import presentation.analysisui.InventoryCheckPanel;
import presentation.analysisui.InventoryDynamicPanel;
import presentation.analysisui.SalesDetailsPanel;
import presentation.analysisui.ViewBusinessHistoryPanel;
import presentation.analysisui.ViewBusinessSituationPanel;
import presentation.billui.BillExaminePanel;
import presentation.billui.BillPanelHelper;
import presentation.component.InfoAdapter;
import presentation.dataui.accountui.AccountDataPanel;
import presentation.dataui.categoryui.CategoryDataPanel;
import presentation.dataui.commodityui.CommodityDataPanel;
import presentation.dataui.customerui.CustomerDataPanel;
import presentation.dataui.userui.UserDataPanel;
import presentation.initui.InitPanel;
import presentation.logui.LogPanel;
import presentation.promotionui.PromotionPanel;
import vo.UserType;
import vo.UserVO;

/**
 * MainWindow���İ�ť�����˴��̳���JPanel
 * @author Ǯ��Ե
 */
@SuppressWarnings("serial")
class LeftButtonPanel extends JPanel{
    private JPanel innerPanel = new JPanel(new GridLayout(11, 1, 5, 5));
    private ArrayList<JToggleButton> buttonList = new ArrayList<JToggleButton>();
    /**
     * ��Panel���Ӱ�ť
     * @param text ��ť��ʾ������
     * @param listener ��ť�󶨵ļ���������Listener�����ɣ�
     */
    private void addButton(String text, ActionListener listener) {
    	JToggleButton button = new JToggleButton(text);
		button.setFont(new Font("����",Font.BOLD,18));
		button.addActionListener(listener);
		button.addActionListener(e->updateButtons(button));
		if ("�˳�".equals(text)) button.addMouseListener(new InfoAdapter("�˳�ϵͳ"));
		else button.addMouseListener(new InfoAdapter("����<" + text + ">����"));
		buttonList.add(button);
    }
    /**
     * ���췽��������MainWindow��UserVO��ʼ����ť��
     * @param mw �����MainWindow����
     */
	public LeftButtonPanel(MainWindow mw) {
		UserVO user = MainWindow.getUser();
		UserType type = user.getType();
		innerPanel.setOpaque(false);
		
		class CloseListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mw.close();
			}	
		}
		
		ActionListener closeListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mw.changePanel();
            }
		    
		};
		
		BillPanelHelper.user = user;
		BillPanelHelper.closeListener = closeListener;
		// �޸İ�ť��------------------------------
		if (type == UserType.KEEPER) {
			addButton("��Ʒ�������", e -> mw.changePanel(new CategoryDataPanel(new CategoryBL(), closeListener)));
			addButton("��Ʒ����", e -> mw.changePanel(new CommodityDataPanel(new CommodityBL(), closeListener)));
			addButton("���鿴", e -> mw.changePanel(new InventoryDynamicPanel()));
			addButton("����̵�", e -> mw.changePanel(new InventoryCheckPanel(closeListener)));
			addButton("����/����", e -> mw.changePanel(BillPanelHelper.create("ChangeBill")));
		}
		else if (type == UserType.SALESMAN) {
			addButton("�ͻ�����", e -> mw.changePanel(new CustomerDataPanel(user, new CustomerBL(), closeListener)));
			addButton("�ƶ�������", e -> mw.changePanel(BillPanelHelper.create("PurchaseBill")));
			addButton("�ƶ������˻���", e -> mw.changePanel(BillPanelHelper.create("PurchaseReturnBill")));
			addButton("�ƶ����۵�", e -> mw.changePanel(BillPanelHelper.create("SalesBill")));
			addButton("�ƶ������˻���", e -> mw.changePanel(BillPanelHelper.create("SalesReturnBill")));
		}
		else if (type == UserType.ACCOUNTANT) {
			addButton("�˻�����", e -> mw.changePanel(new AccountDataPanel(new AccountBL(), closeListener)));
			addButton("�ƶ��ո��", e -> mw.changePanel(BillPanelHelper.create("ReceiptOrPaymentBill")));
			addButton("�ƶ��ֽ���õ�", e -> mw.changePanel(BillPanelHelper.create("CashCostBill")));
			addButton("�鿴������ϸ��", e -> mw.changePanel(new SalesDetailsPanel()));
			addButton("�鿴��Ӫ״����", e -> mw.changePanel(new ViewBusinessSituationPanel(user, closeListener)));
			addButton("�鿴��Ӫ���̱�", e -> mw.changePanel(new ViewBusinessHistoryPanel(user, closeListener)));
			addButton("�ڳ�����", e -> mw.changePanel(new InitPanel(closeListener)));
			addButton("�鿴��־", e -> mw.changePanel(new LogPanel(closeListener)));		
		}
		else if (type == UserType.GM) {
			addButton("��������", e -> mw.changePanel(new BillExaminePanel(mw,closeListener)));
			addButton("�ƶ���������", e -> mw.changePanel(new PromotionPanel(closeListener)));
			addButton("�鿴������ϸ��", e -> mw.changePanel(new SalesDetailsPanel()));
			addButton("�鿴��Ӫ״����", e -> mw.changePanel(new ViewBusinessSituationPanel(user, closeListener)));
			addButton("�鿴��Ӫ���̱�", e -> mw.changePanel(new ViewBusinessHistoryPanel(user, closeListener)));
			addButton("�鿴��־", e -> mw.changePanel(new LogPanel(closeListener)));		
		}
		else if (type == UserType.ADMIN) {
			addButton("�û�����", e -> mw.changePanel(new UserDataPanel(new UserBL(), closeListener)));
			addButton("�鿴��־", e -> mw.changePanel(new LogPanel(closeListener)));		
		}
		addButton("�˳�", new CloseListener());
		//-----------------------------------------
		
		for(JToggleButton button : buttonList) innerPanel.add(button);
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
	
	public void updateButtons(JToggleButton button) {
		for (JToggleButton b : buttonList) {
			if (b != button) b.setSelected(false);
		}
	}
}