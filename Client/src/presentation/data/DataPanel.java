package presentation.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.DataBLService;
import businesslogic.DataBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.CloseListener;
import presentation.component.MyTableModel;
import presentation.component.TopButtonPanel;
import presentation.main.MainWindow;
/**
 * ����������ݵ�ͨ��Panel��� </br>
 * ������Ʒ��Ϣ���ͻ���Ϣ���˻���Ϣ���û���Ϣ</br>
 * @author Ǯ��Ե
 *
 */
public class DataPanel implements PanelInterface {
	private MainWindow mainWindow;
	private DataBL dataBL;
	private TopButtonPanel buttonPanel = new TopButtonPanel();
	private JPanel panel = new JPanel();
	private JTable table = new JTable();
	private JScrollPane srcollpane;
	

	public DataPanel(MainWindow mw, DataBLService dataBLService) {
		this.mainWindow = mw;
		this.dataBL = new DataBL(dataBLService);
		/*
		if (type == DataType.COMMODITY) dataBL = new DataBL(new CustomerBL_stub());
		else if (type == DataType.CUSTOMER) dataBL = new DataBL(new CustomerBL_stub());
		else if (type == DataType.ACCOUNT) dataBL = new AccountBL_stub();
		else if (type == DataType.USER) dataBL = new UserBL_stub();
		*/
		MyTableModel tableModel = dataBL.update();
		table.setModel(tableModel);
		
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel.setLayout(new TableLayout(size));

		class AddListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.setEnable(false);
				new AddWindow(mainWindow, dataBL);
				table.setModel(dataBL.update());
			}
		}
		
		class UpdateListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					mainWindow.setEnable(false);
					//FIXME table����֮��֪��model�᲻���
					new UpdateWindow(mainWindow, dataBL, tableModel.getValueAtRow(table.getSelectedRow()));
					table.setModel(dataBL.update());
				}
			}
		}
		
		class SearchListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO ʵ�ֵ���������������ĶԻ���
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
		buttonPanel.addButton("�޸�", new ImageIcon("resource/ChangeData.png"), new UpdateListener());
		buttonPanel.addButton("��ѯ", new ImageIcon("resource/SearchData.png"), new SearchListener());
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
