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
 * 处理基础数据的通用Panel组件 </br>
 * 包括商品信息、客户信息、账户信息、用户信息</br>
 * @author 钱美缘
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
					//FIXME table更新之后不知道model会不会变
					new UpdateWindow(mainWindow, dataBL, tableModel.getValueAtRow(table.getSelectedRow()));
					table.setModel(dataBL.update());
				}
			}
		}
		
		class SearchListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 实现弹出搜索条件输入的对话框
			}
		}
		
		class DeleteListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showConfirmDialog(null, "确认要删除此条信息？", "提示", JOptionPane.YES_NO_OPTION);
				if (response == 0) {
					if (dataBL.delete("1")) JOptionPane.showMessageDialog(null, "信息已成功删除", "系统", JOptionPane.INFORMATION_MESSAGE); 
					table.setModel(dataBL.update());
				}
			}			
		}
		
		buttonPanel.addButton("增加", new ImageIcon("resource/AddData.png"), new AddListener());
		buttonPanel.addButton("修改", new ImageIcon("resource/ChangeData.png"), new UpdateListener());
		buttonPanel.addButton("查询", new ImageIcon("resource/SearchData.png"), new SearchListener());
		buttonPanel.addButton("删除", new ImageIcon("resource/DeleteData.png"), new DeleteListener());
		buttonPanel.addButton("关闭", new ImageIcon("resource/Close.png"), new CloseListener(mainWindow));
		
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
