package presentation.billui.choosewindow;

import bl_stub.CustomerBL_stub;
import blservice.CustomerBLService;
import blservice.infoservice.GetCustomerInterface;
import vo.CustomerVO;

public class CustomerChooseWin extends ChooseWindow {
    
    private CustomerVO data;

    public CustomerChooseWin() {
        super();
    }

    @Override
    public void init() {
        CustomerBLService customerBl = new CustomerBL_stub();
        setTypes(new String[]{"按编号搜索", "按名称搜索"});
        table.setModel(customerBl.update());
        FitTableColumns();
        frame.setTitle("选择客户");
        frame.setVisible(true);
    }

    @Override
    protected void yesAction() {
        GetCustomerInterface customerInfo = new CustomerBL_stub();
        int index = table.getSelectedRow();
        if(index < 0) return;
        String id = (String) table.getValueAt(index, 0);
        data = customerInfo.getCustomer(id);
        frame.dispose();
    }

    public CustomerVO getCustomer(){
        return data;
    }

}
