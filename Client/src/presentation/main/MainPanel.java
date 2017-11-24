package presentation.main;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bl_stub.BillBL_stub;
import blservice.BillBLService;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.MyTableModel;
import vo.UserVO;

/**
 * �����棨���ݹ��������࣬���������<br/>
 * ���ڵ����ƶ���Ա����ʾδ��ɵĵ��ݡ���<br/>
 * �����ܾ�����ʾ�ȴ������ĵ��ݣ�������ʾ��<br/>
 * ����ϵͳ����Ա����������
 * @author Ǯ��Ե
 *
 */
public class MainPanel implements PanelInterface {
	//private MainWindow mainWindow;
	private final double[][] size = {{0.88,0.12},{0.4, 0.6}};
	private BillBLService billBL;
	private JPanel panel= new JPanel(new TableLayout(size)); 
	private JPanel infoPanel = new JPanel();
	private JLabel infoLabel;
	private JTable table;

	public MainPanel(MainWindow mainWindow) {
		UserVO user = mainWindow.getUser();
		billBL = new BillBL_stub(user);
		
		infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		String htmltxt = "<html><span style=\"font-size:32px;\"><em>��ӭʹ�õƾ߽��������ϵͳ:</em></span><br/> "
				+ "<span style=\"font-size:20px;\">����ǰ�ĵ�¼���Ϊ��" + user.getType().getName() + "</span><br/>" 
				+ "<span style=\"font-size:20px;\">���������� " + user.getName() + "</span></html>";
		infoLabel = new JLabel(htmltxt);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoPanel.add(infoLabel);
		
		//MainPanelTable model = new MainPanelTable(billBL.getBillInfo());
		MyTableModel model = billBL.getBillInfo();
		model.setEditable(new int[]{0,1});
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		
		JPanel buttonPanel=new JPanel();
		JButton billChangeButton = new JButton("�޸ĵ���", new ImageIcon("resource/FreshButton.png"));
		JButton billDeleteButton = new JButton("ɾ������", new ImageIcon("resource/DeleteButton.png"));
		double forthPanelSize[][]={
				{10,TableLayout.FILL,10},
				{10,30,10,30,TableLayout.FILL},
		};
		buttonPanel.setLayout(new TableLayout(forthPanelSize));
		buttonPanel.add(billChangeButton, "1,1");
		buttonPanel.add(billDeleteButton, "1,3");
		
		panel.setVisible(true);
		panel.add(infoPanel, "0,0,1,0");
		panel.add(scrollPane, "0,1");
		panel.add(buttonPanel, "1,1");
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
