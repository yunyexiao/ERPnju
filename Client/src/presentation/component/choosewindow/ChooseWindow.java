package presentation.component.choosewindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public abstract class ChooseWindow {

	protected JDialog frame = new JDialog();
	protected JTable table = new JTable();
	private JComboBox<String> searchTypeBox;
	
	public ChooseWindow() {
		frame.setModal(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width/2, screenSize.height/2);
		frame.setLocation(screenSize.width/4, screenSize.height/4);
		frame.setResizable(false);
		
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		searchPanel.add(new JLabel("选择搜索方式"));
		searchTypeBox = new JComboBox<String>();
		searchPanel.add(searchTypeBox);
		
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		JScrollPane tablePane = new JScrollPane(table);
		//tablePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton yesButton = new JButton("确定");
		JButton quitButton = new JButton("取消");
		southPanel.add(yesButton);
		southPanel.add(quitButton);
		
		frame.setLayout(new BorderLayout());
		frame.add(searchPanel, BorderLayout.NORTH);
		frame.add(tablePane, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		
		quitButton.addActionListener(e -> frame.dispose());
		yesButton.addActionListener(e -> yesAction());

		init();
	}
	
	protected void FitTableColumns(){
	    JTableHeader header = table.getTableHeader();
	    int rowCount = table.getRowCount();
	
	    Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
	    while(columns.hasMoreElements()){
	        TableColumn column = columns.nextElement();
	        int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
	        int width = (int)table.getTableHeader().getDefaultRenderer()
	                 .getTableCellRendererComponent(table, column.getIdentifier()
	                         , false, false, -1, col).getPreferredSize().getWidth();
	        for(int row = 0; row < rowCount; row++){
	        	int preferedWidth = (int)table.getCellRenderer(row, col).getTableCellRendererComponent(table,
	               table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
	             width = Math.max(width, preferedWidth);
	        }
	        header.setResizingColumn(column); // 此行很重要
	        column.setWidth(width + table.getIntercellSpacing().width + 15);
		}
	}
		     
	protected void setTypes(String[] searchTypes) {
		for (String str : searchTypes) {
			searchTypeBox.addItem(str);
		}
	}
	
	/**
	 * 初始化选择窗口，包括搜索方式字符串数组和表格，最后一行一定记得写frame.setVisible(true);
	 */
	abstract public void init();
	
	abstract protected void yesAction();
}
