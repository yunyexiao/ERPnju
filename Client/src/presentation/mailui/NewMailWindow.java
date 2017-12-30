package presentation.mailui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import presentation.component.IdNamePanel;

public class NewMailWindow {
	private JDialog frame = new JDialog();
	private String[] input = null;
	
	public NewMailWindow() {
		frame.setModal(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width/3, screenSize.height/3);
		frame.setLocation((screenSize.width-frame.getWidth())/2, (screenSize.height-frame.getHeight())/2);
		frame.setResizable(false);
		frame.setTitle("д�ʼ�");
		
		IdNamePanel idPanel = new IdNamePanel("�û�", 5, 10);
		TextField textField = new TextField();
		textField.setFont(new Font("����", Font.PLAIN, 16));
		frame.setLayout(new BorderLayout());
		frame.add(idPanel, BorderLayout.NORTH);
		frame.add(new JLabel("����"), BorderLayout.WEST);
		frame.add(textField, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton yesButton = new JButton("����");
		yesButton.addActionListener(e->{input = new String[]{idPanel.getId(), textField.getText()};frame.dispose();});
		JButton quitButton = new JButton("ȡ��");
		quitButton.addActionListener(e->frame.dispose());
		southPanel.add(yesButton);
		southPanel.add(quitButton);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	
	public String[] getMessage() {
		return input;
	}
}
