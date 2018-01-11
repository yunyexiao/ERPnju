package presentation.dataui.commodityui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import blservice.CommodityBLService;
import presentation.component.InfoWindow;
import presentation.dataui.DataPanelInterface;
import vo.MyTableModel;


public class CommodityDataPanel extends JPanel implements DataPanelInterface{
	private JTable table = new JTable();
    private CommodityBLService commodityBl;

    public CommodityDataPanel(CommodityBLService commodityBl) {
        this.commodityBl = commodityBl;
        table.setModel(commodityBl.update());
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane srcollpane = new JScrollPane(table);
		this.add(srcollpane);
    }

    @Override
    public void addAction() {
    	new AddCommodityWindow(commodityBl);
        table.setModel(commodityBl.update());
    }

    @Override
    public void updateAction() {
    	MyTableModel model = (MyTableModel)table.getModel();
        if (table.getSelectedRow() != -1) {
        	new UpdateCommodityWindow(commodityBl, model.getValueAtRow(table.getSelectedRow()));
            table.setModel(commodityBl.update());
        }
        else new InfoWindow("��ѡ����Ҫ�޸ĵ���Ʒ");
    }

    @Override
    public void searchAction() {
    	MyTableModel model = new SearchCommodityWindow(commodityBl).getModel();
        if(model != null) table.setModel(model);
    }

	@Override
	public void deleteAction() {
		int index = table.getSelectedRow();
	    if(index < 0) {new InfoWindow("��ѡ����Ҫɾ������Ʒ��Ϣ");return;}
		int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������Ʒ��Ϣ��", "��ʾ", JOptionPane.YES_NO_OPTION);
		if (response == 0) {
		    String id = (String)((MyTableModel)table.getModel()).getValueAt(index, 0);
			if (commodityBl.delete(id)) new InfoWindow("ѡ�е���Ʒ��Ϣ�ѳɹ�ɾ��");
			table.setModel(commodityBl.update());
		}
	}

}
