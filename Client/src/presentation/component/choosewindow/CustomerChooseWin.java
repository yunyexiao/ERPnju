package presentation.component.choosewindow;

import blservice.CustomerBLService;
import blservice.infoservice.GetCustomerInterface;
import businesslogic.CustomerBL;
import presentation.tools.TableTools;
import vo.CustomerVO;

public class CustomerChooseWin extends ChooseWindow {
    
    private CustomerVO data;

    public CustomerChooseWin() {
        super();
    }

    @Override
    public void init() {
        CustomerBLService customerBl = new CustomerBL();
        setTypes(new String[]{"按编号搜索", "按名称搜索"});
        table.setModel(customerBl.update());
        TableTools.autoFit(table);
        frame.setTitle("选择客户");
        frame.setVisible(true);
    }

    @Override
    protected void yesAction() {
        GetCustomerInterface customerInfo = new CustomerBL();
        int index = table.getSelectedRow();
        if(index < 0) return;
        String id = (String) table.getValueAt(index, 0);
        data = customerInfo.getCustomer(id);
        frame.dispose();
    }

    public CustomerVO getCustomer(){
        return data;
    }

	@Override
	protected void searchAction() {
		// TODO Auto-generated method stub
		
	}

}
