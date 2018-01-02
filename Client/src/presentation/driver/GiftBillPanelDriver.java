package presentation.driver;

import javax.swing.JFrame;

import presentation.billui.ViewGiftBillPanel;
import presentation.component.MyTableModel;
import presentation.tools.StyleTools;
import vo.billvo.BillVO;
import vo.billvo.GiftBillVO;

public class GiftBillPanelDriver {
    
    public GiftBillPanelDriver(){
        JFrame frame = new JFrame();
        frame.setLocation(400, 200);
        StyleTools.setNimbusLookAndFeel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] columnNames = {"���", "����", "�ͺ�", "����", "����"};
        String[][] data = {{"000001", "", "", "", ""}};
        MyTableModel gifts = new MyTableModel(data, columnNames);
        GiftBillVO bill = new GiftBillVO(
            "SPZSD-20171230-12344", "20171230", "14:12:34", "0002", 
            BillVO.PASS, gifts, "XSD-20171230-12013", "000002"
        );
        frame.setContentPane(new ViewGiftBillPanel(bill).getPanel());
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args){
        new GiftBillPanelDriver();
    }

}