package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vo.UserVO;

public class InitPanel implements PanelInterface {
	private JPanel initPanel = new JPanel(new BorderLayout());
	private JTabbedPane tabbedpane = new JTabbedPane();
	private JTable commodityTable = new JTable();
	private DefaultTableModel commodityModel;
	private JPanel commodityPanel = new JPanel();
	private JPanel customerPanel = new JPanel();
	private JPanel accountPanel = new JPanel();
	private MainWindow mainWindow;
	
	public InitPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;

		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		JPanel topPanel = new JPanel(f);
		
		topPanel.setPreferredSize(new Dimension(0, 50));
		topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (close()) mainWindow.changePanel();
			}
		});
		topPanel.add(closeButton);
		
		//create commodity table
		
		JScrollPane commodityPane = new JScrollPane(commodityTable);
		tabbedpane.addTab("��Ʒ��Ϣ", commodityPane);
		tabbedpane.addTab("�ͻ���Ϣ", customerPanel);
		tabbedpane.addTab("�˻���Ϣ", accountPanel);
		initPanel.add(topPanel, BorderLayout.NORTH);
		initPanel.add(tabbedpane, BorderLayout.CENTER);
		initPanel.setVisible(true);
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public void init(UserVO user) {

		String[] commodityHeader = {"��Ʒ���","��Ʒ����","��Ʒ����","��Ʒ�ͺ�","����","�ۼ�","�������","����ۼ�"};
		Object[][] commodityData= {{"��Ʒ���","��Ʒ����","��Ʒ����","��Ʒ�ͺ�","����","�ۼ�","�������","����ۼ�"}};
		commodityModel = new DefaultTableModel(commodityData, commodityHeader);
		commodityTable.setModel(commodityModel);
	}

	@Override
	public JPanel getPanel() {
		return initPanel;
	}

}
