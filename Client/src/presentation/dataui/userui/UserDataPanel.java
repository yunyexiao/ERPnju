package presentation.dataui.userui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blservice.UserBLService;
import presentation.component.InfoWindow;
import presentation.component.MyTableModel;
import presentation.dataui.DataPanel;

public class UserDataPanel extends DataPanel {

	private UserBLService userBL;

    public UserDataPanel(UserBLService userBL, ActionListener closeListener) {
        super(userBL, closeListener);
        this.userBL = userBL;
    }

    @Override
    protected ActionListener getAddListener() {
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserWindow(userBL);
                updateTable();
            }
            
        };
    }

    @Override
    protected ActionListener getUpdateListener() {
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MyTableModel tableModel = (MyTableModel)table.getModel();
                if (table.getSelectedRow() != -1) {
                	new UpdateUserWindow(userBL, tableModel.getValueAtRow(table.getSelectedRow()));
                	updateTable();
                } else new InfoWindow("请选择需要修改的用户");
            }
        };
    }
    
    @Override
    protected ActionListener getSearchListener() {
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	MyTableModel model = new SearchUserWindow(userBL).getModel();
            	if(model != null)table.setModel(model);
            }
        };
    }
}
