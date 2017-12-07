package presentation.component.choosewindow;

import bl_stub.CustomerBL_stub;
import blservice.CustomerBLService;
import blservice.infoservice.GetCustomerInterface;
import presentation.tools.TableTools;
import vo.CustomerVO;

public class CustomerChooseWin extends ChooseWindow {
    
    private CustomerVO data;
    private int type;
    private CustomerBLService customerBl;

    public CustomerChooseWin(int type) {
        super();
        this.type = type;
    }

    @Override
    public void init() {
        customerBl = new CustomerBL_stub();
        setTypes(new String[]{"���������", "����������"});
        table.setModel(customerBl.search("CusType", type + ""));
        TableTools.autoFit(table);
        frame.setTitle("ѡ��ͻ�");
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

	@Override
	protected void searchAction() {
	    String type = searchTypeBox.getSelectedItem().toString(), key = keyField.getText();
	    if(key.length() == 0) {
	        table.setModel(customerBl.update());
	    } else {
	        table.setModel(customerBl.search(type, key));
	    }
	}

}
