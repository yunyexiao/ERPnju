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
import presentation.component.TopButtonPanel;
/**
 * 处理基础数据的通用Panel组件 </br>
 * 包括商品信息、客户信息、账户信息、用户信息</br>
 * @author 钱美缘
 *
 */
public abstract class DataPanel implements PanelInterface {
	private JPanel panel;
	protected JTable table = new JTable();
	private DataBLService dataBL;

	public DataPanel(DataBLService dataBL, ActionListener closeListener) {
		this.dataBL = dataBL;
		table.setModel(dataBL.update());
		
		double[][] size = {{TableLayout.FILL},{TableLayout.PREFERRED,TableLayout.FILL}};
		panel = new JPanel(new TableLayout(size));
		
		class DeleteListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showConfirmDialog(null, "确认要删除此条信息？", "提示", JOptionPane.YES_NO_OPTION);
				if (response == 0) {
					//TODO 将1替换为选中的id
					if (dataBL.delete("1")) JOptionPane.showMessageDialog(null, "信息已成功删除", "系统", JOptionPane.INFORMATION_MESSAGE); 
					table.setModel(dataBL.update());
				}
			}			
		}
		
		TopButtonPanel buttonPanel = new TopButtonPanel();
		buttonPanel.addButton("增加", new ImageIcon("resource/AddData.png"), getAddListener());
		buttonPanel.addButton("修改", new ImageIcon("resource/ChangeData.png"), getUpdateListener());
		buttonPanel.addButton("查询", new ImageIcon("resource/SearchData.png"), getSearchListener());
		buttonPanel.addButton("删除", new ImageIcon("resource/DeleteData.png"), new DeleteListener());
		buttonPanel.addButton("关闭", new ImageIcon("resource/Close.png"), closeListener);
		
		JScrollPane srcollpane = new JScrollPane(table);
		panel.add(buttonPanel.getPanel(), "0,0");
		panel.add(srcollpane, "0,1");
	}
	
	protected void updateTable() {
		System.out.println("表格数据已更新");
		table.setModel(dataBL.update());
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
