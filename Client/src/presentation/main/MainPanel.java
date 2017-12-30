package presentation.main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.billblservice.BillOperationService;
import blservice.billblservice.BillSearchBLService;
import businesslogic.BillOperationBL;
import businesslogic.BillSearchBL;
import businesslogic.UserBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.billui.BillPanelHelper;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.dataui.userui.UpdateUserWindow;
import presentation.mailui.MailPanel;
import vo.UserType;
import vo.UserVO;
import vo.billvo.BillVO;

/**
 * �����棨���ݹ��������࣬���������<br/>
 * ���ڵ����ƶ���Ա����ʾδ��ɵĵ��ݡ���<br/>
 * �����ܾ�����ʾ�ȴ������ĵ��ݣ�������ʾ��<br/>
 * ����ϵͳ����Ա����������
 * @author Ǯ��Ե
 *
 */
public class MainPanel implements PanelInterface {

	private final double[][] size = {{0.88,0.12},{0.4, 0.6}};
	private BillSearchBLService billSearchBL;
	private BillOperationService billOperationBL;
	private JPanel panel= new JPanel(new TableLayout(size)); 
	private JPanel infoPanel = new JPanel();
	private JPanel bPanel = new JPanel();
	private JLabel infoLabel;
	private JTable table;

	public MainPanel(MainWindow mainWindow) {
		UserVO user = MainWindow.getUser();
		billSearchBL = new BillSearchBL();
		billOperationBL = new BillOperationBL();
		
		infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		String htmltxt = "<html><span style=\"font-size:32px;\">��ӭʹ�õƾ߽��������ϵͳ:</span><br/> "
				+ "<span style=\"font-size:16px;\">&emsp;&emsp;����ǰ�ĵ�¼���Ϊ��" + user.getType().getName() + "</span><br/>" 
				+ "<span style=\"font-size:16px;\">&emsp;&emsp;���������� " + user.getName() + "</span></html>";
		infoLabel = new JLabel(htmltxt);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoPanel.add(infoLabel);
		infoPanel.setOpaque(false);
		
		ActionListener closeListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.changePanel();
            }
		};
		
		bPanel.setLayout(new TableLayout(
				new double[][]{{TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL},
					{5,TableLayout.PREFERRED, 15, TableLayout.PREFERRED,TableLayout.FILL}}));
		JButton changeButton = new JButton("�޸ĸ�����Ϣ");
		JButton mailButton = new JButton("�ʼ�ϵͳ");
		changeButton.addActionListener(e->{
			new UpdateUserWindow(new UserBL(), new String[]{
					user.getId(),user.getName(),user.getType().getName(),user.getRankName(),user.getPwd(),user.getSex(),""+user.getAge(),user.getTelNumber()}, true);
		});
		mailButton.addActionListener(e->{mainWindow.changePanel(new MailPanel(closeListener));});
		bPanel.add(changeButton, "1,1");
		bPanel.add(mailButton, "1,3");
		bPanel.setOpaque(false);
		
		if (user.getType() != UserType.ADMIN) {
			MyTableModel tabelModel = billSearchBL.getBillTable(user);
			table = new JTable(tabelModel);
			table.getTableHeader().setReorderingAllowed(false);
			JScrollPane scrollPane = new JScrollPane(table);
			
			JPanel buttonPanel=new JPanel();
			JButton billChangeButton = new JButton("�޸ĵ���", new ImageIcon("resource/FreshButton.png"));
			JButton billDeleteButton = new JButton("ɾ������", new ImageIcon("resource/DeleteButton.png"));
			BillPanelHelper.user = user;
			BillPanelHelper.closeListener = closeListener;
			billChangeButton.addActionListener(e->{
				if (table.getSelectedRow() < 0) {new InfoWindow("��ѡ����Ҫ�޸ĵĵ���"); return;}
				String[] info = tabelModel.getValueAtRow(table.getSelectedRow());
				mainWindow.changePanel(BillPanelHelper.create(billOperationBL.getBillById(info[1])));
			});
			billDeleteButton.addActionListener(e->{
				if (table.getSelectedRow() < 0) {new InfoWindow("��ѡ����Ҫɾ���ĵ���"); return;}
				String info = (String) tabelModel.getValueAt(table.getSelectedRow(), 1);
				BillVO bill = billOperationBL.getBillById(info);
				if (bill.getState() == BillVO.SAVED) {
					if (billOperationBL.deleteBill(info)) {
						table.setModel(billSearchBL.getBillTable(user));
						new InfoWindow("ɾ���ɹ�");
					} else new InfoWindow("ɾ��ʧ��");
				} else {
					new InfoWindow("��״̬�����޷�ɾ��");
				}
			});
			double forthPanelSize[][]={
					{10,TableLayout.FILL,10},
					{10,30,10,30,TableLayout.FILL}
			};
			buttonPanel.setLayout(new TableLayout(forthPanelSize));
			buttonPanel.add(billChangeButton, "1,1");
			buttonPanel.add(billDeleteButton, "1,3");

			panel.add(scrollPane, "0,1");
			panel.add(buttonPanel, "1,1");
		} else {
			JPanel p = new JPanel();
			p.setBackground(Color.WHITE);
			panel.add(p, "0,1,1,1");
		}
		panel.setVisible(true);
		panel.add(infoPanel, "0,0,1,0");
		panel.add(bPanel, "1,0");
	}

	@Override
	public boolean close() {
		return true;
	}
	
	@Override
	public JPanel getPanel() {
		return panel;
	}
	
	public void setBackground() {
		ImageIcon image = new ImageIcon("resource/MainBG.jpg");   
        Image img = image.getImage();  
        img = img.getScaledInstance(infoPanel.getWidth(), infoPanel.getHeight(), Image.SCALE_DEFAULT);  
        image.setImage(img);
		panel.add(new JLabel(image), "0,0,1,0");
	}
}
