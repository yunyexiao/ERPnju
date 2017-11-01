package presentation.data;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blservice.CustomerBLService;
import blservice.DataBLService;
import presentation.main.MainWindow;
import vo.CustomerVO;
import vo.UserType;
/**
 * ��ͨ������ģ���Ӧ��ͨ��������Ӵ���</br>
 * ͬ������UserType�б����ͣ��������޸�
 * @author Ǯ��Ե
 *
 */
public class AddWindow {
	private JFrame frame = new JFrame();
	private JButton yesButton = new JButton("ȷ��");
	private JButton quitButton = new JButton("ȡ��");
	
	public AddWindow(MainWindow mainWindow, DataBLService dataBL) {
		//���ô����С��λ��
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width/2, screenSize.height/2);
		frame.setLocation(screenSize.width/4, screenSize.height/4);
		
		//���ý��沼��
		frame.setLayout(new BorderLayout());
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.RIGHT);
		JPanel southPanel = new JPanel(f);
		southPanel.add(yesButton);
		southPanel.add(quitButton);
		frame.add(southPanel, BorderLayout.SOUTH);
		
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.setEnable(true);
				frame.dispose();
			}			
		});
		
		//TODO ���ݲ�ͬ�û����ò�ͬ�ı���
		if (mainWindow.getUser().getType() == UserType.SALESMAN) {
			frame.setTitle("���ӿͻ�");
			yesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (((CustomerBLService) dataBL).add(new CustomerVO())) {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "�����Ϣ�ɹ�", "ϵͳ", JOptionPane.INFORMATION_MESSAGE);
						mainWindow.setEnable(true);
					}	
				}
			});
		}
		
		//�رմ��������������
		class WindowCloseListener extends WindowAdapter {
			@Override
		    public void windowClosing(WindowEvent e) {
				mainWindow.setEnable(true);
			}
		}
		frame.addWindowListener(new WindowCloseListener());
		
		frame.setVisible(true);
	}
}
