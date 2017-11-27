package presentation.dataui.userui;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import blservice.UserBLService;
import presentation.dataui.SearchWindow;

public class SearchUserWindow extends SearchWindow{
    
    public SearchUserWindow(UserBLService userBl) {
        super(userBl);
    }

    @Override
    protected ButtonGroup initTypeGroup() {
        JRadioButton idRadioButton = new JRadioButton("°´±àºÅËÑË÷");
        JRadioButton nameRadioButton = new JRadioButton("°´Ãû³ÆËÑË÷");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(idRadioButton);
        typeGroup.add(nameRadioButton);
        return typeGroup;
    }
   
}
