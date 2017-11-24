package presentation.bill;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bl_stub.BillBL_stub;
import blservice.BillBLService;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.CloseListener;
import presentation.component.Listener_stub;
import presentation.component.TopButtonPanel;
import presentation.main.MainWindow;

public class BillExaminePanel implements PanelInterface {

	private static double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
	private JPanel panel = new JPanel(new TableLayout(size));
	private BillBLService billBL;
	
	public BillExaminePanel(MainWindow mainWindow) {
		billBL = new BillBL_stub(mainWindow.getUser());
		
		TopButtonPanel buttonPanel = new TopButtonPanel();
		buttonPanel.addButton("²é¿´", new ImageIcon("resource/SearchData.png"), new Listener_stub());
		buttonPanel.addButton("ÉóºË", new ImageIcon("resource/Examine.png"), new Listener_stub());
		buttonPanel.addButton("ºì³å", new ImageIcon("resource/Offset.png"), new Listener_stub());
		buttonPanel.addButton("ºì³å²¢¸´ÖÆ", new ImageIcon("resource/OffsetCopy.png"), new Listener_stub());
		buttonPanel.addButton("¹Ø±Õ", new ImageIcon("resource/Close.png"), new CloseListener(mainWindow));
		panel.add(buttonPanel.getPanel(), "0,0");
		
		JTable table = new JTable(billBL.getBillInfo());
		JScrollPane scrollPane = new JScrollPane(table);
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
