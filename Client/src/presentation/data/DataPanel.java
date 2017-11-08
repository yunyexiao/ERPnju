package presentation.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.DataBLService;
import businesslogic.AccountBL_stub;
import businesslogic.CustomerBL_stub;
import businesslogic.UserBL_stub;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.CloseListener;
import presentation.component.Listener_stub;
import presentation.component.TopButtonPanel;
import presentation.main.MainWindow;
import vo.UserType;
import vo.UserVO;
/**
 * �����������ݵ�ͨ��Panel��� </br>
 * ������Ʒ��Ϣ���ͻ���Ϣ���˻���Ϣ���������û��������Ӧ</br>
 * ���Ϊ�˷�����չ�����ֳ�������ö�����ʶ��
 * @author Ǯ��Ե
 *
 */
public class DataPanel implements PanelInterface {
	private MainWindow mainWindow;
	private DataBLService dataBL;
	private TopButtonPanel buttonPanel = new TopButtonPanel();
	private JPanel panel = new JPanel();
	private JTable table = new JTable();
	private JScrollPane srcollpane;
	

	public DataPanel(MainWindow mw, DataType type) {
		this.mainWindow = mw;
		
		if (type == DataType.COMMODITY) dataBL = new CustomerBL_stub();
		else if (type == DataType.CUSTOMER) dataBL = new CustomerBL_stub();
		else if (type == DataType.ACCOUNT) dataBL = new AccountBL_stub();
		else if (type == DataType.USER) dataBL = new UserBL_stub();
		table.setModel(dataBL.update());
		
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel.setLayout(new TableLayout(size));
		
		//TODO ��ť�ļ������ڲ���inner class
		class AddListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.setEnable(false);
				new AddWindow(mainWindow, dataBL, type);
				table.setModel(dataBL.update());
			}
		}
		class DeleteListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������Ϣ��", "��ʾ", JOptionPane.YES_NO_OPTION);
				if (response == 0) {
					if (dataBL.delete("1")) JOptionPane.showMessageDialog(null, "��Ϣ�ѳɹ�ɾ��", "ϵͳ", JOptionPane.INFORMATION_MESSAGE); 
					table.setModel(dataBL.update());
				}
			}			
		}
		
		buttonPanel.addButton("����", new ImageIcon("resource/AddData.png"), new AddListener());
		buttonPanel.addButton("�޸�", new ImageIcon("resource/ChangeData.png"), new Listener_stub());
		buttonPanel.addButton("��ѯ", new ImageIcon("resource/SearchData.png"), new Listener_stub());
		buttonPanel.addButton("ɾ��", new ImageIcon("resource/DeleteData.png"), new DeleteListener());
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), new CloseListener(mainWindow));
		
		srcollpane = new JScrollPane(table);
		panel.add(buttonPanel.getPanel(), "0,0");
		panel.add(srcollpane, "0,1");
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