package presentation.dataui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import businesslogic.CommodityBL;
import presentation.dataui.commodityui.CommodityDataPanel;

public class DriverDataPanel {

    public DriverDataPanel() {}
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setLocation(200, 150);
        frame.setSize(800, 400);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                exit(frame);
            }
        });
        frame.add(new CommodityDataPanel(new CommodityBL(), e->exit(frame)).getPanel());
        frame.setVisible(true);
        frame.setTitle("≤‚ ‘");
    }
    
    public static void exit(JFrame frame){
        frame.dispose();
        System.exit(0);
    }

}
