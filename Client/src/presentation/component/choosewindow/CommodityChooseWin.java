package presentation.component.choosewindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import blservice.CommodityBLService;
import blservice.infoservice.GetCommodityInterface;
import businesslogic.CommodityBL;
import presentation.tools.TableTools;
import vo.CategoryVO;
import vo.CommodityVO;

public class CommodityChooseWin extends ChooseWindow {
	
	private CommodityVO data;
	private CommodityBLService commodityBL;

	public CommodityChooseWin() {
		super();
	}

	@Override
	public void init() {
		commodityBL = new CommodityBL();
		setTypes(new String[]{"���������", "����������", "����������"});
		table.setModel(commodityBL.update());
		TableTools.autoFit(table);
		searchTypeBox.addItemListener(e -> keyField.setEditable(searchTypeBox.getSelectedIndex()!=2));
		keyField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
        		CategoryVO category = new CategoryChooseWin().getCategory();
        		if (category != null) {
        			keyField.setText(category.getName());
        		}
        	}
		});
		
		frame.setTitle("ѡ����Ʒ");
		frame.setVisible(true);
	}

	@Override
	protected void yesAction() {
		GetCommodityInterface commodityInfo = new CommodityBL();
		if (table.getSelectedRow() != -1) {
			data = commodityInfo.getCommodity((String) table.getValueAt(table.getSelectedRow(), 0));
			frame.dispose();
		}
	}

	public CommodityVO getCommodity() {
		return data;
	}

	@Override
	protected void searchAction() {
		if ("".equals(keyField.getText())) table.setModel(commodityBL.update());
		else if ("���������".equals(searchTypeBox.getSelectedItem())) table.setModel(commodityBL.search("���������", keyField.getText()));
		else if ("����������".equals(searchTypeBox.getSelectedItem())) table.setModel(commodityBL.search("����������", keyField.getText()));
		else if ("����������".equals(searchTypeBox.getSelectedItem())) table.setModel(commodityBL.search("����������", keyField.getText()));
		TableTools.autoFit(table);
	}
}
