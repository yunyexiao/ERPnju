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
import javax.swing.JDialog;
import javax.swing.JPanel;

import presentation.main.MainWindow;

public class FatherWindow {

	protected JDialog frame = new JDialog();
	protected JButton yesButton = new JButton("ȷ��");
	private JButton quitButton = new JButton("ȡ��");
	
	protected FatherWindow(MainWindow mainWindow) {
		frame.setModal(true);
		
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
		
		//�رմ��������������
		class WindowCloseListener extends WindowAdapter {
			@Override
		    public void windowClosing(WindowEvent e) {
				mainWindow.setEnable(true);
			}
		}
		frame.addWindowListener(new WindowCloseListener());
	}
}
