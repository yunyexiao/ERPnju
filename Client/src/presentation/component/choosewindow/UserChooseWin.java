package presentation.component.choosewindow;

import blservice.UserBLService;
import blservice.infoservice.GetUserInterface;
import businesslogic.UserBL;
import presentation.tools.TableTools;
import vo.MyTableModel;
import vo.UserVO;

public class UserChooseWin extends ChooseWindow {
    
    private UserVO data;
    private UserBLService userBl;

    public UserChooseWin() {
        super();
        frame.setTitle("ѡ���û�");
        frame.setVisible(true);
    }

    @Override
    public void init() {
        userBl = new UserBL();
        setTypes(new String[]{"���������", "����������"});
        table.setModel(userBl.update());
        TableTools.autoFit(table);
    }

    @Override
    protected void yesAction() {
        GetUserInterface userInfo = new UserBL();
        int index = table.getSelectedRow();
        if(index < 0) return;
        String id = table.getValueAt(index, 0).toString();
        data = userInfo.getUser(id);
        frame.dispose();
    }
    
    public UserVO getUser(){
        return data;
    }

    @Override
    protected void searchAction() {
        String type = searchTypeBox.getSelectedItem().toString(),
               key = keyField.getText();
        if(key.length() == 0){
            table.setModel(userBl.update());
        } else {
            table.setModel(userBl.search(type, key));
        }
    }

}
