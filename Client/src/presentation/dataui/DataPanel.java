package presentation.dataui;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import blservice.AccountBLService;
import blservice.CommodityBLService;
import blservice.CustomerBLService;
import blservice.UserBLService;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.TopButtonPanel;
import presentation.dataui.accountui.AccountDataPanel;
import presentation.dataui.commodityui.CommodityDataPanel;
import presentation.dataui.customerui.CustomerDataPanel;
import presentation.dataui.userui.UserDataPanel;
/**
 * ����������ݵ�ͨ��Panel��� </br>
 * ������Ʒ��Ϣ���ͻ���Ϣ���˻���Ϣ���û���Ϣ</br>
 * @author Ǯ��Ե
 *
 */
public class DataPanel implements PanelInterface {
	private JPanel panel = new JPanel();

	public DataPanel(AccountBLService accountBL, ActionListener closeListener) {
		initDataPanel(new AccountDataPanel(accountBL), closeListener);
	}
	public DataPanel(CommodityBLService commodityBL, ActionListener closeListener) {
		initDataPanel(new CommodityDataPanel(commodityBL), closeListener);
	}
	public DataPanel(CustomerBLService customerBL, ActionListener closeListener) {
		initDataPanel(new CustomerDataPanel(customerBL), closeListener);
	}
	public DataPanel(UserBLService userBL, ActionListener closeListener) {
		initDataPanel(new UserDataPanel(userBL), closeListener);
	}
	private void initDataPanel(DataPanelInterface dataImpl, ActionListener closeListener) {
		double[][] size = {{TableLayout.FILL},{TableLayout.PREFERRED,TableLayout.FILL}};
		panel.setLayout(new TableLayout(size));
		
		TopButtonPanel buttonPanel = new TopButtonPanel();
		buttonPanel.addButton("����", new ImageIcon("resource/AddData.png"), e->dataImpl.addAction());
		buttonPanel.addButton("�޸�", new ImageIcon("resource/ChangeData.png"), e->dataImpl.updateAction());
		buttonPanel.addButton("��ѯ", new ImageIcon("resource/SearchData.png"), e->dataImpl.searchAction());
		buttonPanel.addButton("ɾ��", new ImageIcon("resource/DeleteData.png"), e->dataImpl.deleteAction());
		//buttonPanel.addButton("ˢ��", new ImageIcon("resource/Refresh.png"), e -> {table.setModel(dataBL.update());TableTools.autoFit(table);});
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), closeListener);
		panel.add(buttonPanel.getPanel(), "0,0");
		panel.add((JScrollPane)dataImpl, "0,1");
	}
	
	@Override
	public boolean close() {
		return true;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}
}
