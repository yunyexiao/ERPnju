package presentation.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class InfoWindow {

	private JDialog frame = new JDialog();
	
	public InfoWindow(String str) {
		frame.setModal(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width/5, screenSize.height/7);
		frame.setLocation(screenSize.width*2/5, screenSize.height*3/7);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(Color.WHITE);
		
		//frame.setLayout(null);
		JLabel label = new JLabel(str, JLabel.CENTER);
		label.setFont(new Font("µÈÏß", Font.PLAIN, 25));
		frame.add(label);
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			private int count = 50;
			@Override
			public void run() {
				if (count > 0) {
					count--;
					frame.setOpacity(count * 0.02f);
				} else frame.dispose();
			}
		};
		timer.schedule(task, 700L, 10L);
		
		frame.setVisible(true);
	}
}
