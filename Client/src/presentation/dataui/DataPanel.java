package presentation.dataui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.DataBLService;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.component.TopButtonPanel;
/**
 * ����������ݵ�ͨ��Panel��� </br>
 * ������Ʒ��Ϣ���ͻ���Ϣ���˻���Ϣ���û���Ϣ</br>
 * @author Ǯ��Ե
 *
 */
public abstract class DataPanel implements PanelInterface {
	private JPanel panel;
	protected JTable table = new JTable();
	private DataBLService dataBL;

	public DataPanel(DataBLService dataBL, ActionListener closeListener) {
		this.dataBL = dataBL;
		table.setModel(dataBL.update());
		table.getTableHeader().setReorderingAllowed(false);
		
		double[][] size = {{TableLayout.FILL},{TableLayout.PREFERRED,TableLayout.FILL}};
		panel = new JPanel(new TableLayout(size));
		
		TopButtonPanel buttonPanel = new TopButtonPanel();
		buttonPanel.addButton("����", new ImageIcon("resource/AddData.png"), getAddListener());
		buttonPanel.addButton("�޸�", new ImageIcon("resource/ChangeData.png"), getUpdateListener());
		buttonPanel.addButton("��ѯ", new ImageIcon("resource/SearchData.png"), getSearchListener());
		buttonPanel.addButton("ɾ��", new ImageIcon("resource/DeleteData.png"), getDeleteListener());
		buttonPanel.addButton("ˢ��", new ImageIcon("resource/Refresh.png"), e -> table.setModel(dataBL.update()));
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), closeListener);
		
		JScrollPane srcollpane = new JScrollPane(table);
		panel.add(buttonPanel.getPanel(), "0,0");
		panel.add(srcollpane, "0,1");
	}
	
	protected void updateTable() {
		System.out.println("��������Ѹ���");
		table.setModel(dataBL.update());
	}
	
	protected ActionListener getDeleteListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			    int index = table.getSelectedRow();
			    if(index < 0) {new InfoWindow("��ѡ����Ҫɾ������Ϣ");return;}
				int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������Ϣ��", "��ʾ", JOptionPane.YES_NO_OPTION);
				if (response == 0) {
				    String id = (String)((MyTableModel)table.getModel()).getValueAt(index, 0);
					if (dataBL.delete(id)) new InfoWindow("��Ϣ�ѳɹ�ɾ��");
					table.setModel(dataBL.update());
				}
			}			
		};
	}
	
	abstract protected ActionListener getAddListener();
	
	abstract protected ActionListener getUpdateListener();
	
	abstract protected ActionListener getSearchListener();

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
