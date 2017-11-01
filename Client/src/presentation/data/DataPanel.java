package presentation.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.DataBLService;
import businesslogic.CustomerBL_stub;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.TopButtonPanel;
import presentation.main.MainWindow;
import vo.UserType;
import vo.UserVO;
/**
 * 处理基础数据的通用Panel组件 </br>
 * 包括商品信息、客户信息、账户信息，正好与用户身份相对应</br>
 * 如果为了方便扩展则建议拆分出单独的枚举类标识。
 * @author 钱美缘
 *
 */
public class DataPanel implements PanelInterface {
	private MainWindow mainWindow;
	private UserVO user;
	private DataBLService dataBL;
	private TopButtonPanel buttonPanel = new TopButtonPanel();
	private JPanel panel = new JPanel();
	private JTable table = new JTable();
	private JScrollPane srcollpane;
	

	public DataPanel(MainWindow mw) {
		this.mainWindow = mw;
		this.user = mw.getUser();
		
		if (user.getType() == UserType.SALESMAN) {
			dataBL = new CustomerBL_stub();
		}
		table.setModel(dataBL.update());
		
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel.setLayout(new TableLayout(size));
		
		//TODO 按钮的监听器内部类inner class
		class AddListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.setEnable(false);
				new AddWindow(mainWindow, dataBL);
				table.setModel(dataBL.update());
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
		class CloseListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.changePanel();
			}
		}
		
		buttonPanel.addButton("增加", new ImageIcon("resource/AddData.png"), new AddListener());
		buttonPanel.addButton("修改", new ImageIcon("resource/ChangeData.png"), new CloseListener());
		buttonPanel.addButton("查询", new ImageIcon("resource/SearchData.png"), new CloseListener());
		buttonPanel.addButton("删除", new ImageIcon("resource/DeleteData.png"), new DeleteListener());
		buttonPanel.addButton("关闭", new ImageIcon("resource/Close.png"), new CloseListener());
		
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
