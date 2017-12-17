package presentation.initui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import blservice.InitBLService;
import layout.TableLayout;

public class InitChooseWin{

	private JDialog frame = new JDialog();
	private String year = null;

	public InitChooseWin(InitBLService initBL) {
		frame.setModal(true);
		frame.setSize(400, 150);
		frame.setLocation(400, 250);
		frame.setTitle("ѡ���������");
		frame.setLayout(new TableLayout(new double[][]{{TableLayout.FILL,TableLayout.PREFERRED,TableLayout.FILL},
			{15,TableLayout.PREFERRED,10,TableLayout.PREFERRED,10,TableLayout.PREFERRED,15}}));
		frame.setResizable(false);
		
		JComboBox<String> chooseBox = new JComboBox<String>(initBL.getInitInfo());
		JButton yesButton = new JButton("ȷ��");
		yesButton.addActionListener(e -> {year = (String) chooseBox.getSelectedItem();frame.dispose();});
		frame.add(new JLabel("��ѡ���ڳ���Ϣ�Ŀ�ʼʱ�䣺"), "1 1");
		frame.add(chooseBox, "1 3");
		frame.add(yesButton, "1 5");
		
		frame.setVisible(true);
	}
	
	public String getYear() {
		return year;
	}
}
