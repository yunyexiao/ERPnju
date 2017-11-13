package presentation.logui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.LogBLService;
import businesslogic.LogBL_stub;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.CloseListener;
import presentation.component.TopButtonPanel;
import presentation.main.MainWindow;

public class LogPanel implements PanelInterface {
	
	private static double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
	private JPanel panel = new JPanel(new TableLayout(size));
	private LogBLService logBL = new LogBL_stub();

	public LogPanel(MainWindow mw) {
		TopButtonPanel buttonPanel = new TopButtonPanel();
		
		JTable table = new JTable(logBL.getLogInfo());
		JScrollPane scrollPane = new JScrollPane(table);
		
		class SearchListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] re = buttonPanel.getCalender();
				table.setModel(logBL.searchByTime(re[0], re[1]));
			}
		}
		buttonPanel.addCalendar("起始日期");
		buttonPanel.addCalendar("结束日期");
		buttonPanel.addButton("查询", new ImageIcon("resource/SearchData.png"), new SearchListener());
		buttonPanel.addButton("关闭", new ImageIcon("resource/Close.png"), new CloseListener(mw));
		
		panel.add(buttonPanel.getPanel(), "0,0");
		panel.add(scrollPane, "0,1");
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
