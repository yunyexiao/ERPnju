package presentation.component.choosewindow;

import blservice.billblservice.SalesBillBLService;
import businesslogic.SalesBillBL;
import presentation.tools.TableTools;
import vo.billvo.SalesBillVO;

public class SalesBillChooseWin extends ChooseWindow {
    
    private String[] data;
    private SalesBillBLService salesBillBl;
    
    public SalesBillChooseWin(){
        super();
    }

    @Override
    public void init() {
        salesBillBl = new SalesBillBL();
        setTypes(new String[]{"���������", "��ʱ������"});
        table.setModel(salesBillBl.getFinishedBills());
        TableTools.autoFit(table);
        frame.setTitle("ѡ��Դ���۵�");
        frame.setVisible(true);
    }

    @Override
    protected void yesAction() {
        int index = table.getSelectedRow();
        if(index >= 0){
            String id = (String)table.getValueAt(index, 1);
            SalesBillVO bill = salesBillBl.getBill(id);
            System.out.println(bill.getBeforeDiscount());
            System.out.println(bill.getSum());
            double discountRate = bill.getSum() / bill.getBeforeDiscount();
            data = new String[]{id, String.format("%.4f", discountRate)};
            frame.dispose();
        }
    }
    
    public String[] getSalesBillInfo(){
        return data;
    }

	@Override
	protected void searchAction() {
		String type = searchTypeBox.getSelectedItem().toString()
		     , key = keyField.getText();
		if(key.length() == 0){
		    table.setModel(salesBillBl.getFinishedBills());
		} else {
		    table.setModel(salesBillBl.search(type, key));
		}
	}

}
