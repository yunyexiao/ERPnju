package presentation.logui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bl_stub.LogBL_stub;
import blservice.LogBLService;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.CloseListener;
import presentation.component.TopButtonPanel;
import presentation.main.MainWindow;

/**
 * 查看日志使用的Panel
 * @author 钱美缘
 *
 */
public class LogPanel implements PanelInterface {
	
	private JPanel panel;
	private LogBLService logBL = new LogBL_stub();

	public LogPanel(MainWindow mw) {
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel = new JPanel(new TableLayout(size));
		
		JTable table = new JTable(logBL.getLogInfo());
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, "0,1");
		
		class SearchListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] input = new SearchWindow().getInput();
				table.setModel(logBL.searchByTime(input[0], input[1]));
			}
		}
		
		TopButtonPanel buttonPanel = new TopButtonPanel();
		buttonPanel.addButton("查询", new ImageIcon("resource/SearchData.png"), new SearchListener());
		buttonPanel.addButton("关闭", new ImageIcon("resource/Close.png"), new CloseListener(mw));
		panel.add(buttonPanel.getPanel(), "0,0");
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
