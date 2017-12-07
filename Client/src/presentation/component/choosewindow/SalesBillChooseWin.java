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
        setTypes(new String[]{"按编号搜索", "按时间搜索"});
        table.setModel(salesBillBl.getFinishedBills());
        TableTools.autoFit(table);
        frame.setTitle("选择源销售单");
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
		// TODO Auto-generated method stub
		
	}

}
